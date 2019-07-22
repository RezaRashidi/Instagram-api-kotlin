

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * ReelMention.
 *
 * @method float getHeight()
 * @method int getIsHidden()
 * @method int getIsPinned()
 * @method float getRotation()
 * @method User getUser()
 * @method float getWidth()
 * @method float getX()
 * @method float getY()
 * @method float getZ()
 * @method bool isHeight()
 * @method bool isIsHidden()
 * @method bool isIsPinned()
 * @method bool isRotation()
 * @method bool isUser()
 * @method bool isWidth()
 * @method bool isX()
 * @method bool isY()
 * @method bool isZ()
 * @method this setHeight(float $value)
 * @method this setIsHidden(int $value)
 * @method this setIsPinned(int $value)
 * @method this setRotation(float $value)
 * @method this setUser(User $value)
 * @method this setWidth(float $value)
 * @method this setX(float $value)
 * @method this setY(float $value)
 * @method this setZ(float $value)
 * @method this unsetHeight()
 * @method this unsetIsHidden()
 * @method this unsetIsPinned()
 * @method this unsetRotation()
 * @method this unsetUser()
 * @method this unsetWidth()
 * @method this unsetX()
 * @method this unsetY()
 * @method this unsetZ()
 */
data class ReelMention (
    val x         : Float,
    val y         : Float,
    val z         : Float, // Unused by IG for now. So far its always int(0).
    val width     : Float,
    val height    : Float,
    val rotation  : Float,
    val is_pinned : Int,
    val user      : User,
    val is_hidden : Int
){
//    val JSON_PROPERTY_MAP = [
//        PropertyCollection.Sticker::class,
//        "user"      => "User",
//        "is_hidden" => "int",
//    ]
}
