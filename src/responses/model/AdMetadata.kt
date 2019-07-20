

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * AdMetadata.
 *
 * @method mixed getType()
 * @method mixed getValue()
 * @method bool isType()
 * @method bool isValue()
 * @method this setType(mixed $value)
 * @method this setValue(mixed $value)
 * @method this unsetType()
 * @method this unsetValue()
 */
data class AdMetadata (
    val value : String,
    val type  : String
){
//    val JSON_PROPERTY_MAP = [
//        "value" => "",
//        "type"  => "",
//    ]
}
