

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * QPNode.
 *
 * @method ContextualFilters getContextualFilters()
 * @method Creative[] getCreatives()
 * @method string getId()
 * @method int getMaxImpressions()
 * @method string getPromotionId()
 * @method Template getTemplate()
 * @method string[] getTriggers()
 * @method bool isContextualFilters()
 * @method bool isCreatives()
 * @method bool isId()
 * @method bool isMaxImpressions()
 * @method bool isPromotionId()
 * @method bool isTemplate()
 * @method bool isTriggers()
 * @method this setContextualFilters(ContextualFilters $value)
 * @method this setCreatives(Creative[] $value)
 * @method this setId(string $value)
 * @method this setMaxImpressions(int $value)
 * @method this setPromotionId(string $value)
 * @method this setTemplate(Template $value)
 * @method this setTriggers(string[] $value)
 * @method this unsetContextualFilters()
 * @method this unsetCreatives()
 * @method this unsetId()
 * @method this unsetMaxImpressions()
 * @method this unsetPromotionId()
 * @method this unsetTemplate()
 * @method this unsetTriggers()
 */
data class QPNode (
    val id                 : String,
    val promotion_id       : String,
    val max_impressions    : Int,
    val triggers           : MutableList<String>,
    val contextual_filters : ContextualFilters,
    val template           : Template,
    val creatives          : MutableList<Creative>
){
//    val JSON_PROPERTY_MAP = [
//        "id"                 => "string",
//        "promotion_id"       => "string",
//        "max_impressions"    => "int",
//        "triggers"           => "string[]",
//        "contextual_filters" => "ContextualFilters",
//        "template"           => "Template",
//        "creatives"          => "Creative[]",
//    ]
}
