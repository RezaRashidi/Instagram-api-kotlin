

package instagramAPI.responses.Model

import instagramAPI.AutoPropertyMapper

/**
 * QPExtraInfo.
 *
 * @method string getExtraInfo()
 * @method int getSurface()
 * @method bool isExtraInfo()
 * @method bool isSurface()
 * @method this setExtraInfo(string $value)
 * @method this setSurface(int $value)
 * @method this unsetExtraInfo()
 * @method this unsetSurface()
 */
class QPExtraInfo : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "surface"                 => "int",
        "extra_info"              => "string",
    ]
}
