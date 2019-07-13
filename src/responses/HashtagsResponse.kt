

package instagramAPI.responses

import instagramAPI.Response

/**
 * HashtagsResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model.Hashtag[] getTags()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isTags()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setTags(model.Hashtag[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetTags()
 * @method this unset_Messages()
 */
class HashtagsResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "tags" => "model.Hashtag[]",
    ]
}
