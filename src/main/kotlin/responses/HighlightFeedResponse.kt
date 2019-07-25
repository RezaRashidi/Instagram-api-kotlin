

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Story
import instagramAPI.responses.model.StoryTray
import instagramAPI.responses.model.StoryTvChannel

/**
 * HighlightFeedResponse.
 *
 * @method bool getAutoLoadMoreEnabled()
 * @method mixed getMessage()
 * @method string getNextMaxId()
 * @method bool getShowEmptyState()
 * @method string getStatus()
 * @method model.Story[] getStories()
 * @method model.StoryTray[] getTray()
 * @method model.StoryTvChannel getTvChannel()
 * @method model._Message[] get_Messages()
 * @method bool isAutoLoadMoreEnabled()
 * @method bool isMessage()
 * @method bool isNextMaxId()
 * @method bool isShowEmptyState()
 * @method bool isStatus()
 * @method bool isStories()
 * @method bool isTray()
 * @method bool isTvChannel()
 * @method bool is_Messages()
 * @method this setAutoLoadMoreEnabled(bool $value)
 * @method this setMessage(mixed $value)
 * @method this setNextMaxId(string $value)
 * @method this setShowEmptyState(bool $value)
 * @method this setStatus(string $value)
 * @method this setStories(model.Story[] $value)
 * @method this setTray(model.StoryTray[] $value)
 * @method this setTvChannel(model.StoryTvChannel $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAutoLoadMoreEnabled()
 * @method this unsetMessage()
 * @method this unsetNextMaxId()
 * @method this unsetShowEmptyState()
 * @method this unsetStatus()
 * @method this unsetStories()
 * @method this unsetTray()
 * @method this unsetTvChannel()
 * @method this unset_Messages()
 */
data class HighlightFeedResponse (
    val auto_load_more_enabled : Boolean,
    val next_max_id            : String,
    val stories                : MutableList<Story>,
    val show_empty_state       : Boolean,
    val tray                   : MutableList<StoryTray>,
    val tv_channel             : StoryTvChannel
){
//    val JSON_PROPERTY_MAP = [
//        "auto_load_more_enabled" => "bool",
//        "next_max_id"            => "string",
//        "stories"                => "model.Story[]",
//        "show_empty_state"       => "bool",
//        "tray"                   => "model.StoryTray[]",
//        "tv_channel"             => "model.StoryTvChannel",
//    ]
}
