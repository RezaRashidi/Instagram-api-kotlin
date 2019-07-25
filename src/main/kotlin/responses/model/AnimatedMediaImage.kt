

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * AnimatedMediaImage.
 *
 * @method AnimatedMediaImageFixedHeigth getFixedHeight()
 * @method bool isFixedHeight()
 * @method this setFixedHeight(AnimatedMediaImageFixedHeigth $value)
 * @method this unsetFixedHeight()
 */
data class AnimatedMediaImage (
    val fixed_height : AnimatedMediaImageFixedHeigth
){
//    val JSON_PROPERTY_MAP = [
//        "fixed_height"  => "AnimatedMediaImageFixedHeigth",
//    ]
}
