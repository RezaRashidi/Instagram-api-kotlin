

package instagramAPI.responses.PropertyCollection

import instagramAPI.AutoPropertyMapper

/**
 * Sticker.
 *
 * This property collection represents the placement of a story sticker. It is
 * imported by various kinds of story sticker models.
 *
 * @method float getHeight()
 * @method int getIsPinned()
 * @method float getRotation()
 * @method float getWidth()
 * @method float getX()
 * @method float getY()
 * @method float getZ()
 * @method bool isHeight()
 * @method bool isIsPinned()
 * @method bool isRotation()
 * @method bool isWidth()
 * @method bool isX()
 * @method bool isY()
 * @method bool isZ()
 * @method this setHeight(float $value)
 * @method this setIsPinned(int $value)
 * @method this setRotation(float $value)
 * @method this setWidth(float $value)
 * @method this setX(float $value)
 * @method this setY(float $value)
 * @method this setZ(float $value)
 * @method this unsetHeight()
 * @method this unsetIsPinned()
 * @method this unsetRotation()
 * @method this unsetWidth()
 * @method this unsetX()
 * @method this unsetY()
 * @method this unsetZ()
 */
class Sticker : AutoPropertyMapper(){
    val JSON_PROPERTY_MAP = mapOf(
        "x"         to "float",
        "y"         to "float",
        "z"         to "float", // Unused by IG for now. So far it"s always int(0).
        "width"     to "float",
        "height"    to "float",
        "rotation"  to "float",
        "is_pinned" to "int"
    )
}
