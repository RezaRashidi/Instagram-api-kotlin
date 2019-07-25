

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Reel

/**
 * CreateHighlightResponse.
 *
 * @method mixed getMessage()
 * @method model.Reel getReel()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isReel()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setReel(model.Reel $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetReel()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class CreateHighlightResponse (
    val reel: Reel
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "reel" to "model.Reel"
//    )
}
