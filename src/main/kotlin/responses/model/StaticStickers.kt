

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * StaticStickers.
 *
 * @method string getId()
 * @method mixed getIncludeInRecent()
 * @method Stickers[] getStickers()
 * @method bool isId()
 * @method bool isIncludeInRecent()
 * @method bool isStickers()
 * @method this setId(string $value)
 * @method this setIncludeInRecent(mixed $value)
 * @method this setStickers(Stickers[] $value)
 * @method this unsetId()
 * @method this unsetIncludeInRecent()
 * @method this unsetStickers()
 */
data class StaticStickers (
    val include_in_recent : String,
    val id                : String,
    val stickers          : MutableList<Stickers>
){
//    val JSON_PROPERTY_MAP = [
//        "include_in_recent" => "",
//        "id"                => "string",
//        "stickers"          => "Stickers[]",
//    ]
}
