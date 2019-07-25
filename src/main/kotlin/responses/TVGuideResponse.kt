

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Badging
import instagramAPI.responses.model.Composer
import instagramAPI.responses.model.TVChannel

/**
 * TVGuideResponse.
 *
 * @method model.Badging getBadging()
 * @method model.TVChannel[] getChannels()
 * @method model.Composer getComposer()
 * @method mixed getMessage()
 * @method model.TVChannel getMyChannel()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isBadging()
 * @method bool isChannels()
 * @method bool isComposer()
 * @method bool isMessage()
 * @method bool isMyChannel()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setBadging(model.Badging $value)
 * @method this setChannels(model.TVChannel[] $value)
 * @method this setComposer(model.Composer $value)
 * @method this setMessage(mixed $value)
 * @method this setMyChannel(model.TVChannel $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetBadging()
 * @method this unsetChannels()
 * @method this unsetComposer()
 * @method this unsetMessage()
 * @method this unsetMyChannel()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class TVGuideResponse (
    val channels   : MutableList<TVChannel>,
    val my_channel : TVChannel,
    val badging    : Badging,
    val composer   : Composer
){
//    val JSON_PROPERTY_MAP = [
//        "channels"   => "model.TVChannel[]",
//        "my_channel" => "model.TVChannel",
//        "badging"    => "model.Badging",
//        "composer"   => "model.Composer",
//    ]
}
