

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.SystemControl
import instagramAPI.responses.model.TraceControl

/**
 * LoomFetchConfigResponse.
 *
 * @method int getId()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model.SystemControl getSystemControl()
 * @method model.TraceControl getTraceControl()
 * @method model._Message[] get_Messages()
 * @method bool isId()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isSystemControl()
 * @method bool isTraceControl()
 * @method bool is_Messages()
 * @method this setId(int $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setSystemControl(model.SystemControl $value)
 * @method this setTraceControl(model.TraceControl $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetId()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetSystemControl()
 * @method this unsetTraceControl()
 * @method this unset_Messages()
 */
data class LoomFetchConfigResponse (
    val system_control : SystemControl,
    val trace_control  : TraceControl,
    val id             : Int
    ){
//    val JSON_PROPERTY_MAP = [
//        "system_control" => "model.SystemControl",
//        "trace_control"  => "model.TraceControl",
//        "id"             => "int",
//    ]
}
