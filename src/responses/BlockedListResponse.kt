

package instagramAPI.responses

import instagramAPI.Response

/**
 * BlockedListResponse.
 *
 * @method model.User[] getBlockedList()
 * @method mixed getMessage()
 * @method string getNextMaxId()
 * @method mixed getPageSize()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isBlockedList()
 * @method bool isMessage()
 * @method bool isNextMaxId()
 * @method bool isPageSize()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setBlockedList(model.User[] $value)
 * @method this setMessage(mixed $value)
 * @method this setNextMaxId(string $value)
 * @method this setPageSize(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetBlockedList()
 * @method this unsetMessage()
 * @method this unsetNextMaxId()
 * @method this unsetPageSize()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class BlockedListResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "blocked_list" to "model.User[]",
        "next_max_id"  to "string",
        "page_size"    to ""
    )
}
