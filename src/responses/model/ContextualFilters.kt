

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * ContextualFilters.
 *
 * @method string getClauseType()
 * @method mixed getClauses()
 * @method mixed getFilters()
 * @method bool isClauseType()
 * @method bool isClauses()
 * @method bool isFilters()
 * @method this setClauseType(string $value)
 * @method this setClauses(mixed $value)
 * @method this setFilters(mixed $value)
 * @method this unsetClauseType()
 * @method this unsetClauses()
 * @method this unsetFilters()
 */
data class ContextualFilters (
    val clause_type : String,
    val filters     : String,
    val clauses     : String
){
//    val JSON_PROPERTY_MAP = [
//        "clause_type" => "string",
//        "filters"     => "",
//        "clauses"     => "",
//    ]
}
