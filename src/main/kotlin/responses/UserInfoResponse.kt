

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.User

/**
 * UserInfoResponse.
 *
 * @method mixed getMegaphone()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model.User getUser()
 * @method model._Message[] get_Messages()
 * @method bool isMegaphone()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isUser()
 * @method bool is_Messages()
 * @method this setMegaphone(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setUser(model.User $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMegaphone()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetUser()
 * @method this unset_Messages()
 */
data class UserInfoResponse (
    val megaphone : String,
    val user      : User
){
//    val JSON_PROPERTY_MAP = [
//        "megaphone" => "",
//        "user"      => "model.User",
//    ]
}
