

package instagramAPI.responses

import instagramAPI.Response

/**
 * ResumableOffsetResponse.
 *
 * @method mixed getMessage()
 * @method int getOffset()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isOffset()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setOffset(int $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetOffset()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class ResumableOffsetResponse (
    val offset : Int
){
//    val JSON_PROPERTY_MAP = [
//        "offset" => "int",
//    ]

    /**
     * Checks if the response was successful.
     *
     * @return bool
     */
    fun isOk(): Boolean{
        val offset = _getProperty("offset")
        if (offset !== null && offset >= 0) {
            return true
        } else {
            // Set a nice message for exceptions.
            if (getMessage() === null) {
                setMessage("Offset for resumable uploader is missing or invalid.")
            }

            return false
        }
    }
}
