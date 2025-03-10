

package instagramAPI.realtimes.Handler

class RegionHintHandler : AbstractHandler : HandlerInterface
{
    val MODULE = "region_hint"

    /** {@inheritdoc} */
    public fun handleMessage(
        Message $message)
    {
        $region = $message.getData()
        if ($region === null || $region === "") {
            throw HandlerException("Invalid region hint.")
        }
        this._target.emit("region-hint", [$message.getData()])
    }
}
