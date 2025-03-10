

package instagramAPI.realtimes.Command.Direct

abstract class ShareItem : SendItem
{
    /**
     * Constructor.
     *
     * @param string $threadId
     * @param string $itemType
     * @param array  $options
     */
    public fun __construct(
        $threadId,
        $itemType,
        array $options = [])
    {
        parent::__construct($threadId, $itemType, $options)

        if (isset($options["text"])) {
            if (!is_string($options["text"])) {
                throw  IllegalArgumentException("The text must be a string.")
            }
            this._data["text"] = $options["text"]
        } else {
            this._data["text"] = ""
        }
    }
}
