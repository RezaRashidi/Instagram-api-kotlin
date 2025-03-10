

package instagramAPI.responses

import instagramAPI.Response

/**
 * SuggestedUsersBadgeResponse.
 *
 * @method mixed getMessage()
 * @method string[] getNewSuggestionIds()
 * @method mixed getShouldBadge()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isNewSuggestionIds()
 * @method bool isShouldBadge()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setNewSuggestionIds(string[] $value)
 * @method this setShouldBadge(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetNewSuggestionIds()
 * @method this unsetShouldBadge()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class SuggestedUsersBadgeResponse (
    val should_badge       : String,
    val new_suggestion_ids : MutableList<String>
){
//    val JSON_PROPERTY_MAP = [
//        "should_badge"       => "",
//        "new_suggestion_ids" => "string[]",
//    ]
}
