

package InstagramAPI.Realtime.Command

import InstagramAPI.Realtime.CommandInterface
import InstagramAPI.Realtime.Mqtt

class IrisSubscribe : CommandInterface
{
    val INVALID_SEQUENCE_ID = -1

    /** @var int */
    private $_sequenceId

    /**
     * Constructor.
     *
     * @param int $sequenceId
     *
     * @throws  IllegalArgumentException
     */
    public fun __construct(
        $sequenceId)
    {
        if ($sequenceId === self::INVALID_SEQUENCE_ID) {
            throw  IllegalArgumentException("Invalid Iris sequence identifier.")
        }
        this._sequenceId = $sequenceId
    }

    /** {@inheritdoc} */
    public fun getTopic()
    {
        return Mqtt.Topics::IRIS_SUB
    }

    /** {@inheritdoc} */
    public fun getQosLevel()
    {
        return Mqtt.QosLevel::ACKNOWLEDGED_DELIVERY
    }

    /** {@inheritdoc} */
    public fun jsonSerialize()
    {
        return [
            "seq_id" => this._sequenceId,
        ]
    }
}
