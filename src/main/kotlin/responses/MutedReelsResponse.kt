

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.User

/**
 * MutedReelsResponse.
 *
 * @method mixed getBigList()
 * @method mixed getMessage()
 * @method string getNextMaxId()
 * @method mixed getPageSize()
 * @method string getStatus()
 * @method model.User[] getUsers()
 * @method model._Message[] get_Messages()
 * @method bool isBigList()
 * @method bool isMessage()
 * @method bool isNextMaxId()
 * @method bool isPageSize()
 * @method bool isStatus()
 * @method bool isUsers()
 * @method bool is_Messages()
 * @method this setBigList(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setNextMaxId(string $value)
 * @method this setPageSize(mixed $value)
 * @method this setStatus(string $value)
 * @method this setUsers(model.User[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetBigList()
 * @method this unsetMessage()
 * @method this unsetNextMaxId()
 * @method this unsetPageSize()
 * @method this unsetStatus()
 * @method this unsetUsers()
 * @method this unset_Messages()
 */
data class MutedReelsResponse (
    val users       : MutableList<User>,
    val next_max_id : String,
    val page_size   : String,
    val big_list    : String
){
//    val JSON_PROPERTY_MAP = [
//        "users"       => "model.User[]",
//        "next_max_id" => "string",
//        "page_size"   => "",
//        "big_list"    => "",
//    ]
}
