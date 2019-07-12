

package instagramAPI.Response.Model

import instagramAPI.AutoPropertyMapper

/**
 * PostLive.
 *
 * @method PostLiveItem[] getPostLiveItems()
 * @method bool isPostLiveItems()
 * @method this setPostLiveItems(PostLiveItem[] $value)
 * @method this unsetPostLiveItems()
 */
class PostLive : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "post_live_items" => "PostLiveItem[]",
    ]
}
