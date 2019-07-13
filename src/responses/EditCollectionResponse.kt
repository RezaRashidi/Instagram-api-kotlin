

package instagramAPI.responses

import instagramAPI.Response

/**
 * EditCollectionResponse.
 *
 * @method string getCollectionId()
 * @method string getCollectionName()
 * @method model.Item getCoverMedia()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isCollectionId()
 * @method bool isCollectionName()
 * @method bool isCoverMedia()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setCollectionId(string $value)
 * @method this setCollectionName(string $value)
 * @method this setCoverMedia(model.Item $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetCollectionId()
 * @method this unsetCollectionName()
 * @method this unsetCoverMedia()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class EditCollectionResponse : Response
{
    val JSON_PROPERTY_MAP = [
        Model.Collection::class, // Import property map.
    ]
}
