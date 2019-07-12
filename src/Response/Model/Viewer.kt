

package instagramAPI.Response.Model

import instagramAPI.AutoPropertyMapper

/**
 * Viewer.
 *
 * @method EligiblePromotions getEligiblePromotions()
 * @method bool isEligiblePromotions()
 * @method this setEligiblePromotions(EligiblePromotions $value)
 * @method this unsetEligiblePromotions()
 */
class Viewer : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "eligible_promotions"   => "EligiblePromotions",
    ]
}
