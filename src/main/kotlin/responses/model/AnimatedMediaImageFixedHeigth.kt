

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * AnimatedMediaImageFixedHeigth.
 *
 * @method string getHeigth()
 * @method string getMp4()
 * @method string getMp4Size()
 * @method string getSize()
 * @method string getUrl()
 * @method string getWebp()
 * @method string getWebpSize()
 * @method string getWidth()
 * @method bool isHeigth()
 * @method bool isMp4()
 * @method bool isMp4Size()
 * @method bool isSize()
 * @method bool isUrl()
 * @method bool isWebp()
 * @method bool isWebpSize()
 * @method bool isWidth()
 * @method this setHeigth(string $value)
 * @method this setMp4(string $value)
 * @method this setMp4Size(string $value)
 * @method this setSize(string $value)
 * @method this setUrl(string $value)
 * @method this setWebp(string $value)
 * @method this setWebpSize(string $value)
 * @method this setWidth(string $value)
 * @method this unsetHeigth()
 * @method this unsetMp4()
 * @method this unsetMp4Size()
 * @method this unsetSize()
 * @method this unsetUrl()
 * @method this unsetWebp()
 * @method this unsetWebpSize()
 * @method this unsetWidth()
 */
data class AnimatedMediaImageFixedHeigth (
    val url       : String,
    val width     : String,
    val heigth    : String,
    val size      : String,
    val mp4       : String,
    val mp4_size  : String,
    val webp      : String,
    val webp_size : String
){
//    val JSON_PROPERTY_MAP = [
//        "url"       => "string",
//        "width"     => "string",
//        "heigth"    => "string",
//        "size"      => "string",
//        "mp4"       => "string",
//        "mp4_size"  => "string",
//        "webp"      => "string",
//        "webp_size" => "string",
//    ]
}
