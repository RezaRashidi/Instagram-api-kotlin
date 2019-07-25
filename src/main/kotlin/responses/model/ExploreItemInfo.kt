

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * ExploreItemInfo.
 *
 * @method int getAspectRatio()
 * @method bool getAutoplay()
 * @method string getDestinationView()
 * @method int getNumColumns()
 * @method int getTotalNumColumns()
 * @method bool isAspectRatio()
 * @method bool isAutoplay()
 * @method bool isDestinationView()
 * @method bool isNumColumns()
 * @method bool isTotalNumColumns()
 * @method this setAspectRatio(int $value)
 * @method this setAutoplay(bool $value)
 * @method this setDestinationView(string $value)
 * @method this setNumColumns(int $value)
 * @method this setTotalNumColumns(int $value)
 * @method this unsetAspectRatio()
 * @method this unsetAutoplay()
 * @method this unsetDestinationView()
 * @method this unsetNumColumns()
 * @method this unsetTotalNumColumns()
 */
data class ExploreItemInfo (
    val num_columns       : Int,
    val total_num_columns : Int,
    val aspect_ratio      : Int,
    val autoplay          : Boolean,
    val destination_view  : String
){
//    val JSON_PROPERTY_MAP = [
//        "num_columns"       => "int",
//        "total_num_columns" => "int",
//        "aspect_ratio"      => "int",
//        "autoplay"          => "bool",
//        "destination_view"  => "string",
//    ]
}
