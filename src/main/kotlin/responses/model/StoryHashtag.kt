

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * StoryHashtag.
 *
 * @method string getAttribution()
 * @method string getCustomTitle()
 * @method Hashtag getHashtag()
 * @method float getHeight()
 * @method int getIsHidden()
 * @method int getIsPinned()
 * @method float getRotation()
 * @method float getWidth()
 * @method float getX()
 * @method float getY()
 * @method float getZ()
 * @method bool isAttribution()
 * @method bool isCustomTitle()
 * @method bool isHashtag()
 * @method bool isHeight()
 * @method bool isIsHidden()
 * @method bool isIsPinned()
 * @method bool isRotation()
 * @method bool isWidth()
 * @method bool isX()
 * @method bool isY()
 * @method bool isZ()
 * @method this setAttribution(string $value)
 * @method this setCustomTitle(string $value)
 * @method this setHashtag(Hashtag $value)
 * @method this setHeight(float $value)
 * @method this setIsHidden(int $value)
 * @method this setIsPinned(int $value)
 * @method this setRotation(float $value)
 * @method this setWidth(float $value)
 * @method this setX(float $value)
 * @method this setY(float $value)
 * @method this setZ(float $value)
 * @method this unsetAttribution()
 * @method this unsetCustomTitle()
 * @method this unsetHashtag()
 * @method this unsetHeight()
 * @method this unsetIsHidden()
 * @method this unsetIsPinned()
 * @method this unsetRotation()
 * @method this unsetWidth()
 * @method this unsetX()
 * @method this unsetY()
 * @method this unsetZ()
 */
data class StoryHashtag (
    val x         : Float,
    val y         : Float,
    val z         : Float, // Unused by IG for now. So far its always int(0).
    val width     : Float,
    val height    : Float,
    val rotation  : Float,
    val is_pinned : Int,
    val hashtag   : Hashtag,
    val attribution : String,
    val custom_title : String,
    val is_hidden : Int
){
//    val JSON_PROPERTY_MAP = [
//        PropertyCollection.Sticker::class,
//        "hashtag"       => "Hashtag",
//        "attribution"   => "string",
//        "custom_title"  => "string",
//        "is_hidden"     => "int",
//    ]
}
