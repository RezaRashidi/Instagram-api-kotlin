

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.StoryQuestionResponderInfos

/**
 * StoryAnswersResponse.
 *
 * @method mixed getMessage()
 * @method model.StoryQuestionResponderInfos getResponderInfo()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isResponderInfo()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setResponderInfo(model.StoryQuestionResponderInfos $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetResponderInfo()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class StoryAnswersResponse (
    val responder_info : StoryQuestionResponderInfos
){
//    val JSON_PROPERTY_MAP = [
//        "responder_info" => "model.StoryQuestionResponderInfos",
//    ]
}
