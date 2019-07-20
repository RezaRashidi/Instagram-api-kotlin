

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * _Message.
 *
 * @method mixed getKey()
 * @method mixed getTime()
 * @method bool isKey()
 * @method bool isTime()
 * @method this setKey(mixed $value)
 * @method this setTime(mixed $value)
 * @method this unsetKey()
 * @method this unsetTime()
 */
data class _Message (
    val key  : String,
    val time : String
){
//    val JSON_PROPERTY_MAP = [
//        "key"  => "",
//        "time" => "",
//    ]
}
