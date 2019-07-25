

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * BroadcastStatusItem.
 *
 * @method string getBroadcastStatus()
 * @method string getCoverFrameUrl()
 * @method bool getHasReducedVisibility()
 * @method string getId()
 * @method int getViewerCount()
 * @method bool isBroadcastStatus()
 * @method bool isCoverFrameUrl()
 * @method bool isHasReducedVisibility()
 * @method bool isId()
 * @method bool isViewerCount()
 * @method this setBroadcastStatus(string $value)
 * @method this setCoverFrameUrl(string $value)
 * @method this setHasReducedVisibility(bool $value)
 * @method this setId(string $value)
 * @method this setViewerCount(int $value)
 * @method this unsetBroadcastStatus()
 * @method this unsetCoverFrameUrl()
 * @method this unsetHasReducedVisibility()
 * @method this unsetId()
 * @method this unsetViewerCount()
 */
data class BroadcastStatusItem (
    val broadcast_status       : String,
    val has_reduced_visibility : Boolean,
    val cover_frame_url        : String,
    val viewer_count           : Int,
    val id                     : String
){
//    val JSON_PROPERTY_MAP = [
//        "broadcast_status"       => "string",
//        "has_reduced_visibility" => "bool",
//        "cover_frame_url"        => "string",
//        "viewer_count"           => "int",
//        "id"                     => "string",
//    ]
}
