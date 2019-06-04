

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

/**
 * UserList.
 *
 * @method int getPosition()
 * @method User getUser()
 * @method bool isPosition()
 * @method bool isUser()
 * @method this setPosition(int $value)
 * @method this setUser(User $value)
 * @method this unsetPosition()
 * @method this unsetUser()
 */
class UserList : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'position' => 'int',
        'user'     => 'User',
    ]
}
