

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * AssetModel.
 *
 * @method string getAssetUrl()
 * @method string getId()
 * @method bool isAssetUrl()
 * @method bool isId()
 * @method this setAssetUrl(string $value)
 * @method this setId(string $value)
 * @method this unsetAssetUrl()
 * @method this unsetId()
 */
data class AssetModel (
    val asset_url : String,
    val id        : String
){
//    val JSON_PROPERTY_MAP = [
//        "asset_url" => "string",
//        "id"        => "string",
//    ]
}
