

package instagramAPI.Realtime.Command.Direct

import instagramAPI.Realtime.Command.DirectCommand

abstract class SendItem : DirectCommand(){
    val ACTION = "send_item"

    /**
     * Constructor.
     *
     * @param string $threadId
     * @param string $itemType
     * @param array  $options
     *
     * @throws  IllegalArgumentException
     */
    fun __construct(threadId: String, itemType: String, array options = []){
        parent::__construct(self::ACTION, threadId, options)

        // Handle action.
        if (!_getSupportedItemTypes().contains(itemType)) {
            throw  IllegalArgumentException("\"$itemType\" is not a supported item type.")
        }
        this._data["item_type"] = itemType
    }

    /** {@inheritdoc} */
    protected fun _isClientContextRequired(): Boolean {
        return true
    }

    /**
     * Get the list of supported item types.
     *
     * @return array
     */
    protected fun _getSupportedItemTypes(): Array<String>{
        return arrayOf(
            SendText::TYPE,
            SendLike::TYPE,
            SendReaction::TYPE,
            SendPost::TYPE,
            SendStory::TYPE,
            SendProfile::TYPE,
            SendLocation::TYPE,
            SendHashtag::TYPE
        )
    }
}
