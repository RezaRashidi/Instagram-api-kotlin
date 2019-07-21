

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * VoterInfo.
 *
 * @method string getMaxId()
 * @method bool getMoreAvailable()
 * @method string getPollId()
 * @method Voter[] getVoters()
 * @method bool isMaxId()
 * @method bool isMoreAvailable()
 * @method bool isPollId()
 * @method bool isVoters()
 * @method this setMaxId(string $value)
 * @method this setMoreAvailable(bool $value)
 * @method this setPollId(string $value)
 * @method this setVoters(Voter[] $value)
 * @method this unsetMaxId()
 * @method this unsetMoreAvailable()
 * @method this unsetPollId()
 * @method this unsetVoters()
 */
data class VoterInfo (
    val poll_id           : String,
    val voters            : MutableList<Voter>,
    val max_id            : String,
    val more_available    : Boolean
){
//    val JSON_PROPERTY_MAP = [
//        "poll_id"           => "string",
//        "voters"            => "Voter[]",
//        "max_id"            => "string",
//        "more_available"    => "bool",
//    ]
}
