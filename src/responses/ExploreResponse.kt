

package instagramAPI.responses

import instagramAPI.Response

/**
 * ExploreResponse.
 *
 * @method mixed getAutoLoadMoreEnabled()
 * @method model.ExploreItem[] getItems()
 * @method string getMaxId()
 * @method mixed getMessage()
 * @method mixed getMoreAvailable()
 * @method string getNextMaxId()
 * @method int getNumResults()
 * @method string getRankToken()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isAutoLoadMoreEnabled()
 * @method bool isItems()
 * @method bool isMaxId()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isNextMaxId()
 * @method bool isNumResults()
 * @method bool isRankToken()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setAutoLoadMoreEnabled(mixed $value)
 * @method this setItems(model.ExploreItem[] $value)
 * @method this setMaxId(string $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(mixed $value)
 * @method this setNextMaxId(string $value)
 * @method this setNumResults(int $value)
 * @method this setRankToken(string $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAutoLoadMoreEnabled()
 * @method this unsetItems()
 * @method this unsetMaxId()
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetNextMaxId()
 * @method this unsetNumResults()
 * @method this unsetRankToken()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class ExploreResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "num_results"            to "int",
        "auto_load_more_enabled" to "",
        "items"                  to "model.ExploreItem[]",
        "more_available"         to "",
        "next_max_id"            to "string",
        "max_id"                 to "string",
        "rank_token"             to "string"
    )
}
