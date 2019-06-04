

package InstagramAPI.Realtime.Parser

import Fbns.Client.Thrift.Compact
import Fbns.Client.Thrift.Reader
import InstagramAPI.Realtime.Handler.RegionHintHandler
import InstagramAPI.Realtime.Message
import InstagramAPI.Realtime.ParserInterface

class RegionHintParser : ParserInterface
{
    val FIELD_TOPIC = 1

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
        $region = null
        Reader($payload, fun ($context, $field, $value, $type) import (&$region) {
            if ($type === Compact::TYPE_BINARY && $field === self::FIELD_TOPIC) {
                $region = $value
            }
        })

        return [this._createMessage($region)]
    }

    /**
     * Create a message from given topic and payload.
     *
     * @param string $region
     *
     * @throws .RuntimeException
     * @throws .DomainException
     *
     * @return Message
     */
    protected fun _createMessage(
        $region)
    {
        if ($region === null) {
            throw .RuntimeException("Incomplete region hint message.")
        }

        return Message(RegionHintHandler::MODULE, $region)
    }
}
