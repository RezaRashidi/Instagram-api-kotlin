

package instagramAPI.responses

import instagramAPI.Response

/**
 * FinalViewerListResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method int getTotalUniqueViewerCount()
 * @method model.User[] getUsers()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isTotalUniqueViewerCount()
 * @method bool isUsers()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setTotalUniqueViewerCount(int $value)
 * @method this setUsers(model.User[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetTotalUniqueViewerCount()
 * @method this unsetUsers()
 * @method this unset_Messages()
 */
class FinalViewerListResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "users"                     to "model.User[]",
        "total_unique_viewer_count" to "int"
    )
}
