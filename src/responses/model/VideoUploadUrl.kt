

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * VideoUploadUrl.
 *
 * @method float getExpires()
 * @method string getJob()
 * @method string getUrl()
 * @method bool isExpires()
 * @method bool isJob()
 * @method bool isUrl()
 * @method this setExpires(float $value)
 * @method this setJob(string $value)
 * @method this setUrl(string $value)
 * @method this unsetExpires()
 * @method this unsetJob()
 * @method this unsetUrl()
 */
data class VideoUploadUrl (
    val url     : String,
    val job     : String,
    val expires : Float
){
//    val JSON_PROPERTY_MAP = [
//        "url"     => "string",
//        "job"     => "string",
//        "expires" => "float",
//    ]
}
