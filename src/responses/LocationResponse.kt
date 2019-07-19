

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Location

/**
 * LocationResponse.
 *
 * @method mixed getMessage()
 * @method string getRequestId()
 * @method string getStatus()
 * @method model.Location[] getVenues()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isRequestId()
 * @method bool isStatus()
 * @method bool isVenues()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setRequestId(string $value)
 * @method this setStatus(string $value)
 * @method this setVenues(model.Location[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetRequestId()
 * @method this unsetStatus()
 * @method this unsetVenues()
 * @method this unset_Messages()
 */
data class LocationResponse (
    val venues     : Location,
    val request_id : String
){
//    val JSON_PROPERTY_MAP = [
//        "venues"     => "model.Location[]",
//        "request_id" => "string",
//    ]
}
