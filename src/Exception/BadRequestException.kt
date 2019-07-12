

package instagramAPI.Exception

/**
 * Used for endpoint calls that fail with HTTP code "400 Bad Request", but only
 * if no other more serious exception was found in the server response.
 */
class BadRequestException( override  val message: String?) : EndpointException(message)
{
}
