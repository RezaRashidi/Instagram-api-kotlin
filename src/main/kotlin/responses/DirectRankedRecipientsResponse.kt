

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.DirectRankedRecipient

/**
 * DirectRankedRecipientsResponse.
 *
 * @method mixed getExpires()
 * @method mixed getFiltered()
 * @method mixed getMessage()
 * @method string getRankToken()
 * @method model.DirectRankedRecipient[] getRankedRecipients()
 * @method string getRequestId()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isExpires()
 * @method bool isFiltered()
 * @method bool isMessage()
 * @method bool isRankToken()
 * @method bool isRankedRecipients()
 * @method bool isRequestId()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setExpires(mixed $value)
 * @method this setFiltered(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setRankToken(string $value)
 * @method this setRankedRecipients(model.DirectRankedRecipient[] $value)
 * @method this setRequestId(string $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetExpires()
 * @method this unsetFiltered()
 * @method this unsetMessage()
 * @method this unsetRankToken()
 * @method this unsetRankedRecipients()
 * @method this unsetRequestId()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class DirectRankedRecipientsResponse (
    val expires: String,
    val ranked_recipients: MutableList<DirectRankedRecipient>,
    val filtered: String,
    val request_id: String,
    val rank_token: String
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "expires"           to "",
//        "ranked_recipients" to "model.DirectRankedRecipient[]",
//        "filtered"          to "",
//        "request_id"        to "string",
//        "rank_token"        to "string"
//    )
}
