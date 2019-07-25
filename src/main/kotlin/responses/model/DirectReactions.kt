

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * DirectReactions.
 *
 * @method DirectReaction[] getLikes()
 * @method int getLikesCount()
 * @method bool isLikes()
 * @method bool isLikesCount()
 * @method this setLikes(DirectReaction[] $value)
 * @method this setLikesCount(int $value)
 * @method this unsetLikes()
 * @method this unsetLikesCount()
 */
data class DirectReactions (
    val likes_count : Int,
    val likes       : MutableList<DirectReaction>
){
//    val JSON_PROPERTY_MAP = [
//        "likes_count" => "int",
//        "likes"       => "DirectReaction[]",
//    ]
}
