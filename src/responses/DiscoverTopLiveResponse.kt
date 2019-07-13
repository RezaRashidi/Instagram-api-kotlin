

package instagramAPI.responses

import instagramAPI.Response

/**
 * DiscoverTopLiveResponse.
 *
 * @method bool getAutoLoadMoreEnabled()
 * @method model.Broadcast[] getBroadcasts()
 * @method mixed getMessage()
 * @method bool getMoreAvailable()
 * @method string getNextMaxId()
 * @method model.PostLiveItem[] getPostLiveBroadcasts()
 * @method mixed getScoreMap()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isAutoLoadMoreEnabled()
 * @method bool isBroadcasts()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isNextMaxId()
 * @method bool isPostLiveBroadcasts()
 * @method bool isScoreMap()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setAutoLoadMoreEnabled(bool $value)
 * @method this setBroadcasts(model.Broadcast[] $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(bool $value)
 * @method this setNextMaxId(string $value)
 * @method this setPostLiveBroadcasts(model.PostLiveItem[] $value)
 * @method this setScoreMap(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAutoLoadMoreEnabled()
 * @method this unsetBroadcasts()
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetNextMaxId()
 * @method this unsetPostLiveBroadcasts()
 * @method this unsetScoreMap()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class DiscoverTopLiveResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "broadcasts"             to "model.Broadcast[]",
        "post_live_broadcasts"   to "model.PostLiveItem[]",
        "score_map"              to "",
        "more_available"         to "bool",
        "auto_load_more_enabled" to "bool",
        "next_max_id"            to "string"
    )
}
