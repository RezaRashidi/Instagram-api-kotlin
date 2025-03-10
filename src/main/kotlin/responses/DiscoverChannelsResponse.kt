

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Item

/**
 * DiscoverChannelsResponse.
 *
 * @method mixed getAutoLoadMoreEnabled()
 * @method model.Item[] getItems()
 * @method mixed getMessage()
 * @method mixed getMoreAvailable()
 * @method string getNextMaxId()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isAutoLoadMoreEnabled()
 * @method bool isItems()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isNextMaxId()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setAutoLoadMoreEnabled(mixed $value)
 * @method this setItems(model.Item[] $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(mixed $value)
 * @method this setNextMaxId(string $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAutoLoadMoreEnabled()
 * @method this unsetItems()
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetNextMaxId()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class DiscoverChannelsResponse (
    val auto_load_more_enabled: String,
    val items: MutableList<Item>,
    val more_available: String,
    val next_max_id: String
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "auto_load_more_enabled" to "",
//        "items"                  to "model.Item[]",
//        "more_available"         to "",
//        "next_max_id"            to "string"
//    )
}
