

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

/**
 * SummaryPromotions.
 *
 * @method BusinessEdge[] getEdges()
 * @method PageInfo getPageInfo()
 * @method bool isEdges()
 * @method bool isPageInfo()
 * @method this setEdges(BusinessEdge[] $value)
 * @method this setPageInfo(PageInfo $value)
 * @method this unsetEdges()
 * @method this unsetPageInfo()
 */
class SummaryPromotions : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'edges'     => 'BusinessEdge[]',
        'page_info' => 'PageInfo',
    ]
}
