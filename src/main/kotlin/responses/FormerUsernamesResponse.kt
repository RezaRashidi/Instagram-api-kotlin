

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.FormerUsername

/**
 * FormerUsernamesResponse.
 *
 * @method model.FormerUsername[] getFormerUsernames()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isFormerUsernames()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setFormerUsernames(model.FormerUsername[] $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetFormerUsernames()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class FormerUsernamesResponse (
    val former_usernames: MutableList<FormerUsername>
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "former_usernames" to "model.FormerUsername[]"
//    )
}
