

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Badging.
 *
 * @method mixed getIds()
 * @method mixed getItems()
 * @method bool isIds()
 * @method bool isItems()
 * @method this setIds(mixed $value)
 * @method this setItems(mixed $value)
 * @method this unsetIds()
 * @method this unsetItems()
 */
class Badging : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "ids"   => "",
        "items" => "",
    ]
}
