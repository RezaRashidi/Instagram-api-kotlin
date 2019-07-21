

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Related.
 *
 * @method string getId()
 * @method mixed getName()
 * @method mixed getType()
 * @method bool isId()
 * @method bool isName()
 * @method bool isType()
 * @method this setId(string $value)
 * @method this setName(mixed $value)
 * @method this setType(mixed $value)
 * @method this unsetId()
 * @method this unsetName()
 * @method this unsetType()
 */
data class Related (
    val name  : String,
    val id    : String,
    val type  : String
){
//    val JSON_PROPERTY_MAP = [
//        "name" => "",
//        "id"   => "string",
//        "type" => "",
//    ]
}
