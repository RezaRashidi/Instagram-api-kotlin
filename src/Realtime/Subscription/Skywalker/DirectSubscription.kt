

package InstagramAPI.Realtime.Subscription.Skywalker

import InstagramAPI.Realtime.Subscription.SkywalkerSubscription

class DirectSubscription : SkywalkerSubscription
{
    val ID = 'direct'
    val TEMPLATE = 'ig/u/v1/%s'

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
