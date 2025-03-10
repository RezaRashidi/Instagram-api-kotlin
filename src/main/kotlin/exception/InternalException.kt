

package instagramAPI.exception

/**
 * All internally generated non-server exceptions must derive from this class.
 */
open class InternalException( override  val message: String?) : InstagramException(message)
{
}
