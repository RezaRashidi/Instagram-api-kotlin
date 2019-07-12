

package instagramAPI.Realtime.Subscription

import instagramAPI.Realtime.Mqtt
import instagramAPI.Realtime.SubscriptionInterface

abstract class SkywalkerSubscription : SubscriptionInterface
{
    /** @var string */
    protected $_accountId

    /**
     * Constructor.
     *
     * @param string $accountId
     */
    public fun __construct(
        $accountId)
    {
        this._accountId = $accountId
    }

    /** {@inheritdoc} */
    public fun getTopic()
    {
        return Mqtt.Topics::PUBSUB
    }

    /** {@inheritdoc} */
    abstract public fun getId()

    /** {@inheritdoc} */
    abstract public fun __toString()
}
