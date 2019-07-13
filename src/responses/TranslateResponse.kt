

package instagramAPI.responses

import instagramAPI.Response

/**
 * TranslateResponse.
 *
 * @method model.CommentTranslations[] getCommentTranslations()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isCommentTranslations()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setCommentTranslations(model.CommentTranslations[] $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetCommentTranslations()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class TranslateResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "comment_translations" => "model.CommentTranslations[]",
    ]
}
