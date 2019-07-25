

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Explore.
 *
 * @method string getActorId()
 * @method mixed getExplanation()
 * @method mixed getSourceToken()
 * @method bool isActorId()
 * @method bool isExplanation()
 * @method bool isSourceToken()
 * @method this setActorId(string $value)
 * @method this setExplanation(mixed $value)
 * @method this setSourceToken(mixed $value)
 * @method this unsetActorId()
 * @method this unsetExplanation()
 * @method this unsetSourceToken()
 */
data class Explore (
    val explanation  : String ,
    val actor_id     : String,
    val source_token : String
){
//    val JSON_PROPERTY_MAP = [
//        "explanation"  => "",
//        "actor_id"     => "string",
//        "source_token" => "",
//    ]
}
