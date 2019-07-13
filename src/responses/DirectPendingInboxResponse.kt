

package instagramAPI.responses

import instagramAPI.Response

/**
 * DirectPendingInboxResponse.
 *
 * @method model.DirectInbox getInbox()
 * @method mixed getMessage()
 * @method mixed getPendingRequestsTotal()
 * @method string getSeqId()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isInbox()
 * @method bool isMessage()
 * @method bool isPendingRequestsTotal()
 * @method bool isSeqId()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setInbox(model.DirectInbox $value)
 * @method this setMessage(mixed $value)
 * @method this setPendingRequestsTotal(mixed $value)
 * @method this setSeqId(string $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetInbox()
 * @method this unsetMessage()
 * @method this unsetPendingRequestsTotal()
 * @method this unsetSeqId()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class DirectPendingInboxResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "seq_id"                 to "string",
        "pending_requests_total" to "",
        "inbox"                  to "model.DirectInbox"
    )
}
