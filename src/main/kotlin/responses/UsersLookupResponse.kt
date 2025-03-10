

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.User

/**
 * UsersLookupResponse.
 *
 * @method bool getCanEmailReset()
 * @method bool getCanSmsReset()
 * @method string getCorrectedInput()
 * @method string getEmail()
 * @method bool getEmailSent()
 * @method bool getHasValidPhone()
 * @method string getLookupSource()
 * @method mixed getMessage()
 * @method string getPhoneNumber()
 * @method string getStatus()
 * @method model.User getUser()
 * @method string getUserId()
 * @method model._Message[] get_Messages()
 * @method bool isCanEmailReset()
 * @method bool isCanSmsReset()
 * @method bool isCorrectedInput()
 * @method bool isEmail()
 * @method bool isEmailSent()
 * @method bool isHasValidPhone()
 * @method bool isLookupSource()
 * @method bool isMessage()
 * @method bool isPhoneNumber()
 * @method bool isStatus()
 * @method bool isUser()
 * @method bool isUserId()
 * @method bool is_Messages()
 * @method this setCanEmailReset(bool $value)
 * @method this setCanSmsReset(bool $value)
 * @method this setCorrectedInput(string $value)
 * @method this setEmail(string $value)
 * @method this setEmailSent(bool $value)
 * @method this setHasValidPhone(bool $value)
 * @method this setLookupSource(string $value)
 * @method this setMessage(mixed $value)
 * @method this setPhoneNumber(string $value)
 * @method this setStatus(string $value)
 * @method this setUser(model.User $value)
 * @method this setUserId(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetCanEmailReset()
 * @method this unsetCanSmsReset()
 * @method this unsetCorrectedInput()
 * @method this unsetEmail()
 * @method this unsetEmailSent()
 * @method this unsetHasValidPhone()
 * @method this unsetLookupSource()
 * @method this unsetMessage()
 * @method this unsetPhoneNumber()
 * @method this unsetStatus()
 * @method this unsetUser()
 * @method this unsetUserId()
 * @method this unset_Messages()
 */
data class UsersLookupResponse (
    val user            : User,
    val email_sent      : Boolean,
    val has_valid_phone : Boolean,
    val can_email_reset : Boolean,
    val can_sms_reset   : Boolean,
    val user_id         : String,
    val lookup_source   : String,
    val email           : String,
    val phone_number    : String,
    val corrected_input : String
){
//    val JSON_PROPERTY_MAP = [
//        "user"            => "model.User",
//        "email_sent"      => "bool",
//        "has_valid_phone" => "bool",
//        "can_email_reset" => "bool",
//        "can_sms_reset"   => "bool",
//        "user_id"         => "string",
//        "lookup_source"   => "string",
//        "email"           => "string",
//        "phone_number"    => "string",
//        "corrected_input" => "string",
//    ]
}
