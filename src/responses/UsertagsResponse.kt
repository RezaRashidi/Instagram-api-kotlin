

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Item

/**
 * UsertagsResponse.
 *
 * @method mixed getAutoLoadMoreEnabled()
 * @method model.Item[] getItems()
 * @method mixed getMessage()
 * @method mixed getMoreAvailable()
 * @method mixed getNewPhotos()
 * @method string getNextMaxId()
 * @method int getNumResults()
 * @method mixed getRequiresReview()
 * @method string getStatus()
 * @method mixed getTotalCount()
 * @method model._Message[] get_Messages()
 * @method bool isAutoLoadMoreEnabled()
 * @method bool isItems()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isNewPhotos()
 * @method bool isNextMaxId()
 * @method bool isNumResults()
 * @method bool isRequiresReview()
 * @method bool isStatus()
 * @method bool isTotalCount()
 * @method bool is_Messages()
 * @method this setAutoLoadMoreEnabled(mixed $value)
 * @method this setItems(model.Item[] $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(mixed $value)
 * @method this setNewPhotos(mixed $value)
 * @method this setNextMaxId(string $value)
 * @method this setNumResults(int $value)
 * @method this setRequiresReview(mixed $value)
 * @method this setStatus(string $value)
 * @method this setTotalCount(mixed $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAutoLoadMoreEnabled()
 * @method this unsetItems()
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetNewPhotos()
 * @method this unsetNextMaxId()
 * @method this unsetNumResults()
 * @method this unsetRequiresReview()
 * @method this unsetStatus()
 * @method this unsetTotalCount()
 * @method this unset_Messages()
 */
data class UsertagsResponse (
    val num_results            : Int,
    val auto_load_more_enabled : String,
    val items                  : MutableList<Item>,
    val more_available         : String,
    val next_max_id            : String,
    val total_count            : String,
    val requires_review        : String,
    val new_photos             : String
){
//    val JSON_PROPERTY_MAP = [
//        "num_results"            => "int",
//        "auto_load_more_enabled" => "",
//        "items"                  => "model.Item[]",
//        "more_available"         => "",
//        "next_max_id"            => "string",
//        "total_count"            => "",
//        "requires_review"        => "",
//        "new_photos"             => "",
//    ]
}
