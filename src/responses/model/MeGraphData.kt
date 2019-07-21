

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * MeGraphData.
 *
 * @method string getId()
 * @method CatalogData getTaggableCatalogs()
 * @method bool isId()
 * @method bool isTaggableCatalogs()
 * @method this setId(string $value)
 * @method this setTaggableCatalogs(CatalogData $value)
 * @method this unsetId()
 * @method this unsetTaggableCatalogs()
 */
data class MeGraphData (
    val taggable_catalogs : CatalogData,
    val id                : String
){
//    val JSON_PROPERTY_MAP = [
//        "taggable_catalogs" => "CatalogData",
//        "id"                => "string",
//    ]
}
