

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Item
import instagramAPI.responses.model.Section
import instagramAPI.responses.model.StoryTray

/**
 * TagFeedResponse.
 *
 * @method bool getAutoLoadMoreEnabled()
 * @method model.Item[] getItems()
 * @method mixed getMessage()
 * @method bool getMoreAvailable()
 * @method string getNextMaxId()
 * @method mixed getNextMediaIds()
 * @method int getNextPage()
 * @method int getNumResults()
 * @method model.Item[] getRankedItems()
 * @method model.Section[] getSections()
 * @method string getStatus()
 * @method model.StoryTray getStory()
 * @method model._Message[] get_Messages()
 * @method bool isAutoLoadMoreEnabled()
 * @method bool isItems()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isNextMaxId()
 * @method bool isNextMediaIds()
 * @method bool isNextPage()
 * @method bool isNumResults()
 * @method bool isRankedItems()
 * @method bool isSections()
 * @method bool isStatus()
 * @method bool isStory()
 * @method bool is_Messages()
 * @method this setAutoLoadMoreEnabled(bool $value)
 * @method this setItems(model.Item[] $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(bool $value)
 * @method this setNextMaxId(string $value)
 * @method this setNextMediaIds(mixed $value)
 * @method this setNextPage(int $value)
 * @method this setNumResults(int $value)
 * @method this setRankedItems(model.Item[] $value)
 * @method this setSections(model.Section[] $value)
 * @method this setStatus(string $value)
 * @method this setStory(model.StoryTray $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAutoLoadMoreEnabled()
 * @method this unsetItems()
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetNextMaxId()
 * @method this unsetNextMediaIds()
 * @method this unsetNextPage()
 * @method this unsetNumResults()
 * @method this unsetRankedItems()
 * @method this unsetSections()
 * @method this unsetStatus()
 * @method this unsetStory()
 * @method this unset_Messages()
 */
data class TagFeedResponse (
    val sections               : MutableList<Section>,
    val num_results            : Int,
    val ranked_items           : MutableList<Item>,
    val auto_load_more_enabled : Boolean,
    val items                  : MutableList<Item>,
    val story                  : StoryTray,
    val more_available         : Boolean,
    val next_max_id            : String,
    val next_media_ids         : String,
    val next_page              : Int
){
//    val JSON_PROPERTY_MAP = [
//        "sections"               => "model.Section[]",
//        "num_results"            => "int",
//        "ranked_items"           => "model.Item[]",
//        "auto_load_more_enabled" => "bool",
//        "items"                  => "model.Item[]",
//        "story"                  => "model.StoryTray",
//        "more_available"         => "bool",
//        "next_max_id"            => "string",
//        "next_media_ids"         => "",
//        "next_page"              => "int",
//    ]
}
