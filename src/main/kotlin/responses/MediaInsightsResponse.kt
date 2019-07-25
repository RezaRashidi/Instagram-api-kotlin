

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.MediaInsights

/**
 * MediaInsightsResponse.
 *
 * @method model.MediaInsights getMediaOrganicInsights()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMediaOrganicInsights()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMediaOrganicInsights(model.MediaInsights $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMediaOrganicInsights()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class MediaInsightsResponse (
    val media_organic_insights : MediaInsights
){
//    val JSON_PROPERTY_MAP = [
//        "media_organic_insights" => "model.MediaInsights",
//    ]
}
