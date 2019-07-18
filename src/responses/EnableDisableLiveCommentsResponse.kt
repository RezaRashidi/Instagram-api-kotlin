

package instagramAPI.responses

import instagramAPI.Response

/**
 * EnableDisableLiveCommentsResponse.
 *
 * @method int getCommentMuted()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isCommentMuted()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setCommentMuted(int $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetCommentMuted()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class EnableDisableLiveCommentsResponse (
    val comment_muted: Int
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "comment_muted" to "int"
//    )
}
