

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * FormerUsername.
 *
 * @method string getChangeTimestamp()
 * @method string getFormerUsername()
 * @method bool isChangeTimestamp()
 * @method bool isFormerUsername()
 * @method this setChangeTimestamp(string $value)
 * @method this setFormerUsername(string $value)
 * @method this unsetChangeTimestamp()
 * @method this unsetFormerUsername()
 */
data class FormerUsername (
    val former_username  : String,
    val change_timestamp : String
){
//    val JSON_PROPERTY_MAP = [
//        "former_username"  => "string",
//        "change_timestamp" => "string",
//    ]
}
