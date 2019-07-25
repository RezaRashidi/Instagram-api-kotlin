

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * AccountSummaryUnit.
 *
 * @method int getPostsCount()
 * @method bool isPostsCount()
 * @method this setPostsCount(int $value)
 * @method this unsetPostsCount()
 */
data class AccountSummaryUnit (
    val posts_count : Int
){
//    val JSON_PROPERTY_MAP = [
//        "posts_count"          => "int",
//    ]
}
