

package InstagramAPI

import GuzzleHttp.Psr7.MultipartStream
import GuzzleHttp.Psr7.Request as HttpRequest
import GuzzleHttp.Psr7.Stream
import InstagramAPI.Exception.InstagramException
import InstagramAPI.Exception.LoginRequiredException
import Psr.Http.Message.ResponseInterface as HttpResponseInterface
import Psr.Http.Message.StreamInterface
import fun GuzzleHttp.Psr7.stream_for

/**
 * Bridge between Instagram Client calls, the object mapper & response objects.
 */
class Request
{
    /**
     * The Instagram class instance we belong to.
     *
     * @var .InstagramAPI.Instagram
     */
    private lateinit var _parent:Instagram

    /**
     * Which API version to import for this request.
     *
     * @var int
     */
    protected var _apiVersion:Int? = null

    /**
     * Endpoint URL (absolute or relative) for this request.
     *
     * @var string
     */
    protected var _url :String


    /**
     * An array of query params.
     *
     * @var array
     */
    protected var _params

    /**
     * An array of POST params.
     *
     * @var array
     */
    protected var _posts

    /**
     * An array of POST params keys to exclude from signed body.
     *
     * @var string[]
     */
    protected var _excludeSigned

    /**
     * Raw request body.
     *
     * @var StreamInterface
     */
    protected var _body

    /**
     * An array of files to upload.
     *
     * @var array
     */
    protected var _files

    /**
     * An array of HTTP headers to add to the request.
     *
     * @var string[]
     */
    protected var _headers

    /**
     * Whether to add the default headers.
     *
     * @var bool
     */
    protected var _defaultHeaders:Boolean? = null

    /**
     * Whether this API call needs authorization.
     *
     * On by default since most calls require authorization.
     *
     * @var bool
     */
    protected var _needsAuth:Boolean? = null

    /**
     * Whether this API call needs signing of the POST data.
     *
     * On by default since most calls require it.
     *
     * @var bool
     */
    protected var _signedPost:Boolean? = null

    /**
     * Whether this API call needs signing of the GET params.
     *
     * Off by default.
     *
     * @var bool
     */
    protected var _signedGet:Boolean? = null

    /**
     * Whether this API endpoint responds with multiple JSON objects.
     *
     * Off by default.
     *
     * @var bool
     */
    protected var _isMultiResponse:Boolean? = null

    /**
     * Whether this API call needs gz-compressing of the POST data.
     *
     * Off by default
     *
     * @var bool
     */
    protected var _isBodyCompressed:Boolean? = null

    /**
     * Opened file handles.
     *
     * @var resource[]
     */
    protected var _handles

    /**
     * Extra Guzzle options for this request.
     *
     * @var array
     */
    protected var _guzzleOptions

    /**
     * Cached HTTP response object.
     *
     * @var HttpResponseInterface
     */
    protected var _httpResponse


    /**
     * Constructor.
     *
     * @param Instagram parent
     * @param string    url
     */
    public fun __construct(
        .InstagramAPI.Instagram parent,
        url)
    {
        this._parent = parent
        this._url = url

        // Set defaults.
        this._apiVersion = 1
        this._headers = []
        this._params = []
        this._posts = []
        this._files = []
        this._handles = []
        this._guzzleOptions = []
        this._needsAuth = true
        this._signedPost = true
        this._signedGet = false
        this._isMultiResponse = false
        this._isBodyCompressed = false
        this._excludeSigned = []
        this._defaultHeaders = true
    }

    /**
     * Destructor.
     */
    public fun __destruct()
    {
        // Ensure that all opened handles are closed.
        this._closeHandles()
    }

    /**
     * Set API version to use.
     *
     * @param int apiVersion
     *
     * @throws .InvalidArgumentException In case of unsupported API version.
     *
     * @return self
     */
    public fun setVersion(
        apiVersion)
    {
        if (!array_key_exists(apiVersion, Constants::API_URLS)) {
            throw .InvalidArgumentException(sprintf(""%d" is not a supported API version.", apiVersion))
        }
        this._apiVersion = apiVersion

        return this
    }

    /**
     * Add query param to request, overwriting any previous value.
     *
     * @param string key
     * @param mixed  value
     *
     * @return self
     */
    public fun addParam(
        key,
        value)
    {
        if (value === true) {
            value = "true"
        } elseif (value === false) {
            value = "false"
        }
        this._params[key] = value

        return this
    }

    /**
     * Add POST param to request, overwriting any previous value.
     *
     * @param string key
     * @param mixed  value
     *
     * @return self
     */
    public fun addPost(
        key,
        value)
    {
        if (value === true) {
            value = "true"
        } elseif (value === false) {
            value = "false"
        }
        this._posts[key] = value

        return this
    }

    /**
     * Add unsigned POST param to request, overwriting any previous value.
     *
     * This adds a POST value and marks it as "never sign it", even if this
     * is a signed request. Instagram sometimes needs a few unsigned values.
     *
     * @param string key
     * @param mixed  value
     *
     * @return self
     */
    public fun addUnsignedPost(
        key,
        value)
    {
        this.addPost(key, value)
        this._excludeSigned[] = key

        return this
    }

    /**
     * Add an on-disk file to a POST request, which causes this to become a multipart form request.
     *
     * @param string      key      Form field name.
     * @param string      filepath Path to a file.
     * @param string|null filename Filename to import in Content-Disposition header.
     * @param array       headers  An associative array of headers.
     *
     * @throws .InvalidArgumentException
     *
     * @return self
     */
    public fun addFile(
        key,
        filepath,
        filename = null,
        array headers = [])
    {
        // Validate
        if (!is_file(filepath)) {
            throw .InvalidArgumentException(sprintf("File "%s" does not exist.", filepath))
        }
        if (!is_readable(filepath)) {
            throw .InvalidArgumentException(sprintf("File "%s" is not readable.", filepath))
        }
        // Inherit value from filepath, if not supplied.
        if (filename === null) {
            filename = filepath
        }
        filename = basename(filename)
        // Default headers.
        headers = headers + [
            "Content-Type"              => "application/octet-stream",
            "Content-Transfer-Encoding" => "binary",
        ]
        this._files[key] = [
            "filepath" => filepath,
            "filename" => filename,
            "headers"  => headers,
        ]

        return this
    }

    /**
     * Add raw file data to a POST request, which causes this to become a multipart form request.
     *
     * @param string      key      Form field name.
     * @param string      data     File data.
     * @param string|null filename Filename to import in Content-Disposition header.
     * @param array       headers  An associative array of headers.
     *
     * @return self
     */
    public fun addFileData(
        key,
        data,
        filename,
        array headers = [])
    {
        filename = basename(filename)
        // Default headers.
        headers = headers + [
            "Content-Type"              => "application/octet-stream",
            "Content-Transfer-Encoding" => "binary",
        ]
        this._files[key] = [
            "contents" => data,
            "filename" => filename,
            "headers"  => headers,
        ]

        return this
    }

    /**
     * Add custom header to request, overwriting any previous or default value.
     *
     * The custom value will even take precedence over the default headers!
     *
     * WARNING: If this is called multiple times with the same header "key"
     * name, it will only keep the LATEST value given for that specific header.
     * It will NOT keep any of its older values, since you can only have ONE
     * value per header! If you want multiple values in headers that support
     * it, you must manually format them properly and send us the final string,
     * usually by separating the value string entries with a semicolon.
     *
     * @param string key
     * @param string value
     *
     * @return self
     */
    public fun addHeader(
        key,
        value)
    {
        this._headers[key] = value

        return this
    }

    /**
     * Add headers used by most API requests.
     *
     * @return self
     */
    protected fun _addDefaultHeaders()
    {
        if (this._defaultHeaders) {
            this._headers["X-IG-App-ID"] = Constants::FACEBOOK_ANALYTICS_APPLICATION_ID
            this._headers["X-IG-Capabilities"] = Constants::X_IG_Capabilities
            this._headers["X-IG-Connection-Type"] = Constants::X_IG_Connection_Type
            this._headers["X-IG-Connection-Speed"] = mt_rand(1000, 3700)."kbps"
            // TODO: IMPLEMENT PROPER CALCULATION OF THESE HEADERS.
            this._headers["X-IG-Bandwidth-Speed-KBPS"] = "-1.000"
            this._headers["X-IG-Bandwidth-TotalBytes-B"] = "0"
            this._headers["X-IG-Bandwidth-TotalTime-MS"] = "0"
        }

        return this
    }

    /**
     * Set the "add default headers" flag.
     *
     * @param bool flag
     *
     * @return self
     */
    public fun setAddDefaultHeaders(
        flag)
    {
        this._defaultHeaders = flag

        return this
    }

    /**
     * Set the extra Guzzle options for this request.
     *
     * @param array guzzleOptions Extra Guzzle options for this request.
     *
     * @return self
     */
    public fun setGuzzleOptions(
        array guzzleOptions)
    {
        this._guzzleOptions = guzzleOptions

        return this
    }

    /**
     * Set raw request body.
     *
     * @param StreamInterface stream
     *
     * @return self
     */
    public fun setBody(
        StreamInterface stream)
    {
        this._body = stream

        return this
    }

    /**
     * Set authorized request flag.
     *
     * @param bool needsAuth
     *
     * @return self
     */
    public fun setNeedsAuth(
        needsAuth)
    {
        this._needsAuth = needsAuth

        return this
    }

    /**
     * Set signed request data flag.
     *
     * @param bool signedPost
     *
     * @return self
     */
    public fun setSignedPost(
        signedPost = true)
    {
        this._signedPost = signedPost

        return this
    }

    /**
     * Set signed request params flag.
     *
     * @param bool signedGet
     *
     * @return self
     */
    public fun setSignedGet(
        signedGet = false)
    {
        this._signedGet = signedGet

        return this
    }

    /**
     * Set the "this API endpoint responds with multiple JSON objects" flag.
     *
     * @param bool flag
     *
     * @return self
     */
    public fun setIsMultiResponse(
        flag = false)
    {
        this._isMultiResponse = flag

        return this
    }

    /**
     * Set gz-compressed request params flag.
     *
     * @param bool isBodyCompressed
     *
     * @return self
     */
    public fun setIsBodyCompressed(
        isBodyCompressed = false)
    {
        this._isBodyCompressed = isBodyCompressed

        if (isBodyCompressed === true) {
            this._headers["Content-Encoding"] = "gzip"
        } elseif (isset(this._headers["Content-Encoding"]) && this._headers["Content-Encoding"] === "gzip") {
            unset(this._headers["Content-Encoding"])
        }

        return this
    }

    /**
     * Get a Stream for the given file.
     *
     * @param array file
     *
     * @throws .InvalidArgumentException
     * @throws .RuntimeException
     *
     * @return StreamInterface
     */
    protected fun _getStreamForFile(
        array file)
    {
        if (isset(file["contents"])) {
            result = stream_for(file["contents"]) // Throws.
        } elseif (isset(file["filepath"])) {
            handle = fopen(file["filepath"], "rb")
            if (handle === false) {
                throw .RuntimeException(sprintf("Could not open file "%s" for reading.", file["filepath"]))
            }
            this._handles[] = handle
            result = stream_for(handle) // Throws.
        } else {
            throw .InvalidArgumentException("No data for stream creation.")
        }

        return result
    }

    /**
     * Convert the request"s data into its HTTP POST multipart body contents.
     *
     * @throws .InvalidArgumentException
     * @throws .RuntimeException
     *
     * @return MultipartStream
     */
    protected fun _getMultipartBody()
    {
        // Here is a tricky part: all form data (including files) must be ordered by hash code.
        // So we are creating an index for building POST data.
        index = Utils::reorderByHashCode(array_merge(this._posts, this._files))
        // Build multipart elements using created index.
        elements = []
        foreach (index as key => value) {
            if (!isset(this._files[key])) {
                element = [
                    "name"     => key,
                    "contents" => value,
                ]
            } else {
                file = this._files[key]
                element = [
                    "name"     => key,
                    "contents" => this._getStreamForFile(file), // Throws.
                    "filename" => isset(file["filename"]) ? file["filename"] : null,
                    "headers"  => isset(file["headers"]) ? file["headers"] : [],
                ]
            }
            elements[] = element
        }

        return MultipartStream( // Throws.
            elements,
            Utils::generateMultipartBoundary()
        )
    }

    /**
     * Close opened file handles.
     */
    protected fun _closeHandles()
    {
        if (!is_array(this._handles) || !count(this._handles)) {
            return
        }

        foreach (this._handles as handle) {
            Utils::safe_fclose(handle)
        }
        this._resetHandles()
    }

    /**
     * Reset opened handles array.
     */
    protected fun _resetHandles()
    {
        this._handles = []
    }

    /**
     * Convert the request"s data into its HTTP POST urlencoded body contents.
     *
     * @throws .InvalidArgumentException
     *
     * @return Stream
     */
    protected fun _getUrlencodedBody()
    {
        this._headers["Content-Type"] = Constants::CONTENT_TYPE

        return stream_for( // Throws.
            http_build_query(Utils::reorderByHashCode(this._posts))
        )
    }

    /**
     * Convert the request"s data into its HTTP POST body contents.
     *
     * @throws .InvalidArgumentException
     * @throws .RuntimeException
     *
     * @return StreamInterface|null The body stream if POST request otherwise NULL if GET request.
     */
    protected fun _getRequestBody()
    {
        // Check and return raw body stream if set.
        if (this._body !== null) {
            if (this._isBodyCompressed) {
                return stream_for(zlib_encode((string) this._body, ZLIB_ENCODING_GZIP))
            }

            return this._body
        }
        // We have no POST data and no files.
        if (!count(this._posts) && !count(this._files)) {
            return
        }
        // Sign POST data if needed.
        if (this._signedPost) {
            this._posts = Signatures::signData(this._posts, this._excludeSigned)
        }
        // Switch between multipart (at least one file) or urlencoded body.
        if (!count(this._files)) {
            result = this._getUrlencodedBody() // Throws.
        } else {
            result = this._getMultipartBody() // Throws.
        }

        if (this._isBodyCompressed) {
            return stream_for(zlib_encode((string) result, ZLIB_ENCODING_GZIP))
        }

        return result
    }

    /**
     * Build HTTP request object.
     *
     * @throws .InvalidArgumentException
     * @throws .RuntimeException
     *
     * @return HttpRequest
     */
    protected fun _buildHttpRequest()
    {
        endpoint = this._url
        // Determine the URI to import (it"s either relative to API, or a full URI).
        if (strncmp(endpoint, "http:", 5) !== 0 && strncmp(endpoint, "https:", 6) !== 0) {
            endpoint = Constants::API_URLS[this._apiVersion].endpoint
        }
        // Check signed request params flag.
        if (this._signedGet) {
            this._params = Signatures::signData(this._params)
        }
        // Generate the final endpoint URL, by adding any custom query params.
        if (count(this._params)) {
            endpoint = endpoint
                .(strpos(endpoint, "?") === false ? "?" : "&")
                .http_build_query(Utils::reorderByHashCode(this._params))
        }
        // Add default headers (if enabled).
        this._addDefaultHeaders()
        /** @var StreamInterface|null postData The POST body stream is NULL if GET request instead. */
        postData = this._getRequestBody() // Throws.
        // Determine request method.
        method = postData !== null ? "POST" : "GET"
        // Build HTTP request object.
        return HttpRequest( // Throws (they didn"t document that properly).
            method,
            endpoint,
            this._headers,
            postData
        )
    }

    /**
     * Helper which throws an error if not logged in.
     *
     * Remember to ALWAYS call this fun at the top of any API request that
     * requires the user to be logged in!
     *
     * @throws LoginRequiredException
     */
    protected fun _throwIfNotLoggedIn()
    {
        // Check the cached login state. May not reflect what will happen on the
        // server. But it"s the best we can check without trying the actual request!
        if (!this._parent.isMaybeLoggedIn) {
            throw LoginRequiredException("User not logged in. Please call login() and then try again.")
        }
    }

    /**
     * Perform the request and get its raw HTTP response.
     *
     * @throws .InvalidArgumentException
     * @throws .RuntimeException
     * @throws InstagramException
     *
     * @return HttpResponseInterface
     */
    public fun getHttpResponse()
    {
        // Prevent request from sending multiple times.
        if (this._httpResponse === null) {
            if (this._needsAuth) {
                // Throw if this requires authentication and we"re not logged in.
                this._throwIfNotLoggedIn()
            }

            this._resetHandles()

            try {
                this._httpResponse = this._parent.client.api( // Throws.
                    this._buildHttpRequest(), // Throws.
                    this._guzzleOptions
                )
            } finally {
                this._closeHandles()
            }
        }

        return this._httpResponse
    }

    /**
     * Return the raw HTTP response body.
     *
     * @throws .InvalidArgumentException
     * @throws .RuntimeException
     * @throws InstagramException
     *
     * @return string
     */
    public fun getRawResponse()
    {
        httpResponse = this.getHttpResponse() // Throws.
        body = (string) httpResponse.getBody()

        // Handle API endpoints that respond with multiple JSON objects.
        // NOTE: We simply merge all JSON objects into a single object. This
        // text replacement of "}.r.n{" is safe, becaimport the actual JSON data
        // objects never contain literal newline characters (http://json.org).
        // And if we get any duplicate properties, then PHP will simply select
        // the latest value for that property (ex: a:1,a:2 is treated as a:2).
        if (this._isMultiResponse) {
            body = str_replace("}.r.n{", ",", body)
        }

        return body
    }

    /**
     * Return safely JSON-decoded HTTP response.
     *
     * This uses a special decoder which handles 64-bit numbers correctly.
     *
     * @param bool assoc When FALSE, decode to object instead of associative array.
     *
     * @throws .InvalidArgumentException
     * @throws .RuntimeException
     * @throws InstagramException
     *
     * @return mixed
     */
    public fun getDecodedResponse(
        assoc = true)
    {
        // Important: Special JSON decoder.
        return Client::api_body_decode(
            this.getRawResponse(), // Throws.
            assoc
        )
    }

    /**
     * Perform the request and map its response data to the provided object.
     *
     * @param Response responseObject An instance of a class object whose properties to fill with the response.
     *
     * @throws .InvalidArgumentException
     * @throws .RuntimeException
     * @throws InstagramException
     *
     * @return Response The provided responseObject with all JSON properties filled.
     */
    public fun getResponse(
        Response responseObject)
    {
        // Check for API response success and put its response in the object.
        this._parent.client.mapServerResponse( // Throws.
            responseObject,
            this.getRawResponse(), // Throws.
            this.getHttpResponse() // Throws.
        )

        return responseObject
    }
}
