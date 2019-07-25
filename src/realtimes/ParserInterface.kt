

package instagramAPI.realtimes

interface ParserInterface
{
    /**
     * Parse incoming MQTT message.
     *
     * @param string $topic   MQTT topic.
     * @param string $payload MQTT payload.
     *
     * @return Message[]
     */
    fun parseMessage(topic: String, payload: String)
}
