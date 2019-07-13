

package instagramAPI.responses

import instagramAPI.Response

/**
 * TVChannelsResponse.
 *
 * @method string getId()
 * @method model.Item[] getItems()
 * @method string getMaxId()
 * @method mixed getMessage()
 * @method bool getMoreAvailable()
 * @method mixed getSeenState()
 * @method string getStatus()
 * @method string getTitle()
 * @method string getType()
 * @method model.User getUserDict()
 * @method model._Message[] get_Messages()
 * @method bool isId()
 * @method bool isItems()
 * @method bool isMaxId()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isSeenState()
 * @method bool isStatus()
 * @method bool isTitle()
 * @method bool isType()
 * @method bool isUserDict()
 * @method bool is_Messages()
 * @method this setId(string $value)
 * @method this setItems(model.Item[] $value)
 * @method this setMaxId(string $value)
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(bool $value)
 * @method this setSeenState(mixed $value)
 * @method this setStatus(string $value)
 * @method this setTitle(string $value)
 * @method this setType(string $value)
 * @method this setUserDict(model.User $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetId()
 * @method this unsetItems()
 * @method this unsetMaxId()
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetSeenState()
 * @method this unsetStatus()
 * @method this unsetTitle()
 * @method this unsetType()
 * @method this unsetUserDict()
 * @method this unset_Messages()
 */
class TVChannelsResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "type"              => "string",
        "title"             => "string",
        "id"                => "string",
        "items"             => "model.Item[]",
        "more_available"    => "bool",
        "max_id"            => "string",
        "seen_state"        => "",
        "user_dict"         => "model.User",
    ]
}
