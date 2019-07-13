

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Attribution.
 *
 * @method string getName()
 * @method bool isName()
 * @method this setName(string $value)
 * @method this unsetName()
 */
class Attribution : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "name" => "string",
    ]
}
