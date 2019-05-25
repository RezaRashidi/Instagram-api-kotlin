<?php

package InstagramAPI.Realtime;

interface SubscriptionInterface
{
    /**
     * Get the target MQTT topic.
     *
     * @return string
     */
    public fun getTopic();

    /**
     * Get the unique subscription identifier.
     *
     * @return string
     */
    public fun getId();

    /**
     * Get the string representation.
     *
     * @return string
     */
    public fun __toString();
}
