

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * DirectThreadLastSeenAt.
 *
 * @method string getItemId()
 * @method mixed getTimestamp()
 * @method bool isItemId()
 * @method bool isTimestamp()
 * @method this setItemId(string $value)
 * @method this setTimestamp(mixed $value)
 * @method this unsetItemId()
 * @method this unsetTimestamp()
 */
data class DirectThreadLastSeenAt (
    val item_id   : String,
    val timestamp : String
){
//    val JSON_PROPERTY_MAP = [
//        "item_id"   => "string",
//        "timestamp" => "",
//    ]
}
