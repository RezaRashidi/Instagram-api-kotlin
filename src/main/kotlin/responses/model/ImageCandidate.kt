

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * ImageCandidate.
 *
 * @method int getHeight()
 * @method string getUrl()
 * @method int getWidth()
 * @method bool isHeight()
 * @method bool isUrl()
 * @method bool isWidth()
 * @method this setHeight(int $value)
 * @method this setUrl(string $value)
 * @method this setWidth(int $value)
 * @method this unsetHeight()
 * @method this unsetUrl()
 * @method this unsetWidth()
 */
data class ImageCandidate (
    val url    : String,
    val width  : Int,
    val height : Int
){
//    val JSON_PROPERTY_MAP = [
//        "url"    => "string",
//        "width"  => "int",
//        "height" => "int",
//    ]
}
