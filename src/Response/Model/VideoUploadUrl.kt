

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

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
class VideoUploadUrl : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'url'     => 'string',
        'job'     => 'string',
        'expires' => 'float',
    ]
}
