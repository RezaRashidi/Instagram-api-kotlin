

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.GraphData

/**
 * GraphqlResponse.
 *
 * @method model.GraphData getData()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isData()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setData(model.GraphData $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetData()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class GraphqlResponse (
    val data : GraphData
){
//    val JSON_PROPERTY_MAP = [
//        "data"            => "model.GraphData",
//    ]

    /**
     * Checks if the response was successful.
     *
     * @return bool
     */
    fun isOk(): Boolean {
        return true
    }
}
