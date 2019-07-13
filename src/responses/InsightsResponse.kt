

package instagramAPI.responses

import instagramAPI.Response

/**
 * InsightsResponse.
 *
 * @method model.Insights getInstagramUser()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isInstagramUser()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setInstagramUser(model.Insights $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetInstagramUser()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class InsightsResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "instagram_user" => "model.Insights",
    ]
}
