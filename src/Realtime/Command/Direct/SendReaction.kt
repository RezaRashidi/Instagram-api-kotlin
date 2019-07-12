

package instagramAPI.Realtime.Command.Direct

final class SendReaction : SendItem(){
    val TYPE = "reaction"

    val REACTION_LIKE = "like"

    val STATUS_CREATED = "created"
    val STATUS_DELETED = "deleted"

    /**
     * Constructor.
     *
     * @param string $threadId
     * @param string $threadItemId
     * @param string $reaction
     * @param string $status
     * @param array  $options
     *
     * @throws  IllegalArgumentException
     */
    fun __construct(threadId: String, threadItemId: String, reaction: String, status: String, array options = []){
        parent::__construct(threadId, self::TYPE, options)

        // Handle thread item identifier.
        this._data["item_id"] = this._validateThreadItemId(threadItemId)
        this._data["node_type"] = "item"

        // Handle reaction type.
        if (!_getSupportedReactions().contains(reaction)) {
            throw IllegalArgumentException("\"$reaction\" is not a supported reaction.")
        }
        this._data["reaction_type"] = reaction

        // Handle reaction status.
        if (!_getSupportedStatuses().contains(status)) {
            throw IllegalArgumentException("\"$status\" is not a supported reaction status.")
        }
        this._data["reaction_status"] = status
    }

    /**
     * Get the list of supported reactions.
     *
     * @return array
     */
    protected fun _getSupportedReactions(): Array<String> {
        return arrayOf(
            REACTION_LIKE
        )
    }

    /**
     * Get the list of supported statuses.
     *
     * @return array
     */
    protected fun _getSupportedStatuses(): Array<String> {
        return arrayOf(
            STATUS_CREATED,
            STATUS_DELETED
        )
    }
}
