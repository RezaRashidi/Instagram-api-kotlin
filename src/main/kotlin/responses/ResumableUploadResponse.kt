

package instagramAPI.responses

import instagramAPI.Response

/**
 * ResumableUploadResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method int getUploadId()
 * @method mixed getXsharingNonces()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isUploadId()
 * @method bool isXsharingNonces()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setUploadId(int $value)
 * @method this setXsharingNonces(mixed $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetUploadId()
 * @method this unsetXsharingNonces()
 * @method this unset_Messages()
 */
data class ResumableUploadResponse (
    val xsharing_nonces : String,
    val upload_id       : Int
){
//    val JSON_PROPERTY_MAP = [
//        "xsharing_nonces" => "",
//        "upload_id"       => "int",
//    ]
}
