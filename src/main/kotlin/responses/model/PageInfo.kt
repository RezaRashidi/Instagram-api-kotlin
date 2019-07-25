

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * PageInfo.
 *
 * @method string getEndCursor()
 * @method bool getHasNextPage()
 * @method bool getHasPreviousPage()
 * @method bool isEndCursor()
 * @method bool isHasNextPage()
 * @method bool isHasPreviousPage()
 * @method this setEndCursor(string $value)
 * @method this setHasNextPage(bool $value)
 * @method this setHasPreviousPage(bool $value)
 * @method this unsetEndCursor()
 * @method this unsetHasNextPage()
 * @method this unsetHasPreviousPage()
 */
data class PageInfo (
    val end_cursor        : String,
    val has_next_page     : Boolean,
    val has_previous_page : Boolean
){
//    val JSON_PROPERTY_MAP = [
//        "end_cursor"        => "string",
//        "has_next_page"     => "bool",
//        "has_previous_page" => "bool",
//    ]
}
