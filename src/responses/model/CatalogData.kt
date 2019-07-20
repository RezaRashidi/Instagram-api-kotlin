

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * CatalogData.
 *
 * @method CatalogEdge[] getEdges()
 * @method PageInfo getPageInfo()
 * @method bool isEdges()
 * @method bool isPageInfo()
 * @method this setEdges(CatalogEdge[] $value)
 * @method this setPageInfo(PageInfo $value)
 * @method this unsetEdges()
 * @method this unsetPageInfo()
 */
data class CatalogData (
    val page_info          : PageInfo,
    val edges              : MutableList<CatalogEdge>
){
//    val JSON_PROPERTY_MAP = [
//        "page_info"          => "PageInfo",
//        "edges"              => "CatalogEdge[]",
//    ]
}
