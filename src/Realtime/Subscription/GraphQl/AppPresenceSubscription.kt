

package instagramAPI.Realtime.Subscription.GraphQl

import instagramAPI.Realtime.Subscription.GraphQlSubscription

class AppPresenceSubscription : GraphQlSubscription
{
    val ID = "presence_subscribe"
    val QUERY = "17846944882223835"

    /**
     * Constructor.
     *
     * @param string $subscriptionId
     */
    public fun __construct(
        $subscriptionId)
    {
        parent::__construct(self::QUERY, [
            "client_subscription_id" => $subscriptionId,
        ])
    }

    /** {@inheritdoc} */
    public fun getId()
    {
        return self::ID
    }
}
