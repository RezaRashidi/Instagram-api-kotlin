

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

/**
 * CatalogEdge.
 *
 * @method CatalogNode getNode()
 * @method bool isNode()
 * @method this setNode(CatalogNode $value)
 * @method this unsetNode()
 */
class CatalogEdge : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "node"          => "CatalogNode",
    ]
}
