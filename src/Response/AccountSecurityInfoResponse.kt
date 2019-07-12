

package instagramAPI.Response

import instagramAPI.Response

//import instagramAPI.Response

/**
 * AccountSecurityInfoResponse.
 *
 * @method mixed getBackupCodes()
 * @method int getCountryCode()
 * @method mixed getIsPhoneConfirmed()
 * @method mixed getIsTwoFactorEnabled()
 * @method mixed getMessage()
 * @method string getNationalNumber()
 * @method string getPhoneNumber()
 * @method string getStatus()
 * @method Model._Message[] get_Messages()
 * @method bool isBackupCodes()
 * @method bool isCountryCode()
 * @method bool isIsPhoneConfirmed()
 * @method bool isIsTwoFactorEnabled()
 * @method bool isMessage()
 * @method bool isNationalNumber()
 * @method bool isPhoneNumber()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setBackupCodes(mixed $value)
 * @method this setCountryCode(int $value)
 * @method this setIsPhoneConfirmed(mixed $value)
 * @method this setIsTwoFactorEnabled(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setNationalNumber(string $value)
 * @method this setPhoneNumber(string $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(Model._Message[] $value)
 * @method this unsetBackupCodes()
 * @method this unsetCountryCode()
 * @method this unsetIsPhoneConfirmed()
 * @method this unsetIsTwoFactorEnabled()
 * @method this unsetMessage()
 * @method this unsetNationalNumber()
 * @method this unsetPhoneNumber()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class AccountSecurityInfoResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "backup_codes"          to "",
        "is_phone_confirmed"    to "",
        "country_code"          to "int",
        "phone_number"          to "string",
        "is_two_factor_enabled" to "",
        "national_number"       to "string" // Really int, but may be >32bit.
    )
}
