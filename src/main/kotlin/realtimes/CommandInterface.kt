package instagramAPI.realtimes

interface CommandInterface : JsonSerializable
{
    /**
     * Get the target topic.
     *
     * @return string
     */
    fun getTopic(): String

    /**
     * Get the MQTT QoS level.
     *
     * @return int
     */
    fun getQosLevel(): Int
}
