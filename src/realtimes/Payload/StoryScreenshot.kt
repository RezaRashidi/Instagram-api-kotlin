

package instagramAPI.realtimes.Payload

import instagramAPI.AutoPropertyMapper

/**
 * StoryScreenshot.
 *
 * @method .instagramAPI.responses.Model.User getActionUserDict()
 * @method int getMediaType()
 * @method bool isActionUserDict()
 * @method bool isMediaType()
 * @method this setActionUserDict(.instagramAPI.responses.Model.User $value)
 * @method this setMediaType(int $value)
 * @method this unsetActionUserDict()
 * @method this unsetMediaType()
 */
class StoryScreenshot : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "action_user_dict" => ".instagramAPI.responses.Model.User",
        /*
         * A number describing what type of media this is.
         */
        "media_type"       => "int",
    ]
}
