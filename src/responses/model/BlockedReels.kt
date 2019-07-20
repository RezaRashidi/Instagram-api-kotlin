

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * BlockedReels.
 *
 * @method mixed getBigList()
 * @method mixed getPageSize()
 * @method User[] getUsers()
 * @method bool isBigList()
 * @method bool isPageSize()
 * @method bool isUsers()
 * @method this setBigList(mixed $value)
 * @method this setPageSize(mixed $value)
 * @method this setUsers(User[] $value)
 * @method this unsetBigList()
 * @method this unsetPageSize()
 * @method this unsetUsers()
 */
data class BlockedReels (
    val users     : MutableList<User>,
    val page_size : String,
    val big_list  : String
){
//    val JSON_PROPERTY_MAP = [
//        "users"     => "User[]",
//        "page_size" => "",
//        "big_list"  => "",
//    ]
}
