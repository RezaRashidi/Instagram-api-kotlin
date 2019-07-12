

package instagramAPI.Exception

/**
 * Used for endpoint calls that returned an empty/invalid (non-JSON) response.
 */
class EmptyResponseException( override  val message: String?) : EndpointException(message)
{
}
