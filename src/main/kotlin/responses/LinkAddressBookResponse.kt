

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Suggestion

/**
 * LinkAddressBookResponse.
 *
 * @method model.Suggestion[] getItems()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isItems()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setItems(model.Suggestion[] $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetItems()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class LinkAddressBookResponse (
    val items : MutableList<Suggestion>
){
//    val JSON_PROPERTY_MAP = [
//        "items" => "model.Suggestion[]",
//    ]
}
