

package instagramAPI.exception

/**
 * Means that request start-line and/or headers are too large
 * to be processed by Instagram"s server.
 */
class RequestHeadersTooLargeException( override  val message: String?) : RequestException(message)
{
}
