

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * ProductTags.
 *
 * @method In[] getIn()
 * @method bool isIn()
 * @method this setIn(In[] $value)
 * @method this unsetIn()
 */
data class ProductTags (
    val in : MutableList<In>
){
//    val JSON_PROPERTY_MAP = [
//        "in"        => "In[]",
//    ]
}
