

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * SharedFollowerAccountsInfo.
 *
 * @method bool getHasSharedFollowerAccounts()
 * @method bool isHasSharedFollowerAccounts()
 * @method this setHasSharedFollowerAccounts(bool $value)
 * @method this unsetHasSharedFollowerAccounts()
 */
data class SharedFollowerAccountsInfo (
    val has_shared_follower_accounts : Boolean
){
//    val JSON_PROPERTY_MAP = [
//        "has_shared_follower_accounts" => "bool",
//    ]
}
