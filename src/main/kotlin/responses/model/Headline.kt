

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Headline.
 *
 * @method int getBitFlags()
 * @method mixed getContentType()
 * @method string getCreatedAt()
 * @method string getCreatedAtUtc()
 * @method string getMediaId()
 * @method string getPk()
 * @method mixed getStatus()
 * @method string getText()
 * @method mixed getType()
 * @method User getUser()
 * @method string getUserId()
 * @method bool isBitFlags()
 * @method bool isContentType()
 * @method bool isCreatedAt()
 * @method bool isCreatedAtUtc()
 * @method bool isMediaId()
 * @method bool isPk()
 * @method bool isStatus()
 * @method bool isText()
 * @method bool isType()
 * @method bool isUser()
 * @method bool isUserId()
 * @method this setBitFlags(int $value)
 * @method this setContentType(mixed $value)
 * @method this setCreatedAt(string $value)
 * @method this setCreatedAtUtc(string $value)
 * @method this setMediaId(string $value)
 * @method this setPk(string $value)
 * @method this setStatus(mixed $value)
 * @method this setText(string $value)
 * @method this setType(mixed $value)
 * @method this setUser(User $value)
 * @method this setUserId(string $value)
 * @method this unsetBitFlags()
 * @method this unsetContentType()
 * @method this unsetCreatedAt()
 * @method this unsetCreatedAtUtc()
 * @method this unsetMediaId()
 * @method this unsetPk()
 * @method this unsetStatus()
 * @method this unsetText()
 * @method this unsetType()
 * @method this unsetUser()
 * @method this unsetUserId()
 */
data class Headline (
    val content_type   : String,
    val user           : User,
    val user_id        : String,
    val pk             : String,
    val text           : String,
    val type           : String,
    val created_at     : String,
    val created_at_utc : String,
    val media_id       : String,
    val bit_flags      : Int,
    val status         : String
){
//    val JSON_PROPERTY_MAP = [
//        "content_type"   => "",
//        "user"           => "User",
//        "user_id"        => "string",
//        "pk"             => "string",
//        "text"           => "string",
//        "type"           => "",
//        "created_at"     => "string",
//        "created_at_utc" => "string",
//        "media_id"       => "string",
//        "bit_flags"      => "int",
//        "status"         => "",
//    ]
}
