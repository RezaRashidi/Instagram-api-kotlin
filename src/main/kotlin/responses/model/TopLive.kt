

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * TopLive.
 *
 * @method User[] getBroadcastOwners()
 * @method mixed getRankedPosition()
 * @method bool isBroadcastOwners()
 * @method bool isRankedPosition()
 * @method this setBroadcastOwners(User[] $value)
 * @method this setRankedPosition(mixed $value)
 * @method this unsetBroadcastOwners()
 * @method this unsetRankedPosition()
 */
data class TopLive (
    val broadcast_owners : MutableList<User>,
    val ranked_position  : String
){
//    val JSON_PROPERTY_MAP = [
//        "broadcast_owners" => "User[]",
//        "ranked_position"  => "",
//    ]
}
