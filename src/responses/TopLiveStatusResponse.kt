

package instagramAPI.responses

import instagramAPI.Response

/**
 * TopLiveStatusResponse.
 *
 * @method model.BroadcastStatusItem[] getBroadcastStatusItems()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isBroadcastStatusItems()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setBroadcastStatusItems(model.BroadcastStatusItem[] $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetBroadcastStatusItems()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class TopLiveStatusResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "broadcast_status_items" => "model.BroadcastStatusItem[]",
    ]
}
