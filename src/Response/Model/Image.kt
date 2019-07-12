

package instagramAPI.Response.Model

import instagramAPI.AutoPropertyMapper

/**
 * Image.
 *
 * @method int getHeight()
 * @method string getUri()
 * @method int getWidth()
 * @method bool isHeight()
 * @method bool isUri()
 * @method bool isWidth()
 * @method this setHeight(int $value)
 * @method this setUri(string $value)
 * @method this setWidth(int $value)
 * @method this unsetHeight()
 * @method this unsetUri()
 * @method this unsetWidth()
 */
class Image : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "uri"    => "string",
        "width"  => "int",
        "height" => "int",
    ]
}
