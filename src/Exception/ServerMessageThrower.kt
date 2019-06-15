package InstagramAPI.Exception

import InstagramAPI.Response
import Psr.Http.Message.ResponseInterface as HttpResponseInterface

/**
 * Parses Instagram"s API error messages and throws an appropriate exception.
 *
 * @author SteveJobzniak (https://github.com/SteveJobzniak)
 */
object ServerMessageThrower {
	/**
	 * Map from server messages to various exceptions.
	 *
	 * If the first letter of a pattern is "/", we treat it as a regex.
	 *
	 * The exceptions should be roughly arranged by how common they are, with
	 * the most common ones checked first, at the top.
	 *
	 * Note that not all exceptions are listed below. Some are thrown via other
	 * methods than this automatic message parser.
	 *
	 * WARNING TO CONTRIBUTORS: Do NOT "contribute" a bunch of endpoint fun
	 * specific garbage exceptions here, such as "User not found", "Duplicate
	 * comment", "No permission to view profile" or other garbage. Those server
	 * messages are human-readable, unreliable (they can change at any time) and
	 * are also totally non-critical. You should handle them yourself in your
	 * end-user applications by simply catching their generic EndpointException
	 * and looking at the contents of its getMessage() property, or checking if
	 * it hasResponse() and then getResponse() to see the full server response.
	 * The exceptions listed below are *critical* exceptions related to the CORE
	 * of the API! Nothing else.
	 *
	 * @var array
	 */
	val EXCEPTION_MAP = mapOf(
		/*
		 * WARNING: We MUST be sure to list these exception messages in an order
		 * which guarantees that they will be properly detected without being
		 * detected as something else!
		 *
		 * For example, the "challenge_required" string ALSO exists inside of
		 * "checkpoint_challenge_required", so if we check for ChallengeRequired
		 * problems above CheckpointRequired, then we would ALWAYS detect
		 * checkpoints as "challenge required" since that string exists in both
		 * of them.
		 *
		 * Always list all exceptions in an order that guarantees that they
		 * cannot be misdetected as each other! The exceptions with the longest
		 * strings, in case of similar strings, MUST be checked/listed EARLIER!
		 *
		 * So in that example, CheckpointRequired MUST be listed above
		 * ChallengeRequired!
		 */
		"LoginRequiredException" to arrayOf("login_required"),
		"CheckpointRequiredException" to arrayOf("checkpoint_required", // message
		                                         "checkpoint_challenge_required"// error_type
		), "ChallengeRequiredException" to arrayOf("challenge_required"),
		"FeedbackRequiredException" to arrayOf("feedback_required"),
		"ConsentRequiredException" to arrayOf("consent_required"), "IncorrectPasswordException" to arrayOf(
			// "The password you entered is incorrect".
			"/password(.*?)incorrect/", // message
			"bad_password" // error_type
		), "InvalidSmsCodeException" to arrayOf(
			// "Please check the security code we sent you and try again".
			"/check(.*?)security(.*?)code/", // message
			"sms_code_validation_code_invalid" // error_type
		), "AccountDisabledException" to arrayOf(
			// "Your account has been disabled for violating our terms".
			"/account(.*?)disabled(.*?)violating/"), "SentryBlockException" to arrayOf("sentry_block"),
		"InvalidUserException" to arrayOf(
			// "The username you entered doesn"t appear to belong to an account"
			"/username(.*?)doesn\'t(.*?)belong/", // message
			"invalid_user" // error_type
		), "ForcedPasswordResetException" to arrayOf("/reset(.*?)password/"))

	/**
	 * Parses a server message and throws the appropriate exception.
	 *
	 * Uses the generic EndpointException if no other exceptions match.
	 *
	 * @param string|null                prefixString   What prefix to import for
	 *                                                   the message in the
	 *                                                   final exception. Should
	 *                                                   be something helpful
	 *                                                   such as the name of the
	 *                                                   class or fun which
	 *                                                   threw. Can be `NULL`.
	 * @param string|null                serverMessage  The failure string from
	 *                                                   Instagram"s API (from
	 *                                                   `getMessage()`). Might
	 *                                                   be empty in some cases.
	 * @param Response|null              serverResponse The complete server
	 *                                                   response object, if one
	 *                                                   is available
	 *                                                   (optional).
	 * @param HttpResponseInterface|null httpResponse   The HTTP response
	 *                                                   object (if available).
	 *
	 * @throws InstagramException The appropriate exception.
	 */
	fun autoThrow(prefixString: String?, serverMessage: String?, serverResponse: Response? = null,
	              httpResponse: HttpResponseInterface? = null) {
		// We will analyze both the `message` AND `error_type` (if available).
		val messages = mutableListOf<String>(serverMessage)
		var serverErrorType = null
		if (serverResponse !== null) {
			// We are reading a property that isn"t defined in the class
			// property map, so we must import "has" first, to ensure it exists.
			if (serverResponse.hasErrorType() &&serverResponse.getErrorType() is String ) {
				serverErrorType = serverResponse.getErrorType()
				messages.add(serverErrorType)
			}
		}

		exceptionClass = null

		// Check if the server message is in our CRITICAL exception table.
		for(message in  messages ) {
			for(  className.patterns in EXCEPTION_MAP) {
				for( pattern in  patterns ) {
					if (pattern[0] == "/") {
						// Regex check.
						if (preg_match(pattern, message)) {
							exceptionClass = className
							break 3
						}
					} else {
						// Regular string search.
						if (strpos(message, pattern) !== false) {
							exceptionClass = className
							break 3
						}
					}
				}
			}
		}

		// Check the HTTP status code if no critical exception has been found.
		if (exceptionClass === null) {
			// NOTE FOR CONTRIBUTORS: All HTTP status exceptions below MUST be
			// derived from EndpointException, since all HTTP errors are
			// endpoint-error-related responses and MUST be easily catchable!
			httpStatusCode = httpResponse !== null ? httpResponse.getStatusCode() : null
			switch(httpStatusCode) {
				case 400:
				exceptionClass = "BadRequestException"
				break
				case 404:
				exceptionClass = "NotFoundException"
				break
				default:
				// No critical exceptions and no HTTP code exceptions have
				// been found, so import the generic "API fun exception"!
				exceptionClass = "EndpointException"
			}
		}

		// We need to specify the full package path to the exception class.
		fullClassPath = "..".__package__."..".exceptionClass

		// Determine which message to display to the user.
		displayMessage = is_string(serverMessage) && strlen(serverMessage)
		? serverMessage : serverErrorType
		if (!is_string(displayMessage) || !strlen(displayMessage)) {
			displayMessage = "Request failed."
		}

		// Some Instagram messages already have punctuation, and others need it.
		displayMessage = self::prettifyMessage(displayMessage)

		// Create an instance of the final exception class, with the pretty msg.
		e = fullClassPath(prefixString !== null
		                  ? sprintf ("%s: %s", prefixString, displayMessage)
		: displayMessage
		)

		// Attach the server response to the exception, IF a response exists.
		// NOTE: Only possible on exceptions derived from InstagramException.
		if (serverResponse instanceof Response && e instanceof . InstagramAPI . Exception . InstagramException) {
			e.setResponse(serverResponse)
		}

		throw e
	}

	/**
	 * Nicely reformats externally generated exception messages.
	 *
	 * This is used for guaranteeing consistent message formatting with full
	 * English sentences, ready for display to the user.
	 *
	 * @param string message The original message.
	 *
	 * @return string The cleaned-up message.
	 */
	fun prettifyMessage(message: String):String {
		// Some messages already have punctuation, and others need it. Prettify
		// the message by ensuring that it ALWAYS ends in punctuation, for
		// consistency with all of our internal error messages.
		lastChar = substr(message, -1)
		if (lastChar !== "" && lastChar !== "." && lastChar !== "!" && lastChar !== "?") {
			message.= "."
		}

		// Guarantee that the first letter is uppercase.
		message = ucfirst(message)

		// Replace all underscores (ie. "Login_required.") with spaces.
		message = str_replace("_", " ", message)

		return message
	}
}
