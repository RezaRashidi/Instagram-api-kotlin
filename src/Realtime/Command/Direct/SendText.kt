

package InstagramAPI.Realtime.Command.Direct

final class SendText : SendItem
{
    val TYPE = "text"

    /**
     * Constructor.
     *
     * @param string $threadId
     * @param string $text
     * @param array  $options
     *
     * @throws . IllegalArgumentException
     */
    public fun __construct(
        $threadId,
        $text,
        array $options = [])
    {
        parent::__construct($threadId, self::TYPE, $options)

        if (!is_string($text)) {
            throw . IllegalArgumentException("The text must be a string.")
        }

        if ($text === "") {
            throw . IllegalArgumentException("The text can not be empty.")
        }
        this._data["text"] = $text
    }
}
