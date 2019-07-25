

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.User

/**
 * PostLiveViewerListResponse.
 *
 * @method mixed getMessage()
 * @method mixed getNextMaxId()
 * @method string getStatus()
 * @method int getTotalViewerCount()
 * @method model.User[] getUsers()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isNextMaxId()
 * @method bool isStatus()
 * @method bool isTotalViewerCount()
 * @method bool isUsers()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setNextMaxId(mixed $value)
 * @method this setStatus(string $value)
 * @method this setTotalViewerCount(int $value)
 * @method this setUsers(model.User[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetNextMaxId()
 * @method this unsetStatus()
 * @method this unsetTotalViewerCount()
 * @method this unsetUsers()
 * @method this unset_Messages()
 */
data class PostLiveViewerListResponse (
    val users              : MutableList<User>,
    val next_max_id        :String,
    val total_viewer_count : Int
){
//    val JSON_PROPERTY_MAP = [
//        "users"              => "model.User[]",
//        "next_max_id"        => "",
//        "total_viewer_count" => "int",
//    ]
}
