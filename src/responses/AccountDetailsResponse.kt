

package instagramAPI.responses

import instagramAPI.Response

/**
 * AccountDetailsResponse.
 *
 * @method model.AdsInfo getAdsInfo()
 * @method string getDateJoined()
 * @method model.FormerUsernameInfo getFormerUsernameInfo()
 * @method mixed getMessage()
 * @method model.PrimaryCountryInfo getPrimaryCountryInfo()
 * @method model.SharedFollowerAccountsInfo getSharedFollowerAccountsInfo()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isAdsInfo()
 * @method bool isDateJoined()
 * @method bool isFormerUsernameInfo()
 * @method bool isMessage()
 * @method bool isPrimaryCountryInfo()
 * @method bool isSharedFollowerAccountsInfo()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setAdsInfo(model.AdsInfo $value)
 * @method this setDateJoined(string $value)
 * @method this setFormerUsernameInfo(model.FormerUsernameInfo $value)
 * @method this setMessage(mixed $value)
 * @method this setPrimaryCountryInfo(model.PrimaryCountryInfo $value)
 * @method this setSharedFollowerAccountsInfo(model.SharedFollowerAccountsInfo $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAdsInfo()
 * @method this unsetDateJoined()
 * @method this unsetFormerUsernameInfo()
 * @method this unsetMessage()
 * @method this unsetPrimaryCountryInfo()
 * @method this unsetSharedFollowerAccountsInfo()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class AccountDetailsResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "date_joined"                   to "string",
        "former_username_info"          to "model.FormerUsernameInfo",
        "primary_country_info"          to "model.PrimaryCountryInfo",
        "shared_follower_accounts_info" to "model.SharedFollowerAccountsInfo",
        "ads_info"                      to "model.AdsInfo"
    )
}
