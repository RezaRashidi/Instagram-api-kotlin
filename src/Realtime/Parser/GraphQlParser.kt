

package InstagramAPI.Realtime.Parser

import Fbns.Client.Thrift.Compact
import Fbns.Client.Thrift.Reader
import InstagramAPI.Client
import InstagramAPI.Realtime.Message
import InstagramAPI.Realtime.ParserInterface
import InstagramAPI.Realtime.Subscription.GraphQl.AppPresenceSubscription
import InstagramAPI.Realtime.Subscription.GraphQl.ZeroProvisionSubscription

class GraphQlParser : ParserInterface
{
    val FIELD_TOPIC = 1
    val FIELD_PAYLOAD = 2

    val TOPIC_DIRECT = 'direct'

    val MODULE_DIRECT = 'direct'

    val TOPIC_TO_MODULE_ENUM = [
        self::TOPIC_DIRECT               => self::MODULE_DIRECT,
        AppPresenceSubscription::QUERY   => AppPresenceSubscription::ID,
        ZeroProvisionSubscription::QUERY => ZeroProvisionSubscription::ID,
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
            if ($type === Compact::TYPE_BINARY) {
                if ($field === self::FIELD_TOPIC) {
                    $msgTopic = $value
                } elseif ($field === self::FIELD_PAYLOAD) {
                    $msgPayload = $value
                }
            }
        })

        return [this._createMessage($msgTopic, $msgPayload)]
    }

    /**
     * Create a message from given topic and payload.
     *
     * @param string $topic
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
            throw .RuntimeException('Incomplete GraphQL message.')
        }

        if (!array_key_exists($topic, self::TOPIC_TO_MODULE_ENUM)) {
            throw .DomainException(sprintf('Unknown GraphQL topic "%s".', $topic))
        }

        $data = Client::api_body_decode($payload)
        if (!is_array($data)) {
            throw .RuntimeException('Invalid GraphQL payload.')
        }

        return Message(self::TOPIC_TO_MODULE_ENUM[$topic], $data)
    }
}
