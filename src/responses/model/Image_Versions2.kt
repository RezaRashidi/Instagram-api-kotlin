

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Image_Versions2.
 *
 * @method ImageCandidate[] getCandidates()
 * @method mixed getTraceToken()
 * @method bool isCandidates()
 * @method bool isTraceToken()
 * @method this setCandidates(ImageCandidate[] $value)
 * @method this setTraceToken(mixed $value)
 * @method this unsetCandidates()
 * @method this unsetTraceToken()
 */
data class Image_Versions2 (
    val candidates  : MutableList<ImageCandidate>,
    val trace_token : String
){
//    val JSON_PROPERTY_MAP = [
//        "candidates"  => "ImageCandidate[]",
//        "trace_token" => "",
//    ]
}
