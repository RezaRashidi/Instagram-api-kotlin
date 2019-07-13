

package instagramAPI.realtimes.Payload.Action

import instagramAPI.Realtime.Payload.RealtimeAction

/**
 * AckAction.
 *
 * @method string getAction()
 * @method .instagramAPI.responses.Model.DirectSendItemPayload getPayload()
 * @method string getStatus()
 * @method mixed getStatusCode()
 * @method bool isAction()
 * @method bool isPayload()
 * @method bool isStatus()
 * @method bool isStatusCode()
 * @method this setAction(string $value)
 * @method this setPayload(.instagramAPI.responses.Model.DirectSendItemPayload $value)
 * @method this setStatus(string $value)
 * @method this setStatusCode(mixed $value)
 * @method this unsetAction()
 * @method this unsetPayload()
 * @method this unsetStatus()
 * @method this unsetStatusCode()
 */
class AckAction : RealtimeAction
{
    val JSON_PROPERTY_MAP = [
        "status_code" => "",
        "payload"     => ".instagramAPI.responses.Model.DirectSendItemPayload",
    ]
}
