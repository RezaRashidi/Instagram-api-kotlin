

package InstagramAPI.Realtime.Command.Direct

final class SendHashtag : ShareItem
{
    val TYPE = "hashtag"

    /**
     * Constructor.
     *
     * @param string $threadId
     * @param string $hashtag
     * @param array  $options
     *
     * @throws .InvalidArgumentException
     */
    public fun __construct(
        $threadId,
        $hashtag,
        array $options = [])
    {
        parent::__construct($threadId, self::TYPE, $options)

        if (!is_string($hashtag)) {
            throw .InvalidArgumentException("The hashtag must be a string.")
        }

        $hashtag = ltrim(trim($hashtag), "#")
        if ($hashtag === "") {
            throw .InvalidArgumentException("The hashtag must not be empty.")
        }

        if (strpos($hashtag, " ") !== false) {
            throw .InvalidArgumentException("The hashtag must be one word.")
        }

        this._data["hashtag"] = $hashtag
        // Yeah, we need to send the hashtag twice.
        this._data["item_id"] = $hashtag
    }
}
