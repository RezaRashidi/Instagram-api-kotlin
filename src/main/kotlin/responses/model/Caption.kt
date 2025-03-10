

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Caption.
 *
 * @method int getBitFlags()
 * @method mixed getContentType()
 * @method string getCreatedAt()
 * @method string getCreatedAtUtc()
 * @method bool getDidReportAsSpam()
 * @method bool getHasTranslation()
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
 * @method bool isDidReportAsSpam()
 * @method bool isHasTranslation()
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
 * @method this setDidReportAsSpam(bool $value)
 * @method this setHasTranslation(bool $value)
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
 * @method this unsetDidReportAsSpam()
 * @method this unsetHasTranslation()
 * @method this unsetMediaId()
 * @method this unsetPk()
 * @method this unsetStatus()
 * @method this unsetText()
 * @method this unsetType()
 * @method this unsetUser()
 * @method this unsetUserId()
 */
data class Caption (
    val status             : String,
    val user_id            : String,
    val created_at_utc     : String,
    val created_at         : String,
    val bit_flags          : Int,
    val user               : User,
    val content_type       : String,
    val text               : String,
    val media_id           : String,
    val pk                 : String,
    val type               : String,
    val has_translation    : Boolean,
    val did_report_as_spam : Boolean
){
//    val JSON_PROPERTY_MAP = [
//        "status"             => "",
//        "user_id"            => "string",
//        "created_at_utc"     => "string",
//        "created_at"         => "string",
//        "bit_flags"          => "int",
//        "user"               => "User",
//        "content_type"       => "",
//        "text"               => "string",
//        "media_id"           => "string",
//        "pk"                 => "string",
//        "type"               => "",
//        "has_translation"    => "bool",
//        "did_report_as_spam" => "bool",
//    ]
}
