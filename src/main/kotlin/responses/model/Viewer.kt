

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Viewer.
 *
 * @method EligiblePromotions getEligiblePromotions()
 * @method bool isEligiblePromotions()
 * @method this setEligiblePromotions(EligiblePromotions $value)
 * @method this unsetEligiblePromotions()
 */
data class Viewer (
    val eligible_promotions : EligiblePromotions
){
//    val JSON_PROPERTY_MAP = [
//        "eligible_promotions"   => "EligiblePromotions",
//    ]
}
