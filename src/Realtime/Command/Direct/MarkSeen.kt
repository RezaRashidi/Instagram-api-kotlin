

package InstagramAPI.Realtime.Command.Direct

import InstagramAPI.Realtime.Command.DirectCommand

final class MarkSeen : DirectCommand
{
    val ACTION = "mark_seen"

    /**
     * Constructor.
     *
     * @param string $threadId
     * @param string $threadItemId
     * @param array  $options
     *
     * @throws  IllegalArgumentException
     */
    public fun __construct(
        $threadId,
        $threadItemId,
        array $options = [])
    {
        parent::__construct(self::ACTION, $threadId, $options)

        this._data["item_id"] = this._validateThreadItemId($threadItemId)
    }

    /** {@inheritdoc} */
    protected fun _isClientContextRequired()
    {
        return false
    }
}
