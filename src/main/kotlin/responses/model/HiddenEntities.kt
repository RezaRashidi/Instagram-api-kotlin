

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * HiddenEntities.
 *
 * @method mixed getHashtag()
 * @method mixed getPlace()
 * @method mixed getUser()
 * @method bool isHashtag()
 * @method bool isPlace()
 * @method bool isUser()
 * @method this setHashtag(mixed $value)
 * @method this setPlace(mixed $value)
 * @method this setUser(mixed $value)
 * @method this unsetHashtag()
 * @method this unsetPlace()
 * @method this unsetUser()
 */
data class HiddenEntities (
    val user    : String,
    val hashtag : String,
    val place   : String
){
    // TODO: The server returns each of these fields as [] arrays, but we don"t
    // know what kind of objects those arrays can contain since we"ve never seen
    // any values in them. So for now, these are left as default types. Most
    // likely, they"ll need to be User[], Tag[] and Location[].
//    val JSON_PROPERTY_MAP = [
//        "user"    => "",
//        "hashtag" => "",
//        "place"   => "",
//    ]
}
