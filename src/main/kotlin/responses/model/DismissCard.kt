

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * DismissCard.
 *
 * @method mixed getButtonText()
 * @method mixed getCameraTarget()
 * @method mixed getCardId()
 * @method mixed getFaceFilterId()
 * @method string getImageUrl()
 * @method mixed getMessage()
 * @method mixed getTitle()
 * @method bool isButtonText()
 * @method bool isCameraTarget()
 * @method bool isCardId()
 * @method bool isFaceFilterId()
 * @method bool isImageUrl()
 * @method bool isMessage()
 * @method bool isTitle()
 * @method this setButtonText(mixed $value)
 * @method this setCameraTarget(mixed $value)
 * @method this setCardId(mixed $value)
 * @method this setFaceFilterId(mixed $value)
 * @method this setImageUrl(string $value)
 * @method this setMessage(mixed $value)
 * @method this setTitle(mixed $value)
 * @method this unsetButtonText()
 * @method this unsetCameraTarget()
 * @method this unsetCardId()
 * @method this unsetFaceFilterId()
 * @method this unsetImageUrl()
 * @method this unsetMessage()
 * @method this unsetTitle()
 */
data class DismissCard (
    val card_id        : String,
    val image_url      : String,
    val title          : String,
    val message        : String,
    val button_text    : String,
    val camera_target  : String,
    val face_filter_id : String
){
//    val JSON_PROPERTY_MAP = [
//        "card_id"        => "",
//        "image_url"      => "string",
//        "title"          => "",
//        "message"        => "",
//        "button_text"    => "",
//        "camera_target"  => "",
//        "face_filter_id" => "",
//    ]
}
