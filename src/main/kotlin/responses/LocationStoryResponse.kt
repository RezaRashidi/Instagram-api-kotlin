

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.StoryTray

/**
 * LocationStoryResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model.StoryTray getStory()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isStory()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setStory(model.StoryTray $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetStory()
 * @method this unset_Messages()
 */
data class LocationStoryResponse (
    val story : StoryTray
){
//    val JSON_PROPERTY_MAP = [
//        "story"               => "model.StoryTray",
//    ]
}
