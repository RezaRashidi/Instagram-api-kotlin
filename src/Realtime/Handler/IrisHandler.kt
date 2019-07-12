

package instagramAPI.Realtime.Handler

class IrisHandler : AbstractHandler : HandlerInterface
{
    val MODULE = "iris"

    /** {@inheritdoc} */
    public fun handleMessage(
        Message $message)
    {
        $iris = IrisSubscribeAck($message.getData())
        if (!$iris.isSucceeded()) {
            throw HandlerException(sprintf(
                "Failed to subscribe to Iris (%d): %s.",
                $iris.getErrorType(),
                $iris.getErrorMessage()
            ))
        }
        this._target.emit("iris-subscribed", [$iris])
    }
}
