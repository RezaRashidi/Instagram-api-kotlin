

package instagramAPI.Response

import instagramAPI.Response

/**
 * DirectInboxResponse.
 *
 * @method Model.DirectInbox getInbox()
 * @method Model.Megaphone getMegaphone()
 * @method mixed getMessage()
 * @method mixed getPendingRequestsTotal()
 * @method Model.User[] getPendingRequestsUsers()
 * @method string getSeqId()
 * @method string getSnapshotAtMs()
 * @method string getStatus()
 * @method Model._Message[] get_Messages()
 * @method bool isInbox()
 * @method bool isMegaphone()
 * @method bool isMessage()
 * @method bool isPendingRequestsTotal()
 * @method bool isPendingRequestsUsers()
 * @method bool isSeqId()
 * @method bool isSnapshotAtMs()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setInbox(Model.DirectInbox $value)
 * @method this setMegaphone(Model.Megaphone $value)
 * @method this setMessage(mixed $value)
 * @method this setPendingRequestsTotal(mixed $value)
 * @method this setPendingRequestsUsers(Model.User[] $value)
 * @method this setSeqId(string $value)
 * @method this setSnapshotAtMs(string $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(Model._Message[] $value)
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
class DirectInboxResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "pending_requests_total" to "",
        "seq_id"                 to "string",
        "pending_requests_users" to "Model.User[]",
        "inbox"                  to "Model.DirectInbox",
        "megaphone"              to "Model.Megaphone",
        "snapshot_at_ms"         to "string"
    )
}
