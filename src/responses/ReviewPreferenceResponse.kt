

package instagramAPI.responses

import instagramAPI.Response

/**
 * ReviewPreferenceResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model.User getUser()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isUser()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setUser(model.User $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetUser()
 * @method this unset_Messages()
 */
class ReviewPreferenceResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "user" => "model.User",
    ]
}
