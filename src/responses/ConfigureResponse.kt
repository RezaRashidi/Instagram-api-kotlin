

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.DirectMessageMetadata
import instagramAPI.responses.model.Item

/**
 * ConfigureResponse.
 *
 * @method string getClientSidecarId()
 * @method model.Item getMedia()
 * @method mixed getMessage()
 * @method model.DirectMessageMetadata[] getMessageMetadata()
 * @method string getStatus()
 * @method string getUploadId()
 * @method model._Message[] get_Messages()
 * @method bool isClientSidecarId()
 * @method bool isMedia()
 * @method bool isMessage()
 * @method bool isMessageMetadata()
 * @method bool isStatus()
 * @method bool isUploadId()
 * @method bool is_Messages()
 * @method this setClientSidecarId(string $value)
 * @method this setMedia(model.Item $value)
 * @method this setMessage(mixed $value)
 * @method this setMessageMetadata(model.DirectMessageMetadata[] $value)
 * @method this setStatus(string $value)
 * @method this setUploadId(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetClientSidecarId()
 * @method this unsetMedia()
 * @method this unsetMessage()
 * @method this unsetMessageMetadata()
 * @method this unsetStatus()
 * @method this unsetUploadId()
 * @method this unset_Messages()
 */
data class ConfigureResponse (
    val upload_id: String,
    val media: Item,
    val client_sidecar_id: String,
    val message_metadata: MutableList<DirectMessageMetadata>
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "upload_id"         to "string",
//        "media"             to "model.Item",
//        "client_sidecar_id" to "string",
//        "message_metadata"  to "model.DirectMessageMetadata[]"
//    )
}
