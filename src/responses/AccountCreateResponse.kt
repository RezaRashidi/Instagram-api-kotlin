

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.User

/**
 * AccountCreateResponse.
 *
 * @method mixed getAccountCreated()
 * @method model.User getCreatedUser()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isAccountCreated()
 * @method bool isCreatedUser()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setAccountCreated(mixed $value)
 * @method this setCreatedUser(model.User $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAccountCreated()
 * @method this unsetCreatedUser()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class AccountCreateResponse (
    val account_created : String,
    val created_user    : User
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "account_created" to "",
//        "created_user"    to "model.User"
//    )
}
