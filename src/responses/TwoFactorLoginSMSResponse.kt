

package instagramAPI.responses

import instagramAPI.Response

/**
 * TwoFactorLoginSMSResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model.TwoFactorInfo getTwoFactorInfo()
 * @method bool getTwoFactorRequired()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isTwoFactorInfo()
 * @method bool isTwoFactorRequired()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setTwoFactorInfo(model.TwoFactorInfo $value)
 * @method this setTwoFactorRequired(bool $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetTwoFactorInfo()
 * @method this unsetTwoFactorRequired()
 * @method this unset_Messages()
 */
class TwoFactorLoginSMSResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "two_factor_required" => "bool",
        "two_factor_info"     => "model.TwoFactorInfo",
    ]
}
