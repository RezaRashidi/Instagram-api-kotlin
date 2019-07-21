

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * LiveComment.
 *
 * @method Comment getComment()
 * @method mixed getEvent()
 * @method mixed getOffset()
 * @method bool isComment()
 * @method bool isEvent()
 * @method bool isOffset()
 * @method this setComment(Comment $value)
 * @method this setEvent(mixed $value)
 * @method this setOffset(mixed $value)
 * @method this unsetComment()
 * @method this unsetEvent()
 * @method this unsetOffset()
 */
data class LiveComment (
    val comment : Comment,
    val offset  : String,
    val event   : String
){
//    val JSON_PROPERTY_MAP = [
//        "comment" => "Comment",
//        "offset"  => "",
//        "event"   => "",
//    ]
}
