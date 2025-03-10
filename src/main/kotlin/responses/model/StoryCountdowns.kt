

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * StoryCountdowns.
 *
 * @method CountdownSticker getCountdownSticker()
 * @method float getHeight()
 * @method int getIsHidden()
 * @method int getIsPinned()
 * @method float getRotation()
 * @method float getWidth()
 * @method float getX()
 * @method float getY()
 * @method float getZ()
 * @method bool isCountdownSticker()
 * @method bool isHeight()
 * @method bool isIsHidden()
 * @method bool isIsPinned()
 * @method bool isRotation()
 * @method bool isWidth()
 * @method bool isX()
 * @method bool isY()
 * @method bool isZ()
 * @method this setCountdownSticker(CountdownSticker $value)
 * @method this setHeight(float $value)
 * @method this setIsHidden(int $value)
 * @method this setIsPinned(int $value)
 * @method this setRotation(float $value)
 * @method this setWidth(float $value)
 * @method this setX(float $value)
 * @method this setY(float $value)
 * @method this setZ(float $value)
 * @method this unsetCountdownSticker()
 * @method this unsetHeight()
 * @method this unsetIsHidden()
 * @method this unsetIsPinned()
 * @method this unsetRotation()
 * @method this unsetWidth()
 * @method this unsetX()
 * @method this unsetY()
 * @method this unsetZ()
 */
data class StoryCountdowns (
    val x                 : Float,
    val y                 : Float,
    val z                 : Float,
    val width             : Float,
    val height            : Float,
    val rotation          : Float,
    val is_pinned         : Int,
    val is_hidden         : Int,
    val countdown_sticker : CountdownSticker
){
//    val JSON_PROPERTY_MAP = [
//        "x"                 => "float",
//        "y"                 => "float",
//        "z"                 => "float",
//        "width"             => "float",
//        "height"            => "float",
//        "rotation"          => "float",
//        "is_pinned"         => "int",
//        "is_hidden"         => "int",
//        "countdown_sticker" => "CountdownSticker",
//    ]
}
