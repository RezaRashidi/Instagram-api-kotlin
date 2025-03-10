

package instagramAPI.realtimes.Command.Direct

import instagramAPI.Realtime.Command.DirectCommand

final class IndicateActivity : DirectCommand
{
    val ACTION = "indicate_activity"

    /**
     * Constructor.
     *
     * @param string $threadId
     * @param bool   $status
     * @param array  $options
     *
     * @throws  IllegalArgumentException
     */
    public fun __construct(
        $threadId,
        $status,
        array $options = [])
    {
        parent::__construct(self::ACTION, $threadId, $options)

        this._data["activity_status"] = $status ? "1" : "0"
    }

    /** {@inheritdoc} */
    protected fun _isClientContextRequired()
    {
        return true
    }
}
