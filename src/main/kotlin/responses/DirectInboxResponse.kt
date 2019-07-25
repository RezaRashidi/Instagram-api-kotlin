

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.DirectInbox
import instagramAPI.responses.model.Megaphone
import instagramAPI.responses.model.User

/**
 * DirectInboxResponse.
 *
 * @method model.DirectInbox getInbox()
 * @method model.Megaphone getMegaphone()
 * @method mixed getMessage()
 * @method mixed getPendingRequestsTotal()
 * @method model.User[] getPendingRequestsUsers()
 * @method string getSeqId()
 * @method string getSnapshotAtMs()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isInbox()
 * @method bool isMegaphone()
 * @method bool isMessage()
 * @method bool isPendingRequestsTotal()
 * @method bool isPendingRequestsUsers()
 * @method bool isSeqId()
 * @method bool isSnapshotAtMs()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setInbox(model.DirectInbox $value)
 * @method this setMegaphone(model.Megaphone $value)
 * @method this setMessage(mixed $value)
 * @method this setPendingRequestsTotal(mixed $value)
 * @method this setPendingRequestsUsers(model.User[] $value)
 * @method this setSeqId(string $value)
 * @method this setSnapshotAtMs(string $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetInbox()
 * @method this unsetMegaphone()
 * @method this unsetMessage()
 * @method this unsetPendingRequestsTotal()
 * @method this unsetPendingRequestsUsers()
 * @method this unsetSeqId()
 * @method this unsetSnapshotAtMs()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class DirectInboxResponse (
    val pending_requests_total: String,
    val seq_id: String,
    val pending_requests_users: MutableList<User>,
    val inbox: DirectInbox,
    val megaphone: Megaphone,
    val snapshot_at_ms: String
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "pending_requests_total" to "",
//        "seq_id"                 to "string",
//        "pending_requests_users" to "model.User[]",
//        "inbox"                  to "model.DirectInbox",
//        "megaphone"              to "model.Megaphone",
//        "snapshot_at_ms"         to "string"
//    )
}
