

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * media.
 *
 * @method bool getCommentThreadingEnabled()
 * @method mixed getExpiringAt()
 * @method string getId()
 * @method string getImage()
 * @method User getUser()
 * @method bool isCommentThreadingEnabled()
 * @method bool isExpiringAt()
 * @method bool isId()
 * @method bool isImage()
 * @method bool isUser()
 * @method this setCommentThreadingEnabled(bool $value)
 * @method this setExpiringAt(mixed $value)
 * @method this setId(string $value)
 * @method this setImage(string $value)
 * @method this setUser(User $value)
 * @method this unsetCommentThreadingEnabled()
 * @method this unsetExpiringAt()
 * @method this unsetId()
 * @method this unsetImage()
 * @method this unsetUser()
 */
data class Media (
    val image                            : String,
    val id                               : String,
    val user                             : User,
    val expiring_at                      : String,
    val comment_threading_enabled        : Boolean
){
//    val JSON_PROPERTY_MAP = [
//        "image"                            => "string",
//        "id"                               => "string",
//        "user"                             => "User",
//        "expiring_at"                      => "",
//        "comment_threading_enabled"        => "bool",
//    ]
}
