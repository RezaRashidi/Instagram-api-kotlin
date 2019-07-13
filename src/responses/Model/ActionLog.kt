

package instagramAPI.responses.Model

import instagramAPI.AutoPropertyMapper

/**
 * ActionLog.
 *
 * @method Bold[] getBold()
 * @method mixed getDescription()
 * @method bool isBold()
 * @method bool isDescription()
 * @method this setBold(Bold[] $value)
 * @method this setDescription(mixed $value)
 * @method this unsetBold()
 * @method this unsetDescription()
 */
class ActionLog : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "bold"        => "Bold[]",
        "description" => "",
    ]
}
