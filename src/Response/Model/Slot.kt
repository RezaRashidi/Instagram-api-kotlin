

package instagramAPI.Response.Model

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
class Slot : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "slot"                 => "int",
        "cooldown"             => "int",
    ]
}
