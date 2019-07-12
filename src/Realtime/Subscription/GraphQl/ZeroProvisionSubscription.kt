

package instagramAPI.Realtime.Subscription.GraphQl

import instagramAPI.Realtime.Subscription.GraphQlSubscription

class ZeroProvisionSubscription : GraphQlSubscription
{
    val QUERY = "17913953740109069"
    val ID = "zero_provision"

    /**
     * Constructor.
     *
     * @param string $deviceId
     */
    public fun __construct(
        $deviceId)
    {
        parent::__construct(self::QUERY, [
            "client_subscription_id" => Signatures::generateUUID(),
            "device_id"              => $deviceId,
        ])
    }

    /** {@inheritdoc} */
    public fun getId()
    {
        return self::ID
    }
}
