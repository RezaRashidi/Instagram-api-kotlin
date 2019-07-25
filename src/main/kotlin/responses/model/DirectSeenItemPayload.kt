

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * DirectSeenItemPayload.
 *
 * @method mixed getCount()
 * @method string getTimestamp()
 * @method bool isCount()
 * @method bool isTimestamp()
 * @method this setCount(mixed $value)
 * @method this setTimestamp(string $value)
 * @method this unsetCount()
 * @method this unsetTimestamp()
 */
data class DirectSeenItemPayload (
    val count     : String,
    val timestamp : String
){
//    val JSON_PROPERTY_MAP = [
//        "count"     => "",
//        "timestamp" => "string",
//    ]
}
