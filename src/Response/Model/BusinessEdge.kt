

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

/**
 * BusinessEdge.
 *
 * @method mixed getCursor()
 * @method BusinessNode getNode()
 * @method bool isCursor()
 * @method bool isNode()
 * @method this setCursor(mixed $value)
 * @method this setNode(BusinessNode $value)
 * @method this unsetCursor()
 * @method this unsetNode()
 */
class BusinessEdge : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'node'   => 'BusinessNode',
        'cursor' => '',
    ]
}
