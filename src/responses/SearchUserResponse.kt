

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.User

/**
 * SearchUserResponse.
 *
 * @method bool getHasMore()
 * @method mixed getMessage()
 * @method int getNumResults()
 * @method string getRankToken()
 * @method string getStatus()
 * @method model.User[] getUsers()
 * @method model._Message[] get_Messages()
 * @method bool isHasMore()
 * @method bool isMessage()
 * @method bool isNumResults()
 * @method bool isRankToken()
 * @method bool isStatus()
 * @method bool isUsers()
 * @method bool is_Messages()
 * @method this setHasMore(bool $value)
 * @method this setMessage(mixed $value)
 * @method this setNumResults(int $value)
 * @method this setRankToken(string $value)
 * @method this setStatus(string $value)
 * @method this setUsers(model.User[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetHasMore()
 * @method this unsetMessage()
 * @method this unsetNumResults()
 * @method this unsetRankToken()
 * @method this unsetStatus()
 * @method this unsetUsers()
 * @method this unset_Messages()
 */
data class SearchUserResponse (
    val has_more    : Boolean,
    val num_results : Int,
    val users       : MutableList<User>,
    val rank_token  : String
){
//    val JSON_PROPERTY_MAP = [
//        "has_more"    => "bool",
//        "num_results" => "int",
//        "users"       => "model.User[]",
//        "rank_token"  => "string",
//    ]
}
