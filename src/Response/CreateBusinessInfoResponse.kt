

package instagramAPI.Response

import instagramAPI.Response

/**
 * CreateBusinessInfoResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method Model.User[] getUsers()
 * @method Model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isUsers()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setUsers(Model.User[] $value)
 * @method this set_Messages(Model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetUsers()
 * @method this unset_Messages()
 */
class CreateBusinessInfoResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "users" to "Model.User[]"
    )
}
