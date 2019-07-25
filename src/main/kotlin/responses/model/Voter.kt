

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Voter.
 *
 * @method User getUser()
 * @method int getVote()
 * @method bool isUser()
 * @method bool isVote()
 * @method this setUser(User $value)
 * @method this setVote(int $value)
 * @method this unsetUser()
 * @method this unsetVote()
 */
data class Voter (
    val user : User,
    val vote : Int
){
//    val JSON_PROPERTY_MAP = [
//        "user"  => "User",
//        "vote"  => "int",
//    ]
}
