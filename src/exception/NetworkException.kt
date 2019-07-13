

package instagramAPI.exception

import jdk.javadoc.internal.doclets.toolkit.util.DocPath.parent
import instagramAPI.Exception.RequestException as RequestException1

/**
 * This exception re-wraps ALL networking/socket exceptions.
 *
 * Currently (and probably forever) we"re using the great Guzzle library for all
 * network communication. So this exception is used for re-wrapping their
 * exceptions into something derived from our own base InstagramException
 * instead, to make life much easier for our users (that way they only have
 * to catch our base exception in their projects, and won"t have to also catch
 * Guzzle"s various exceptions).
 *
 * It also ensures that users get a proper stack-trace showing which area of OUR
 * code threw the exception, instead of some useless line of GUZZLE"S code.
 *
 * The message we import is the same as the message of the Guzzle exception, but
 * nicely formatted to begin with "Network: " and to always end in punctuation
 * that forms complete English sentences, so that the exception message is ready
 * for display in user-facing applications.
 *
 * And if you (the programmer) want extra details that were included in Guzzle"s
 * exception, you can simply import "getGuzzleException()" on this NetworkException
 * to get the original Guzzle exception instead, which has more networking
 * information, such as the failed HTTP request that was attempted.
 */
class NetworkException : RequestException1()
{
    /** @var .exception */
    private var _guzzleException:Exception?

    /**
     * Constructor.
     *
     * @param .exception $guzzleException The original Guzzle exception.
     */
    constructor( guzzleException :Exception)
    {
        this._guzzleException = guzzleException

        // Ensure that the message is nicely formatted and follows our standard.
        var message = "Network: "+ ServerMessageThrower.prettifyMessage(this._guzzleException.getMessage())

        // Construct with our custom message.
        // NOTE: We DON"T assign the guzzleException to "$previous", otherwise
        // the user would still see something like "Uncaught GuzzleHttp.exception.
        // RequestException" and Guzzle"s stack trace, instead of "Uncaught
        // instagramAPI.exception.NetworkException" and OUR correct stack trace.
       super(message) //todo::call core exception  constructor
    }

    /**
     * Gets the original Guzzle exception, which contains much more details.
     *
     * @return .exception The original Guzzle exception.
     */
     fun getGuzzleException()
    {
        return this._guzzleException
    }
}
