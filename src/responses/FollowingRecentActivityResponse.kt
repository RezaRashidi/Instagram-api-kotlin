

package instagramAPI.responses

import instagramAPI.Response

/**
 * FollowingRecentActivityResponse.
 *
 * @method mixed getAutoLoadMoreEnabled()
 * @method mixed getMegaphone()
 * @method mixed getMessage()
 * @method string getNextMaxId()
 * @method string getStatus()
 * @method model.Story[] getStories()
 * @method model._Message[] get_Messages()
 * @method bool isAutoLoadMoreEnabled()
 * @method bool isMegaphone()
 * @method bool isMessage()
 * @method bool isNextMaxId()
 * @method bool isStatus()
 * @method bool isStories()
 * @method bool is_Messages()
 * @method this setAutoLoadMoreEnabled(mixed $value)
 * @method this setMegaphone(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setNextMaxId(string $value)
 * @method this setStatus(string $value)
 * @method this setStories(model.Story[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAutoLoadMoreEnabled()
 * @method this unsetMegaphone()
 * @method this unsetMessage()
 * @method this unsetNextMaxId()
 * @method this unsetStatus()
 * @method this unsetStories()
 * @method this unset_Messages()
 */
class FollowingRecentActivityResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "stories"                to "model.Story[]",
        "next_max_id"            to "string",
        "auto_load_more_enabled" to "",
        "megaphone"              to ""
    )
}
