

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * LiveViewerInvite.
 *
 * @method Broadcast getBroadcast()
 * @method string getMessage()
 * @method string getText()
 * @method string getTitle()
 * @method bool isBroadcast()
 * @method bool isMessage()
 * @method bool isText()
 * @method bool isTitle()
 * @method this setBroadcast(Broadcast $value)
 * @method this setMessage(string $value)
 * @method this setText(string $value)
 * @method this setTitle(string $value)
 * @method this unsetBroadcast()
 * @method this unsetMessage()
 * @method this unsetText()
 * @method this unsetTitle()
 */
data class LiveViewerInvite (
    val text      : String,
    val broadcast : Broadcast,
    val title     : String,
    val message   : String
){
//    val JSON_PROPERTY_MAP = [
//        "text"      => "string",
//        "broadcast" => "Broadcast",
//        "title"     => "string",
//        "message"   => "string",
//    ]
}
