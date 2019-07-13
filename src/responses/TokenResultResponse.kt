

package instagramAPI.responses

import instagramAPI.Response

/**
 * TokenResultResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model.Token getToken()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isToken()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setToken(model.Token $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetToken()
 * @method this unset_Messages()
 */
class TokenResultResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "token" => "model.Token",
    ]
}
