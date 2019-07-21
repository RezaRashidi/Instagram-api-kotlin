

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Tab.
 *
 * @method string getTitle()
 * @method string getType()
 * @method bool isTitle()
 * @method bool isType()
 * @method this setTitle(string $value)
 * @method this setType(string $value)
 * @method this unsetTitle()
 * @method this unsetType()
 */
data class Tab (
    val type  : String,
    val title : String
){
//    val JSON_PROPERTY_MAP = [
//        "type"  => "string",
//        "title" => "string",
//    ]
}
