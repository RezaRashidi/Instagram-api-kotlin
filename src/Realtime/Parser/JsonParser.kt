

package InstagramAPI.Realtime.Parser

import InstagramAPI.Client
import InstagramAPI.Realtime.Message
import InstagramAPI.Realtime.ParserInterface

class JsonParser : ParserInterface
{
    /** @var string */
    protected $_module

    /**
     * Constructor.
     *
     * @param string $module
     */
    public fun __construct(
        $module)
    {
        this._module = $module
    }

    /**
     * {@inheritdoc}
     *
     * @throws .RuntimeException
     */
    public fun parseMessage(
        $topic,
        $payload)
    {
        $data = Client::api_body_decode($payload)
        if (!is_array($data)) {
            throw .RuntimeException('Invalid JSON payload.')
        }

        return [Message(this._module, $data)]
    }
}
