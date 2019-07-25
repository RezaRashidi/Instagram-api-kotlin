

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * TwoFactorInfo.
 *
 * @method mixed getObfuscatedPhoneNumber()
 * @method PhoneVerificationSettings getPhoneVerificationSettings()
 * @method string getTwoFactorIdentifier()
 * @method string getUsername()
 * @method bool isObfuscatedPhoneNumber()
 * @method bool isPhoneVerificationSettings()
 * @method bool isTwoFactorIdentifier()
 * @method bool isUsername()
 * @method this setObfuscatedPhoneNumber(mixed $value)
 * @method this setPhoneVerificationSettings(PhoneVerificationSettings $value)
 * @method this setTwoFactorIdentifier(string $value)
 * @method this setUsername(string $value)
 * @method this unsetObfuscatedPhoneNumber()
 * @method this unsetPhoneVerificationSettings()
 * @method this unsetTwoFactorIdentifier()
 * @method this unsetUsername()
 */
data class TwoFactorInfo (
    val username                    : String,
    val two_factor_identifier       : String,
    val phone_verification_settings : PhoneVerificationSettings,
    val obfuscated_phone_number     : String
){
//    val JSON_PROPERTY_MAP = [
//        "username"                    => "string",
//        "two_factor_identifier"       => "string",
//        "phone_verification_settings" => "PhoneVerificationSettings",
//        "obfuscated_phone_number"     => "",
//    ]
}
