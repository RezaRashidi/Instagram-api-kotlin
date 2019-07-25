

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * UserList.
 *
 * @method int getPosition()
 * @method User getUser()
 * @method bool isPosition()
 * @method bool isUser()
 * @method this setPosition(int $value)
 * @method this setUser(User $value)
 * @method this unsetPosition()
 * @method this unsetUser()
 */
data class UserList (
    val position : Int,
    val user     : User
){
//    val JSON_PROPERTY_MAP = [
//        "position" => "int",
//        "user"     => "User",
//    ]
}
