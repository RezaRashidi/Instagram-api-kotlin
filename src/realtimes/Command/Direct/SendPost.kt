

package instagramAPI.realtimes.Command.Direct

final class SendPost : ShareItem(){
    val TYPE = "media_share"

    val MEDIA_REGEXP = "#^.d+_.d+$#D"

    /**
     * Constructor.
     *
     * @param string $threadId
     * @param string $mediaId
     * @param array  $options
     *
     * @throws  IllegalArgumentException
     */
    fun __construct(threadId: String, mediaId: String, array options = []){
        parent::__construct(threadId, self::TYPE, options)

        if (!preg_match(MEDIA_REGEXP, mediaId)) {
            throw  IllegalArgumentException("\"$mediaId\" is not a valid media ID.")
        }
        this._data["media_id"] = mediaId
    }
}
