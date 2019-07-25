

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.CountdownSticker

/**
 * StoryCountdownsResponse.
 *
 * @method model.CountdownSticker[] getCountdowns()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isCountdowns()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setCountdowns(model.CountdownSticker[] $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetCountdowns()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class StoryCountdownsResponse (
    val countdowns : MutableList<CountdownSticker>
){
//    val JSON_PROPERTY_MAP = [
//        "countdowns" => "model.CountdownSticker[]",
//    ]
}
