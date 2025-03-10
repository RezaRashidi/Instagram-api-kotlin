

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * TraySuggestions.
 *
 * @method string getBannerSubtitle()
 * @method string getBannerTitle()
 * @method string getSuggestionType()
 * @method StoryTray[] getTray()
 * @method string getTrayTitle()
 * @method bool isBannerSubtitle()
 * @method bool isBannerTitle()
 * @method bool isSuggestionType()
 * @method bool isTray()
 * @method bool isTrayTitle()
 * @method this setBannerSubtitle(string $value)
 * @method this setBannerTitle(string $value)
 * @method this setSuggestionType(string $value)
 * @method this setTray(StoryTray[] $value)
 * @method this setTrayTitle(string $value)
 * @method this unsetBannerSubtitle()
 * @method this unsetBannerTitle()
 * @method this unsetSuggestionType()
 * @method this unsetTray()
 * @method this unsetTrayTitle()
 */
data class TraySuggestions (
    val tray            : MutableList<StoryTray>,
    val tray_title      : String,
    val banner_title    : String,
    val banner_subtitle : String,
    val suggestion_type : String
){
//    val JSON_PROPERTY_MAP = [
//        "tray"            => "StoryTray[]",
//        "tray_title"      => "string",
//        "banner_title"    => "string",
//        "banner_subtitle" => "string",
//        "suggestion_type" => "string",
//    ]
}
