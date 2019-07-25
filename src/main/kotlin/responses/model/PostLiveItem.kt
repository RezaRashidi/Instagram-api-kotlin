

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * PostLiveItem.
 *
 * @method Broadcast[] getBroadcasts()
 * @method mixed getCanReply()
 * @method mixed getCanReshare()
 * @method mixed getLastSeenBroadcastTs()
 * @method mixed getMuted()
 * @method int getPeakViewerCount()
 * @method string getPk()
 * @method mixed getRankedPosition()
 * @method mixed getSeenRankedPosition()
 * @method User getUser()
 * @method bool isBroadcasts()
 * @method bool isCanReply()
 * @method bool isCanReshare()
 * @method bool isLastSeenBroadcastTs()
 * @method bool isMuted()
 * @method bool isPeakViewerCount()
 * @method bool isPk()
 * @method bool isRankedPosition()
 * @method bool isSeenRankedPosition()
 * @method bool isUser()
 * @method this setBroadcasts(Broadcast[] $value)
 * @method this setCanReply(mixed $value)
 * @method this setCanReshare(mixed $value)
 * @method this setLastSeenBroadcastTs(mixed $value)
 * @method this setMuted(mixed $value)
 * @method this setPeakViewerCount(int $value)
 * @method this setPk(string $value)
 * @method this setRankedPosition(mixed $value)
 * @method this setSeenRankedPosition(mixed $value)
 * @method this setUser(User $value)
 * @method this unsetBroadcasts()
 * @method this unsetCanReply()
 * @method this unsetCanReshare()
 * @method this unsetLastSeenBroadcastTs()
 * @method this unsetMuted()
 * @method this unsetPeakViewerCount()
 * @method this unsetPk()
 * @method this unsetRankedPosition()
 * @method this unsetSeenRankedPosition()
 * @method this unsetUser()
 */
data class PostLiveItem (
    val pk                     : String,
    val user                   : User,
    val broadcasts             : MutableList<Broadcast>,
    val peak_viewer_count      : Int,
    val last_seen_broadcast_ts : String,
    val can_reply              : String,
    val ranked_position        : String,
    val seen_ranked_position   : String,
    val muted                  : String,
    val can_reshare            : String
){
//    val JSON_PROPERTY_MAP = [
//        "pk"                     => "string",
//        "user"                   => "User",
//        "broadcasts"             => "Broadcast[]",
//        "peak_viewer_count"      => "int",
//        "last_seen_broadcast_ts" => "",
//        "can_reply"              => "",
//        "ranked_position"        => "",
//        "seen_ranked_position"   => "",
//        "muted"                  => "",
//        "can_reshare"            => "",
//    ]
}
