

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * IOSLinks.
 *
 * @method string getCanvasDocId()
 * @method int getLinkType()
 * @method bool isCanvasDocId()
 * @method bool isLinkType()
 * @method this setCanvasDocId(string $value)
 * @method this setLinkType(int $value)
 * @method this unsetCanvasDocId()
 * @method this unsetLinkType()
 */
data class IOSLinks (
    val linkType    : Int,
    val canvasDocId : String
){
//    val JSON_PROPERTY_MAP = [
//        "linkType"    => "int",
//        "canvasDocId" => "string",
//    ]
}
