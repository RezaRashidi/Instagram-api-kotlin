

package InstagramAPI.Realtime.Parser

import InstagramAPI.Client
import InstagramAPI.Realtime.Message
import InstagramAPI.Realtime.ParserInterface

class IrisParser : ParserInterface
{
    val MODULE = "direct"

    /**
     * {@inheritdoc}
     *
     * @throws .RuntimeException
     */
    public fun parseMessage(
        $topic,
        $payload)
    {
        $messages = Client::api_body_decode($payload)
        if (!is_array($messages)) {
            throw .RuntimeException("Invalid Iris payload.")
        }

        $result = []
        foreach ($messages as $message) {
            $result[] = Message(self::MODULE, $message)
        }

        return $result
    }
}
