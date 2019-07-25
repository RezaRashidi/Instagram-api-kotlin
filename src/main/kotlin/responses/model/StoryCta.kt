

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * StoryCta.
 *
 * @method AndroidLinks[] getLinks()
 * @method bool isLinks()
 * @method this setLinks(AndroidLinks[] $value)
 * @method this unsetLinks()
 */
data class StoryCta (
    val links : MutableList<AndroidLinks>
){
//    val JSON_PROPERTY_MAP = [
//        "links"          => "AndroidLinks[]",
//    ]
}
