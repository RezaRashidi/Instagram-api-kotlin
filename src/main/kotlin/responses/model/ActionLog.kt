

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * ActionLog.
 *
 * @method Bold[] getBold()
 * @method mixed getDescription()
 * @method bool isBold()
 * @method bool isDescription()
 * @method this setBold(Bold[] $value)
 * @method this setDescription(mixed $value)
 * @method this unsetBold()
 * @method this unsetDescription()
 */
data class ActionLog (
    val bold        : MutableList<Bold>,
    val description : String
){
//    val JSON_PROPERTY_MAP = [
//        "bold"        => "Bold[]",
//        "description" => "",
//    ]
}
