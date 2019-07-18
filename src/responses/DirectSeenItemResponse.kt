

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.DirectSendItemPayload

/**
 * DirectSeenItemResponse.
 *
 * @method mixed getAction()
 * @method mixed getMessage()
 * @method model.DirectSeenItemPayload getPayload()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isAction()
 * @method bool isMessage()
 * @method bool isPayload()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setAction(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setPayload(model.DirectSeenItemPayload $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAction()
 * @method this unsetMessage()
 * @method this unsetPayload()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class DirectSeenItemResponse (
    val action: String,
    val payload: DirectSendItemPayload
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "action"  to "",
//        "payload" to "model.DirectSeenItemPayload" // The number of unseen items.
    )
}
