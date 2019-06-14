

package InstagramAPI.Realtime.Command.Direct

final class SendLocation : ShareItem
{
    val TYPE = "location"

    /**
     * Constructor.
     *
     * @param string $threadId
     * @param string $locationId
     * @param array  $options
     *
     * @throws . IllegalArgumentException
     */
    public fun __construct(
        $threadId,
        $locationId,
        array $options = [])
    {
        parent::__construct($threadId, self::TYPE, $options)

        if (!ctype_digit($locationId) && (!is_int($locationId) || $locationId < 0)) {
            throw . IllegalArgumentException(sprintf(""%s" is not a valid location ID.", $locationId))
        }
        this._data["venue_id"] = (string) $locationId
        // Yeah, we need to send the location ID twice.
        this._data["item_id"] = (string) $locationId
    }
}
