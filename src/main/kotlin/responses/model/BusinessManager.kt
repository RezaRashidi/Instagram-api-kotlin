

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * BusinessManager.
 *
 * @method BusinessNode getAccountInsightsUnit()
 * @method AccountSummaryUnit getAccountSummaryUnit()
 * @method BusinessFeed getFeed()
 * @method BusinessNode getFollowersUnit()
 * @method PromotionsUnit getPromotionsUnit()
 * @method BusinessNode getStoriesUnit()
 * @method BusinessNode getTopPostsUnit()
 * @method bool isAccountInsightsUnit()
 * @method bool isAccountSummaryUnit()
 * @method bool isFeed()
 * @method bool isFollowersUnit()
 * @method bool isPromotionsUnit()
 * @method bool isStoriesUnit()
 * @method bool isTopPostsUnit()
 * @method this setAccountInsightsUnit(BusinessNode $value)
 * @method this setAccountSummaryUnit(AccountSummaryUnit $value)
 * @method this setFeed(BusinessFeed $value)
 * @method this setFollowersUnit(BusinessNode $value)
 * @method this setPromotionsUnit(PromotionsUnit $value)
 * @method this setStoriesUnit(BusinessNode $value)
 * @method this setTopPostsUnit(BusinessNode $value)
 * @method this unsetAccountInsightsUnit()
 * @method this unsetAccountSummaryUnit()
 * @method this unsetFeed()
 * @method this unsetFollowersUnit()
 * @method this unsetPromotionsUnit()
 * @method this unsetStoriesUnit()
 * @method this unsetTopPostsUnit()
 */
data class BusinessManager (
    val account_summary_unit  : AccountSummaryUnit,
    val account_insights_unit : BusinessNode,
    val followers_unit        : BusinessNode,
    val top_posts_unit        : BusinessNode,
    val stories_unit          : BusinessNode,
    val promotions_unit       : PromotionsUnit,
    val feed                  : BusinessFeed
){
//    val JSON_PROPERTY_MAP = [
//        "account_summary_unit"  => "AccountSummaryUnit",
//        "account_insights_unit" => "BusinessNode",
//        "followers_unit"        => "BusinessNode",
//        "top_posts_unit"        => "BusinessNode",
//        "stories_unit"          => "BusinessNode",
//        "promotions_unit"       => "PromotionsUnit",
//        "feed"                  => "BusinessFeed",
//    ]
}
