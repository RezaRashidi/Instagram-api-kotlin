

package instagramAPI.responses.Model

import instagramAPI.AutoPropertyMapper

/**
 * Voter.
 *
 * @method User getUser()
 * @method int getVote()
 * @method bool isUser()
 * @method bool isVote()
 * @method this setUser(User $value)
 * @method this setVote(int $value)
 * @method this unsetUser()
 * @method this unsetVote()
 */
class Voter : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "user"  => "User",
        "vote"  => "int",
    ]
}
