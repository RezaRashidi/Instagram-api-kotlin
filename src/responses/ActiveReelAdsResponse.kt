

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Reel

/**
 * ActiveReelAdsResponse.
 *
 * @method mixed getMessage()
 * @method bool getMoreAvailable()
 * @method string getNextMaxId()
 * @method model.Reel[] getReels()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isMoreAvailable()
 * @method bool isNextMaxId()
 * @method bool isReels()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setMoreAvailable(bool $value)
 * @method this setNextMaxId(string $value)
 * @method this setReels(model.Reel[] $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetMoreAvailable()
 * @method this unsetNextMaxId()
 * @method this unsetReels()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class ActiveReelAdsResponse (
    val reels          : MutableList<Reel>,
    val next_max_id    : String,
    val more_available : Boolean
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "reels"          to "model.Reel[]",
//        "next_max_id"    to "string",
//        "more_available" to "bool"
//    )
}
