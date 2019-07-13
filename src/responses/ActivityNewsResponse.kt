

package instagramAPI.responses

import instagramAPI.Response

/**
 * ActivityNewsResponse.
 *
 * @method mixed getAdsManager()
 * @method model.Aymf getAymf()
 * @method mixed getContinuation()
 * @method mixed getContinuationToken()
 * @method model.Counts getCounts()
 * @method model.Story[] getFriendRequestStories()
 * @method mixed getMessage()
 * @method model.Story[] getNewStories()
 * @method model.Story[] getOldStories()
 * @method mixed getPartition()
 * @method string getStatus()
 * @method model.Subscription getSubscription()
 * @method model._Message[] get_Messages()
 * @method bool isAdsManager()
 * @method bool isAymf()
 * @method bool isContinuation()
 * @method bool isContinuationToken()
 * @method bool isCounts()
 * @method bool isFriendRequestStories()
 * @method bool isMessage()
 * @method bool isNewStories()
 * @method bool isOldStories()
 * @method bool isPartition()
 * @method bool isStatus()
 * @method bool isSubscription()
 * @method bool is_Messages()
 * @method this setAdsManager(mixed $value)
 * @method this setAymf(model.Aymf $value)
 * @method this setContinuation(mixed $value)
 * @method this setContinuationToken(mixed $value)
 * @method this setCounts(model.Counts $value)
 * @method this setFriendRequestStories(model.Story[] $value)
 * @method this setMessage(mixed $value)
 * @method this setNewStories(model.Story[] $value)
 * @method this setOldStories(model.Story[] $value)
 * @method this setPartition(mixed $value)
 * @method this setStatus(string $value)
 * @method this setSubscription(model.Subscription $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAdsManager()
 * @method this unsetAymf()
 * @method this unsetContinuation()
 * @method this unsetContinuationToken()
 * @method this unsetCounts()
 * @method this unsetFriendRequestStories()
 * @method this unsetMessage()
 * @method this unsetNewStories()
 * @method this unsetOldStories()
 * @method this unsetPartition()
 * @method this unsetStatus()
 * @method this unsetSubscription()
 * @method this unset_Messages()
 */
class ActivityNewsResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "new_stories"            to "model.Story[]",
        "old_stories"            to "model.Story[]",
        "continuation"           to "",
        "friend_request_stories" to "model.Story[]",
        "counts"                 to "model.Counts",
        "subscription"           to "model.Subscription",
        "partition"              to "",
        "continuation_token"     to "",
        "ads_manager"            to "",
        "aymf"                   to "model.Aymf"
    )
}
