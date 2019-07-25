

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

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
data class BusinessEdge (
    val node   : BusinessNode,
    val cursor : String
){
//    val JSON_PROPERTY_MAP = [
//        "node"   => "BusinessNode",
//        "cursor" => "",
//    ]
}
