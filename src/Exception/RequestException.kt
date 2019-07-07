

package InstagramAPI.Exception

/**
 * All server-response API related exceptions must derive from this class.
 */
open class RequestException( override  val message: String?) : InstagramException(message)
{
}
