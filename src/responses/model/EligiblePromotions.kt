

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * EligiblePromotions.
 *
 * @method Edges[] getEdges()
 * @method bool isEdges()
 * @method this setEdges(Edges[] $value)
 * @method this unsetEdges()
 */
data class EligiblePromotions (
    val edges : MutableList<Edges>
){
//    val JSON_PROPERTY_MAP = [
//        "edges"   => "Edges[]",
//    ]
}
