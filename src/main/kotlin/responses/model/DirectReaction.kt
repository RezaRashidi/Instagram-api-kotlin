

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * DirectReaction.
 *
 * @method string getClientContext()
 * @method string getItemId()
 * @method string getNodeType()
 * @method string getReactionStatus()
 * @method string getReactionType()
 * @method string getSenderId()
 * @method string getTimestamp()
 * @method bool isClientContext()
 * @method bool isItemId()
 * @method bool isNodeType()
 * @method bool isReactionStatus()
 * @method bool isReactionType()
 * @method bool isSenderId()
 * @method bool isTimestamp()
 * @method this setClientContext(string $value)
 * @method this setItemId(string $value)
 * @method this setNodeType(string $value)
 * @method this setReactionStatus(string $value)
 * @method this setReactionType(string $value)
 * @method this setSenderId(string $value)
 * @method this setTimestamp(string $value)
 * @method this unsetClientContext()
 * @method this unsetItemId()
 * @method this unsetNodeType()
 * @method this unsetReactionStatus()
 * @method this unsetReactionType()
 * @method this unsetSenderId()
 * @method this unsetTimestamp()
 */
data class DirectReaction (
    val reaction_type   : String,
    val timestamp       : String,
    val sender_id       : String,
    val client_context  : String,
    val reaction_status : String,
    val node_type       : String,
    val item_id         : String
){
//    val JSON_PROPERTY_MAP = [
//        "reaction_type"   => "string",
//        "timestamp"       => "string",
//        "sender_id"       => "string",
//        "client_context"  => "string",
//        "reaction_status" => "string",
//        "node_type"       => "string",
//        "item_id"         => "string",
//    ]
}
