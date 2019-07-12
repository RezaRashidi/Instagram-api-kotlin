

package instagramAPI.Realtime.Subscription.GraphQl

import instagramAPI.Realtime.Subscription.GraphQlSubscription

class DirectTypingSubscription : GraphQlSubscription
{
    val QUERY = "17867973967082385"

    /**
     * Constructor.
     *
     * @param string $accountId
     */
    public fun __construct(
        $accountId)
    {
        parent::__construct(self::QUERY, [
            "user_id" => $accountId,
        ])
    }

    /** {@inheritdoc} */
    public fun getId()
    {
        return "direct_typing"
    }
}
