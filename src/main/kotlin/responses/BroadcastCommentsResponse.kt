

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Comment

/**
 * BroadcastCommentsResponse.
 *
 * @method mixed getCaption()
 * @method bool getCaptionIsEdited()
 * @method int getCommentCount()
 * @method bool getCommentLikesEnabled()
 * @method int getCommentMuted()
 * @method model.Comment[] getComments()
 * @method bool getHasMoreComments()
 * @method bool getHasMoreHeadloadComments()
 * @method string getIsFirstFetch()
 * @method int getLiveSecondsPerComment()
 * @method string getMediaHeaderDisplay()
 * @method mixed getMessage()
 * @method model.Comment getPinnedComment()
 * @method string getStatus()
 * @method model.Comment[] getSystemComments()
 * @method model._Message[] get_Messages()
 * @method bool isCaption()
 * @method bool isCaptionIsEdited()
 * @method bool isCommentCount()
 * @method bool isCommentLikesEnabled()
 * @method bool isCommentMuted()
 * @method bool isComments()
 * @method bool isHasMoreComments()
 * @method bool isHasMoreHeadloadComments()
 * @method bool isIsFirstFetch()
 * @method bool isLiveSecondsPerComment()
 * @method bool isMediaHeaderDisplay()
 * @method bool isMessage()
 * @method bool isPinnedComment()
 * @method bool isStatus()
 * @method bool isSystemComments()
 * @method bool is_Messages()
 * @method this setCaption(mixed $value)
 * @method this setCaptionIsEdited(bool $value)
 * @method this setCommentCount(int $value)
 * @method this setCommentLikesEnabled(bool $value)
 * @method this setCommentMuted(int $value)
 * @method this setComments(model.Comment[] $value)
 * @method this setHasMoreComments(bool $value)
 * @method this setHasMoreHeadloadComments(bool $value)
 * @method this setIsFirstFetch(string $value)
 * @method this setLiveSecondsPerComment(int $value)
 * @method this setMediaHeaderDisplay(string $value)
 * @method this setMessage(mixed $value)
 * @method this setPinnedComment(model.Comment $value)
 * @method this setStatus(string $value)
 * @method this setSystemComments(model.Comment[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetCaption()
 * @method this unsetCaptionIsEdited()
 * @method this unsetCommentCount()
 * @method this unsetCommentLikesEnabled()
 * @method this unsetCommentMuted()
 * @method this unsetComments()
 * @method this unsetHasMoreComments()
 * @method this unsetHasMoreHeadloadComments()
 * @method this unsetIsFirstFetch()
 * @method this unsetLiveSecondsPerComment()
 * @method this unsetMediaHeaderDisplay()
 * @method this unsetMessage()
 * @method this unsetPinnedComment()
 * @method this unsetStatus()
 * @method this unsetSystemComments()
 * @method this unset_Messages()
 */
data class BroadcastCommentsResponse (
    val comments                   : MutableList<Comment>,
    val comment_count              : Int,
    val live_seconds_per_comment   : Int,
    val has_more_headload_comments : Boolean,
/*
 * NOTE: Instagram sends True or False as a string in this property.
 */
    val is_first_fetch             : String,
    val comment_likes_enabled      : Boolean,
    val pinned_comment             : Comment,
    val system_comments            : MutableList<Comment>,
    val has_more_comments          : Boolean,
    val caption_is_edited          : Boolean,
    val caption                    : String,
    val comment_muted              : Int,
    val media_header_display       : String
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "comments"                   to "model.Comment[]",
//        "comment_count"              to "int",
//        "live_seconds_per_comment"   to "int",
//        "has_more_headload_comments" to "bool",
//        /*
//         * NOTE: Instagram sends "True" or "False" as a string in this property.
//         */
//        "is_first_fetch"             to "string",
//        "comment_likes_enabled"      to "bool",
//        "pinned_comment"             to "model.Comment",
//        "system_comments"            to "model.Comment[]",
//        "has_more_comments"          to "bool",
//        "caption_is_edited"          to "bool",
//        "caption"                    to "",
//        "comment_muted"              to "int",
//        "media_header_display"       to "string"
//    )
}
