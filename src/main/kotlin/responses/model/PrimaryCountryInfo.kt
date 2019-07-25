

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * PrimaryCountryInfo.
 *
 * @method string getCountryName()
 * @method bool getHasCountry()
 * @method bool getIsVisible()
 * @method bool isCountryName()
 * @method bool isHasCountry()
 * @method bool isIsVisible()
 * @method this setCountryName(string $value)
 * @method this setHasCountry(bool $value)
 * @method this setIsVisible(bool $value)
 * @method this unsetCountryName()
 * @method this unsetHasCountry()
 * @method this unsetIsVisible()
 */
data class PrimaryCountryInfo (
    val is_visible              : Boolean,
    val has_country             : Boolean,
    val country_name            : String
){
//    val JSON_PROPERTY_MAP = [
//        "is_visible"              => "bool",
//        "has_country"             => "bool",
//        "country_name"            => "string",
//    ]
}
