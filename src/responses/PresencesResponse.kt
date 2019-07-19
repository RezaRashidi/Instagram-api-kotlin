

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.unpredictableKeys.PresenceUnpredictableContainer

/**
 * PresencesResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model.unpredictableKeys.PresenceUnpredictableContainer getUserPresence()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isUserPresence()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setUserPresence(model.unpredictableKeys.PresenceUnpredictableContainer $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetUserPresence()
 * @method this unset_Messages()
 */
data class PresencesResponse (
    val user_presence: PresenceUnpredictableContainer
){
//    val JSON_PROPERTY_MAP = [
//        "user_presence" => "model.unpredictableKeys.PresenceUnpredictableContainer",
//    ]
}
