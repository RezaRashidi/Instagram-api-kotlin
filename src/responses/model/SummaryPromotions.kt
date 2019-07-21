

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

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
data class SummaryPromotions (
    val edges     : MutableList<BusinessEdge>,
    val page_info : PageInfo
){
//    val JSON_PROPERTY_MAP = [
//        "edges"     => "BusinessEdge[]",
//        "page_info" => "PageInfo",
//    ]
}
