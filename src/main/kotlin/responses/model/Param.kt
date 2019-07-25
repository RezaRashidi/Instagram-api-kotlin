

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Param.
 *
 * @method mixed getName()
 * @method mixed getValue()
 * @method bool isName()
 * @method bool isValue()
 * @method this setName(mixed $value)
 * @method this setValue(mixed $value)
 * @method this unsetName()
 * @method this unsetValue()
 */
data class Param (
    val name  : String,
    val value : String
){
//    val JSON_PROPERTY_MAP = [
//        "name"  => "",
//        "value" => "",
//    ]
}
