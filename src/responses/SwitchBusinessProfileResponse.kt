

package instagramAPI.responses

import instagramAPI.Response

/**
 * SwitchBusinessProfileResponse.
 *
 * @method mixed getMessage()
 * @method mixed getSocialContext()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isSocialContext()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setSocialContext(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetSocialContext()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class SwitchBusinessProfileResponse (
    val social_context : String
){
//    val JSON_PROPERTY_MAP = [
//        "social_context" => "",
//    ]
}
