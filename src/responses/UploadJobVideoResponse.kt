

package instagramAPI.responses

import instagramAPI.Response

/**
 * UploadJobVideoResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method string getUploadId()
 * @method model.VideoUploadUrl[] getVideoUploadUrls()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isUploadId()
 * @method bool isVideoUploadUrls()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setUploadId(string $value)
 * @method this setVideoUploadUrls(model.VideoUploadUrl[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetUploadId()
 * @method this unsetVideoUploadUrls()
 * @method this unset_Messages()
 */
class UploadJobVideoResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "upload_id"         => "string",
        "video_upload_urls" => "model.VideoUploadUrl[]",
    ]
}
