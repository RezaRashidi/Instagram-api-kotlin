

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Usertag.
 *
 * @method In[] getIn()
 * @method bool getPhotoOfYou()
 * @method bool isIn()
 * @method bool isPhotoOfYou()
 * @method this setIn(In[] $value)
 * @method this setPhotoOfYou(bool $value)
 * @method this unsetIn()
 * @method this unsetPhotoOfYou()
 */
data class Usertag (
    val in           : MutableList<In>,
    val photo_of_you : Boolean
){
//    val JSON_PROPERTY_MAP = [
//        "in"           => "In[]",
//        "photo_of_you" => "bool",
//    ]
}
