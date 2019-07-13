

package instagramAPI.responses

import instagramAPI.Response

/**
 * TVSearchResponse.
 *
 * @method mixed getMessage()
 * @method int getNumResults()
 * @method string getRankToken()
 * @method model.TVSearchResult[] getResults()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isNumResults()
 * @method bool isRankToken()
 * @method bool isResults()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setNumResults(int $value)
 * @method this setRankToken(string $value)
 * @method this setResults(model.TVSearchResult[] $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetNumResults()
 * @method this unsetRankToken()
 * @method this unsetResults()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class TVSearchResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "results"       => "model.TVSearchResult[]",
        "num_results"   => "int",
        "rank_token"    => "string",
    ]
}
