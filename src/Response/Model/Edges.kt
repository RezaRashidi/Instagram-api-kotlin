

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

/**
 * Edges.
 *
 * @method QPNode getNode()
 * @method int getPriority()
 * @method TimeRange getTimeRange()
 * @method bool isNode()
 * @method bool isPriority()
 * @method bool isTimeRange()
 * @method this setNode(QPNode $value)
 * @method this setPriority(int $value)
 * @method this setTimeRange(TimeRange $value)
 * @method this unsetNode()
 * @method this unsetPriority()
 * @method this unsetTimeRange()
 */
class Edges : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'priority'      => 'int',
        'time_range'    => 'TimeRange',
        'node'          => 'QPNode',
    ]
}
