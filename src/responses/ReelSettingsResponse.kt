

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.BlockedReels

/**
 * ReelSettingsResponse.
 *
 * @method model.BlockedReels getBlockedReels()
 * @method mixed getMessage()
 * @method mixed getMessagePrefs()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isBlockedReels()
 * @method bool isMessage()
 * @method bool isMessagePrefs()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setBlockedReels(model.BlockedReels $value)
 * @method this setMessage(mixed $value)
 * @method this setMessagePrefs(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetBlockedReels()
 * @method this unsetMessage()
 * @method this unsetMessagePrefs()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class ReelSettingsResponse (
    val message_prefs : String,
    val blocked_reels : BlockedReels
){
//    val JSON_PROPERTY_MAP = [
//        "message_prefs" => "",
//        "blocked_reels" => "model.BlockedReels",
//    ]
}
