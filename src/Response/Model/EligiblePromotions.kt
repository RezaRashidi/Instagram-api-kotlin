

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

/**
 * EligiblePromotions.
 *
 * @method Edges[] getEdges()
 * @method bool isEdges()
 * @method this setEdges(Edges[] $value)
 * @method this unsetEdges()
 */
class EligiblePromotions : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "edges"   => "Edges[]",
    ]
}
