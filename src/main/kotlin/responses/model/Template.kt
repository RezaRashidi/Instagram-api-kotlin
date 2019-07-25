

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Template.
 *
 * @method string getName()
 * @method mixed getParameters()
 * @method bool isName()
 * @method bool isParameters()
 * @method this setName(string $value)
 * @method this setParameters(mixed $value)
 * @method this unsetName()
 * @method this unsetParameters()
 */
data class Template (
    val name       : String,
    val parameters : String
){
//    val JSON_PROPERTY_MAP = [
//        "name"       => "string",
//        "parameters" => "",
//    ]
}
