

package instagramAPI.responses

import instagramAPI.Response

/**
 * FBSearchResponse.
 *
 * @method bool getClearClientCache()
 * @method bool getHasMore()
 * @method model.UserList[] getList()
 * @method mixed getMessage()
 * @method string getRankToken()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isClearClientCache()
 * @method bool isHasMore()
 * @method bool isList()
 * @method bool isMessage()
 * @method bool isRankToken()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setClearClientCache(bool $value)
 * @method this setHasMore(bool $value)
 * @method this setList(model.UserList[] $value)
 * @method this setMessage(mixed $value)
 * @method this setRankToken(string $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetClearClientCache()
 * @method this unsetHasMore()
 * @method this unsetList()
 * @method this unsetMessage()
 * @method this unsetRankToken()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class FBSearchResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "has_more"              to "bool",
        "list"                  to "model.UserList[]",
        "clear_client_cache"    to "bool",
        "has_more"              to "bool",
        "rank_token"            to "string"
    )
}
