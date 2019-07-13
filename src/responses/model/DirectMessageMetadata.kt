

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * DirectMessageMetadata.
 *
 * @method string getItemId()
 * @method string[] getParticipantIds()
 * @method string getThreadId()
 * @method string getTimestamp()
 * @method bool isItemId()
 * @method bool isParticipantIds()
 * @method bool isThreadId()
 * @method bool isTimestamp()
 * @method this setItemId(string $value)
 * @method this setParticipantIds(string[] $value)
 * @method this setThreadId(string $value)
 * @method this setTimestamp(string $value)
 * @method this unsetItemId()
 * @method this unsetParticipantIds()
 * @method this unsetThreadId()
 * @method this unsetTimestamp()
 */
class DirectMessageMetadata : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "thread_id"       => "string",
        "item_id"         => "string",
        "timestamp"       => "string",
        "participant_ids" => "string[]",
    ]
}
