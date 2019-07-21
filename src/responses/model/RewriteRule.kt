

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * RewriteRule.
 *
 * @method string getMatcher()
 * @method string getReplacer()
 * @method bool isMatcher()
 * @method bool isReplacer()
 * @method this setMatcher(string $value)
 * @method this setReplacer(string $value)
 * @method this unsetMatcher()
 * @method this unsetReplacer()
 */
data class RewriteRule (
    val matcher  : String,
    val replacer : String
){
//    val JSON_PROPERTY_MAP = [
//        "matcher"    => "string",
//        "replacer"   => "string",
//    ]
}
