

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * ReelShare.
 *
 * @method mixed getBroadcasts()
 * @method bool getIsReelPersisted()
 * @method Item getMedia()
 * @method string getMentionedUserId()
 * @method string getReelOwnerId()
 * @method string getReelType()
 * @method int getStickerVersion()
 * @method string getStoryRankingToken()
 * @method string getText()
 * @method Item[] getTray()
 * @method string getType()
 * @method bool isBroadcasts()
 * @method bool isIsReelPersisted()
 * @method bool isMedia()
 * @method bool isMentionedUserId()
 * @method bool isReelOwnerId()
 * @method bool isReelType()
 * @method bool isStickerVersion()
 * @method bool isStoryRankingToken()
 * @method bool isText()
 * @method bool isTray()
 * @method bool isType()
 * @method this setBroadcasts(mixed $value)
 * @method this setIsReelPersisted(bool $value)
 * @method this setMedia(Item $value)
 * @method this setMentionedUserId(string $value)
 * @method this setReelOwnerId(string $value)
 * @method this setReelType(string $value)
 * @method this setStickerVersion(int $value)
 * @method this setStoryRankingToken(string $value)
 * @method this setText(string $value)
 * @method this setTray(Item[] $value)
 * @method this setType(string $value)
 * @method this unsetBroadcasts()
 * @method this unsetIsReelPersisted()
 * @method this unsetMedia()
 * @method this unsetMentionedUserId()
 * @method this unsetReelOwnerId()
 * @method this unsetReelType()
 * @method this unsetStickerVersion()
 * @method this unsetStoryRankingToken()
 * @method this unsetText()
 * @method this unsetTray()
 * @method this unsetType()
 */
data class ReelShare (
    val tray                : MutableList<Item>,
    val story_ranking_token : String,
    val broadcasts          : String,
    val sticker_version     : Int,
    val text                : String,
    val type                : String,
    val is_reel_persisted   : Boolean,
    val reel_owner_id       : String,
    val reel_type           : String,
    val media               : Item,
    val mentioned_user_id   : String
){
//    val JSON_PROPERTY_MAP = [
//        "tray"                => "Item[]",
//        "story_ranking_token" => "string",
//        "broadcasts"          => "",
//        "sticker_version"     => "int",
//        "text"                => "string",
//        "type"                => "string",
//        "is_reel_persisted"   => "bool",
//        "reel_owner_id"       => "string",
//        "reel_type"           => "string",
//        "media"               => "Item",
//        "mentioned_user_id"   => "string",
//    ]
}
