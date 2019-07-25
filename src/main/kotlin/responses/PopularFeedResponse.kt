

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Item

/**
 * PopularFeedResponse.
 *
 * @method mixed getAutoLoadMoreEnabled()
 * @method model.Item[] getItems()
 * @method string getMaxId()
 * @method mixed getMessage()
 * @method mixed getMoreAvailable()
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
 * @method this setAutoLoadMoreEnabled(mixed $value)
 * @method this setItems(model.Item[] $value)
 * @method this setMaxId(string $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(mixed $value)
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
data class PopularFeedResponse (
    val next_max_id            : String,
    val more_available         : String,
    val auto_load_more_enabled : String,
    val items                  : MutableList<Item>,
    val num_results            : Int,
    val max_id                 : String
){
//    val JSON_PROPERTY_MAP = [
//        "next_max_id"            => "string",
//        "more_available"         => "",
//        "auto_load_more_enabled" => "",
//        "items"                  => "model.Item[]",
//        "num_results"            => "int",
//        "max_id"                 => "string",
//    ]
}
