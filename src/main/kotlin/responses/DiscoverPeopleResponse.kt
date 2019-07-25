

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.SuggestedUsers

/**
 * DiscoverPeopleResponse.
 *
 * @method string getMaxId()
 * @method mixed getMessage()
 * @method bool getMoreAvailable()
 * @method model.SuggestedUsers getNewSuggestedUsers()
 * @method string getStatus()
 * @method model.SuggestedUsers getSuggestedUsers()
 * @method model._Message[] get_Messages()
 * @method bool isMaxId()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isNewSuggestedUsers()
 * @method bool isStatus()
 * @method bool isSuggestedUsers()
 * @method bool is_Messages()
 * @method this setMaxId(string $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(bool $value)
 * @method this setNewSuggestedUsers(model.SuggestedUsers $value)
 * @method this setStatus(string $value)
 * @method this setSuggestedUsers(model.SuggestedUsers $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMaxId()
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetNewSuggestedUsers()
 * @method this unsetStatus()
 * @method this unsetSuggestedUsers()
 * @method this unset_Messages()
 */
data class DiscoverPeopleResponse (
    val more_available: Boolean,
    val max_id: String,
    val suggested_users: SuggestedUsers,
    val new_suggested_users: SuggestedUsers
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "more_available"      to "bool",
//        "max_id"              to "string",
//        "suggested_users"     to "model.SuggestedUsers",
//        "new_suggested_users" to "model.SuggestedUsers"
//    )
}
