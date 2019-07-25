

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * PostLive.
 *
 * @method PostLiveItem[] getPostLiveItems()
 * @method bool isPostLiveItems()
 * @method this setPostLiveItems(PostLiveItem[] $value)
 * @method this unsetPostLiveItems()
 */
data class PostLive (
    val post_live_items : MutableList<PostLiveItem>
){
//    val JSON_PROPERTY_MAP = [
//        "post_live_items" => "PostLiveItem[]",
//    ]
}
