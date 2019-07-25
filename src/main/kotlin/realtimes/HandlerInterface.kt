

package instagramAPI.realtimes

import instagramAPI.realtimes.Handler.HandlerException

interface HandlerInterface
{
    /**
     * Handle the message.
     *
     * @param Message $message
     *
     * @throws HandlerException
     */
    fun handleMessage(message: Message)
}
