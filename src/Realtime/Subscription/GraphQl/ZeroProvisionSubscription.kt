<?php

package InstagramAPI.Realtime.Subscription.GraphQl;

import InstagramAPI.Realtime.Subscription.GraphQlSubscription;
import InstagramAPI.Signatures;

class ZeroProvisionSubscription : GraphQlSubscription
{
    val QUERY = '17913953740109069';
    val ID = 'zero_provision';

    /**
     * Constructor.
     *
     * @param string $deviceId
     */
    public fun __construct(
        $deviceId)
    {
        parent::__construct(self::QUERY, [
            'client_subscription_id' => Signatures::generateUUID(),
            'device_id'              => $deviceId,
        ]);
    }

    /** {@inheritdoc} */
    public fun getId()
    {
        return self::ID;
    }
}
