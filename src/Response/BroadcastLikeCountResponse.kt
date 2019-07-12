

package instagramAPI.Response

import instagramAPI.Response

/**
 * BroadcastLikeCountResponse.
 *
 * @method int getBurstLikes()
 * @method string getLikeTs()
 * @method Model.User[] getLikers()
 * @method int getLikes()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method Model._Message[] get_Messages()
 * @method bool isBurstLikes()
 * @method bool isLikeTs()
 * @method bool isLikers()
 * @method bool isLikes()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setBurstLikes(int $value)
 * @method this setLikeTs(string $value)
 * @method this setLikers(Model.User[] $value)
 * @method this setLikes(int $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(Model._Message[] $value)
 * @method this unsetBurstLikes()
 * @method this unsetLikeTs()
 * @method this unsetLikers()
 * @method this unsetLikes()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class BroadcastLikeCountResponse : Response() {
    override val JSON_PROPERTY_MAP = mapOf(
        "like_ts"     to "string",
        "likes"       to "int",
        "burst_likes" to "int",
        "likers"      to "Model.User[]"
    )
}
