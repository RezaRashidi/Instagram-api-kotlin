

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Reel
import instagramAPI.responses.model.unpredictableKeys.ReelUnpredictableContainer

/**
 * ReelsMediaResponse.
 *
 * @method mixed getMessage()
 * @method model.unpredictableKeys.ReelUnpredictableContainer getReels()
 * @method model.Reel[] getReelsMedia()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isReels()
 * @method bool isReelsMedia()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setReels(model.unpredictableKeys.ReelUnpredictableContainer $value)
 * @method this setReelsMedia(model.Reel[] $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetReels()
 * @method this unsetReelsMedia()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class ReelsMediaResponse (
    val reels_media : Reel,
    val reels       : ReelUnpredictableContainer
){
//    val JSON_PROPERTY_MAP = [
//        "reels_media" => "model.Reel[]",
//        "reels"       => "model.unpredictableKeys.ReelUnpredictableContainer",
//    ]
}
