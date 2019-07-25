

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.DirectThreadItem
import instagramAPI.responses.model.User

/**
 * DirectCreateGroupThreadResponse.
 *
 * @method mixed getCanonical()
 * @method bool getHasNewer()
 * @method bool getHasOlder()
 * @method model.User getInviter()
 * @method mixed getIsPin()
 * @method model.DirectThreadItem[] getItems()
 * @method mixed getLastActivityAt()
 * @method mixed getLastSeenAt()
 * @method model.User[] getLeftUsers()
 * @method mixed getMessage()
 * @method mixed getMuted()
 * @method mixed getNamed()
 * @method mixed getPending()
 * @method string getStatus()
 * @method string getThreadId()
 * @method mixed getThreadTitle()
 * @method mixed getThreadType()
 * @method model.User[] getUsers()
 * @method string getViewerId()
 * @method model._Message[] get_Messages()
 * @method bool isCanonical()
 * @method bool isHasNewer()
 * @method bool isHasOlder()
 * @method bool isInviter()
 * @method bool isIsPin()
 * @method bool isItems()
 * @method bool isLastActivityAt()
 * @method bool isLastSeenAt()
 * @method bool isLeftUsers()
 * @method bool isMessage()
 * @method bool isMuted()
 * @method bool isNamed()
 * @method bool isPending()
 * @method bool isStatus()
 * @method bool isThreadId()
 * @method bool isThreadTitle()
 * @method bool isThreadType()
 * @method bool isUsers()
 * @method bool isViewerId()
 * @method bool is_Messages()
 * @method this setCanonical(mixed $value)
 * @method this setHasNewer(bool $value)
 * @method this setHasOlder(bool $value)
 * @method this setInviter(model.User $value)
 * @method this setIsPin(mixed $value)
 * @method this setItems(model.DirectThreadItem[] $value)
 * @method this setLastActivityAt(mixed $value)
 * @method this setLastSeenAt(mixed $value)
 * @method this setLeftUsers(model.User[] $value)
 * @method this setMessage(mixed $value)
 * @method this setMuted(mixed $value)
 * @method this setNamed(mixed $value)
 * @method this setPending(mixed $value)
 * @method this setStatus(string $value)
 * @method this setThreadId(string $value)
 * @method this setThreadTitle(mixed $value)
 * @method this setThreadType(mixed $value)
 * @method this setUsers(model.User[] $value)
 * @method this setViewerId(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetCanonical()
 * @method this unsetHasNewer()
 * @method this unsetHasOlder()
 * @method this unsetInviter()
 * @method this unsetIsPin()
 * @method this unsetItems()
 * @method this unsetLastActivityAt()
 * @method this unsetLastSeenAt()
 * @method this unsetLeftUsers()
 * @method this unsetMessage()
 * @method this unsetMuted()
 * @method this unsetNamed()
 * @method this unsetPending()
 * @method this unsetStatus()
 * @method this unsetThreadId()
 * @method this unsetThreadTitle()
 * @method this unsetThreadType()
 * @method this unsetUsers()
 * @method this unsetViewerId()
 * @method this unset_Messages()
 */
data class DirectCreateGroupThreadResponse (
    val thread_id: String,
    val users: MutableList<User>,
    val left_users: MutableList<User>,
    val items: MutableList<DirectThreadItem>,
    val last_activity_at: String,
    val muted: String,
    val named: String,
    val canonical: String,
    val pending: String,
    val thread_type: String,
    val viewer_id: String,
    val thread_title: String,
    val inviter: User,
    val has_older: Boolean,
    val has_newer: Boolean,
    val last_seen_at: String,
    val is_pin: String
    ){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "thread_id"        to "string",
//        "users"            to "model.User[]",
//        "left_users"       to "model.User[]",
//        "items"            to "model.DirectThreadItem[]",
//        "last_activity_at" to "",
//        "muted"            to "",
//        "named"            to "",
//        "canonical"        to "",
//        "pending"          to "",
//        "thread_type"      to "",
//        "viewer_id"        to "string",
//        "thread_title"     to "",
//        "inviter"          to "model.User",
//        "has_older"        to "bool",
//        "has_newer"        to "bool",
//        "last_seen_at"     to "",
//        "is_pin"           to ""
//    )
}
