

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * VideoCallEvent.
 *
 * @method string getAction()
 * @method string getVcId()
 * @method bool isAction()
 * @method bool isVcId()
 * @method this setAction(string $value)
 * @method this setVcId(string $value)
 * @method this unsetAction()
 * @method this unsetVcId()
 */
data class VideoCallEvent (
    val action : String,
    val vc_id  : String
){
    val VIDEO_CALL_STARTED = "video_call_started"
    val VIDEO_CALL_JOINED = "video_call_joined"
    val VIDEO_CALL_LEFT = "video_call_left"
    val VIDEO_CALL_ENDED = "video_call_ended"
    val UNKNOWN = "unknown"

//    val JSON_PROPERTY_MAP = [
//        "action" => "string",
//        "vc_id"  => "string",
//    ]
}
