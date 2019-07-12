

package instagramAPI.Response.Model

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
class Image_Versions2 : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "candidates"  => "ImageCandidate[]",
        "trace_token" => "",
    ]
}
