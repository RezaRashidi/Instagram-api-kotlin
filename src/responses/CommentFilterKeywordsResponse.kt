

package instagramAPI.responses

import instagramAPI.Response

/**
 * CommentFilterKeywordsResponse.
 *
 * @method mixed getKeywords()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isKeywords()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setKeywords(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetKeywords()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class CommentFilterKeywordsResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "keywords" to ""
    )
}
