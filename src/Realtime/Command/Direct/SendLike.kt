

package InstagramAPI.Realtime.Command.Direct

final class SendLike : SendItem
{
    val TYPE = "like"

    /**
     * Constructor.
     *
     * @param string $threadId
     * @param array  $options
     *
     * @throws  IllegalArgumentException
     */
    public fun __construct(
        $threadId,
        array $options = [])
    {
        parent::__construct($threadId, self::TYPE, $options)
    }
}
