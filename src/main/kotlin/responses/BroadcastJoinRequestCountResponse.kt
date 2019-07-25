

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.User

/**
 * BroadcastJoinRequestCountResponse.
 *
 * @method string getFetchTs()
 * @method mixed getMessage()
 * @method int getNumNewRequests()
 * @method int getNumTotalRequests()
 * @method int getNumUnseenRequests()
 * @method string getStatus()
 * @method model.User[] getUsers()
 * @method model._Message[] get_Messages()
 * @method bool isFetchTs()
 * @method bool isMessage()
 * @method bool isNumNewRequests()
 * @method bool isNumTotalRequests()
 * @method bool isNumUnseenRequests()
 * @method bool isStatus()
 * @method bool isUsers()
 * @method bool is_Messages()
 * @method this setFetchTs(string $value)
 * @method this setMessage(mixed $value)
 * @method this setNumNewRequests(int $value)
 * @method this setNumTotalRequests(int $value)
 * @method this setNumUnseenRequests(int $value)
 * @method this setStatus(string $value)
 * @method this setUsers(model.User[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetFetchTs()
 * @method this unsetMessage()
 * @method this unsetNumNewRequests()
 * @method this unsetNumTotalRequests()
 * @method this unsetNumUnseenRequests()
 * @method this unsetStatus()
 * @method this unsetUsers()
 * @method this unset_Messages()
 */
data class BroadcastJoinRequestCountResponse (
    val fetch_ts            : String,
    val num_total_requests  : Int,
    val num_new_requests    : Int,
    val users               : MutableList<User>,
    val num_unseen_requests : Int
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "fetch_ts"            to "string",
//        "num_total_requests"  to "int",
//        "num_new_requests"    to "int",
//        "users"               to "model.User[]",
//        "num_unseen_requests" to "int"
//    )
}
