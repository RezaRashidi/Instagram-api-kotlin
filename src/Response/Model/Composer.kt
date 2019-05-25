

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

/**
 * Composer.
 *
 * @method bool getNuxFinished()
 * @method bool isNuxFinished()
 * @method this setNuxFinished(bool $value)
 * @method this unsetNuxFinished()
 */
class Composer : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'nux_finished'   => 'bool',
    ]
}
