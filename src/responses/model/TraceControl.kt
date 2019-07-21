

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * TraceControl.
 *
 * @method int getMaxTraceTimeoutMs()
 * @method bool isMaxTraceTimeoutMs()
 * @method this setMaxTraceTimeoutMs(int $value)
 * @method this unsetMaxTraceTimeoutMs()
 */
data class TraceControl (
    val max_trace_timeout_ms : Int
){
//    val JSON_PROPERTY_MAP = [
//        "max_trace_timeout_ms"             => "int",
//    ]
}
