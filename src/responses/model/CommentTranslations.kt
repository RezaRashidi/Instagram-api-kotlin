

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

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
data class CommentTranslations (
    val id          : String,
    val translation : String
){
//    val JSON_PROPERTY_MAP = [
//        "id"          => "string",
//        "translation" => "",
//    ]
}
