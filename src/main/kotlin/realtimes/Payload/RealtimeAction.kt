

package instagramAPI.realtimes.Payload

import instagramAPI.AutoPropertyMapper

/**
 * RealtimeAction.
 *
 * @method string getAction()
 * @method string getStatus()
 * @method bool isAction()
 * @method bool isStatus()
 * @method this setAction(string $value)
 * @method this setStatus(string $value)
 * @method this unsetAction()
 * @method this unsetStatus()
 */
abstract class RealtimeAction : AutoPropertyMapper
{
    val ACK = "item_ack"
    val UNSEEN_COUNT = "inbox_unseen_count"
    val UNKNOWN = "unknown"

    val JSON_PROPERTY_MAP = [
        "status" => "string",
        "action" => "string",
    ]
}
