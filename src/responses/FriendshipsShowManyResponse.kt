

package instagramAPI.responses

import instagramAPI.Response

/**
 * FriendshipsShowManyResponse.
 *
 * @method model.unpredictableKeys.FriendshipStatusUnpredictableContainer getFriendshipStatuses()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isFriendshipStatuses()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setFriendshipStatuses(model.unpredictableKeys.FriendshipStatusUnpredictableContainer $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetFriendshipStatuses()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class FriendshipsShowManyResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "friendship_statuses" to "model.unpredictableKeys.FriendshipStatusUnpredictableContainer"
    )
}
