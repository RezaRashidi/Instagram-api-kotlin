

package instagramAPI.Realtime.Command.Direct

final class SendLocation : ShareItem
{
    val TYPE = "location"

    /**
     * Constructor.
     *
     * @param (string) $threadId
     * @param (string) $locationId
     * @param array  $options
     *
     * @throws  IllegalArgumentException
     */
    fun __construct(threadId: String, locationId: String, array options = [])
    {
        parent::__construct(threadId, self::TYPE, options)

        if (!(locationId.toIntOrNull() && locationId > 0) && (locationId !is Int || locationId < 0)) {
            throw  IllegalArgumentException(sprintf(""%s" is not a valid location ID.", $locationId))
        }
        this._data["venue_id"] = (string) locationId
        // Yeah, we need to send the location ID twice.
        this._data["item_id"] = (string) locationId
    }
}
