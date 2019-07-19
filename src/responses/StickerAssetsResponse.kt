

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.StaticStickers

/**
 * StickerAssetsResponse.
 *
 * @method mixed getMessage()
 * @method model.StaticStickers[] getStaticStickers()
 * @method string getStatus()
 * @method mixed getVersion()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStaticStickers()
 * @method bool isStatus()
 * @method bool isVersion()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStaticStickers(model.StaticStickers[] $value)
 * @method this setStatus(string $value)
 * @method this setVersion(mixed $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStaticStickers()
 * @method this unsetStatus()
 * @method this unsetVersion()
 * @method this unset_Messages()
 */
data class StickerAssetsResponse (
    val version         : String,
    val static_stickers : MutableList<StaticStickers>
){
//    val JSON_PROPERTY_MAP = [
//        "version"         => "",
//        "static_stickers" => "model.StaticStickers[]",
//    ]
}
