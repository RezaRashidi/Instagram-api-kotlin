

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

/**
 * FormerUsername.
 *
 * @method string getChangeTimestamp()
 * @method string getFormerUsername()
 * @method bool isChangeTimestamp()
 * @method bool isFormerUsername()
 * @method this setChangeTimestamp(string $value)
 * @method this setFormerUsername(string $value)
 * @method this unsetChangeTimestamp()
 * @method this unsetFormerUsername()
 */
class FormerUsername : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "former_username"  => "string",
        "change_timestamp" => "string",
    ]
}
