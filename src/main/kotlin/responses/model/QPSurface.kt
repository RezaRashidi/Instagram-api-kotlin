

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * QPSurface.
 *
 * @method int getCooldown()
 * @method int getSurfaceId()
 * @method bool isCooldown()
 * @method bool isSurfaceId()
 * @method this setCooldown(int $value)
 * @method this setSurfaceId(int $value)
 * @method this unsetCooldown()
 * @method this unsetSurfaceId()
 */
data class QPSurface (
    val surface_id : Int,
    val cooldown   : Int
){
//    val JSON_PROPERTY_MAP = [
//        "surface_id"             => "int",
//        "cooldown"               => "int",
//    ]
}
