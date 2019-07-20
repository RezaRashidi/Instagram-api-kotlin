

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * DirectLink.
 *
 * @method LinkContext getLinkContext()
 * @method string getText()
 * @method bool isLinkContext()
 * @method bool isText()
 * @method this setLinkContext(LinkContext $value)
 * @method this setText(string $value)
 * @method this unsetLinkContext()
 * @method this unsetText()
 */
data class DirectLink (
    val text         : String,
    val link_context : LinkContext
){
//    val JSON_PROPERTY_MAP = [
//        "text"         => "string",
//        "link_context" => "LinkContext",
//    ]
}
