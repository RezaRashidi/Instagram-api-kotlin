

package instagramAPI.responses

import instagramAPI.Response

/**
 * SuggestedSearchesResponse.
 *
 * @method mixed getMessage()
 * @method string getRankToken()
 * @method string getStatus()
 * @method model.Suggested[] getSuggested()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isRankToken()
 * @method bool isStatus()
 * @method bool isSuggested()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setRankToken(string $value)
 * @method this setStatus(string $value)
 * @method this setSuggested(model.Suggested[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetRankToken()
 * @method this unsetStatus()
 * @method this unsetSuggested()
 * @method this unset_Messages()
 */
class SuggestedSearchesResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "suggested"  => "model.Suggested[]",
        "rank_token" => "string",
    ]
}
