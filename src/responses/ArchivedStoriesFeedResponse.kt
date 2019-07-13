

package instagramAPI.responses

import instagramAPI.Response

/**
 * ArchivedStoriesFeedResponse.
 *
 * @method model.ArchivedStoriesFeedItem[] getItems()
 * @method string getMaxId()
 * @method mixed getMessage()
 * @method bool getMoreAvailable()
 * @method int getNumResults()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isItems()
 * @method bool isMaxId()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isNumResults()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setItems(model.ArchivedStoriesFeedItem[] $value)
 * @method this setMaxId(string $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(bool $value)
 * @method this setNumResults(int $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetItems()
 * @method this unsetMaxId()
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetNumResults()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class ArchivedStoriesFeedResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "items"                  to "model.ArchivedStoriesFeedItem[]",
        "num_results"            to "int",
        "more_available"         to "bool",
        "max_id"                 to "string"
    )
}
