

package instagramAPI.responses

import instagramAPI.Response

/**
 * EnableTwoFactorSMSResponse.
 *
 * @method mixed getBackupCodes()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isBackupCodes()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setBackupCodes(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetBackupCodes()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class EnableTwoFactorSMSResponse (
    val backup_codes: String
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "backup_codes" to ""
//    )
}
