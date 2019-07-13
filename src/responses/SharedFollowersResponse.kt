

package instagramAPI.responses

import instagramAPI.Response

/**
 * SharedFollowersResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model.SharedFollower[] getUsers()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isUsers()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setUsers(model.SharedFollower[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetUsers()
 * @method this unset_Messages()
 */
class SharedFollowersResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "users" => "model.SharedFollower[]",
    ]
}
