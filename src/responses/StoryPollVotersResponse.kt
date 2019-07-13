

package instagramAPI.responses

import instagramAPI.Response

/**
 * StoryPollVotersResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model.VoterInfo getVoterInfo()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isVoterInfo()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setVoterInfo(model.VoterInfo $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetVoterInfo()
 * @method this unset_Messages()
 */
class StoryPollVotersResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "voter_info"    => "model.VoterInfo",
    ]
}
