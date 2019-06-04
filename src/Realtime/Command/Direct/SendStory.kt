

package InstagramAPI.Realtime.Command.Direct

final class SendStory : ShareItem
{
    val TYPE = "story_share"

    val STORY_REGEXP = "#^.d+_.d+$#D"

    /**
     * Constructor.
     *
     * @param string $threadId
     * @param string $storyId
     * @param array  $options
     *
     * @throws .InvalidArgumentException
     */
    public fun __construct(
        $threadId,
        $storyId,
        array $options = [])
    {
        parent::__construct($threadId, self::TYPE, $options)

        if (!preg_match(self::STORY_REGEXP, $storyId)) {
            throw .InvalidArgumentException(sprintf(""%s" is not a valid story ID.", $storyId))
        }
        this._data["item_id"] = (string) $storyId
    }
}
