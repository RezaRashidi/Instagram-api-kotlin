

package instagramAPI.Response.Model

import instagramAPI.AutoPropertyMapper

/**
 * InlineFollow.
 *
 * @method bool getFollowing()
 * @method bool getOutgoingRequest()
 * @method User getUserInfo()
 * @method bool isFollowing()
 * @method bool isOutgoingRequest()
 * @method bool isUserInfo()
 * @method this setFollowing(bool $value)
 * @method this setOutgoingRequest(bool $value)
 * @method this setUserInfo(User $value)
 * @method this unsetFollowing()
 * @method this unsetOutgoingRequest()
 * @method this unsetUserInfo()
 */
class InlineFollow : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "user_info"        => "User",
        "following"        => "bool",
        "outgoing_request" => "bool",
    ]
}
