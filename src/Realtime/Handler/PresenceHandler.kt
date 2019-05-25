

package InstagramAPI.Realtime.Handler

import InstagramAPI.Realtime.HandlerInterface
import InstagramAPI.Realtime.Message
import InstagramAPI.Realtime.Subscription.GraphQl.AppPresenceSubscription
import InstagramAPI.Response.Model.UserPresence

class PresenceHandler : AbstractHandler : HandlerInterface
{
    val MODULE = AppPresenceSubscription::ID

    /** {@inheritdoc} */
    public fun handleMessage(
        Message $message)
    {
        $data = $message.getData()
        if (!isset($data['presence_event']) || !is_array($data['presence_event'])) {
            throw HandlerException('Invalid presence (event data is missing).')
        }
        $presence = UserPresence($data['presence_event'])
        this._target.emit('presence', [$presence])
    }
}
