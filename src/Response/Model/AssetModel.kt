

package instagramAPI.Response.Model

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
class AssetModel : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "asset_url" => "string",
        "id"        => "string",
    ]
}
