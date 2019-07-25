

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.User

/**
 * MediaLikersResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method int getUserCount()
 * @method model.User[] getUsers()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isUserCount()
 * @method bool isUsers()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setUserCount(int $value)
 * @method this setUsers(model.User[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetUserCount()
 * @method this unsetUsers()
 * @method this unset_Messages()
 */
data class MediaLikersResponse (
    val user_count : Int,
    val users      : MutableList<User>
){
//    val JSON_PROPERTY_MAP = [
//        "user_count" => "int",
//        "users"      => "model.User[]",
//    ]
}
