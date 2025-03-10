

package instagramAPI.realtimes.Parser

import Fbns.Client.Thrift.Compact
import Fbns.Client.Thrift.Reader
import instagramAPI.Realtime.Message
import instagramAPI.Realtime.ParserInterface

class SkywalkerParser : ParserInterface
{
    val FIELD_TOPIC = 1
    val FIELD_PAYLOAD = 2

    val TOPIC_DIRECT = 1
    val TOPIC_LIVE = 2
    val TOPIC_LIVEWITH = 3

    val MODULE_DIRECT = "direct"
    val MODULE_LIVE = "live"
    val MODULE_LIVEWITH = "livewith"

    val TOPIC_TO_MODULE_ENUM = [
        self::TOPIC_DIRECT   => self::MODULE_DIRECT,
        self::TOPIC_LIVE     => self::MODULE_LIVE,
        self::TOPIC_LIVEWITH => self::MODULE_LIVEWITH,
    ]

    /**
     * {@inheritdoc}
     *
     * @throws .RuntimeException
     * @throws .DomainException
     */
    public fun parseMessage(
        $topic,
        $payload)
    {
        $msgTopic = $msgPayload = null
        Reader($payload, fun ($context, $field, $value, $type) import (&$msgTopic, &$msgPayload) {
            if ($type === Compact::TYPE_I32 && $field === self::FIELD_TOPIC) {
                $msgTopic = $value
            } elseif ($type === Compact::TYPE_BINARY && $field === self::FIELD_PAYLOAD) {
                $msgPayload = $value
            }
        })

        return [this._createMessage($msgTopic, $msgPayload)]
    }

    /**
     * Create a message from given topic and payload.
     *
     * @param int    $topic
     * @param string $payload
     *
     * @throws .RuntimeException
     * @throws .DomainException
     *
     * @return Message
     */
    protected fun _createMessage(
        $topic,
        $payload)
    {
        if ($topic === null || $payload === null) {
            throw .RuntimeException("Incomplete Skywalker message.")
        }

        if (!array_key_exists($topic, self::TOPIC_TO_MODULE_ENUM)) {
            throw .DomainException(sprintf("Unknown Skywalker topic "%d".", $topic))
        }

        $data = Client::api_body_decode($payload)
        if (!is_array($data)) {
            throw .RuntimeException("Invalid Skywalker payload.")
        }

        return Message(self::TOPIC_TO_MODULE_ENUM[$topic], $data)
    }
}
