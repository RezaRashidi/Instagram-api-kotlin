

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Bold.
 *
 * @method mixed getEnd()
 * @method mixed getStart()
 * @method bool isEnd()
 * @method bool isStart()
 * @method this setEnd(mixed $value)
 * @method this setStart(mixed $value)
 * @method this unsetEnd()
 * @method this unsetStart()
 */
data class Bold (
    val start : String,
    val end   : String
){
//    val JSON_PROPERTY_MAP = [
//        "start" => "",
//        "end"   => "",
//    ]
}
