

package instagramAPI.responses

import instagramAPI.Response

/**
 * MediaCommentRepliesResponse.
 *
 * @method int getChildCommentCount()
 * @method model.Comment[] getChildComments()
 * @method bool getHasMoreHeadChildComments()
 * @method bool getHasMoreTailChildComments()
 * @method mixed getMessage()
 * @method string getNextMaxChildCursor()
 * @method string getNextMinChildCursor()
 * @method int getNumHeadChildComments()
 * @method int getNumTailChildComments()
 * @method model.Comment getParentComment()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isChildCommentCount()
 * @method bool isChildComments()
 * @method bool isHasMoreHeadChildComments()
 * @method bool isHasMoreTailChildComments()
 * @method bool isMessage()
 * @method bool isNextMaxChildCursor()
 * @method bool isNextMinChildCursor()
 * @method bool isNumHeadChildComments()
 * @method bool isNumTailChildComments()
 * @method bool isParentComment()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setChildCommentCount(int $value)
 * @method this setChildComments(model.Comment[] $value)
 * @method this setHasMoreHeadChildComments(bool $value)
 * @method this setHasMoreTailChildComments(bool $value)
 * @method this setMessage(mixed $value)
 * @method this setNextMaxChildCursor(string $value)
 * @method this setNextMinChildCursor(string $value)
 * @method this setNumHeadChildComments(int $value)
 * @method this setNumTailChildComments(int $value)
 * @method this setParentComment(model.Comment $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetChildCommentCount()
 * @method this unsetChildComments()
 * @method this unsetHasMoreHeadChildComments()
 * @method this unsetHasMoreTailChildComments()
 * @method this unsetMessage()
 * @method this unsetNextMaxChildCursor()
 * @method this unsetNextMinChildCursor()
 * @method this unsetNumHeadChildComments()
 * @method this unsetNumTailChildComments()
 * @method this unsetParentComment()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class MediaCommentRepliesResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "parent_comment"               => "model.Comment",
        /*
         * Number of child comments in this comment thread.
         */
        "child_comment_count"          => "int",
        "child_comments"               => "model.Comment[]",
        /*
         * When "has_more_tail_child_comments" is true, you can import the value
         * in "next_max_child_cursor" as "max_id" parameter to load up to
         * "num_tail_child_comments" older child-comments.
         */
        "has_more_tail_child_comments"      => "bool",
        "next_max_child_cursor"             => "string",
        "num_tail_child_comments"           => "int",
        /*
         * When "has_more_head_child_comments" is true, you can import the value
         * in "next_min_child_cursor" as "min_id" parameter to load up to
         * "num_head_child_comments" newer child-comments.
         */
        "has_more_head_child_comments"      => "bool",
        "next_min_child_cursor"             => "string",
        "num_head_child_comments"           => "int",
    ]
}
