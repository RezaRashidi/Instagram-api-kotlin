

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Section

/**
 * LocationFeedResponse.
 *
 * @method mixed getMessage()
 * @method bool getMoreAvailable()
 * @method string getNextMaxId()
 * @method int[] getNextMediaIds()
 * @method int getNextPage()
 * @method model.Section[] getSections()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isNextMaxId()
 * @method bool isNextMediaIds()
 * @method bool isNextPage()
 * @method bool isSections()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(bool $value)
 * @method this setNextMaxId(string $value)
 * @method this setNextMediaIds(int[] $value)
 * @method this setNextPage(int $value)
 * @method this setSections(model.Section[] $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetNextMaxId()
 * @method this unsetNextMediaIds()
 * @method this unsetNextPage()
 * @method this unsetSections()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class LocationFeedResponse (
    val sections               : MutableList<Section>,
    val next_page              : Int,
    val more_available         : Boolean,
    val next_media_ids         : MutableList<Int>,
    val next_max_id            : String
){
//    val JSON_PROPERTY_MAP = [
//        "sections"               => "model.Section[]",
//        "next_page"              => "int",
//        "more_available"         => "bool",
//        "next_media_ids"         => "int[]",
//        "next_max_id"            => "string",
//    ]
}
