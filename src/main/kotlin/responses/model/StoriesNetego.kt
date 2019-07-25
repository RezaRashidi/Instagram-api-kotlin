

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * StoriesNetego.
 *
 * @method string getHideUnitIfSeen()
 * @method string getId()
 * @method string getTrackingToken()
 * @method bool isHideUnitIfSeen()
 * @method bool isId()
 * @method bool isTrackingToken()
 * @method this setHideUnitIfSeen(string $value)
 * @method this setId(string $value)
 * @method this setTrackingToken(string $value)
 * @method this unsetHideUnitIfSeen()
 * @method this unsetId()
 * @method this unsetTrackingToken()
 */
data class StoriesNetego (
    val tracking_token    : String,
    val hide_unit_if_seen : String,
    val id                : String
){
//    val JSON_PROPERTY_MAP = [
//        "tracking_token"    => "string",
//        "hide_unit_if_seen" => "string",
//        "id"                => "string",
//    ]
}
