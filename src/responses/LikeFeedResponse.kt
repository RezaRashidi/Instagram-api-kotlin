

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Item

/**
 * LikeFeedResponse.
 *
 * @method mixed getAutoLoadMoreEnabled()
 * @method model.Item[] getItems()
 * @method mixed getLastCountedAt()
 * @method mixed getMessage()
 * @method mixed getMoreAvailable()
 * @method string getNextMaxId()
 * @method int getNumResults()
 * @method mixed getPatches()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isAutoLoadMoreEnabled()
 * @method bool isItems()
 * @method bool isLastCountedAt()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isNextMaxId()
 * @method bool isNumResults()
 * @method bool isPatches()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setAutoLoadMoreEnabled(mixed $value)
 * @method this setItems(model.Item[] $value)
 * @method this setLastCountedAt(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(mixed $value)
 * @method this setNextMaxId(string $value)
 * @method this setNumResults(int $value)
 * @method this setPatches(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAutoLoadMoreEnabled()
 * @method this unsetItems()
 * @method this unsetLastCountedAt()
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetNextMaxId()
 * @method this unsetNumResults()
 * @method this unsetPatches()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class LikeFeedResponse (
    val auto_load_more_enabled : String,
    val items                  : MutableList<Item>,
    val more_available         : String,
    val patches                : String,
    val last_counted_at        : String,
    val num_results            : Int,
    val next_max_id            : String
){
//    val JSON_PROPERTY_MAP = [
//        "auto_load_more_enabled" => "",
//        "items"                  => "model.Item[]",
//        "more_available"         => "",
//        "patches"                => "",
//        "last_counted_at"        => "",
//        "num_results"            => "int",
//        "next_max_id"            => "string",
//    ]
}
