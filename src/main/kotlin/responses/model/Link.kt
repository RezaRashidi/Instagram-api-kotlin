

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Link.
 *
 * @method int getEnd()
 * @method string getId()
 * @method LinkContext getLinkContext()
 * @method int getStart()
 * @method string getText()
 * @method string getType()
 * @method bool isEnd()
 * @method bool isId()
 * @method bool isLinkContext()
 * @method bool isStart()
 * @method bool isText()
 * @method bool isType()
 * @method this setEnd(int $value)
 * @method this setId(string $value)
 * @method this setLinkContext(LinkContext $value)
 * @method this setStart(int $value)
 * @method this setText(string $value)
 * @method this setType(string $value)
 * @method this unsetEnd()
 * @method this unsetId()
 * @method this unsetLinkContext()
 * @method this unsetStart()
 * @method this unsetText()
 * @method this unsetType()
 */
data class Link (
    val start        : Int,
    val end          : Int,
    val id           : String,
    val type         : String,
    val text         : String,
    val link_context : LinkContext
){
//    val JSON_PROPERTY_MAP = [
//        "start"        => "int",
//        "end"          => "int",
//        "id"           => "string",
//        "type"         => "string",
//        "text"         => "string",
//        "link_context" => "LinkContext",
//    ]
}
