

package instagramAPI.responses

import instagramAPI.Response

/**
 * ActiveFeedAdsResponse.
 *
 * @method model.FeedItem[] getFeedItems()
 * @method mixed getMessage()
 * @method bool getMoreAvailable()
 * @method string getNextMaxId()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isFeedItems()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isNextMaxId()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setFeedItems(model.FeedItem[] $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(bool $value)
 * @method this setNextMaxId(string $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetFeedItems()
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetNextMaxId()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class ActiveFeedAdsResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "feed_items"     to "model.FeedItem[]",
        "next_max_id"    to "string",
        "more_available" to "bool"
    )
}
