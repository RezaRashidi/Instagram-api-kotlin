

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
class TraceControl : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "max_trace_timeout_ms"             => "int",
    ]
}
