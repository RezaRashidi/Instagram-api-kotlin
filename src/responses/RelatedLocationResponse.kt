

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Location

/**
 * RelatedLocationResponse.
 *
 * @method mixed getMessage()
 * @method model.Location[] getRelated()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isRelated()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setRelated(model.Location[] $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetRelated()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class RelatedLocationResponse (
    val related : MutableList<Location>
){
//    val JSON_PROPERTY_MAP = [
//        "related" => "model.Location[]",
//    ]
}
