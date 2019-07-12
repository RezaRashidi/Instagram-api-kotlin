

package instagramAPI.Response

import instagramAPI.Response

/**
 * ActivityNewsResponse.
 *
 * @method mixed getAdsManager()
 * @method Model.Aymf getAymf()
 * @method mixed getContinuation()
 * @method mixed getContinuationToken()
 * @method Model.Counts getCounts()
 * @method Model.Story[] getFriendRequestStories()
 * @method mixed getMessage()
 * @method Model.Story[] getNewStories()
 * @method Model.Story[] getOldStories()
 * @method mixed getPartition()
 * @method string getStatus()
 * @method Model.Subscription getSubscription()
 * @method Model._Message[] get_Messages()
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
 * @method this setAymf(Model.Aymf $value)
 * @method this setContinuation(mixed $value)
 * @method this setContinuationToken(mixed $value)
 * @method this setCounts(Model.Counts $value)
 * @method this setFriendRequestStories(Model.Story[] $value)
 * @method this setMessage(mixed $value)
 * @method this setNewStories(Model.Story[] $value)
 * @method this setOldStories(Model.Story[] $value)
 * @method this setPartition(mixed $value)
 * @method this setStatus(string $value)
 * @method this setSubscription(Model.Subscription $value)
 * @method this set_Messages(Model._Message[] $value)
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
        "new_stories"            to "Model.Story[]",
        "old_stories"            to "Model.Story[]",
        "continuation"           to "",
        "friend_request_stories" to "Model.Story[]",
        "counts"                 to "Model.Counts",
        "subscription"           to "Model.Subscription",
        "partition"              to "",
        "continuation_token"     to "",
        "ads_manager"            to "",
        "aymf"                   to "Model.Aymf"
    )
}
