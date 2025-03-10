

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * DirectRankedRecipient.
 *
 * @method DirectThread getThread()
 * @method User getUser()
 * @method bool isThread()
 * @method bool isUser()
 * @method this setThread(DirectThread $value)
 * @method this setUser(User $value)
 * @method this unsetThread()
 * @method this unsetUser()
 */
data class DirectRankedRecipient (
    val thread : DirectThread,
    val user   : User
){
//    val JSON_PROPERTY_MAP = [
//        "thread" => "DirectThread",
//        "user"   => "User",
//    ]
}
