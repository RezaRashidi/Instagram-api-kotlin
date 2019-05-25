

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

/**
 * DirectThreadItemMedia.
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
class DirectThreadItemMedia : AutoPropertyMapper
{
    val PHOTO = 1
    val VIDEO = 2

    val JSON_PROPERTY_MAP = [
        /*
         * A number describing what type of media this is. Should be compared
         * against the `DirectThreadItemMedia::PHOTO` and
         * `DirectThreadItemMedia::VIDEO` constants!
         */
        'media_type'      => 'int',
        'image_versions2' => 'Image_Versions2',
        'video_versions'  => 'VideoVersions[]',
        'original_width'  => 'int',
        'original_height' => 'int',
    ]
}
