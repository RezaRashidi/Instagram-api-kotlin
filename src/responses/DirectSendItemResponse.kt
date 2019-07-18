

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.DirectSendItemPayload

/**
 * DirectSendItemResponse.
 *
 * @method mixed getAction()
 * @method mixed getMessage()
 * @method model.DirectSendItemPayload getPayload()
 * @method string getStatus()
 * @method mixed getStatusCode()
 * @method model._Message[] get_Messages()
 * @method bool isAction()
 * @method bool isMessage()
 * @method bool isPayload()
 * @method bool isStatus()
 * @method bool isStatusCode()
 * @method bool is_Messages()
 * @method this setAction(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setPayload(model.DirectSendItemPayload $value)
 * @method this setStatus(string $value)
 * @method this setStatusCode(mixed $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAction()
 * @method this unsetMessage()
 * @method this unsetPayload()
 * @method this unsetStatus()
 * @method this unsetStatusCode()
 * @method this unset_Messages()
 */
data class DirectSendItemResponse(
    val action: String,
    val status_code: String,
    val payload: DirectSendItemPayload
) {
//    val JSON_PROPERTY_MAP = [
//        "action"      => "",
//        "status_code" => "",
//        "payload"     => "model.DirectSendItemPayload",
//    ]
}
