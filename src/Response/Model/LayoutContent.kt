<?php

package InstagramAPI.Response.Model;

import InstagramAPI.AutoPropertyMapper;

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
class LayoutContent : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'related_style'     => 'string',
        'related'           => 'Tag[]',
        'medias'            => 'SectionMedia[]',
        'feed_type'         => 'string',
        'explore_item_info' => 'ExploreItemInfo',
        'tabs_info'         => 'TabsInfo',
    ];
}
