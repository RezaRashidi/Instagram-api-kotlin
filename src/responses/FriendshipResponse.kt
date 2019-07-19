

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.FriendshipStatus

/**
 * FriendshipResponse.
 *
 * @method model.FriendshipStatus getFriendshipStatus()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isFriendshipStatus()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setFriendshipStatus(model.FriendshipStatus $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetFriendshipStatus()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class FriendshipResponse (
    val friendship_status : FriendshipStatus
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "friendship_status" to "model.FriendshipStatus"
//    )
}
