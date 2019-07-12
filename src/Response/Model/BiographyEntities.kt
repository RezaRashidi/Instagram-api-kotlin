

package instagramAPI.Response.Model

import instagramAPI.AutoPropertyMapper

/**
 * BiographyEntities.
 *
 * @method mixed getEntities()
 * @method string getNuxType()
 * @method string getRawText()
 * @method bool isEntities()
 * @method bool isNuxType()
 * @method bool isRawText()
 * @method this setEntities(mixed $value)
 * @method this setNuxType(string $value)
 * @method this setRawText(string $value)
 * @method this unsetEntities()
 * @method this unsetNuxType()
 * @method this unsetRawText()
 */
class BiographyEntities : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "entities"   => "",
        "raw_text"   => "string",
        "nux_type"   => "string",
    ]
}
