

package instagramAPI.responses

import instagramAPI.Response

/**
 * UnpinCommentBroadcastResponse.
 *
 * @method string getCommentId()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isCommentId()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setCommentId(string $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetCommentId()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class UnpinCommentBroadcastResponse (
    val comment_id : String
){
//    val JSON_PROPERTY_MAP = [
//        "comment_id" => "string",
//    ]
}
