

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Ad4ad.
 *
 * @method mixed getFooter()
 * @method string getId()
 * @method Item getMedia()
 * @method mixed getTitle()
 * @method string getTrackingToken()
 * @method mixed getType()
 * @method bool isFooter()
 * @method bool isId()
 * @method bool isMedia()
 * @method bool isTitle()
 * @method bool isTrackingToken()
 * @method bool isType()
 * @method this setFooter(mixed $value)
 * @method this setId(string $value)
 * @method this setMedia(Item $value)
 * @method this setTitle(mixed $value)
 * @method this setTrackingToken(string $value)
 * @method this setType(mixed $value)
 * @method this unsetFooter()
 * @method this unsetId()
 * @method this unsetMedia()
 * @method this unsetTitle()
 * @method this unsetTrackingToken()
 * @method this unsetType()
 */
data class Ad4ad (
    val type           : String,
    val title          : String,
    val media          : Item,
    val footer         : String,
    val id             : String,
    val tracking_token : String
){
//    val JSON_PROPERTY_MAP = [
//        "type"           => "",
//        "title"          => "",
//        "media"          => "Item",
//        "footer"         => "",
//        "id"             => "string",
//        "tracking_token" => "string",
//    ]
}
