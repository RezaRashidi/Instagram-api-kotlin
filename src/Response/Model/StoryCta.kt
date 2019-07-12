

package instagramAPI.Response.Model

import instagramAPI.AutoPropertyMapper

/**
 * StoryCta.
 *
 * @method AndroidLinks[] getLinks()
 * @method bool isLinks()
 * @method this setLinks(AndroidLinks[] $value)
 * @method this unsetLinks()
 */
class StoryCta : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "links"          => "AndroidLinks[]",
    ]
}
