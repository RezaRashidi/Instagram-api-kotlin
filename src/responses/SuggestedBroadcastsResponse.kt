

package instagramAPI.responses

import instagramAPI.Response

/**
 * SuggestedBroadcastsResponse.
 *
 * @method model.Broadcast[] getBroadcasts()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isBroadcasts()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setBroadcasts(model.Broadcast[] $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetBroadcasts()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class SuggestedBroadcastsResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "broadcasts" => "model.Broadcast[]",
    ]
}
