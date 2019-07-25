

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * DirectExpiringSummary.
 *
 * @method int getCount()
 * @method string getTimestamp()
 * @method string getType()
 * @method bool isCount()
 * @method bool isTimestamp()
 * @method bool isType()
 * @method this setCount(int $value)
 * @method this setTimestamp(string $value)
 * @method this setType(string $value)
 * @method this unsetCount()
 * @method this unsetTimestamp()
 * @method this unsetType()
 */
data class DirectExpiringSummary (
    val type      : String,
    val timestamp : String,
    val count     : Int
){
//    val JSON_PROPERTY_MAP = [
//        "type"      => "string",
//        "timestamp" => "string",
//        "count"     => "int",
//    ]
}
