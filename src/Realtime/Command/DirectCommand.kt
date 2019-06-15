

package InstagramAPI.Realtime.Command

import InstagramAPI.Realtime.CommandInterface
import InstagramAPI.Realtime.Mqtt
import InstagramAPI.Response.Model.In
import InstagramAPI.Signatures

abstract class DirectCommand : CommandInterface
{
    /**
     * @var array
     */
    protected $_data

    /**
     * Constructor.
     *
     * @param string $action
     * @param string $threadId
     * @param array  $options
     *
     * @throws  IllegalArgumentException
     */
    public fun __construct(
        $action,
        $threadId,
        array $options = [])
    {
        this._data = []

        // Handle action.
        if (!in_array($action, this._getSupportedActions(), true)) {
            throw  IllegalArgumentException(sprintf(""%s" is not a supported action.", $action))
        }
        this._data["action"] = $action

        this._data["thread_id"] = this._validateThreadId($threadId)

        // Handle client context.
        if (this._isClientContextRequired()) {
            if (!isset($options["client_context"])) {
                this._data["client_context"] = Signatures::generateUUID()
            } elseif (!Signatures::isValidUUID($options["client_context"])) {
                throw  IllegalArgumentException(sprintf(""%s" is not a valid UUID.", $options["client_context"]))
            } else {
                this._data["client_context"] = $options["client_context"]
            }
        }
    }

    /** {@inheritdoc} */
    public fun getTopic()
    {
        return Mqtt.Topics::SEND_MESSAGE
    }

    /** {@inheritdoc} */
    public fun getQosLevel()
    {
        return Mqtt.QosLevel::FIRE_AND_FORGET
    }

    /** {@inheritdoc} */
    public fun jsonSerialize()
    {
        return this._reorderFieldsByWeight(this._data, this._getFieldsWeights())
    }

    /**
     * Get the client context.
     *
     * @return string|null
     */
    public fun getClientContext()
    {
        return isset(this._data["client_context"]) ? this._data["client_context"] : null
    }

    /**
     * Check whether client_context param is required.
     *
     * @return bool
     */
    abstract protected fun _isClientContextRequired()

    /**
     * Get the list of supported actions.
     *
     * @return array
     */
    protected fun _getSupportedActions()
    {
        return [
            Direct.SendItem::ACTION,
            Direct.MarkSeen::ACTION,
            Direct.IndicateActivity::ACTION,
        ]
    }

    /**
     * Validate given thread identifier.
     *
     * @param (string) $threadId
     *
     * @throws  IllegalArgumentException
     *
     * @return string
     */
    protected fun _validateThreadId(threadId: String): String {
        if (!(threadId.toIntOrNull() && threadId  > 0) && (threadId !is Int || threadId < 0)) {
            throw  IllegalArgumentException(sprintf(""%s" is not a valid thread identifier.", $threadId))
        }

        return threadId
    }

    /**
     * Validate given thread item identifier.
     *
     * @param (string) $threadItemId
     *
     * @throws  IllegalArgumentException
     *
     * @return string
     */
    protected fun _validateThreadItemId(threadItemId: String): String {
        if (!(threadItemId.toIntOrNull() && threadItemId > 0) && (threadItemId!is Int || threadItemId < 0)) {
            throw  IllegalArgumentException(sprintf(""%s" is not a valid thread item identifier.", $threadItemId))
        }

        return threadItemId
    }

    /**
     * Reorders an array of fields by weights to simplify debugging.
     *
     * @param array $fields
     * @param array $weights
     *
     * @return array
     */
    protected fun _reorderFieldsByWeight(
        array $fields,
        array $weights)
    {
        uksort($fields, fun ($a, $b) import ($weights) {
            $a = isset($weights[$a]) ? $weights[$a] : PHP_INT_MAX
            $b = isset($weights[$b]) ? $weights[$b] : PHP_INT_MAX
            if ($a < $b) {
                return -1
            }
            if ($a > $b) {
                return 1
            }

            return 0
        })

        return $fields
    }

    /**
     * Get weights for fields.
     *
     * @return array
     */
    protected fun _getFieldsWeights() {
        return mapOf(
            "thread_id"       to 10,
            "item_type"       to 15,
            "text"            to 20,
            "client_context"  to 25,
            "activity_status" to 30,
            "reaction_type"   to 35,
            "reaction_status" to 40,
            "item_id"         to 45,
            "node_type"       to 50,
            "action"          to 55,
            "profile_user_id" to 60,
            "hashtag"         to 65,
            "venue_id"        to 70,
            "media_id"        to 75,
        )
    }
}
