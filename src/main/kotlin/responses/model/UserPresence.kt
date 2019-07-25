

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * UserPresence.
 *
 * @method string[] getInThreads()
 * @method bool getIsActive()
 * @method string getLastActivityAtMs()
 * @method string getUserId()
 * @method bool isInThreads()
 * @method bool isIsActive()
 * @method bool isLastActivityAtMs()
 * @method bool isUserId()
 * @method this setInThreads(string[] $value)
 * @method this setIsActive(bool $value)
 * @method this setLastActivityAtMs(string $value)
 * @method this setUserId(string $value)
 * @method this unsetInThreads()
 * @method this unsetIsActive()
 * @method this unsetLastActivityAtMs()
 * @method this unsetUserId()
 */
data class UserPresence (
    val user_id             : String,
    val last_activity_at_ms : String,
    val is_active           : Boolean,
    val in_threads          : MutableList<String>
){
//    val JSON_PROPERTY_MAP = [
//        "user_id"             => "string",
//        "last_activity_at_ms" => "string",
//        "is_active"           => "bool",
//        "in_threads"          => "string[]",
//    ]
}
