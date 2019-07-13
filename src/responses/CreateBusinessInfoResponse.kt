

package instagramAPI.responses

import instagramAPI.Response

/**
 * CreateBusinessInfoResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model.User[] getUsers()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isUsers()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setUsers(model.User[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetUsers()
 * @method this unset_Messages()
 */
class CreateBusinessInfoResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "users" to "model.User[]"
    )
}
