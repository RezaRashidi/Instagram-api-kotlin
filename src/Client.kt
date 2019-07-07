

package InstagramAPI

//import GuzzleHttp.Client as GuzzleClient
//import GuzzleHttp.Cookie.CookieJar
//import GuzzleHttp.Cookie.SetCookie
//import GuzzleHttp.HandlerStack
import InstagramAPI.Exception.InstagramException
import InstagramAPI.Exception.LoginRequiredException
import InstagramAPI.Exception.ServerMessageThrower
import InstagramAPI.Middleware.FakeCookies
import InstagramAPI.Middleware.ZeroRating
//import LazyJsonMapper.Exception.LazyJsonMapperException
//import com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time
//import okhttp3.CookieJar
//import Psr.Http.Message.RequestInterface as HttpRequestInterface
//import Psr.Http.Message.ResponseInterface as HttpResponseInterface
//import fun GuzzleHttp.Psr7.modify_request

/**
 * This class handles core API network communication.
 *
 * WARNING TO CONTRIBUTORS: This class is a wrapper for the HTTP client, and
 * handles raw networking, cookies, HTTP requests and responses. Don"t put
 * anything related to high level API funs (such as file uploads) here.
 * Most of the higher level code belongs in either the Request class or in the
 * individual endpoint funs.
 *
 * @author mgp25: Founder, Reversing, Project Leader (https://github.com/mgp25)
 * @author SteveJobzniak (https://github.com/SteveJobzniak)
 */
class Client
{
    /**
     * How frequently we"re allowed to auto-save the cookie jar, in seconds.
     *
     * @var int
     */
    val COOKIE_AUTOSAVE_INTERVAL = 45

    /**
     * The Instagram class instance we belong to.
     *
     * @var .InstagramAPI.Instagram
     */
    protected var _parent: Instagram

    /**
     * What user agent to identify our client as.
     *
     * @var string
     */
    protected var _userAgent: String

    /**
     * The SSL certificate verification behavior of requests.
     *
     * @see http://docs.guzzlephp.org/en/latest/request-options.html#verify
     *
     * @var bool|string
     */
    protected var _verifySSL

    /**
     * Proxy to import for all requests. Optional.
     *
     * @see http://docs.guzzlephp.org/en/latest/request-options.html#proxy
     *
     * @var string|array|null
     */
    protected var _proxy

    /**
     * Network interface override to use.
     *
     * Only works if Guzzle is using the cURL backend. But that"s
     * almost always the case, on most PHP installations.
     *
     * @see http://php.net/curl_setopt CURLOPT_INTERFACE
     *
     * @var string|null
     */
    protected var _outputInterface: String?

    /**
     * @var .GuzzleHttp.Client
     */
    private var _guzzleClient: GuzzleHttp.Client

    /**
     * @var .InstagramAPI.Middleware.FakeCookies
     */
    private var _fakeCookies: FakeCookies

    /**
     * @var .InstagramAPI.Middleware.ZeroRating
     */
    private var _zeroRating: ZeroRating

    /**
     * @var .GuzzleHttp.Cookie.CookieJar
     */
    private var _cookieJar: GuzzleHttp.Cookie.CookieJar

    /**
     * The timestamp of when we last saved our cookie jar to disk.
     *
     * Used for automatically saving the jar after any API call, after enough
     * time has elapsed since our last save.
     *
     * @var int
     */
    private var _cookieJarLastSaved: Int

    /**
     * The flag to force cURL to reopen a fresh connection.
     *
     * @var bool
     */
    private var _resetConnection: Boolean

//    private var _parent: Instagram
//    private var _verifySSL: Instagram


    /**
     * Constructor.
     *
     * @param .InstagramAPI.Instagram $parent
     */
    constructor(parent:Instagram):this
    {
        _parent = parent

        // Defaults.
        _verifySSL = true
        _proxy = null

        // Create a default handler stack with Guzzle"s auto-selected "best
        // possible transfer handler for the user"s system", and with all of
        // Guzzle"s default middleware (cookie jar support, etc).
        var stack = HandlerStack.create()

        // Create our cookies middleware and add it to the stack.
        _fakeCookies = FakeCookies()
        stack.push(_fakeCookies, "fake_cookies")

        _zeroRating = ZeroRating()
        stack.push(_zeroRating, "zero_rewrite")

        // Default request options (immutable after client creation).
        _guzzleClient = GuzzleClient(
            mapOf(
                "handler"         to stack, // Our middleware is now injected.
                "allow_redirects" to (
                            "max" to 8 // Allow up to eight redirects (that"s plenty).
                        ),
                "connect_timeout" to 30.0, // Give up trying to connect after 30s.
                "decode_content"  to true, // Decode gzip/deflate/etc HTTP responses.
                "timeout"         to 240.0, // Maximum per-request time (seconds).
                // Tells Guzzle to stop throwing exceptions on non-"2xx" HTTP codes,
                // thus ensuring that it only triggers exceptions on socket errors!
                // We"ll instead MANUALLY be throwing on certain other HTTP codes.
                "http_errors"     to false
            )
        )

        _resetConnection = false
    }

    /**
     * Resets certain Client settings via the current Settings storage.
     *
     * Used whenever we switch active user, to configure our internal state.
     *
     * @param bool $resetCookieJar (optional) Whether to clear current cookies.
     *
     * @throws .InstagramAPI.Exception.SettingsException
     */
    fun updateFromCurrentSettings(resetCookieJar: Boolean = false){
        // Update our internal client state from the user"s settings.
        _userAgent = _parent.device.getUserAgent()
        loadCookieJar(resetCookieJar)

        // Verify that the jar contains a non-expired csrftoken for the API
        // domain. Instagram gives us a 1-year csrftoken whenever we log in.
        // If it"s missing, we"re definitely NOT logged in! But even if all of
        // these checks succeed, the cookie may still not be valid. It"s just a
        // preliminary check to detect definitely-invalid session cookies!
        if (this.getToken() === null) {
            _parent.isMaybeLoggedIn = false
        }

        // Load rewrite rules (if any).
        zeroRating().update(_parent.settings.getRewriteRules())
    }

    /**
     * Loads all cookies via the current Settings storage.
     *
     * @param bool $resetCookieJar (optional) Whether to clear current cookies.
     *
     * @throws .InstagramAPI.Exception.SettingsException
     */
    fun loadCookieJar(resetCookieJar: Boolean = false){
        // Mark any previous cookie jar for garbage collection.
        _cookieJar = null

        // Delete all current cookies from the storage if this is a reset.
        if (resetCookieJar) {
            _parent.settings.setCookies("")
        }

        // Get all cookies for the currently active user.
        var cookieData = _parent.settings.getCookies()

        // Attempt to restore the cookies, otherwise create a new, empty jar.
        var restoredCookies = if(cookieData is String) @json_decode(cookieData, true) else null
        if (!is_array(restoredCookies)) {
            restoredCookies = []
        }

        // Memory-based cookie jar which must be manually saved later.
        _cookieJar = CookieJar(false, restoredCookies)

        // Reset the "last saved" timestamp to the current time to prevent
        // auto-saving the cookies again immediately after this jar is loaded.
        _cookieJarLastSaved = time()
    }

    /**
     * Retrieve the CSRF token from the current cookie jar.
     *
     * Note that Instagram gives you a 1-year token expiration timestamp when
     * you log in. But if you log out, they set its timestamp to "0" which means
     * that the cookie is "expired" and invalid. We ignore token cookies if they
     * have been logged out, or if they have expired naturally.
     *
     * @return string|null The token if found and non-expired, otherwise NULL.
     */
    fun getToken(): String? {
        val cookie = getCookie("csrftoken", "i.instagram.com")
        if (cookie === null || cookie.getValue() === "") {
            return null
        }

        return cookie.getValue()
    }

    /**
     * Searches for a specific cookie in the current jar.
     *
     * @param string      $name   The name of the cookie.
     * @param string|null $domain (optional) Require a specific domain match.
     * @param string|null $path   (optional) Require a specific path match.
     *
     * @return .GuzzleHttp.Cookie.SetCookie|null A cookie if found and non-expired, otherwise NULL.
     */
    fun getCookie( name: String, domain: String ?= null, path: String ?= null){
        var foundCookie = null
        if (_cookieJar instanceof CookieJar) {
            /** @var SetCookie $cookie */
            for (cookie in this._cookieJar.getIterator()) {
                if (cookie.getName() === name
                    && !cookie.isExpired()
                    && (domain === null || cookie.matchesDomain(domain))
                    && (path === null || cookie.matchesPath(path))) {
                    // Loop-"break" is omitted intentionally, becaimport we might
                    // have more than one cookie with the same name, so we will
                    // return the LAST one. This is necessary becaimport Instagram
                    // has changed their cookie domain from `i.instagram.com` to
                    // `.instagram.com` and we want the *most recent* cookie.
                    // Guzzle"s `CookieJar::setCookie()` always places the most
                    // recently added/modified cookies at the *end* of array.
                    foundCookie = cookie
                }
            }
        }

        return foundCookie
    }

    /**
     * Gives you all cookies in the Jar encoded as a JSON string.
     *
     * This allows custom Settings storages to retrieve all cookies for saving.
     *
     * @throws  IllegalArgumentException If the JSON cannot be encoded.
     *
     * @return string
     */
    fun getCookieJarAsJSON(): String{
        if (!_cookieJar instanceof CookieJar) {
            return "[]"
        }

        // Gets ALL cookies from the jar, even temporary session-based cookies.
        val cookies = _cookieJar.toArray()

        // Throws if data can"t be encoded as JSON (will never happen).

        return GuzzleHttp.json_encode(cookies)
    }

    /**
     * Tells current settings storage to store cookies if necessary.
     *
     * NOTE: This Client class is NOT responsible for calling this fun!
     * Instead, our parent "Instagram" instance takes care of it and saves the
     * cookies "onCloseUser", so that cookies are written to storage in a
     * single, efficient write when the user"s session is finished. We also call
     * it during some important fun calls such as login/logout. Client also
     * automatically calls it when enough time has elapsed since last save.
     *
     * @throws  IllegalArgumentException                 If the JSON cannot be encoded.
     * @throws .InstagramAPI.Exception.SettingsException
     */
    fun saveCookieJar(){
        // Tell the settings storage to persist the latest cookies.
        val newCookies = getCookieJarAsJSON()
        _parent.settings.setCookies(newCookies)

        // Reset the "last saved" timestamp to the current time.
        _cookieJarLastSaved = time()
    }

    /**
     * Controls the SSL verification behavior of the Client.
     *
     * @see http://docs.guzzlephp.org/en/latest/request-options.html#verify
     *
     * @param bool|string $state TRUE to verify using PHP"s default CA bundle,
     *                           FALSE to disable SSL verification (this is
     *                           insecure!), String to verify using this path to
     *                           a custom CA bundle file.
     */
    fun setVerifySSL(state: String ?= null)
    {
        _verifySSL = state
    }

    /**
     * Gets the current SSL verification behavior of the Client.
     *
     * @return bool|string
     */
    fun getVerifySSL(){
        return _verifySSL
    }

    /**
     * Set the proxy to import for requests.
     *
     * @see http://docs.guzzlephp.org/en/latest/request-options.html#proxy
     *
     * @param string|array|null $value String or Array specifying a proxy in
     *                                 Guzzle format, or NULL to disable proxying.
     */
    fun setProxy(value){
        _proxy = value
        _resetConnection = true
    }

    /**
     * Gets the current proxy used for requests.
     *
     * @return string|array|null
     */
    fun getProxy(){
        return _proxy
    }

    /**
     * Sets the network interface override to use.
     *
     * Only works if Guzzle is using the cURL backend. But that"s
     * almost always the case, on most PHP installations.
     *
     * @see http://php.net/curl_setopt CURLOPT_INTERFACE
     *
     * @param string|null $value Interface name, IP address or hostname, or NULL to
     *                           disable override and let Guzzle import any interface.
     */
    fun setOutputInterface(value: String?){
        _outputInterface = value
        _resetConnection = true
    }

    /**
     * Gets the current network interface override used for requests.
     *
     * @return string|null
     */
    fun getOutputInterface(): String?{
        return _outputInterface
    }

    /**
     * Output debugging information.
     *
     * @param (string)              $method        "GET" or "POST".
     * @param (string)              $url           The URL or endpoint used for the request.
     * @param (string)|null         $uploadedBody  What was sent to the server. import NULL to
     *                                             avoid displaying it.
     * @param (int)|null            $uploadedBytes How many bytes were uploaded. import NULL to
     *                                             avoid displaying it.
     * @param (HttpResponseInterface)$response      The Guzzle response object from the request.
     * @param (string)               $responseBody  The actual text-body reply from the server.
     */
    protected fun _printDebug(method: String, url: String, uploadedBody: String?, uploadedBytes: Int?,
        response: HttpResponseInterface, responseBody: String){
        Debug.printRequest(method, url)

        // Display the data body that was uploaded, if provided for debugging.
        // NOTE: Only provide this from funs that submit meaningful BODY data!
        if (uploadedBody is String) {
            Debug.printPostData(uploadedBody)
        }

        // Display the number of bytes uploaded in the data body, if provided for debugging.
        // NOTE: Only provide this from funs that actually upload files!
        if (uploadedBytes !== null) {
            Debug.printUpload(Utils.formatBytes(uploadedBytes))
        }

        // Display the number of bytes received from the response, and status code.
        val bytes = when {
            response.hasHeader("x-encoded-content-length") -> Utils.formatBytes( response.getHeaderLine("x-encoded-content-length").toInt() )
            response.hasHeader("Content-Length")           -> Utils.formatBytes( response.getHeaderLine("Content-Length").toInt() )
            else -> 0.toString()
        }
        Debug.printHttpCode(response.getStatusCode(), bytes)

        // Display the actual API response body.
        Debug.printResponse(responseBody, _parent.truncatedDebug)
    }

    /**
     * Maps a server response onto a specific kind of result object.
     *
     * The result is placed directly inside `$responseObject`.
     *
     * @param Response              $responseObject An instance of a class object whose
     *                                              properties to fill with the response.
     * @param string                $rawResponse    A raw JSON response string
     *                                              from Instagram"s server.
     * @param HttpResponseInterface $httpResponse   HTTP response object.
     *
     * @throws InstagramException In case of invalid or failed API response.
     */
    fun mapServerResponse(responseObject: Response, rawResponse: String, httpResponse: HttpResponseInterface ){
        // Attempt to decode the raw JSON to an array.
        // Important: Special JSON decoder which handles 64-bit numbers!
        var jsonArray = api_body_decode(rawResponse, true)

        // If the server response is not an array, it means that JSON decoding
        // failed or some other bad thing happened. So analyze the HTTP status
        // code (if available) to see what really happened.
        if (!is_array(jsonArray)) {
            var httpStatusCode = if (httpResponse !== null) httpResponse.getStatusCode() else null
            when (httpStatusCode) {
                400 -> {
                    throw InstagramAPI.Exception.BadRequestException("Invalid request options.")
                }
                404 -> {
                    throw InstagramAPI.Exception.NotFoundException("Requested resource does not exist.")
                }
                else -> {
                    throw InstagramAPI.Exception.EmptyResponseException("No response from server. Either a connection or configuration error.")
                }
            }
        }

        // Perform mapping of all response properties.
        try {
            // Assign the object data. Only throws if custom _init() fails.
            // NOTE: False = assign data without automatic analysis.
            responseObject.assignObjectData(jsonArray, false) // Throws.

            // import API developer debugging? We"ll throw if class lacks property
            // definitions, or if they can"t be mapped as defined in the class
            // property map. But we"ll ignore missing properties in our custom
            // UnpredictableKeys containers, since those ALWAYS lack keys. -)
            if (_parent.apiDeveloperDebug) {
                // Perform manual analysis (so that we can intercept its analysis result).
                var analysis = responseObject.exportClassAnalysis() // Never throws.

                // Remove all "missing_definitions" errors for UnpredictableKeys containers.
                // NOTE: We will keep any "bad_definitions" errors for them.
                for ((className, x) in analysis.missing_definitions) {
                    if (className.indexOf("..Response..Model..UnpredictableKeys..") !== false) {
                        unset(analysis.missing_definitions[className])
                    }
                }

                // If any problems remain after that, throw with all combined summaries.
                if (analysis.hasProblems()) {
                    throw LazyJsonMapperException(
                        analysis.generateNiceSummariesAsString()
                    )
                }
            }
        } catch (e: LazyJsonMapperException) {
            // Since there was a problem, let"s help our developers by
            // displaying the server"s JSON data in a human-readable format,
            // which makes it easy to see the structure and necessary changes
            // and speeds up the job of updating responses and models.
            try {
                // Decode to stdClass to properly preserve empty objects `{}`,
                // otherwise they would appear as empty `[]` arrays in output.
                // NOTE: Large >32-bit numbers will be transformed into strings,
                // which helps us see which numeric values need "string" type.
                var jsonObject = this.api_body_decode(rawResponse, false)
                if (is_object(jsonObject)) {
                    var prettyJson = @json_encode(
                        jsonObject,
                        JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE
                    )
                    if (prettyJson !== false) {
                        Debug.printResponse(
                            "Human-Readable Response:" + PHP_EOL.prettyJson,
                            false // Not truncated.
                        )
                    }
                }
            } catch (e: Exception) {
                // Ignore errors.
            }

            // Exceptions will only be thrown if API developer debugging is
            // enabled and finds a problem. Either way, we should re-wrap the
            // exception to our native type instead. The message gives enough
            // details and we don"t need to know the exact Lazy sub-exception.
            throw InstagramException(e.getMessage())
        }

        // Save the HTTP response object as the "getHttpResponse()" value.
        responseObject.setHttpResponse(httpResponse)

        // Throw an exception if the API response was unsuccessful.
        // NOTE: It will contain the full server response object too, which
        // means that the user can look at the full response details via the
        // exception itself.
        if (!responseObject.isOk()) {
            var message = if (responseObject instanceof InstagramAPI.Response.DirectSendItemResponse && responseObject.getPayload() !== null) {
                responseObject.getPayload().getMessage()
            } else {
                responseObject.getMessage()
            }

            try {
                ServerMessageThrower.autoThrow(
                    get_class(responseObject),
                    message,
                    responseObject,
                    httpResponse
                )
            } catch (e: LoginRequiredException) {
                // Instagram told us that our session is invalid (that we are
                // not logged in). Update our cached "logged in?" state. This
                // ensures that users with various retry-algorithms won"t hammer
                // their server. When this flag is false, ALL further attempts
                // at AUTHENTICATED requests will be aborted by our library.
                _parent.isMaybeLoggedIn = false

                throw e // Re-throw.
            }
        }
    }

    /**
     * Helper which builds in the most important Guzzle options.
     *
     * Takes care of adding all critical options that we need on every request.
     * Such as cookies and the user"s proxy. But don"t call this fun
     * manually. It"s automatically called by _guzzleRequest()!
     *
     * @param array $guzzleOptions The options specific to the current request.
     *
     * @return array A guzzle options array.
     */
    protected fun _buildGuzzleOptions(array guzzleOptions = []){
        val criticalOptions = mapOf(
            "cookies" to (if(_cookieJar instanceof CookieJar) _cookieJar else false),
            "verify"  to _verifySSL,
            "proxy"   to (if(_proxy !== null) _proxy else null)
        )

        // Critical options always overwrite identical keys in regular opts.
        // This ensures that we can"t screw up the proxy/verify/cookies.
        var finalOptions = array_merge(guzzleOptions, criticalOptions)

        // Now merge any specific Guzzle cURL-backend overrides. We must do this
        // separately since it"s in an associative array and we can"t just
        // overwrite that whole array in case the caller had curl options.
        if (!array_key_exists("curl", finalOptions)) {
            finalOptions["curl"] = []
        }

        // Add their network interface override if they want it.
        // This option MUST be non-empty if set, otherwise it breaks cURL.
        if (_outputInterface is String && _outputInterface !== "") {
            finalOptions["curl"][CURLOPT_INTERFACE] = _outputInterface
        }
        if (_resetConnection) {
            finalOptions["curl"][CURLOPT_FRESH_CONNECT] = true
            _resetConnection = false
        }

        return finalOptions
    }

    /**
     * Wraps Guzzle"s request and adds special error handling and options.
     *
     * Automatically throws exceptions on certain very serious HTTP errors. And
     * re-wraps all Guzzle errors to our own internal exceptions instead. You
     * must ALWAYS import this (or _apiRequest()) instead of the raw Guzzle Client!
     * However, you can never assume the server response contains what you
     * wanted. Be sure to validate the API reply too, since Instagram"s API
     * calls themselves may fail with a JSON message explaining what went wrong.
     *
     * WARNING: This is a semi-lowlevel handler which only applies critical
     * options and HTTP connection handling! Most funs will want to call
     * _apiRequest() instead. An even higher-level handler which takes care of
     * debugging, server response checking and response decoding!
     *
     * @param HttpRequestInterface $request       HTTP request to send.
     * @param array                $guzzleOptions Extra Guzzle options for this request.
     *
     * @throws .InstagramAPI.Exception.NetworkException                For any network/socket related errors.
     * @throws .InstagramAPI.Exception.ThrottledException              When we"re throttled by server.
     * @throws .InstagramAPI.Exception.RequestHeadersTooLargeException When request is too large.
     *
     * @return HttpResponseInterface
     */
    protected fun _guzzleRequest( request: HttpRequestInterface, array guzzleOptions = []){
        // Add critically important options for authenticating the request.
        val guzzleOptions = _buildGuzzleOptions(guzzleOptions)

        // Attempt the request. Will throw in case of socket errors!
        try {
            var response = _guzzleClient.send(request, guzzleOptions)
        } catch (e: Exception) {
            // Re-wrap Guzzle"s exception using our own NetworkException.
            throw InstagramAPI.Exception.NetworkException(e)
        }

        // Detect very serious HTTP status codes in the response.
        var httpCode = response.getStatusCode()
        // "429 Too Many Requests"
        // "431 Request Header Fields Too Large"
        when (httpCode) {
            429 -> throw InstagramAPI.Exception.ThrottledException("Throttled by Instagram becaimport of too many API requests.")
            431 -> throw InstagramAPI.Exception.RequestHeadersTooLargeException("The request start-line and/or headers are too large to process.")
            // WARNING: Do NOT detect 404 and other higher-level HTTP errors here,
            // since we catch those later during steps like mapServerResponse()
            // and autoThrow. This is a warning to future contributors!
        }

        // We"ll periodically auto-save our cookies at certain intervals. This
        // complements the "onCloseUser" and "login()/logout()" force-saving.
        if ((time() - _cookieJarLastSaved) > COOKIE_AUTOSAVE_INTERVAL) {
            saveCookieJar()
        }

        // The response may still have serious but "valid response" errors, such
        // as "400 Bad Request". But it"s up to the CALLER to handle those!
        return response
    }

    /**
     * Internal wrapper around _guzzleRequest().
     *
     * This takes care of many common additional tasks needed by our library,
     * so you should try to always import this instead of the raw _guzzleRequest()!
     *
     * Available library options are:
     * - "noDebug": Can be set to TRUE to forcibly hide debugging output for
     *   this request. The user controls debugging globally, but this is an
     *   override that prevents them from seeing certain requests that you may
     *   not want to trigger debugging (such as perhaps individual steps of a
     *   file upload process). However, debugging SHOULD be allowed in MOST cases!
     *   So only import this feature if you have a very good reason.
     * - "debugUploadedBody": Set to TRUE to make debugging display the data that
     *   was uploaded in the body of the request. DO NOT import this if your fun
     *   uploaded binary data, since printing those bytes would kill the terminal!
     * - "debugUploadedBytes": Set to TRUE to make debugging display the size of
     *   the uploaded body data. Should ALWAYS be TRUE when uploading binary data.
     *
     * @param HttpRequestInterface $request        HTTP request to send.
     * @param array                $guzzleOptions  Extra Guzzle options for this request.
     * @param array                $libraryOptions Additional options for controlling Library features
     *                                             such as the debugging output.
     *
     * @throws .InstagramAPI.Exception.NetworkException   For any network/socket related errors.
     * @throws .InstagramAPI.Exception.ThrottledException When we"re throttled by server.
     *
     * @return HttpResponseInterface
     */
    protected fun _apiRequest(request: HttpRequestInterface, guzzleOptions: List<String> = listOf<String>(), libraryOptions: Map<String,
            Boolean> =
        mapOf())
    {
        // Perform the API request and retrieve the raw HTTP response body.
        val guzzleResponse = _guzzleRequest(request, guzzleOptions)
        var uploadedBody: String?

        // Debugging (must be shown before possible decoding error).
        if (_parent.debug!! && (libraryOptions["noDebug"].isBlank() || !libraryOptions["noDebug"])) {
            // Determine whether we should display the contents of the UPLOADED body.
            if (!(libraryOptions["debugUploadedBody"].isBlank()) && libraryOptions["debugUploadedBody"]) {
                uploadedBody = request.getBody().toString()
                if (uploadedBody.isEmpty()) {
                    uploadedBody = null
                }
            } else {
                uploadedBody = null // Don"t display.
            }

            // Determine whether we should display the size of the UPLOADED body.
            val uploadedBytes = if (!(libraryOptions["debugUploadedBytes"].isBlank()) && libraryOptions["debugUploadedBytes"]) {
                // Calculate the uploaded bytes by looking at request"s body size, if it exists.
                request.getBody().getSize()
            } else {
                null // Don"t display.
            }

            _printDebug(
                request.getMethod(),
                _zeroRating.rewrite( request.getUri() ).toString(),
                uploadedBody,
                uploadedBytes,
                guzzleResponse,
                guzzleResponse.getBody().toString()
            )
        }

        return guzzleResponse
    }

    /**
     * Perform an Instagram API call.
     *
     * @param HttpRequestInterface $request       HTTP request to send.
     * @param array                $guzzleOptions Extra Guzzle options for this request.
     *
     * @throws InstagramException
     *
     * @return HttpResponseInterface
     */
    fun api( requestRE: HttpRequestInterface,  guzzleOptions: MutableList<String> = mutableListOf<String>()){
        // Set up headers that are required for every request.
        val request = modify_request(requestRE, (
            "set_headers" to (
                "User-Agent"       to _userAgent,
                // Keep the API"s HTTPS connection alive in Guzzle for future
                // re-use, to greatly speed up all further queries after this.
                "Connection"       to "Keep-Alive",
                "X-FB-HTTP-Engine" to Constants.X_FB_HTTP_Engine,
                "Accept"           to "*/*",
                "Accept-Encoding"  to Constants.ACCEPT_ENCODING,
                "Accept-Language"  to Constants.ACCEPT_LANGUAGE
            )
        )

        // Check the Content-Type header for debugging.
        val contentType = request.getHeader("Content-Type")
        val isFormData = contentType.count() && reset(contentType) === Constants.CONTENT_TYPE

        // Perform the API request.
        val response = _apiRequest(request, guzzleOptions, mapOf<String,Boolean>(
            "debugUploadedBody"  to isFormData,
            "debugUploadedBytes" to !isFormData
        ))

        return response
    }

    /**
     * Decode a JSON reply from Instagram"s API.
     *
     * WARNING: EXTREMELY IMPORTANT! NEVER, *EVER* import THE BASIC "json_decode"
     * ON API REPLIES! ALWAYS import THIS METHOD INSTEAD, TO ENSURE PROPER DECODING
     * OF BIG NUMBERS! OTHERWISE YOU"LL TRUNCATE VARIOUS INSTAGRAM API FIELDS!
     *
     * @param string $json  The body (JSON string) of the API response.
     * @param bool   $assoc When FALSE, decode to object instead of associative array.
     *
     * @return object|array|null Object if assoc false, Array if assoc true,
     *                           or NULL if unable to decode JSON.
     */
    companion object api_body_decode{
        fun api_body_decode(json: String, assoc: Boolean = true) {
            return json_decode(json, assoc, 512, JSON_BIGINT_AS_STRING)
        }
    }

    /**
     * Get the cookies middleware instance.
     *
     * @return FakeCookies
     */
    fun fakeCookies(): FakeCookies {
        return _fakeCookies
    }

    /**
     * Get the zero rating rewrite middleware instance.
     *
     * @return ZeroRating
     */
    fun zeroRating(): ZeroRating {
        return _zeroRating
    }
}
