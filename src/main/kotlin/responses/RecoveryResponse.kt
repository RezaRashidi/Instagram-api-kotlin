

package instagramAPI.responses

import instagramAPI.Response

/**
 * RecoveryResponse.
 *
 * @method string getBody()
 * @method mixed getMessage()
 * @method bool getPhoneNumberValid()
 * @method string getStatus()
 * @method string getTitle()
 * @method model._Message[] get_Messages()
 * @method bool isBody()
 * @method bool isMessage()
 * @method bool isPhoneNumberValid()
 * @method bool isStatus()
 * @method bool isTitle()
 * @method bool is_Messages()
 * @method this setBody(string $value)
 * @method this setMessage(mixed $value)
 * @method this setPhoneNumberValid(bool $value)
 * @method this setStatus(string $value)
 * @method this setTitle(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetBody()
 * @method this unsetMessage()
 * @method this unsetPhoneNumberValid()
 * @method this unsetStatus()
 * @method this unsetTitle()
 * @method this unset_Messages()
 */
data class RecoveryResponse (
    val phone_number_valid : Boolean,
    val title              : String,
    val body               : String
){
//    val JSON_PROPERTY_MAP = [
//        "phone_number_valid" => "bool",
//        "title"              => "string",
//        "body"               => "string",
//    ]
}
