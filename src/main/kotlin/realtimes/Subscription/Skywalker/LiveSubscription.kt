

package instagramAPI.realtimes.Subscription.Skywalker

import instagramAPI.Realtime.Subscription.SkywalkerSubscription

class LiveSubscription : SkywalkerSubscription
{
    val ID = "live"
    val TEMPLATE = "ig/live_notification_subscribe/%s"

    /** {@inheritdoc} */
    public fun getId()
    {
        return self::ID
    }

    /** {@inheritdoc} */
    public fun __toString()
    {
        return sprintf(self::TEMPLATE, this._accountId)
    }
}
