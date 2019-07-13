

package instagramAPI.responses

import instagramAPI.Response

/**
 * DirectThreadResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model.DirectThread getThread()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isThread()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setThread(model.DirectThread $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetThread()
 * @method this unset_Messages()
 */
class DirectThreadResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "thread" to "model.DirectThread"
    )
}
