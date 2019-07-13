

package instagramAPI.responses

import instagramAPI.Response

/**
 * QPCooldownsResponse.
 *
 * @method int getDefault()
 * @method int getGlobal()
 * @method mixed getMessage()
 * @method model.Slot[] getSlots()
 * @method string getStatus()
 * @method model.QPSurface[] getSurfaces()
 * @method model._Message[] get_Messages()
 * @method bool isDefault()
 * @method bool isGlobal()
 * @method bool isMessage()
 * @method bool isSlots()
 * @method bool isStatus()
 * @method bool isSurfaces()
 * @method bool is_Messages()
 * @method this setDefault(int $value)
 * @method this setGlobal(int $value)
 * @method this setMessage(mixed $value)
 * @method this setSlots(model.Slot[] $value)
 * @method this setStatus(string $value)
 * @method this setSurfaces(model.QPSurface[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetDefault()
 * @method this unsetGlobal()
 * @method this unsetMessage()
 * @method this unsetSlots()
 * @method this unsetStatus()
 * @method this unsetSurfaces()
 * @method this unset_Messages()
 */
class QPCooldownsResponse : Response
{
    val JSON_PROPERTY_MAP = [
        "global"    => "int",
        "default"   => "int",
        "surfaces"  => "model.QPSurface[]",
        "slots"     => "model.Slot[]",
    ]
}
