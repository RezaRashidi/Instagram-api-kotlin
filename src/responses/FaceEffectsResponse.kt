

package instagramAPI.responses

import instagramAPI.Response

/**
 * FaceEffectsResponse.
 *
 * @method model.Effect[] getEffects()
 * @method model.Effect getLoadingEffect()
 * @method mixed getMessage()
 * @method mixed getSdkVersion()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isEffects()
 * @method bool isLoadingEffect()
 * @method bool isMessage()
 * @method bool isSdkVersion()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setEffects(model.Effect[] $value)
 * @method this setLoadingEffect(model.Effect $value)
 * @method this setMessage(mixed $value)
 * @method this setSdkVersion(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetEffects()
 * @method this unsetLoadingEffect()
 * @method this unsetMessage()
 * @method this unsetSdkVersion()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class FaceEffectsResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "sdk_version"    to "",
        "effects"        to "model.Effect[]",
        "loading_effect" to "model.Effect"
    )
}
