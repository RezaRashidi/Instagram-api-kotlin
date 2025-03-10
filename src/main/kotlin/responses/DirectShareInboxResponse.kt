

package instagramAPI.responses

import instagramAPI.Response

/**
 * DirectShareInboxResponse.
 *
 * @method mixed getLastCountedAt()
 * @method string getMaxId()
 * @method mixed getMessage()
 * @method mixed getNewShares()
 * @method mixed getNewSharesInfo()
 * @method mixed getPatches()
 * @method mixed getShares()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isLastCountedAt()
 * @method bool isMaxId()
 * @method bool isMessage()
 * @method bool isNewShares()
 * @method bool isNewSharesInfo()
 * @method bool isPatches()
 * @method bool isShares()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setLastCountedAt(mixed $value)
 * @method this setMaxId(string $value)
 * @method this setMessage(mixed $value)
 * @method this setNewShares(mixed $value)
 * @method this setNewSharesInfo(mixed $value)
 * @method this setPatches(mixed $value)
 * @method this setShares(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetLastCountedAt()
 * @method this unsetMaxId()
 * @method this unsetMessage()
 * @method this unsetNewShares()
 * @method this unsetNewSharesInfo()
 * @method this unsetPatches()
 * @method this unsetShares()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class DirectShareInboxResponse (
    val shares: String,
    val max_id: String,
    val new_shares: String,
    val patches: String,
    val last_counted_at: String,
    val new_shares_info: String
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "shares"          to "",
//        "max_id"          to "string",
//        "new_shares"      to "",
//        "patches"         to "",
//        "last_counted_at" to "",
//        "new_shares_info" to ""
//    )
}
