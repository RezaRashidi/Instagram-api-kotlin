

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Item

/**
 * MediaInfoResponse.
 *
 * @method mixed getAutoLoadMoreEnabled()
 * @method model.Item[] getItems()
 * @method mixed getMessage()
 * @method mixed getMoreAvailable()
 * @method int getNumResults()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isAutoLoadMoreEnabled()
 * @method bool isItems()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isNumResults()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setAutoLoadMoreEnabled(mixed $value)
 * @method this setItems(model.Item[] $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(mixed $value)
 * @method this setNumResults(int $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAutoLoadMoreEnabled()
 * @method this unsetItems()
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetNumResults()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class MediaInfoResponse (
    val auto_load_more_enabled : String,
    val num_results            : Int,
    val more_available         : String,
    val items                  : MutableList<Item>
){
//    val JSON_PROPERTY_MAP = [
//        "auto_load_more_enabled" => "",
//        "num_results"            => "int",
//        "more_available"         => "",
//        "items"                  => "model.Item[]",
//    ]
}
