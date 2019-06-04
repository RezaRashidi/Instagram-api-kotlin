

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

/**
 * CommentTranslations.
 *
 * @method string getId()
 * @method mixed getTranslation()
 * @method bool isId()
 * @method bool isTranslation()
 * @method this setId(string $value)
 * @method this setTranslation(mixed $value)
 * @method this unsetId()
 * @method this unsetTranslation()
 */
class CommentTranslations : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "id"          => "string",
        "translation" => "",
    ]
}
