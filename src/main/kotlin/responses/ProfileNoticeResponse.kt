

package instagramAPI.responses

import instagramAPI.Response

/**
 * ProfileNoticeResponse.
 *
 * @method bool getHasChangePasswordMegaphone()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isHasChangePasswordMegaphone()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setHasChangePasswordMegaphone(bool $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetHasChangePasswordMegaphone()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class ProfileNoticeResponse (
    val has_change_password_megaphone: Boolean
){
//    val JSON_PROPERTY_MAP = [
//        "has_change_password_megaphone" => "bool",
//    ]
}
