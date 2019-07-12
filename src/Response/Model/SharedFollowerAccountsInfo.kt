

package instagramAPI.Response.Model

import instagramAPI.AutoPropertyMapper

/**
 * SharedFollowerAccountsInfo.
 *
 * @method bool getHasSharedFollowerAccounts()
 * @method bool isHasSharedFollowerAccounts()
 * @method this setHasSharedFollowerAccounts(bool $value)
 * @method this unsetHasSharedFollowerAccounts()
 */
class SharedFollowerAccountsInfo : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "has_shared_follower_accounts" => "bool",
    ]
}
