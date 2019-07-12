

package instagramAPI.Realtime.Payload.Event

import instagramAPI.AutoPropertyMapper

/**
 * PatchEventOp.
 *
 * @method mixed getDoublePublish()
 * @method mixed getOp()
 * @method mixed getPath()
 * @method mixed getTs()
 * @method mixed getValue()
 * @method bool isDoublePublish()
 * @method bool isOp()
 * @method bool isPath()
 * @method bool isTs()
 * @method bool isValue()
 * @method this setDoublePublish(mixed $value)
 * @method this setOp(mixed $value)
 * @method this setPath(mixed $value)
 * @method this setTs(mixed $value)
 * @method this setValue(mixed $value)
 * @method this unsetDoublePublish()
 * @method this unsetOp()
 * @method this unsetPath()
 * @method this unsetTs()
 * @method this unsetValue()
 */
class PatchEventOp : AutoPropertyMapper
{
    val ADD = "add"
    val REMOVE = "remove"
    val REPLACE = "replace"
    val NOTIFY = "notify"

    val JSON_PROPERTY_MAP = [
        "op"            => "",
        "path"          => "",
        "value"         => "",
        "ts"            => "",
        "doublePublish" => "",
    ]
}
