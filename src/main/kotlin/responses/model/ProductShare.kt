

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * ProductShare.
 *
 * @method Item getMedia()
 * @method Product getProduct()
 * @method string getText()
 * @method bool isMedia()
 * @method bool isProduct()
 * @method bool isText()
 * @method this setMedia(Item $value)
 * @method this setProduct(Product $value)
 * @method this setText(string $value)
 * @method this unsetMedia()
 * @method this unsetProduct()
 * @method this unsetText()
 */
data class ProductShare (
    val media   : Item,
    val text    : String,
    val product : Product
){
//    val JSON_PROPERTY_MAP = [
//        "media"   => "Item",
//        "text"    => "string",
//        "product" => "Product",
//    ]
}
