

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * MediaData.
 *
 * @method Image_Versions2 getImageVersions2()
 * @method int getMediaType()
 * @method int getOriginalHeight()
 * @method int getOriginalWidth()
 * @method VideoVersions[] getVideoVersions()
 * @method bool isImageVersions2()
 * @method bool isMediaType()
 * @method bool isOriginalHeight()
 * @method bool isOriginalWidth()
 * @method bool isVideoVersions()
 * @method this setImageVersions2(Image_Versions2 $value)
 * @method this setMediaType(int $value)
 * @method this setOriginalHeight(int $value)
 * @method this setOriginalWidth(int $value)
 * @method this setVideoVersions(VideoVersions[] $value)
 * @method this unsetImageVersions2()
 * @method this unsetMediaType()
 * @method this unsetOriginalHeight()
 * @method this unsetOriginalWidth()
 * @method this unsetVideoVersions()
 */
data class MediaData (
    val image_versions2 : Image_Versions2,
    val original_width  : Int,
    val original_height : Int,
    val media_type      : Int,
    val video_versions  : VideoVersions
){
//    val JSON_PROPERTY_MAP = [
//        "image_versions2" => "Image_Versions2",
//        "original_width"  => "int",
//        "original_height" => "int",
//        /*
//         * A number describing what type of media this is.
//         */
//        "media_type"      => "int",
//        "video_versions"  => "VideoVersions[]",
//    ]
}
