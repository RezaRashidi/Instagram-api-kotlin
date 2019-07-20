

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * AdsInfo.
 *
 * @method string getAdsUrl()
 * @method bool getHasAds()
 * @method bool isAdsUrl()
 * @method bool isHasAds()
 * @method this setAdsUrl(string $value)
 * @method this setHasAds(bool $value)
 * @method this unsetAdsUrl()
 * @method this unsetHasAds()
 */
data class AdsInfo (
    val has_ads : Boolean,
    val ads_url : String
){
//    val JSON_PROPERTY_MAP = [
//        "has_ads" => "bool",
//        "ads_url" => "string",
//    ]
}
