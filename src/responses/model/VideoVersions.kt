

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * VideoVersions.
 *
 * @method int getHeight()
 * @method string getId()
 * @method int getType()
 * @method string getUrl()
 * @method int getWidth()
 * @method bool isHeight()
 * @method bool isId()
 * @method bool isType()
 * @method bool isUrl()
 * @method bool isWidth()
 * @method this setHeight(int $value)
 * @method this setId(string $value)
 * @method this setType(int $value)
 * @method this setUrl(string $value)
 * @method this setWidth(int $value)
 * @method this unsetHeight()
 * @method this unsetId()
 * @method this unsetType()
 * @method this unsetUrl()
 * @method this unsetWidth()
 */
data class VideoVersions (
    val type   : Int,
    val width  : Int,
    val height : Int,
    val url    : String,
    val id     : String
){
//    val JSON_PROPERTY_MAP = [
//        "type"   => "int", // Some kinda internal type ID, such as int(102).
//        "width"  => "int",
//        "height" => "int",
//        "url"    => "string",
//        "id"     => "string",
//    ]
}
