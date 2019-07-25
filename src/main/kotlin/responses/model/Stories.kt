

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Stories.
 *
 * @method string getId()
 * @method mixed getIsPortrait()
 * @method TopLive getTopLive()
 * @method StoryTray[] getTray()
 * @method bool isId()
 * @method bool isIsPortrait()
 * @method bool isTopLive()
 * @method bool isTray()
 * @method this setId(string $value)
 * @method this setIsPortrait(mixed $value)
 * @method this setTopLive(TopLive $value)
 * @method this setTray(StoryTray[] $value)
 * @method this unsetId()
 * @method this unsetIsPortrait()
 * @method this unsetTopLive()
 * @method this unsetTray()
 */
data class Stories (
    val is_portrait : String,
    val tray        : MutableList<StoryTray>,
    val id          : String,
    val top_live    : TopLive
){
//    val JSON_PROPERTY_MAP = [
//        "is_portrait" => "",
//        "tray"        => "StoryTray[]",
//        "id"          => "string",
//        "top_live"    => "TopLive",
//    ]
}
