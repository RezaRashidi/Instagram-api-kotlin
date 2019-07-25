

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.FeedAysf
import instagramAPI.responses.model.FeedItem

/**
 * TimelineFeedResponse.
 *
 * @method bool getAutoLoadMoreEnabled()
 * @method bool getClientFeedChangelistApplied()
 * @method mixed getClientGapEnforcerMatrix()
 * @method string getClientSessionId()
 * @method model.FeedItem[] getFeedItems()
 * @method string getFeedPillText()
 * @method bool getIsDirectV2Enabled()
 * @method model.FeedAysf getMegaphone()
 * @method mixed getMessage()
 * @method bool getMoreAvailable()
 * @method string getNextMaxId()
 * @method int getNumResults()
 * @method mixed getPaginationInfo()
 * @method string getStatus()
 * @method string getViewStateVersion()
 * @method model._Message[] get_Messages()
 * @method bool isAutoLoadMoreEnabled()
 * @method bool isClientFeedChangelistApplied()
 * @method bool isClientGapEnforcerMatrix()
 * @method bool isClientSessionId()
 * @method bool isFeedItems()
 * @method bool isFeedPillText()
 * @method bool isIsDirectV2Enabled()
 * @method bool isMegaphone()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isNextMaxId()
 * @method bool isNumResults()
 * @method bool isPaginationInfo()
 * @method bool isStatus()
 * @method bool isViewStateVersion()
 * @method bool is_Messages()
 * @method this setAutoLoadMoreEnabled(bool $value)
 * @method this setClientFeedChangelistApplied(bool $value)
 * @method this setClientGapEnforcerMatrix(mixed $value)
 * @method this setClientSessionId(string $value)
 * @method this setFeedItems(model.FeedItem[] $value)
 * @method this setFeedPillText(string $value)
 * @method this setIsDirectV2Enabled(bool $value)
 * @method this setMegaphone(model.FeedAysf $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(bool $value)
 * @method this setNextMaxId(string $value)
 * @method this setNumResults(int $value)
 * @method this setPaginationInfo(mixed $value)
 * @method this setStatus(string $value)
 * @method this setViewStateVersion(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAutoLoadMoreEnabled()
 * @method this unsetClientFeedChangelistApplied()
 * @method this unsetClientGapEnforcerMatrix()
 * @method this unsetClientSessionId()
 * @method this unsetFeedItems()
 * @method this unsetFeedPillText()
 * @method this unsetIsDirectV2Enabled()
 * @method this unsetMegaphone()
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetNextMaxId()
 * @method this unsetNumResults()
 * @method this unsetPaginationInfo()
 * @method this unsetStatus()
 * @method this unsetViewStateVersion()
 * @method this unset_Messages()
 */
data class TimelineFeedResponse (
    val num_results                    : Int,
    val client_gap_enforcer_matrix     : String,
    val is_direct_v2_enabled           : Boolean,
    val auto_load_more_enabled         : Boolean,
    val more_available                 : Boolean,
    val next_max_id                    : String,
    val pagination_info                : String,
    val feed_items                     : MutableList<FeedItem>,
    val megaphone                      : MutableList<FeedAysf>,
    val client_feed_changelist_applied : Boolean,
    val view_state_version             : String,
    val feed_pill_text                 : String,
    //val client_gap_enforcer_matrix     : String,
    val client_session_id              : String
){ // note saeed : there is two client_gap_enforcer_matrix in php but kotlin let have one
//    val JSON_PROPERTY_MAP = [
//        "num_results"                    => "int",
//        "client_gap_enforcer_matrix"     => "",
//        "is_direct_v2_enabled"           => "bool",
//        "auto_load_more_enabled"         => "bool",
//        "more_available"                 => "bool",
//        "next_max_id"                    => "string",
//        "pagination_info"                => "",
//        "feed_items"                     => "model.FeedItem[]",
//        "megaphone"                      => "model.FeedAysf",
//        "client_feed_changelist_applied" => "bool",
//        "view_state_version"             => "string",
//        "feed_pill_text"                 => "string",
//        "client_gap_enforcer_matrix"     => "",
//        "client_session_id"              => "string",
//    ]
}
