

package InstagramAPI.Realtime.Handler

import InstagramAPI.Realtime.HandlerInterface
import InstagramAPI.Realtime.Message
import InstagramAPI.Realtime.Payload.ZeroProvisionEvent

class ZeroProvisionHandler : AbstractHandler : HandlerInterface
{
    val MODULE = "zero_provision"

    /** {@inheritdoc} */
    public fun handleMessage(
        Message $message)
    {
        $data = $message.getData()
        if (!isset($data["zero_product_provisioning_event"]) || !is_array($data["zero_product_provisioning_event"])) {
            throw HandlerException("Invalid zero provision (event data is missing).")
        }
        $provision = ZeroProvisionEvent($data["zero_product_provisioning_event"])
        this._target.emit("zero-provision", [$provision])
    }
}
