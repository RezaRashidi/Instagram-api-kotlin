

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

/**
 * QPData.
 *
 * @method QPViewerData getData()
 * @method int getSurface()
 * @method bool isData()
 * @method bool isSurface()
 * @method this setData(QPViewerData $value)
 * @method this setSurface(int $value)
 * @method this unsetData()
 * @method this unsetSurface()
 */
class QPData : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'surface'   => 'int',
        'data'      => 'QPViewerData',
    ]
}
