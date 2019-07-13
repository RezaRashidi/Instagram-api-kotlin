

package instagramAPI.responses

import instagramAPI.Response

/**
 * RecentSearchesResponse.
 *
 * @method mixed getMessage()
 * @method model.Suggested[] getRecent()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isRecent()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setRecent(model.Suggested[] $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetRecent()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class RecentSearchesResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "recent" => "model.Suggested[]",
    ]
}
