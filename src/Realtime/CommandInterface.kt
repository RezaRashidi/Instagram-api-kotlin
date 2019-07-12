

package instagramAPI.Realtime

interface CommandInterface : .JsonSerializable
{
    /**
     * Get the target topic.
     *
     * @return string
     */
    public fun getTopic()

    /**
     * Get the MQTT QoS level.
     *
     * @return int
     */
    public fun getQosLevel()
}
