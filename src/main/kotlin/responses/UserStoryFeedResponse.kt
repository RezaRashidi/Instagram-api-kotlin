

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Broadcast
import instagramAPI.responses.model.PostLiveItem
import instagramAPI.responses.model.Reel

/**
 * UserStoryFeedResponse.
 *
 * @method model.Broadcast getBroadcast()
 * @method mixed getMessage()
 * @method model.PostLiveItem getPostLiveItem()
 * @method model.Reel getReel()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isBroadcast()
 * @method bool isMessage()
 * @method bool isPostLiveItem()
 * @method bool isReel()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setBroadcast(model.Broadcast $value)
 * @method this setMessage(mixed $value)
 * @method this setPostLiveItem(model.PostLiveItem $value)
 * @method this setReel(model.Reel $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetBroadcast()
 * @method this unsetMessage()
 * @method this unsetPostLiveItem()
 * @method this unsetReel()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class UserStoryFeedResponse (
    val broadcast      : Broadcast,
    val reel           : Reel,
    val post_live_item : PostLiveItem
){
//    val JSON_PROPERTY_MAP = [
//        "broadcast"      => "model.Broadcast",
//        "reel"           => "model.Reel",
//        "post_live_item" => "model.PostLiveItem",
//    ]
}
