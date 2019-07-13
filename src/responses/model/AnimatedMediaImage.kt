

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
class AnimatedMediaImage : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "fixed_height"  => "AnimatedMediaImageFixedHeigth",
    ]
}
