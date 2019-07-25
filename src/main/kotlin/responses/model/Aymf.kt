

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Aymf.
 *
 * @method AymfItem[] getItems()
 * @method mixed getMoreAvailable()
 * @method bool isItems()
 * @method bool isMoreAvailable()
 * @method this setItems(AymfItem[] $value)
 * @method this setMoreAvailable(mixed $value)
 * @method this unsetItems()
 * @method this unsetMoreAvailable()
 */
data class Aymf (
    val items          : MutableList<AymfItem>,
    val more_available : String
){
//    val JSON_PROPERTY_MAP = [
//        "items"          => "AymfItem[]",
//        "more_available" => "",
//    ]
}
