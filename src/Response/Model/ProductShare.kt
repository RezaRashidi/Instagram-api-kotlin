

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

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
class ProductShare : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "media"   => "Item",
        "text"    => "string",
        "product" => "Product",
    ]
}
