

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.User

/**
 * BroadcastInfoResponse.
 *
 * @method string getBroadcastMessage()
 * @method model.User getBroadcastOwner()
 * @method string getBroadcastStatus()
 * @method string getCoverFrameUrl()
 * @method string getDashAbrPlaybackUrl()
 * @method string getDashManifest()
 * @method string getDashPlaybackUrl()
 * @method string getEncodingTag()
 * @method string getExpireAt()
 * @method string getId()
 * @method bool getInternalOnly()
 * @method string getMediaId()
 * @method mixed getMessage()
 * @method mixed getMuted()
 * @method int getNumberOfQualities()
 * @method string getOrganicTrackingToken()
 * @method string getPublishedTime()
 * @method mixed getRankedPosition()
 * @method string getRtmpPlaybackUrl()
 * @method mixed getSeenRankedPosition()
 * @method string getStatus()
 * @method int getTotalUniqueViewerCount()
 * @method int getViewerCount()
 * @method model._Message[] get_Messages()
 * @method bool isBroadcastMessage()
 * @method bool isBroadcastOwner()
 * @method bool isBroadcastStatus()
 * @method bool isCoverFrameUrl()
 * @method bool isDashAbrPlaybackUrl()
 * @method bool isDashManifest()
 * @method bool isDashPlaybackUrl()
 * @method bool isEncodingTag()
 * @method bool isExpireAt()
 * @method bool isId()
 * @method bool isInternalOnly()
 * @method bool isMediaId()
 * @method bool isMessage()
 * @method bool isMuted()
 * @method bool isNumberOfQualities()
 * @method bool isOrganicTrackingToken()
 * @method bool isPublishedTime()
 * @method bool isRankedPosition()
 * @method bool isRtmpPlaybackUrl()
 * @method bool isSeenRankedPosition()
 * @method bool isStatus()
 * @method bool isTotalUniqueViewerCount()
 * @method bool isViewerCount()
 * @method bool is_Messages()
 * @method this setBroadcastMessage(string $value)
 * @method this setBroadcastOwner(model.User $value)
 * @method this setBroadcastStatus(string $value)
 * @method this setCoverFrameUrl(string $value)
 * @method this setDashAbrPlaybackUrl(string $value)
 * @method this setDashManifest(string $value)
 * @method this setDashPlaybackUrl(string $value)
 * @method this setEncodingTag(string $value)
 * @method this setExpireAt(string $value)
 * @method this setId(string $value)
 * @method this setInternalOnly(bool $value)
 * @method this setMediaId(string $value)
 * @method this setMessage(mixed $value)
 * @method this setMuted(mixed $value)
 * @method this setNumberOfQualities(int $value)
 * @method this setOrganicTrackingToken(string $value)
 * @method this setPublishedTime(string $value)
 * @method this setRankedPosition(mixed $value)
 * @method this setRtmpPlaybackUrl(string $value)
 * @method this setSeenRankedPosition(mixed $value)
 * @method this setStatus(string $value)
 * @method this setTotalUniqueViewerCount(int $value)
 * @method this setViewerCount(int $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetBroadcastMessage()
 * @method this unsetBroadcastOwner()
 * @method this unsetBroadcastStatus()
 * @method this unsetCoverFrameUrl()
 * @method this unsetDashAbrPlaybackUrl()
 * @method this unsetDashManifest()
 * @method this unsetDashPlaybackUrl()
 * @method this unsetEncodingTag()
 * @method this unsetExpireAt()
 * @method this unsetId()
 * @method this unsetInternalOnly()
 * @method this unsetMediaId()
 * @method this unsetMessage()
 * @method this unsetMuted()
 * @method this unsetNumberOfQualities()
 * @method this unsetOrganicTrackingToken()
 * @method this unsetPublishedTime()
 * @method this unsetRankedPosition()
 * @method this unsetRtmpPlaybackUrl()
 * @method this unsetSeenRankedPosition()
 * @method this unsetStatus()
 * @method this unsetTotalUniqueViewerCount()
 * @method this unsetViewerCount()
 * @method this unset_Messages()
 */
data class BroadcastInfoResponse (
    val broadcast_owner           : User,
    val broadcast_status          : String,
    val cover_frame_url           : String,
    val published_time            : String,
    val broadcast_message         : String,
    val muted                     : String,
    val media_id                  : String,
    val id                        : String,
    val rtmp_playback_url         : String,
    val dash_abr_playback_url     : String,
    val dash_playback_url         : String,
    val ranked_position           : String,
    val organic_tracking_token    : String,
    val seen_ranked_position      : String,
    val viewer_count              : Int,
    val dash_manifest             : String,
    val expire_at                 : String,
    val encoding_tag              : String,
    val total_unique_viewer_count : Int,
    val internal_only             : Boolean,
    val number_of_qualities       : Int
){
//    val JSON_PROPERTY_MAP = [
//        Model.Broadcast::class, // Import property map.
//    ]
}
