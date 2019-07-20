

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Groups.
 *
 * @method Item[] getItems()
 * @method mixed getType()
 * @method bool isItems()
 * @method bool isType()
 * @method this setItems(Item[] $value)
 * @method this setType(mixed $value)
 * @method this unsetItems()
 * @method this unsetType()
 */
data class Groups (
    val type  : String,
    val items : MutableList<Item>
){
//    val JSON_PROPERTY_MAP = [
//        "type"  => "",
//        "items" => "Item[]",
//    ]
}
