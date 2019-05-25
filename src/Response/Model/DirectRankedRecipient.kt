

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

/**
 * DirectRankedRecipient.
 *
 * @method DirectThread getThread()
 * @method User getUser()
 * @method bool isThread()
 * @method bool isUser()
 * @method this setThread(DirectThread $value)
 * @method this setUser(User $value)
 * @method this unsetThread()
 * @method this unsetUser()
 */
class DirectRankedRecipient : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'thread' => 'DirectThread',
        'user'   => 'User',
    ]
}
