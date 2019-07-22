

package instagramAPI.responses

import instagramAPI.Response

/**
 * SegmentedStartResponse.
 *
 * @method mixed getMessage()
 * @method string getStatus()
 * @method string getStreamId()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool isStreamId()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this setStreamId(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unsetStreamId()
 * @method this unset_Messages()
 */
data class SegmentedStartResponse (
    val stream_id : String
){
//    val JSON_PROPERTY_MAP = [
//        "stream_id" => "string",
//    ]

    /**
     * Checks if the response was successful.
     *
     * @return bool
     */
    fun isOk(): Boolean{
        val streamId = _getProperty("stream_id")
        if (streamId !== null && streamId !== "") {
            return true
        } else {
            // Set a nice message for exceptions.
            if (getMessage() === null) {
                setMessage("Stream ID for segmented uploader is missing or invalid.")
            }

            return false
        }
    }
}
