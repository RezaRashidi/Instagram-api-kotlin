

package instagramAPI.responses

import instagramAPI.Response

/**
 * SavedFeedResponse.
 *
 * @method mixed getAutoLoadMoreEnabled()
 * @method model.SavedFeedItem[] getItems()
 * @method mixed getMessage()
 * @method mixed getMoreAvailable()
 * @method string getNextMaxId()
 * @method int getNumResults()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isAutoLoadMoreEnabled()
 * @method bool isItems()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isNextMaxId()
 * @method bool isNumResults()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setAutoLoadMoreEnabled(mixed $value)
 * @method this setItems(model.SavedFeedItem[] $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(mixed $value)
 * @method this setNextMaxId(string $value)
 * @method this setNumResults(int $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAutoLoadMoreEnabled()
 * @method this unsetItems()
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetNextMaxId()
 * @method this unsetNumResults()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class SavedFeedResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "items"                  => "model.SavedFeedItem[]",
        "more_available"         => "",
        "next_max_id"            => "string",
        "auto_load_more_enabled" => "",
        "num_results"            => "int",
    ]
}
