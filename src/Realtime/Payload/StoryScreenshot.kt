

package instagramAPI.Realtime.Payload

import instagramAPI.AutoPropertyMapper

/**
 * StoryScreenshot.
 *
 * @method .instagramAPI.Response.Model.User getActionUserDict()
 * @method int getMediaType()
 * @method bool isActionUserDict()
 * @method bool isMediaType()
 * @method this setActionUserDict(.instagramAPI.Response.Model.User $value)
 * @method this setMediaType(int $value)
 * @method this unsetActionUserDict()
 * @method this unsetMediaType()
 */
class StoryScreenshot : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "action_user_dict" => ".instagramAPI.Response.Model.User",
        /*
         * A number describing what type of media this is.
         */
        "media_type"       => "int",
    ]
}
