

package InstagramAPI.Realtime.Command.Direct

final class SendReaction : SendItem
{
    val TYPE = 'reaction'

    val REACTION_LIKE = 'like'

    val STATUS_CREATED = 'created'
    val STATUS_DELETED = 'deleted'

    /**
     * Constructor.
     *
     * @param string $threadId
     * @param string $threadItemId
     * @param string $reaction
     * @param string $status
     * @param array  $options
     *
     * @throws .InvalidArgumentException
     */
    public fun __construct(
        $threadId,
        $threadItemId,
        $reaction,
        $status,
        array $options = [])
    {
        parent::__construct($threadId, self::TYPE, $options)

        // Handle thread item identifier.
        this._data['item_id'] = this._validateThreadItemId($threadItemId)
        this._data['node_type'] = 'item'

        // Handle reaction type.
        if (!in_array($reaction, this._getSupportedReactions(), true)) {
            throw .InvalidArgumentException(sprintf('"%s" is not a supported reaction.', $reaction))
        }
        this._data['reaction_type'] = $reaction

        // Handle reaction status.
        if (!in_array($status, this._getSupportedStatuses(), true)) {
            throw .InvalidArgumentException(sprintf('"%s" is not a supported reaction status.', $status))
        }
        this._data['reaction_status'] = $status
    }

    /**
     * Get the list of supported reactions.
     *
     * @return array
     */
    protected fun _getSupportedReactions()
    {
        return [
            self::REACTION_LIKE,
        ]
    }

    /**
     * Get the list of supported statuses.
     *
     * @return array
     */
    protected fun _getSupportedStatuses()
    {
        return [
            self::STATUS_CREATED,
            self::STATUS_DELETED,
        ]
    }
}
