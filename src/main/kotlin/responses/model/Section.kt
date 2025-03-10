

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Section.
 *
 * @method ExploreItemInfo getExploreItemInfo()
 * @method string getFeedType()
 * @method LayoutContent getLayoutContent()
 * @method string getLayoutType()
 * @method bool isExploreItemInfo()
 * @method bool isFeedType()
 * @method bool isLayoutContent()
 * @method bool isLayoutType()
 * @method this setExploreItemInfo(ExploreItemInfo $value)
 * @method this setFeedType(string $value)
 * @method this setLayoutContent(LayoutContent $value)
 * @method this setLayoutType(string $value)
 * @method this unsetExploreItemInfo()
 * @method this unsetFeedType()
 * @method this unsetLayoutContent()
 * @method this unsetLayoutType()
 */
data class Section (
    val layout_type       : String,
    val layout_content    : LayoutContent,
    val feed_type         : String,
    val explore_item_info : ExploreItemInfo
){
//    val JSON_PROPERTY_MAP = [
//        "layout_type"       => "string",
//        "layout_content"    => "LayoutContent",
//        "feed_type"         => "string",
//        "explore_item_info" => "ExploreItemInfo",
//    ]
}
