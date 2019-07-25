

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * BiographyEntities.
 *
 * @method mixed getEntities()
 * @method string getNuxType()
 * @method string getRawText()
 * @method bool isEntities()
 * @method bool isNuxType()
 * @method bool isRawText()
 * @method this setEntities(mixed $value)
 * @method this setNuxType(string $value)
 * @method this setRawText(string $value)
 * @method this unsetEntities()
 * @method this unsetNuxType()
 * @method this unsetRawText()
 */
data class BiographyEntities (
    val entities   : String,
    val raw_text   : String,
    val nux_type   : String
){
//    val JSON_PROPERTY_MAP = [
//        "entities"   => "",
//        "raw_text"   => "string",
//        "nux_type"   => "string",
//    ]
}
