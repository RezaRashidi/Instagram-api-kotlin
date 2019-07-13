

package instagramAPI.responses

import instagramAPI.Response

/**
 * EditMediaResponse.
 *
 * @method model.Item getMedia()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMedia()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMedia(model.Item $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMedia()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class EditMediaResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "media" to "model.Item"
    )
}
