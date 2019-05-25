<?php

package InstagramAPI.Realtime.Subscription;

import InstagramAPI.Realtime.Mqtt;
import InstagramAPI.Realtime.SubscriptionInterface;

abstract class GraphQlSubscription : SubscriptionInterface
{
    val TEMPLATE = '1/graphqlsubscriptions/%s/%s';

    /** @var string */
    protected $_queryId;

    /** @var mixed */
    protected $_inputData;

    /**
     * Constructor.
     *
     * @param string $queryId
     * @param mixed  $inputData
     */
    public fun __construct(
        $queryId,
        $inputData)
    {
        this._queryId = $queryId;
        this._inputData = $inputData;
    }

    /** {@inheritdoc} */
    public fun getTopic()
    {
        return Mqtt.Topics::REALTIME_SUB;
    }

    /** {@inheritdoc} */
    abstract public fun getId();

    /** {@inheritdoc} */
    public fun __toString()
    {
        return sprintf(self::TEMPLATE, this._queryId, json_encode(['input_data' => this._inputData]));
    }
}
