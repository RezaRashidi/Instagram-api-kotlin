

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * TVSearchResult.
 *
 * @method TVChannel getChannel()
 * @method int getNumResults()
 * @method string getRankToken()
 * @method string getType()
 * @method User getUser()
 * @method bool isChannel()
 * @method bool isNumResults()
 * @method bool isRankToken()
 * @method bool isType()
 * @method bool isUser()
 * @method this setChannel(TVChannel $value)
 * @method this setNumResults(int $value)
 * @method this setRankToken(string $value)
 * @method this setType(string $value)
 * @method this setUser(User $value)
 * @method this unsetChannel()
 * @method this unsetNumResults()
 * @method this unsetRankToken()
 * @method this unsetType()
 * @method this unsetUser()
 */
data class TVSearchResult (
    val type          : String,
    val User          : User,
    val channel       : TVChannel,
    val num_results   : Int,
    val rank_token    : String
){
//    val JSON_PROPERTY_MAP = [
//        "type"          => "string",
//        "User"          => "User",
//        "channel"       => "TVChannel",
//        "num_results"   => "int",
//        "rank_token"    => "string",
//    ]
}
