

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * FeedItem.
 *
 * @method Ad4ad getAd4ad()
 * @method int getAdLinkType()
 * @method mixed getEndOfFeedDemarcator()
 * @method Item getMediaOrAd()
 * @method StoriesNetego getStoriesNetego()
 * @method SuggestedUsers getSuggestedUsers()
 * @method bool isAd4ad()
 * @method bool isAdLinkType()
 * @method bool isEndOfFeedDemarcator()
 * @method bool isMediaOrAd()
 * @method bool isStoriesNetego()
 * @method bool isSuggestedUsers()
 * @method this setAd4ad(Ad4ad $value)
 * @method this setAdLinkType(int $value)
 * @method this setEndOfFeedDemarcator(mixed $value)
 * @method this setMediaOrAd(Item $value)
 * @method this setStoriesNetego(StoriesNetego $value)
 * @method this setSuggestedUsers(SuggestedUsers $value)
 * @method this unsetAd4ad()
 * @method this unsetAdLinkType()
 * @method this unsetEndOfFeedDemarcator()
 * @method this unsetMediaOrAd()
 * @method this unsetStoriesNetego()
 * @method this unsetSuggestedUsers()
 */
data class FeedItem (
    val media_or_ad            : Item,
    val stories_netego         : StoriesNetego,
    val ad4ad                  : Ad4ad,
    val suggested_users        : SuggestedUsers,
    val end_of_feed_demarcator : String,
    val ad_link_type           : Int
){
//    val JSON_PROPERTY_MAP = [
//        "media_or_ad"            => "Item",
//        "stories_netego"         => "StoriesNetego",
//        "ad4ad"                  => "Ad4ad",
//        "suggested_users"        => "SuggestedUsers",
//        "end_of_feed_demarcator" => "",
//        "ad_link_type"           => "int",
//    ]
}
