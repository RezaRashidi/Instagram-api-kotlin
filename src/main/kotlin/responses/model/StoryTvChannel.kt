

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * StoryTvChannel.
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
data class StoryTvChannel (
    val id             : String,
    val items          : MutableList<Item>,
    val title          : String,
    val type           : String,
    val max_id         : String,
    val more_available : Boolean,
    val seen_state     : mixed,
    val user_dict      : User
){
//    val JSON_PROPERTY_MAP = [
//        "id"             => "string",
//        "items"          => "Item[]",
//        "title"          => "string",
//        "type"           => "string",
//        "max_id"         => "string",
//        "more_available" => "bool",
//        "seen_state"     => "mixed",
//        "user_dict"      => "User",
//    ]
}
