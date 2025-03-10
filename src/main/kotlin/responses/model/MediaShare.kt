

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * MediaShare.
 *
 * @method Item getMedia()
 * @method string getText()
 * @method bool isMedia()
 * @method bool isText()
 * @method this setMedia(Item $value)
 * @method this setText(string $value)
 * @method this unsetMedia()
 * @method this unsetText()
 */
data class MediaShare (
    val media : Item,
    val text  : String
){
//    val JSON_PROPERTY_MAP = [
//        "media" => "Item",
//        "text"  => "string",
//    ]
}
