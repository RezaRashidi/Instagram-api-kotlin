

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.BroadcastQuestion

/**
 * BroadcastQuestionsResponse.
 *
 * @method mixed getMessage()
 * @method model.BroadcastQuestion[] getQuestions()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isQuestions()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setQuestions(model.BroadcastQuestion[] $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetQuestions()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class BroadcastQuestionsResponse (
    val questions: MutableList<BroadcastQuestion>
) {
//    override val JSON_PROPERTY_MAP = mapOf(
//        "questions"          to "model.BroadcastQuestion[]"
//    )
}
