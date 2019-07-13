

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * QueryResponse.
 *
 * @method ShadowInstagramUser getShadowInstagramUser()
 * @method bool isShadowInstagramUser()
 * @method this setShadowInstagramUser(ShadowInstagramUser $value)
 * @method this unsetShadowInstagramUser()
 */
class QueryResponse : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "shadow_instagram_user" => "ShadowInstagramUser",
    ]
}
