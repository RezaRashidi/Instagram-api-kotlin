

package instagramAPI.responses

import instagramAPI.Response

/**
 * BlockedMediaResponse.
 *
 * @method mixed getMediaIds()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMediaIds()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMediaIds(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMediaIds()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class BlockedMediaResponse (
    val media_ids: String
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "media_ids" to ""
//    )
}
