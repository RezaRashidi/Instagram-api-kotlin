

package instagramAPI.realtimes.Payload

import instagramAPI.AutoPropertyMapper

/**
 * ThreadAction.
 *
 * @method .instagramAPI.Responses.Model.ActionLog getActionLog()
 * @method string getUserId()
 * @method bool isActionLog()
 * @method bool isUserId()
 * @method this setActionLog(.instagramAPI.Responses.Model.ActionLog $value)
 * @method this setUserId(string $value)
 * @method this unsetActionLog()
 * @method this unsetUserId()
 */
class ThreadAction : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "user_id"    => "string",
        "action_log" => ".instagramAPI.Responses.Model.ActionLog",
    ]
}
