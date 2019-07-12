

package instagramAPI.Response.Model

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
class AnimatedMedia : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "id"       => "string",
        "images"   => "AnimatedMediaImage",
    ]
}
