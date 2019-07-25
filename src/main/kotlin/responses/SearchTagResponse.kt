

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Tag

/**
 * SearchTagResponse.
 *
 * @method bool getHasMore()
 * @method mixed getMessage()
 * @method string getRankToken()
 * @method model.Tag[] getResults()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isHasMore()
 * @method bool isMessage()
 * @method bool isRankToken()
 * @method bool isResults()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setHasMore(bool $value)
 * @method this setMessage(mixed $value)
 * @method this setRankToken(string $value)
 * @method this setResults(model.Tag[] $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetHasMore()
 * @method this unsetMessage()
 * @method this unsetRankToken()
 * @method this unsetResults()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class SearchTagResponse (
    val has_more   : Boolean,
    val results    : MutableList<Tag>,
    val rank_token : String
){
//    val JSON_PROPERTY_MAP = [
//        "has_more"   => "bool",
//        "results"    => "model.Tag[]",
//        "rank_token" => "string",
//    ]
}
