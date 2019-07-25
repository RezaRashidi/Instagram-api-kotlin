

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * AnimatedMedia.
 *
 * @method string getId()
 * @method AnimatedMediaImage getImages()
 * @method bool isId()
 * @method bool isImages()
 * @method this setId(string $value)
 * @method this setImages(AnimatedMediaImage $value)
 * @method this unsetId()
 * @method this unsetImages()
 */
data class AnimatedMedia (
    val id       : String,
    val images   : AnimatedMediaImage
){
//    val JSON_PROPERTY_MAP = [
//        "id"       => "string",
//        "images"   => "AnimatedMediaImage",
//    ]
}
