

package instagramAPI.responses.Model

import instagramAPI.AutoPropertyMapper

/**
 * AdMetadata.
 *
 * @method mixed getType()
 * @method mixed getValue()
 * @method bool isType()
 * @method bool isValue()
 * @method this setType(mixed $value)
 * @method this setValue(mixed $value)
 * @method this unsetType()
 * @method this unsetValue()
 */
class AdMetadata : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "value" => "",
        "type"  => "",
    ]
}
