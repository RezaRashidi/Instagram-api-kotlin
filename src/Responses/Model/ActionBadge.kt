

package instagramAPI.responses.Model

import instagramAPI.AutoPropertyMapper

/**
 * ActionBadge.
 *
 * @method mixed getActionCount()
 * @method mixed getActionTimestamp()
 * @method mixed getActionType()
 * @method bool isActionCount()
 * @method bool isActionTimestamp()
 * @method bool isActionType()
 * @method this setActionCount(mixed $value)
 * @method this setActionTimestamp(mixed $value)
 * @method this setActionType(mixed $value)
 * @method this unsetActionCount()
 * @method this unsetActionTimestamp()
 * @method this unsetActionType()
 */
class ActionBadge : AutoPropertyMapper
{
    val DELIVERED = "raven_delivered"
    val SENT = "raven_sent"
    val OPENED = "raven_opened"
    val SCREENSHOT = "raven_screenshot"
    val REPLAYED = "raven_replayed"
    val CANNOT_DELIVER = "raven_cannot_deliver"
    val SENDING = "raven_sending"
    val BLOCKED = "raven_blocked"
    val UNKNOWN = "raven_unknown"
    val SUGGESTED = "raven_suggested"

    val JSON_PROPERTY_MAP = [
        "action_type"      => "",
        "action_count"     => "",
        "action_timestamp" => "",
    ]
}
