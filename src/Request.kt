package instagramAPI

//import GuzzleHttp.Psr7.MultipartStream
//import GuzzleHttp.Psr7.Request as HttpRequest
//import GuzzleHttp.Psr7.Stream
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.DataPart
import instagramAPI.exception.InstagramException
import instagramAPI.exception.LoginRequiredException
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.InlineDataPart
import com.sun.deploy.net.HttpRequest
import jdk.nashorn.internal.objects.NativeString.substring
import java.io.File
import java.sql.Array

//import Psr.Http.Message.ResponseInterface as HttpResponseInterface
//import Psr.Http.Message.StreamInterface
//import com.sun.deploy.net.HttpRequest
//import fun GuzzleHttp.Psr7.stream_for
/**
 * Bridge between Instagram Client calls, the object mapper & response objects.
 */
class Request(private val parent: Instagram, private var url: String) {


	/**
	 * Which API version to import for this request.
	 *
	 * @var int
	 */
	private var _apiVersion: Int? = null

	/**
	 * Endpoint URL (absolute or relative) for this request.
	 *
	 * @var string
	 */
//	private  var url: String = url


	/**
	 * An array of query params.
	 *
	 * @var array
	 */
	private var _params: MutableMap<String, String>


	/**
	 * An array of POST params.
	 *
	 * @var array
	 */
	private var _posts: MutableMap<String, String>

	/**
	 * An array of POST params keys to exclude from signed body.
	 *
	 * @var string[]
	 */
	private var _excludeSigned: MutableList<String>

	/**
	 * Raw request body.
	 *
	 * @var StreamInterface
	 */
	private var _body: String? = ""

	/**
	 * An array of files to upload.
	 *
	 * @var array
	 */
	private var _files: MutableMap<String, FileDataPart>

	/**
	 * An array of HTTP headers to add to the request.
	 *
	 * @var string[]
	 */
	private var _headers: MutableMap<String, String>

	/**
	 * Whether to add the default headers.
	 *
	 * @var bool
	 */
	private var _defaultHeaders: Boolean = true

	/**
	 * Whether this API call needs authorization.
	 *
	 * On by default since most calls require authorization.
	 *
	 * @var bool
	 */
	private var _needsAuth: Boolean = true

	/**
	 * Whether this API call needs signing of the POST data.
	 *
	 * On by default since most calls require it.
	 *
	 * @var bool
	 */
	private var _signedPost: Boolean = true

	/**
	 * Whether this API call needs signing of the GET params.
	 *
	 * Off by default.
	 *
	 * @var bool
	 */
	private var _signedGet: Boolean = false

	/**
	 * Whether this API endpoint responds with multiple JSON objects.
	 *
	 * Off by default.
	 *
	 * @var bool
	 */
	private var _isMultiResponse: Boolean = false

	/**
	 * Whether this API call needs gz-compressing of the POST data.
	 *
	 * Off by default
	 *
	 * @var bool
	 */
	private var _isBodyCompressed: Boolean = false

	/**
	 * Opened file handles.
	 *
	 * @var resource[]
	 */
	private var _handles = mutableListOf<String>()

	/**
	 * Extra Guzzle options for this request.
	 *
	 * @var array
	 */
	//private var _guzzleOptions = listOf<String>()

	/**
	 * Cached HTTP response object.
	 *
	 * @var HttpResponseInterface
	 */
	private lateinit var _httpResponse: HttpResponseInterface


	init {
		_apiVersion = 1
		_headers = mutableMapOf()
		_params = mutableMapOf()
		_posts = mutableMapOf()
		_files = mutableMapOf()
//		_handles = []
//		_guzzleOptions = []
		_needsAuth = true
		_signedPost = true
		_signedGet = false
		_isMultiResponse = false
		_isBodyCompressed = false
		_excludeSigned = mutableListOf()
		_defaultHeaders = true
	}

	/**
	 * Destructor.
	 */
//	fun __destruct() {
//		// Ensure that all opened handles are closed.
//		_closeHandles()
//	}

	/**
	 * Set API version to use.
	 *
	 * @param int apiVersion
	 *
	 * @throws  IllegalArgumentException In case of unsupported API version.
	 *
	 * @return self
	 */
	fun setVersion(apiVersion: Int): Request {
		if (Constants.API_URLS.contains(apiVersion)) {
			throw IllegalArgumentException("\"$apiVersion\" is not a supported API version.")
		}
		_apiVersion = apiVersion

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
	fun addParam(key: String, value: String): Request {

		_params[key] = value

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
	fun addPost(key: String, value: String): Request {
//	val valueFa = if (value is Boolean){	if (value) "true"  else "false" } else value


		_posts[key] = value


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
	fun addUnsignedPost(key: String, value: String): Request {
		addPost(key, value)
		_excludeSigned.add(key)

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
	 * @throws  IllegalArgumentException
	 *
	 * @return self
	 */
	fun addFile(key: String, filepath: String, filename: String): Request {
		// Validate
		if (!File(filepath).isFile) {
			throw IllegalArgumentException("File \"$filepath\" does not exist.")
		}
		if (!File(filepath).canRead()) {
			throw IllegalArgumentException("File \"$filepath\" is not readable.")
		}
		// Inherit value from filepath, if not supplied.
//		if (filename === null) {
//			filename = filepath
//		}
		//var _filename = filename?.let { File(it).name }


		// Default headers.
//
//			"Content-Type"              to "application/octet-stream",
//			"Content-Transfer-Encoding" to "binary"
//
		_files[key] = FileDataPart(File(filepath), filename = filename, contentType = "application/octet-stream")


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
//	fun addFileData(key: String, data: String, filename: String?, headers: Map<String, String> = mapOf()):Request {
//		var _filename = filename?.let { File(it).name }
//		// Default headers.
//		var _headers = headers + mapOf(
//			"Content-Type"              to "application/octet-stream",
//			"Content-Transfer-Encoding" to "binary"
//		)
//		_files[key] = mapOf(
//			"contents" to data,
//			"filename" to _filename,
//			"headers"  to _headers
//		)
//
//		return this
//	}

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
	fun addHeader(key: String, value: String): Request {
		_headers[key] = value

		return this
	}

	/**
	 * Add headers used by most API requests.
	 *
	 * @return self
	 */
	private fun _addDefaultHeaders(): Request {
		if (_defaultHeaders) {
			_headers["X-IG-App-ID"] = Constants.FACEBOOK_ANALYTICS_APPLICATION_ID
			_headers["X-IG-Capabilities"] = Constants.X_IG_Capabilities
			_headers["X-IG-Connection-Type"] = Constants.X_IG_Connection_Type
			_headers["X-IG-Connection-Speed"] = (1000..3700).random().toString() + "kbps"
			// TODO: IMPLEMENT PROPER CALCULATION OF THESE HEADERS.
			_headers["X-IG-Bandwidth-Speed-KBPS"] = "-1.000"
			_headers["X-IG-Bandwidth-TotalBytes-B"] = "0"
			_headers["X-IG-Bandwidth-TotalTime-MS"] = "0"
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
	fun setAddDefaultHeaders(flag: Boolean): Request {
		_defaultHeaders = flag

		return this
	}

	/**
	 * Set the extra Guzzle options for this request.
	 *
	 * @param array guzzleOptions Extra Guzzle options for this request.
	 *
	 * @return self
	 */
//	fun setGuzzleOptions( guzzleOptions:List<String>) :Request{
//		_guzzleOptions = guzzleOptions
//
//		return this
//	}

	/**
	 * Set raw request body.
	 *
	 * @param StreamInterface stream
	 *
	 * @return self
	 */
//	fun setBody( stream ):Request {
//		_body = stream
//
//		return this
//	}

	/**
	 * Set authorized request flag.
	 *
	 * @param bool needsAuth
	 *
	 * @return self
	 */
	fun setNeedsAuth(needsAuth: Boolean): Request {
		_needsAuth = needsAuth

		return this
	}

	/**
	 * Set signed request data flag.
	 *
	 * @param bool signedPost
	 *
	 * @return self
	 */
	fun setSignedPost(signedPost: Boolean = true): Request {
		_signedPost = signedPost

		return this
	}

	/**
	 * Set signed request params flag.
	 *
	 * @param bool signedGet
	 *
	 * @return self
	 */
	fun setSignedGet(signedGet: Boolean = false): Request {
		_signedGet = signedGet

		return this
	}

	/**
	 * Set the "this API endpoint responds with multiple JSON objects" flag.
	 *
	 * @param bool flag
	 *
	 * @return self
	 */
	fun setIsMultiResponse(flag: Boolean = false): Request {
		_isMultiResponse = flag

		return this
	}

	/**
	 * Set gz-compressed request params flag.
	 *
	 * @param bool isBodyCompressed
	 *
	 * @return self
	 */
	fun setIsBodyCompressed(isBodyCompressed: Boolean = false): Request {
		_isBodyCompressed = isBodyCompressed

		if (isBodyCompressed) {
			_headers["Content-Encoding"] = "gzip"
		} else if (!(_headers["Content-Encoding"]!!.isBlank()) && _headers["Content-Encoding"] === "gzip") {
			_headers.remove("Content-Encoding")
		}

		return this
	}

	/**
	 * Get a Stream for the given file.
	 *
	 * @param array file
	 *
	 * @throws  IllegalArgumentException
	 * @throws .RuntimeException
	 *
	 * @return StreamInterface
	 */
//	private fun _getStreamForFile( file:Map<String,String>):String {
//
//
//		when {
//			file.containsKey("contents") -> {
//				result = file["contents"] // Throws.
//			}
//			file.containsKey("filepath") -> {
//				val handle = File("filepath").readText()
//				if (handle.canRead()) {
//					throw RuntimeException("Could not open file \"${file["filepath"]}\" for reading.")
//				}
//				_handles.add(handle)
//				result = handle // Throws.
//			}
//			else -> throw IllegalArgumentException("No data for stream creation.")
//		}
//
//		return result
//	}

	/**
	 * Convert the request"s data into its HTTP POST multipart body contents.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .RuntimeException
	 *
	 * @return MultipartStream
	 */
	private fun _getMultipartBody(): kotlin.Array<DataPart> {
		// Here is a tricky part: all form data (including files) must be ordered by hash code.
		// So we are creating an index for building POST data.
		val allUploads = _posts.keys.apply { addAll(_files.keys) }

		val index = Utils.reorderByHashCode(allUploads).toList()
		// Build multipart elements using created index.
		val element = Array<DataPart>(index.size) { i: Int ->
			if (_files.contains(index[i])) {
				return@Array _files[index[i]]!!

			} else {
				return@Array InlineDataPart("name=${index[i]}&contents=${_posts[index[i]]}", name = index[i])


			}
		}



		return element
	}

	/**
	 * Close opened file handles.
	 */
//	private fun _closeHandles() {
//		if (!is_array(_handles) || !count(_handles)) {
//			return
//		}
//
//		for(handle in _handles) {
//			Utils.safe_fclose(handle)
//		}
//		_resetHandles()
//	}

	/**
	 * Reset opened handles array.
	 */
	private fun _resetHandles() {
		_handles = mutableListOf<String>()
	}

	/**
	 * Convert the request"s data into its HTTP POST urlencoded body contents.
	 *
	 * @throws  IllegalArgumentException
	 *
	 * @return Stream
	 */
	private fun _getUrlencodedBody(): String {
		_headers["Content-Type"] = Constants.CONTENT_TYPE

		return Utils.reorderByHashCode(_posts).toString().removePrefix("{").removeSuffix("}").replace(", ", "&")
	}

	/**
	 * Convert the request"s data into its HTTP POST body contents.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .RuntimeException
	 *
	 * @return StreamInterface|null The body stream if POST request otherwise NULL if GET request.
	 */


	private fun _getRequestBodyPost(): String? {
		// Check and return raw body stream if set.
		if (_body !== null) {
//			if (_isBodyCompressed) {
//				return Utils.gzip(_body.toString())
//			}

			return _body
		}

		// Sign POST data if needed.
		if (_signedPost) {
			_posts = Signatures.signData(_posts, _excludeSigned)
		}
		// Switch between multipart (at least one file) or urlencoded body.
		val result = _getUrlencodedBody()     // Throws.


//		if (_isBodyCompressed) {
//			return Utils.gzip(result.toString())
//		}

		return result
	}

	private fun _getRequestbodyUpload(): kotlin.Array<DataPart> {
		// Sign POST data if needed.
		if (_posts.isEmpty() && _files.isEmpty()) {
			return null
		}
		if (_signedPost) {
			_posts = Signatures.signData(_posts, _excludeSigned)
		}
		// Switch between multipart (at least one file) or urlencoded body.
		return _getMultipartBody()     // Throws.


	}

	/**
	 * Build HTTP request object.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .RuntimeException
	 *
	 * @return HttpRequest
	 */
	private fun _buildHttpRequest() {
		var endpoint = url
		// Determine the URI to import (it"s either relative to API, or a full URI).
		if (endpoint.substring(0, 6) !== "http:" && endpoint.substring(0, 7) !== "https:") {
			endpoint = Constants.API_URLS[_apiVersion] + endpoint
		}
		// Check signed request params flag.
		if (_signedGet) {
			_params = Signatures.signData(_params)
		}
		// Generate the final endpoint URL, by adding any custom query params.
		if (_params.count() > 0) {
			endpoint += if (endpoint.indexOf("?") == -1) "?" else "&"
			+Utils.reorderByHashCode(_params).toString().removePrefix("{").removeSuffix("}").replace(", ", "&")
		}
		// Add default headers (if enabled).
		_addDefaultHeaders()
		/** @var StreamInterface|null postData The POST body stream is NULL if GET request instead. */
		val postData = _getRequestBodyPost().isNullOrEmpty() && _getRequestbodyUpload().isNullOrEmpty() // Throws.

		if (postData) {

		return	Fuel.get(url).header(_headers)
		} else {

		return	Fuel.upload(url).add(*_getRequestbodyUpload()).header(_headers)


		}
		// Build HTTP request object.


	}

	/**
	 * Helper which throws an error if not logged in.
	 *
	 * Remember to ALWAYS call this fun at the top of any API request that
	 * requires the user to be logged in!
	 *
	 * @throws LoginRequiredException
	 */
	private fun _throwIfNotLoggedIn() {
		// Check the cached login state. May not reflect what will happen on the
		// server. But it"s the best we can check without trying the actual request!
		if (!parent.isMaybeLoggedIn) {
			throw LoginRequiredException("User not logged in. Please call login() and then try again.")
		}
	}

	/**
	 * Perform the request and get its raw HTTP response.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .RuntimeException
	 * @throws InstagramException
	 *
	 * @return HttpResponseInterface
	 */
	fun getHttpResponse() {
		// Prevent request from sending multiple times.
		if (_httpResponse === null) {
			if (_needsAuth) {
				// Throw if this requires authentication and we"re not logged in.
				_throwIfNotLoggedIn()
			}

			_resetHandles()

			try {
				_httpResponse = parent.client.api( // Throws.
					_buildHttpRequest(), // Throws.
					_guzzleOptions)
			} finally {
				_closeHandles()
			}
		}

		return _httpResponse
	}

	/**
	 * Return the raw HTTP response body.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .RuntimeException
	 * @throws InstagramException
	 *
	 * @return string
	 */
	fun getRawResponse(): String {
		val httpResponse = getHttpResponse() // Throws.
		var body = httpResponse.getBody().toString()

		// Handle API endpoints that respond with multiple JSON objects.
		// NOTE: We simply merge all JSON objects into a single object. This
		// text replacement of "}.r.n{" is safe, becaimport the actual JSON data
		// objects never contain literal newline characters (http://json.org).
		// And if we get any duplicate properties, then PHP will simply select
		// the latest value for that property (ex: a:1,a:2 is treated as a:2).
		if (_isMultiResponse) {
			body = body.replace("}.r.n{", ",")
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
	 * @throws  IllegalArgumentException
	 * @throws .RuntimeException
	 * @throws InstagramException
	 *
	 * @return mixed
	 */
	fun getDecodedResponse(assoc: Boolean = true) {
		// Important: Special JSON decoder.
		return Client.api_body_decode(getRawResponse(), // Throws.
		                              assoc)
	}

	/**
	 * Perform the request and map its response data to the provided object.
	 *
	 * @param responses responseObject An instance of a class object whose properties to fill with the response.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .RuntimeException
	 * @throws InstagramException
	 *
	 * @return responses The provided responseObject with all JSON properties filled.
	 */
	fun getResponse(responseObject: Response): responseObject {
		// Check for API response success and put its response in the object.
		parent.client.mapServerResponse( // Throws.
			responseObject, getRawResponse(), // Throws.
			getHttpResponse() // Throws.
		)

		return responseObject
	}
}
