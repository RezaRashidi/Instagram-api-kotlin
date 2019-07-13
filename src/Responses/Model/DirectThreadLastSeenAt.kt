

package instagramAPI.responses.Model

import instagramAPI.AutoPropertyMapper

/**
 * DirectThreadLastSeenAt.
 *
 * @method string getItemId()
 * @method mixed getTimestamp()
 * @method bool isItemId()
 * @method bool isTimestamp()
 * @method this setItemId(string $value)
 * @method this setTimestamp(mixed $value)
 * @method this unsetItemId()
 * @method this unsetTimestamp()
 */
class DirectThreadLastSeenAt : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "item_id"   => "string",
        "timestamp" => "",
    ]
}
