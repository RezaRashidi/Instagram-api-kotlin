

package instagramAPI.responses

import instagramAPI.Response

/**
 * SendTwoFactorEnableSMSResponse.
 *
 * @method mixed getMessage()
 * @method mixed getObfuscatedPhoneNumber()
 * @method model.PhoneVerificationSettings getPhoneVerificationSettings()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isObfuscatedPhoneNumber()
 * @method bool isPhoneVerificationSettings()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setObfuscatedPhoneNumber(mixed $value)
 * @method this setPhoneVerificationSettings(model.PhoneVerificationSettings $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetObfuscatedPhoneNumber()
 * @method this unsetPhoneVerificationSettings()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class SendTwoFactorEnableSMSResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "phone_verification_settings" => "model.PhoneVerificationSettings",
        "obfuscated_phone_number"     => "",
    ]
}
