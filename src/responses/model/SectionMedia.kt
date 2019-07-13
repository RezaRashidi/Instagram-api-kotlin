

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * SectionMedia.
 *
 * @method Item getMedia()
 * @method bool isMedia()
 * @method this setMedia(Item $value)
 * @method this unsetMedia()
 */
class SectionMedia : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "media"  => "Item",
    ]
}
