package instagramAPI

//import Psr.Http.Message.ResponseInterface as HttpResponseInterface

/**
 * Core class for Instagram API responses.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method responses.model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMessage(mixed value)
 * @method this setStatus(string value)
 * @method this set_Messages(responses.model._Message[] value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */

open class Response{ //: AutoPropertyMapper() {
	/** @var string */
	val STATUS_OK = "ok"
	/** @var string */
	val STATUS_FAIL = "fail"

	open val JSON_PROPERTY_MAP = mapOf(
		/*
		 * Whether the API request succeeded or not.
		 *
		 * Can be: "ok", "fail".
		 */
		"status" to "string",
		/*
		 * Instagram"s API failure error message(s).
		 *
		 * NOTE: This MUST be marked as "mixed" since the server can give us
		 * either a single string OR a data structure with multiple messages.
		 * Our custom `getMessage()` will take care of parsing their value.
		 */
		"message" to "mixed",
		/*
		 * This can exist in any Instagram API response, and carries special
		 * status information.
		 *
		 * Known messages: "fb_needs_reauth", "vkontakte_needs_reauth",
		 * "twitter_needs_reauth", "ameba_needs_reauth", "update_push_token".
		 */
		"_messages" to "responses.model._Message[]")

	/** @var HttpResponseInterface */
	lateinit var httpResponse

	/**
	 * Checks if the response was successful.
	 *
	 * @return bool
	 */
	fun isOk(): Boolean {
		return _getProperty("status") === STATUS_OK
	}

	/**
	 * Gets the message.
	 *
	 * This fun overrides the normal getter with some special processing
	 * to handle unusual multi-error message values in certain responses.
	 *
	 * @throws RuntimeException If the message object is of an unsupported type.
	 *
	 * @return string|null A message string if one exists, otherwise NULL.
	 */
	fun getMessage() :String?{
		// Instagram"s API usually returns a simple error string. But in some
		// cases, they instead return a subarray of individual errors, in case
		// of APIs that can return multiple errors at once.
		//
		// Uncomment this if you want to test multiple error handling:
		// json = "{"status":"fail","message":{"errors":["Select a valid choice. 0 is not one of the available choices."]}}"
		// json = "{"status":"fail","message":{"errors":["Select a valid choice. 0 is not one of the available choices.","Another error.","One more error."]}}"
		// data = json_decode(json, true, 512, JSON_BIGINT_AS_STRING)
		// this._setProperty("message", data["message"])

		var message = _getProperty("message") // TODO http client for create kotlin object from json
		if (message === null || message is String) {
			// Single error string or nothing at all.
			return message
		} else if (message is Array<String>) {

			// Multiple errors in an "errors" subarray.
			if ( message.size == 1 && message["errors"] is Array<String>) {
				// Add "Multiple Errors" prefix if the response contains more than one.
				// But most of the time, there will only be one error in the array.
				var str = (if (message["errors"].size > 1)   "Multiple Errors: " else "")
				str +=  message["errors"].joinToString("and") // Assumes all errors are strings.
				return str
			} else {
				throw RuntimeException(
					"Unknown message object. Expected errors subarray but found something else. Please submit a ticket about needing an Instagram-API library update!")
			}
		} else {
			throw RuntimeException(
				"Unknown message type. Please submit a ticket about needing an Instagram-API library update!")
		}
	}

	/**
	 * Gets the HTTP response.
	 *
	 * @return HttpResponseInterface
	 */
	fun getHttpResponse() {
		return this.httpResponse
	}

	/**
	 * Sets the HTTP response.
	 *
	 * @param HttpResponseInterface response
	 */
	fun setHttpResponse(response: HttpResponseInterface) {
		this.httpResponse = response
	}

	/**
	 * Checks if an HTTP response value exists.
	 *
	 * @return bool
	 */
	fun isHttpResponse():Boolean {
		return httpResponse !== null
	}
}
