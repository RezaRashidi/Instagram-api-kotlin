

package instagramAPI.responses

import instagramAPI.Response

/**
 * SendConfirmEmailResponse.
 *
 * @method mixed getBody()
 * @method mixed getIsEmailLegit()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method mixed getTitle()
 * @method model._Message[] get_Messages()
 * @method bool isBody()
 * @method bool isIsEmailLegit()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isTitle()
 * @method bool is_Messages()
 * @method this setBody(mixed $value)
 * @method this setIsEmailLegit(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setTitle(mixed $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetBody()
 * @method this unsetIsEmailLegit()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetTitle()
 * @method this unset_Messages()
 */
data class SendConfirmEmailResponse (
    val title          : String,
    val is_email_legit : String,
    val body           : String
){
//    val JSON_PROPERTY_MAP = [
//        "title"          => "",
//        "is_email_legit" => "",
//        "body"           => "",
//    ]
}
