

package instagramAPI.Response.Model

import instagramAPI.AutoPropertyMapper

/**
 * Tab.
 *
 * @method string getTitle()
 * @method string getType()
 * @method bool isTitle()
 * @method bool isType()
 * @method this setTitle(string $value)
 * @method this setType(string $value)
 * @method this unsetTitle()
 * @method this unsetType()
 */
class Tab : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "type"  => "string",
        "title" => "string",
    ]
}
