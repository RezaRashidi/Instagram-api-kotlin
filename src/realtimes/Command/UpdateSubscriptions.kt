

package instagramAPI.realtimes.Command

import instagramAPI.Realtime.CommandInterface
import instagramAPI.Realtime.Mqtt.QosLevel

class UpdateSubscriptions : CommandInterface
{
    /** @var string */
    private $_topic

    /** @var array */
    private $_subscribe

    /** @var array */
    private $_unsubscribe

    /**
     * Constructor.
     *
     * @param string $topic
     * @param array  $subscribe
     * @param array  $unsubscribe
     */
    public fun __construct(
        $topic,
        array $subscribe,
        array $unsubscribe)
    {
        this._topic = $topic
        this._subscribe = $subscribe
        this._unsubscribe = $unsubscribe
    }

    /** {@inheritdoc} */
    public fun getTopic()
    {
        return this._topic
    }

    /** {@inheritdoc} */
    public fun getQosLevel()
    {
        return QosLevel::ACKNOWLEDGED_DELIVERY
    }

    /**
     * Prepare the subscriptions list.
     *
     * @param array $subscriptions
     *
     * @return array
     */
    private fun _prepareSubscriptions(
        array $subscriptions)
    {
        $result = []
        foreach ($subscriptions as $subscription) {
            $result[] = (string) $subscription
        }
        usort($result, fun ($a, $b) {
            $hashA = Utils::hashCode($a)
            $hashB = Utils::hashCode($b)

            if ($hashA > $hashB) {
                return 1
            }
            if ($hashA < $hashB) {
                return -1
            }

            return 0
        })

        return $result
    }

    /** {@inheritdoc} */
    public fun jsonSerialize()
    {
        $result = []
        if (count(this._subscribe)) {
            $result["sub"] = this._prepareSubscriptions(this._subscribe)
        }
        if (count(this._unsubscribe)) {
            $result["unsub"] = this._prepareSubscriptions(this._unsubscribe)
        }

        return $result
    }
}
