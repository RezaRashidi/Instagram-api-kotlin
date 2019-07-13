

package instagramAPI.responses

import instagramAPI.Response

/**
 * CommentFilterResponse.
 *
 * @method mixed getConfigValue()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method Model._Message[] get_Messages()
 * @method bool isConfigValue()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setConfigValue(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(Model._Message[] $value)
 * @method this unsetConfigValue()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class CommentFilterResponse : Response() {
    override val JSON_PROPERTY_MAP = mapOf(
        "config_value" to ""
    )
}
