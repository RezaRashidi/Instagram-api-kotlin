

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * PushSettings.
 *
 * @method mixed getChecked()
 * @method mixed getEligible()
 * @method mixed getExample()
 * @method mixed getName()
 * @method mixed getOptions()
 * @method mixed getTitle()
 * @method bool isChecked()
 * @method bool isEligible()
 * @method bool isExample()
 * @method bool isName()
 * @method bool isOptions()
 * @method bool isTitle()
 * @method this setChecked(mixed $value)
 * @method this setEligible(mixed $value)
 * @method this setExample(mixed $value)
 * @method this setName(mixed $value)
 * @method this setOptions(mixed $value)
 * @method this setTitle(mixed $value)
 * @method this unsetChecked()
 * @method this unsetEligible()
 * @method this unsetExample()
 * @method this unsetName()
 * @method this unsetOptions()
 * @method this unsetTitle()
 */
data class PushSettings (
    val name     : String,
    val eligible : String,
    val title    : String,
    val example  : String,
    val options  : String,
    val checked  : String
){
//    val JSON_PROPERTY_MAP = [
//        "name"     => "",
//        "eligible" => "",
//        "title"    => "",
//        "example"  => "",
//        "options"  => "",
//        "checked"  => "",
//    ]
}
