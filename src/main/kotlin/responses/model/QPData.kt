

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * QPData.
 *
 * @method QPViewerData getData()
 * @method int getSurface()
 * @method bool isData()
 * @method bool isSurface()
 * @method this setData(QPViewerData $value)
 * @method this setSurface(int $value)
 * @method this unsetData()
 * @method this unsetSurface()
 */
data class QPData (
    val surface : Int,
    val data    : QPViewerData
){
//    val JSON_PROPERTY_MAP = [
//        "surface"   => "int",
//        "data"      => "QPViewerData",
//    ]
}
