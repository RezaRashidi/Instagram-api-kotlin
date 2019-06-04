

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

/**
 * QPViewerData.
 *
 * @method Viewer getViewer()
 * @method bool isViewer()
 * @method this setViewer(Viewer $value)
 * @method this unsetViewer()
 */
class QPViewerData : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'viewer'   => 'Viewer',
    ]
}
