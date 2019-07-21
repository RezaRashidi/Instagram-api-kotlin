

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * LayoutContent.
 *
 * @method ExploreItemInfo getExploreItemInfo()
 * @method string getFeedType()
 * @method SectionMedia[] getMedias()
 * @method Tag[] getRelated()
 * @method string getRelatedStyle()
 * @method TabsInfo getTabsInfo()
 * @method bool isExploreItemInfo()
 * @method bool isFeedType()
 * @method bool isMedias()
 * @method bool isRelated()
 * @method bool isRelatedStyle()
 * @method bool isTabsInfo()
 * @method this setExploreItemInfo(ExploreItemInfo $value)
 * @method this setFeedType(string $value)
 * @method this setMedias(SectionMedia[] $value)
 * @method this setRelated(Tag[] $value)
 * @method this setRelatedStyle(string $value)
 * @method this setTabsInfo(TabsInfo $value)
 * @method this unsetExploreItemInfo()
 * @method this unsetFeedType()
 * @method this unsetMedias()
 * @method this unsetRelated()
 * @method this unsetRelatedStyle()
 * @method this unsetTabsInfo()
 */
data class LayoutContent (
    val related_style     : String,
    val related           : MutableList<Tag>,
    val medias            : MutableList<SectionMedia>,
    val feed_type         : String,
    val explore_item_info : ExploreItemInfo,
    val tabs_info         : TabsInfo
){
//    val JSON_PROPERTY_MAP = [
//        "related_style"     => "string",
//        "related"           => "Tag[]",
//        "medias"            => "SectionMedia[]",
//        "feed_type"         => "string",
//        "explore_item_info" => "ExploreItemInfo",
//        "tabs_info"         => "TabsInfo",
//    ]
}
