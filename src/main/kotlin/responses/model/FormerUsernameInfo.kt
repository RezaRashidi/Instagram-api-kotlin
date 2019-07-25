

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * FormerUsernameInfo.
 *
 * @method bool getHasFormerUsernames()
 * @method bool isHasFormerUsernames()
 * @method this setHasFormerUsernames(bool $value)
 * @method this unsetHasFormerUsernames()
 */
data class FormerUsernameInfo (
    val has_former_usernames : Boolean
){
//    val JSON_PROPERTY_MAP = [
//        "has_former_usernames" => "bool",
//    ]
}
