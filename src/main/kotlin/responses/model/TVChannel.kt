

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * TVChannel.
 *
 * @method string getId()
 * @method Item[] getItems()
 * @method string getMaxId()
 * @method bool getMoreAvailable()
 * @method mixed getSeenState()
 * @method string getTitle()
 * @method string getType()
 * @method User getUserDict()
 * @method bool isId()
 * @method bool isItems()
 * @method bool isMaxId()
 * @method bool isMoreAvailable()
 * @method bool isSeenState()
 * @method bool isTitle()
 * @method bool isType()
 * @method bool isUserDict()
 * @method this setId(string $value)
 * @method this setItems(Item[] $value)
 * @method this setMaxId(string $value)
 * @method this setMoreAvailable(bool $value)
 * @method this setSeenState(mixed $value)
 * @method this setTitle(string $value)
 * @method this setType(string $value)
 * @method this setUserDict(User $value)
 * @method this unsetId()
 * @method this unsetItems()
 * @method this unsetMaxId()
 * @method this unsetMoreAvailable()
 * @method this unsetSeenState()
 * @method this unsetTitle()
 * @method this unsetType()
 * @method this unsetUserDict()
 */
data class TVChannel (
    val type              : String,
    val title             : String,
    val id                : String,
    val items             : MutableList<Item>,
    val more_available    : Boolean,
    val max_id            : String,
    val seen_state        : String,
    val user_dict         : User
){
//    val JSON_PROPERTY_MAP = [
//        "type"              => "string",
//        "title"             => "string",
//        "id"                => "string",
//        "items"             => "Item[]",
//        "more_available"    => "bool",
//        "max_id"            => "string",
//        "seen_state"        => "",
//        "user_dict"         => "User",
//    ]
}
