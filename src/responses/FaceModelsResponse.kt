

package instagramAPI.responses

import instagramAPI.Response

/**
 * FaceModelsResponse.
 *
 * @method model.FaceModels getFaceModels()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isFaceModels()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setFaceModels(model.FaceModels $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetFaceModels()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class FaceModelsResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "face_models" to "model.FaceModels"
    )
}
