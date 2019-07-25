

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * CatalogNode.
 *
 * @method mixed getCurrentPrice()
 * @method string getDescription()
 * @method mixed getFullPrice()
 * @method string getId()
 * @method mixed getMainImageWithSafeFallback()
 * @method string getName()
 * @method string getRetailerId()
 * @method bool isCurrentPrice()
 * @method bool isDescription()
 * @method bool isFullPrice()
 * @method bool isId()
 * @method bool isMainImageWithSafeFallback()
 * @method bool isName()
 * @method bool isRetailerId()
 * @method this setCurrentPrice(mixed $value)
 * @method this setDescription(string $value)
 * @method this setFullPrice(mixed $value)
 * @method this setId(string $value)
 * @method this setMainImageWithSafeFallback(mixed $value)
 * @method this setName(string $value)
 * @method this setRetailerId(string $value)
 * @method this unsetCurrentPrice()
 * @method this unsetDescription()
 * @method this unsetFullPrice()
 * @method this unsetId()
 * @method this unsetMainImageWithSafeFallback()
 * @method this unsetName()
 * @method this unsetRetailerId()
 */
data class CatalogNode (
    val id                            : String,
    val full_price                    : String,
    val current_price                 : String,
    val name                          : String,
    val description                   : String,
    val main_image_with_safe_fallback : String,
    val retailer_id                   : String
){
//    val JSON_PROPERTY_MAP = [
//        "id"                            => "string",
//        "full_price"                    => "",
//        "current_price"                 => "",
//        "name"                          => "string",
//        "description"                   => "string",
//        "main_image_with_safe_fallback" => "",
//        "retailer_id"                   => "string",
//    ]
}
