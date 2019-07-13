

package instagramAPI.responses

import instagramAPI.Response

/**
 * TagRelatedResponse.
 *
 * @method mixed getMessage()
 * @method model.Related[] getRelated()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isRelated()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setRelated(model.Related[] $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetRelated()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class TagRelatedResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "related" => "model.Related[]",
    ]
}
