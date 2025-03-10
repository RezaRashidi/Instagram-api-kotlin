

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * LiveVideoShare.
 *
 * @method Broadcast getBroadcast()
 * @method string getText()
 * @method int getVideoOffset()
 * @method bool isBroadcast()
 * @method bool isText()
 * @method bool isVideoOffset()
 * @method this setBroadcast(Broadcast $value)
 * @method this setText(string $value)
 * @method this setVideoOffset(int $value)
 * @method this unsetBroadcast()
 * @method this unsetText()
 * @method this unsetVideoOffset()
 */
data class LiveVideoShare (
    val text                : String,
    val broadcast           : Broadcast,
    val video_offset        : Int
){
//    val JSON_PROPERTY_MAP = [
//        "text"                => "string",
//        "broadcast"           => "Broadcast",
//        "video_offset"        => "int",
//    ]
}
