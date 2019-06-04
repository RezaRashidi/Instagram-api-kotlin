

package InstagramAPI.Response.Model

import InstagramAPI.AutoPropertyMapper

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
class RewriteRule : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "matcher"    => "string",
        "replacer"   => "string",
    ]
}
