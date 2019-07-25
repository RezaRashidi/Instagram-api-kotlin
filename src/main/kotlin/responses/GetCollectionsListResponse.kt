

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Collection

/**
 * GetCollectionsListResponse.
 *
 * @method bool getAutoLoadMoreEnabled()
 * @method model.Collection[] getItems()
 * @method mixed getMessage()
 * @method bool getMoreAvailable()
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
 * @method this setAutoLoadMoreEnabled(bool $value)
 * @method this setItems(model.Collection[] $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(bool $value)
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
data class GetCollectionsListResponse (
    val items                  : MutableList<Collection>,
    val more_available         : Boolean,
    val auto_load_more_enabled : Boolean,
    val next_max_id            : String
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "items"                  to "model.Collection[]",
//        "more_available"         to "bool",
//        "auto_load_more_enabled" to "bool",
//        "next_max_id"            to "string"
//    )
}
