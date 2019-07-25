

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Surface
import instagramAPI.responses.model.User

/**
 * BootstrapUsersResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model.Surface[] getSurfaces()
 * @method model.User[] getUsers()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isSurfaces()
 * @method bool isUsers()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setSurfaces(model.Surface[] $value)
 * @method this setUsers(model.User[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetSurfaces()
 * @method this unsetUsers()
 * @method this unset_Messages()
 */
data class BootstrapUsersResponse (
    val surfaces : MutableList<Surface>,
    val users    : MutableList<User>
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "surfaces" to "model.Surface[]",
//        "users"    to "model.User[]"
//    )
}
