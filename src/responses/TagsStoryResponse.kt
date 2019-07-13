

package instagramAPI.responses

import instagramAPI.Response

/**
 * TagsStoryResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model.Reel getStory()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isStory()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setStory(model.Reel $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetStory()
 * @method this unset_Messages()
 */
class TagsStoryResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "story"       => "model.Reel",
    ]
}
