

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.SavedFeedItem

/**
 * CollectionFeedResponse.
 *
 * @method bool getAutoLoadMoreEnabled()
 * @method string getCollectionId()
 * @method string getCollectionName()
 * @method bool getHasRelatedMedia()
 * @method model.SavedFeedItem[] getItems()
 * @method mixed getMessage()
 * @method bool getMoreAvailable()
 * @method string getNextMaxId()
 * @method int getNumResults()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isAutoLoadMoreEnabled()
 * @method bool isCollectionId()
 * @method bool isCollectionName()
 * @method bool isHasRelatedMedia()
 * @method bool isItems()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isNextMaxId()
 * @method bool isNumResults()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setAutoLoadMoreEnabled(bool $value)
 * @method this setCollectionId(string $value)
 * @method this setCollectionName(string $value)
 * @method this setHasRelatedMedia(bool $value)
 * @method this setItems(model.SavedFeedItem[] $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(bool $value)
 * @method this setNextMaxId(string $value)
 * @method this setNumResults(int $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAutoLoadMoreEnabled()
 * @method this unsetCollectionId()
 * @method this unsetCollectionName()
 * @method this unsetHasRelatedMedia()
 * @method this unsetItems()
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetNextMaxId()
 * @method this unsetNumResults()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class CollectionFeedResponse (
    val collection_id          : String,
    val collection_name        : String,
    val items                  : MutableList<SavedFeedItem>,
    val num_results            : Int,
    val more_available         : Boolean,
    val auto_load_more_enabled : Boolean,
    val next_max_id            : String,
    val has_related_media      : Boolean
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "collection_id"          to "string",
//        "collection_name"        to "string",
//        "items"                  to "model.SavedFeedItem[]",
//        "num_results"            to "int",
//        "more_available"         to "bool",
//        "auto_load_more_enabled" to "bool",
//        "next_max_id"            to "string",
//        "has_related_media"      to "bool"
//    )
}
