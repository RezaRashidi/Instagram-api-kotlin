

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.SuggestedUsers
import instagramAPI.responses.model.User

/**
 * FollowerAndFollowingResponse.
 *
 * @method mixed getBigList()
 * @method mixed getMessage()
 * @method string getNextMaxId()
 * @method mixed getPageSize()
 * @method string getStatus()
 * @method model.SuggestedUsers getSuggestedUsers()
 * @method int getTruncateFollowRequestsAtIndex()
 * @method model.User[] getUsers()
 * @method model._Message[] get_Messages()
 * @method bool isBigList()
 * @method bool isMessage()
 * @method bool isNextMaxId()
 * @method bool isPageSize()
 * @method bool isStatus()
 * @method bool isSuggestedUsers()
 * @method bool isTruncateFollowRequestsAtIndex()
 * @method bool isUsers()
 * @method bool is_Messages()
 * @method this setBigList(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setNextMaxId(string $value)
 * @method this setPageSize(mixed $value)
 * @method this setStatus(string $value)
 * @method this setSuggestedUsers(model.SuggestedUsers $value)
 * @method this setTruncateFollowRequestsAtIndex(int $value)
 * @method this setUsers(model.User[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetBigList()
 * @method this unsetMessage()
 * @method this unsetNextMaxId()
 * @method this unsetPageSize()
 * @method this unsetStatus()
 * @method this unsetSuggestedUsers()
 * @method this unsetTruncateFollowRequestsAtIndex()
 * @method this unsetUsers()
 * @method this unset_Messages()
 */
data class FollowerAndFollowingResponse (
    val users                             : MutableList<User>,
    val suggested_users                   : SuggestedUsers,
    val truncate_follow_requests_at_index : Int,
    val next_max_id                       : String,
    val page_size                         : String,
    val big_list                          : String
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "users"                             to "model.User[]",
//        "suggested_users"                   to "model.SuggestedUsers",
//        "truncate_follow_requests_at_index" to "int",
//        "next_max_id"                       to "string",
//        "page_size"                         to "",
//        "big_list"                          to ""
//    )
}
