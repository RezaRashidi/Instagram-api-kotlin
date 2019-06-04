

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

/**
 * Subscription.
 *
 * @method mixed getAuth()
 * @method mixed getSequence()
 * @method mixed getTopic()
 * @method string getUrl()
 * @method bool isAuth()
 * @method bool isSequence()
 * @method bool isTopic()
 * @method bool isUrl()
 * @method this setAuth(mixed $value)
 * @method this setSequence(mixed $value)
 * @method this setTopic(mixed $value)
 * @method this setUrl(string $value)
 * @method this unsetAuth()
 * @method this unsetSequence()
 * @method this unsetTopic()
 * @method this unsetUrl()
 */
class Subscription : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "topic"    => "",
        "url"      => "string",
        "sequence" => "",
        "auth"     => "",
    ]
}
