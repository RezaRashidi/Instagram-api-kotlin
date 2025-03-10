

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Gating.
 *
 * @method mixed getButtons()
 * @method mixed getDescription()
 * @method mixed getGatingType()
 * @method mixed getTitle()
 * @method bool isButtons()
 * @method bool isDescription()
 * @method bool isGatingType()
 * @method bool isTitle()
 * @method this setButtons(mixed $value)
 * @method this setDescription(mixed $value)
 * @method this setGatingType(mixed $value)
 * @method this setTitle(mixed $value)
 * @method this unsetButtons()
 * @method this unsetDescription()
 * @method this unsetGatingType()
 * @method this unsetTitle()
 */
data class Gating (
    val gating_type : String,
    val description : String,
    val buttons     : String,
    val title       : String
){
//    val JSON_PROPERTY_MAP = [
//        "gating_type" => "",
//        "description" => "",
//        "buttons"     => "",
//        "title"       => "",
//    ]
}
