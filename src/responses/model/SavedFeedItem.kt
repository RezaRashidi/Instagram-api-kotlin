

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * SavedFeedItem.
 *
 * @method Item getMedia()
 * @method bool isMedia()
 * @method this setMedia(Item $value)
 * @method this unsetMedia()
 */
data class SavedFeedItem (
    val media : Item
){
//    val JSON_PROPERTY_MAP = [
//        "media" => "Item",
//    ]
}
