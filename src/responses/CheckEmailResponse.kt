

package instagramAPI.responses

import instagramAPI.Response

/**
 * CheckEmailResponse.
 *
 * @method mixed getAvailable()
 * @method mixed getConfirmed()
 * @method mixed getErrorType()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method string[] getUsernameSuggestions()
 * @method mixed getValid()
 * @method model._Message[] get_Messages()
 * @method bool isAvailable()
 * @method bool isConfirmed()
 * @method bool isErrorType()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isUsernameSuggestions()
 * @method bool isValid()
 * @method bool is_Messages()
 * @method this setAvailable(mixed $value)
 * @method this setConfirmed(mixed $value)
 * @method this setErrorType(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setUsernameSuggestions(string[] $value)
 * @method this setValid(mixed $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAvailable()
 * @method this unsetConfirmed()
 * @method this unsetErrorType()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetUsernameSuggestions()
 * @method this unsetValid()
 * @method this unset_Messages()
 */
data class CheckEmailResponse (
    val valid                : String,
    val available            : String,
    val confirmed            : String,
    val username_suggestions : MutableList<String>,
    val error_type           : String
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "valid"                to "",
//        "available"            to "",
//        "confirmed"            to "",
//        "username_suggestions" to "string[]",
//        "error_type"           to ""
//    )
}
