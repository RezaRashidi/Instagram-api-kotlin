

package instagramAPI.Response.Model

import instagramAPI.AutoPropertyMapper

/**
 * TimeRange.
 *
 * @method string getEnd()
 * @method string getStart()
 * @method bool isEnd()
 * @method bool isStart()
 * @method this setEnd(string $value)
 * @method this setStart(string $value)
 * @method this unsetEnd()
 * @method this unsetStart()
 */
class TimeRange : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "start" => "string",
        "end"   => "string",
    ]
}
