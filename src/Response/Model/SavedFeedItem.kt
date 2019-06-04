

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

/**
 * SavedFeedItem.
 *
 * @method Item getMedia()
 * @method bool isMedia()
 * @method this setMedia(Item $value)
 * @method this unsetMedia()
 */
class SavedFeedItem : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "media" => "Item",
    ]
}
