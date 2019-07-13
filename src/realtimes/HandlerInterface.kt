

package instagramAPI.realtimes

import instagramAPI.Realtime.Handler.HandlerException

interface HandlerInterface
{
    /**
     * Handle the message.
     *
     * @param Message $message
     *
     * @throws HandlerException
     */
    public fun handleMessage(
        Message $message)
}
