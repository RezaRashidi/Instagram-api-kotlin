

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

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
data class FaceModels (
    val face_align_model  : String,
    val face_detect_model : String,
    val pdm_multires      : String
){
//    val JSON_PROPERTY_MAP = [
//        "face_align_model"  => "",
//        "face_detect_model" => "",
//        "pdm_multires"      => "",
//    ]
}
