

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Item

/**
 * UserFeedResponse.
 *
 * @method bool getAutoLoadMoreEnabled()
 * @method model.Item[] getItems()
 * @method string getMaxId()
 * @method mixed getMessage()
 * @method bool getMoreAvailable()
 * @method string getNextMaxId()
 * @method int getNumResults()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isAutoLoadMoreEnabled()
 * @method bool isItems()
 * @method bool isMaxId()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isNextMaxId()
 * @method bool isNumResults()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setAutoLoadMoreEnabled(bool $value)
 * @method this setItems(model.Item[] $value)
 * @method this setMaxId(string $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(bool $value)
 * @method this setNextMaxId(string $value)
 * @method this setNumResults(int $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAutoLoadMoreEnabled()
 * @method this unsetItems()
 * @method this unsetMaxId()
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetNextMaxId()
 * @method this unsetNumResults()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class UserFeedResponse (
    val items                  : MutableList<Item>,
    val num_results            : Int,
    val more_available         : Boolean,
    val next_max_id            : String,
    val max_id                 : String,
    val auto_load_more_enabled : Boolean
){
//    val JSON_PROPERTY_MAP = [
//        "items"                  => "model.Item[]",
//        "num_results"            => "int",
//        "more_available"         => "bool",
//        "next_max_id"            => "string",
//        "max_id"                 => "string",
//        "auto_load_more_enabled" => "bool",
//    ]
}
