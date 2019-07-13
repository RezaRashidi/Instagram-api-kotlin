

package instagramAPI.responses.Model

import instagramAPI.AutoPropertyMapper

/**
 * AccountSummaryUnit.
 *
 * @method int getPostsCount()
 * @method bool isPostsCount()
 * @method this setPostsCount(int $value)
 * @method this unsetPostsCount()
 */
class AccountSummaryUnit : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "posts_count"          => "int",
    ]
}
