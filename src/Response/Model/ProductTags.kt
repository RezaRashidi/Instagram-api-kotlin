

package instagramAPI.Response.Model

import instagramAPI.AutoPropertyMapper

/**
 * ProductTags.
 *
 * @method In[] getIn()
 * @method bool isIn()
 * @method this setIn(In[] $value)
 * @method this unsetIn()
 */
class ProductTags : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "in"        => "In[]",
    ]
}
