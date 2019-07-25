

package instagramAPI.realtimes

interface SubscriptionInterface
{
    /**
     * Get the target MQTT topic.
     *
     * @return string
     */
    fun getTopic(): String

    /**
     * Get the unique subscription identifier.
     *
     * @return string
     */
    fun getId(): String

    /**
     * Get the string representation.
     *
     * @return string
     */
    fun __toString(): String
}
