

package instagramAPI.responses

import instagramAPI.Response

/**
 * MediaDeleteResponse.
 *
 * @method mixed getDidDelete()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isDidDelete()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setDidDelete(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetDidDelete()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class MediaDeleteResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "did_delete" => "",
    ]
}
