

package instagramAPI.responses

import instagramAPI.Response

/**
 * OnTagProductResponse.
 *
 * @method model.User getMerchant()
 * @method mixed getMessage()
 * @method model.Product[] getOtherProductItems()
 * @method model.Product getProductItem()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMerchant()
 * @method bool isMessage()
 * @method bool isOtherProductItems()
 * @method bool isProductItem()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMerchant(model.User $value)
 * @method this setMessage(mixed $value)
 * @method this setOtherProductItems(model.Product[] $value)
 * @method this setProductItem(model.Product $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMerchant()
 * @method this unsetMessage()
 * @method this unsetOtherProductItems()
 * @method this unsetProductItem()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class OnTagProductResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "product_item"              => "model.Product",
        "merchant"                  => "model.User",
        "other_product_items"       => "model.Product[]",
    ]
}
