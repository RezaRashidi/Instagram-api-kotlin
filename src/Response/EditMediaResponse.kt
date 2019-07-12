

package instagramAPI.Response

import instagramAPI.Response

/**
 * EditMediaResponse.
 *
 * @method Model.Item getMedia()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method Model._Message[] get_Messages()
 * @method bool isMedia()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMedia(Model.Item $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(Model._Message[] $value)
 * @method this unsetMedia()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class EditMediaResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "media" to "Model.Item"
    )
}
