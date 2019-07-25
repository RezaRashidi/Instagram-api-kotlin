

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Experiment

/**
 * SyncResponse.
 *
 * @method model.Experiment[] getExperiments()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isExperiments()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setExperiments(model.Experiment[] $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetExperiments()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class SyncResponse (
    val experiments : MutableList<Experiment>
){
//    val JSON_PROPERTY_MAP = [
//        "experiments" => "model.Experiment[]",
//    ]
}
