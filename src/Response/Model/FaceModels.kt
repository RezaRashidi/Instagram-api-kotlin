

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

/**
 * FaceModels.
 *
 * @method mixed getFaceAlignModel()
 * @method mixed getFaceDetectModel()
 * @method mixed getPdmMultires()
 * @method bool isFaceAlignModel()
 * @method bool isFaceDetectModel()
 * @method bool isPdmMultires()
 * @method this setFaceAlignModel(mixed $value)
 * @method this setFaceDetectModel(mixed $value)
 * @method this setPdmMultires(mixed $value)
 * @method this unsetFaceAlignModel()
 * @method this unsetFaceDetectModel()
 * @method this unsetPdmMultires()
 */
class FaceModels : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "face_align_model"  => "",
        "face_detect_model" => "",
        "pdm_multires"      => "",
    ]
}
