

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * CatalogEdge.
 *
 * @method CatalogNode getNode()
 * @method bool isNode()
 * @method this setNode(CatalogNode $value)
 * @method this unsetNode()
 */
data class CatalogEdge (
    val node : CatalogNode
){
//    val JSON_PROPERTY_MAP = [
//        "node"          => "CatalogNode",
//    ]
}
