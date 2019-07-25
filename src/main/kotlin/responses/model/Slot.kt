

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Slot.
 *
 * @method int getCooldown()
 * @method int getSlot()
 * @method bool isCooldown()
 * @method bool isSlot()
 * @method this setCooldown(int $value)
 * @method this setSlot(int $value)
 * @method this unsetCooldown()
 * @method this unsetSlot()
 */
data class Slot (
    val slot     : Int,
    val cooldown : Int
){
//    val JSON_PROPERTY_MAP = [
//        "slot"                 => "int",
//        "cooldown"             => "int",
//    ]
}
