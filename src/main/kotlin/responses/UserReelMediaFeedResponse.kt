

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.*

/**
 * UserReelMediaFeedResponse.
 *
 * @method model.Broadcast getBroadcast()
 * @method bool getCanReply()
 * @method bool getCanReshare()
 * @method model.CoverMedia getCoverMedia()
 * @method string getExpiringAt()
 * @method bool getHasBestiesMedia()
 * @method string getId()
 * @method model.Item[] getItems()
 * @method string getLatestReelMedia()
 * @method model.Location getLocation()
 * @method mixed getMessage()
 * @method int getPrefetchCount()
 * @method string getRankedPosition()
 * @method string getReelType()
 * @method string getSeen()
 * @method string getSeenRankedPosition()
 * @method string getStatus()
 * @method string getTitle()
 * @method model.User getUser()
 * @method model._Message[] get_Messages()
 * @method bool isBroadcast()
 * @method bool isCanReply()
 * @method bool isCanReshare()
 * @method bool isCoverMedia()
 * @method bool isExpiringAt()
 * @method bool isHasBestiesMedia()
 * @method bool isId()
 * @method bool isItems()
 * @method bool isLatestReelMedia()
 * @method bool isLocation()
 * @method bool isMessage()
 * @method bool isPrefetchCount()
 * @method bool isRankedPosition()
 * @method bool isReelType()
 * @method bool isSeen()
 * @method bool isSeenRankedPosition()
 * @method bool isStatus()
 * @method bool isTitle()
 * @method bool isUser()
 * @method bool is_Messages()
 * @method this setBroadcast(model.Broadcast $value)
 * @method this setCanReply(bool $value)
 * @method this setCanReshare(bool $value)
 * @method this setCoverMedia(model.CoverMedia $value)
 * @method this setExpiringAt(string $value)
 * @method this setHasBestiesMedia(bool $value)
 * @method this setId(string $value)
 * @method this setItems(model.Item[] $value)
 * @method this setLatestReelMedia(string $value)
 * @method this setLocation(model.Location $value)
 * @method this setMessage(mixed $value)
 * @method this setPrefetchCount(int $value)
 * @method this setRankedPosition(string $value)
 * @method this setReelType(string $value)
 * @method this setSeen(string $value)
 * @method this setSeenRankedPosition(string $value)
 * @method this setStatus(string $value)
 * @method this setTitle(string $value)
 * @method this setUser(model.User $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetBroadcast()
 * @method this unsetCanReply()
 * @method this unsetCanReshare()
 * @method this unsetCoverMedia()
 * @method this unsetExpiringAt()
 * @method this unsetHasBestiesMedia()
 * @method this unsetId()
 * @method this unsetItems()
 * @method this unsetLatestReelMedia()
 * @method this unsetLocation()
 * @method this unsetMessage()
 * @method this unsetPrefetchCount()
 * @method this unsetRankedPosition()
 * @method this unsetReelType()
 * @method this unsetSeen()
 * @method this unsetSeenRankedPosition()
 * @method this unsetStatus()
 * @method this unsetTitle()
 * @method this unsetUser()
 * @method this unset_Messages()
 */
data class UserReelMediaFeedResponse (
    val id                   : String,
    val latest_reel_media    : String,
    val seen                 : String,
    val can_reply            : Boolean,
    val can_reshare          : Boolean,
    val reel_type            : String,
    val cover_media          : CoverMedia,
    val user                 : User,
    val items                : MutableList<Item>,
    val ranked_position      : String,
    val title                : String,
    val seen_ranked_position : String,
    val expiring_at          : String,
    val has_besties_media    : Boolean, // Uses int(0) for false and 1 for true.
    val location             : Location,
    val prefetch_count       : Int,
    val broadcast            : Broadcast
){
//    val JSON_PROPERTY_MAP = [
//        Model.Reel::class, // Import property map.
//    ]
}
