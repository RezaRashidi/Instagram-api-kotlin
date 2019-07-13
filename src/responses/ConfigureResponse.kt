

package instagramAPI.responses

import instagramAPI.Response

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
class ConfigureResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "upload_id"         to "string",
        "media"             to "model.Item",
        "client_sidecar_id" to "string",
        "message_metadata"  to "model.DirectMessageMetadata[]"
    )
}
