

package instagramAPI.responses

import instagramAPI.Response

/**
 * CommentBroadcastResponse.
 *
 * @method model.Comment getComment()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isComment()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setComment(model.Comment $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetComment()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class CommentBroadcastResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "comment" to "model.Comment"
    )
}
