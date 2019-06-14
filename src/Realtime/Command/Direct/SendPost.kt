

package InstagramAPI.Realtime.Command.Direct

final class SendPost : ShareItem
{
    val TYPE = "media_share"

    val MEDIA_REGEXP = "#^.d+_.d+$#D"

    /**
     * Constructor.
     *
     * @param string $threadId
     * @param string $mediaId
     * @param array  $options
     *
     * @throws . IllegalArgumentException
     */
    public fun __construct(
        $threadId,
        $mediaId,
        array $options = [])
    {
        parent::__construct($threadId, self::TYPE, $options)

        if (!preg_match(self::MEDIA_REGEXP, $mediaId)) {
            throw . IllegalArgumentException(sprintf(""%s" is not a valid media ID.", $mediaId))
        }
        this._data["media_id"] = (string) $mediaId
    }
}
