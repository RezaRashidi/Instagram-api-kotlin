

package instagramAPI.exception

/**
 * Used when the server requires us to login again, and also used as a locally
 * triggered exception when we know for sure that we aren"t logged in.
 */
class LoginRequiredException( override  val message: String?) : RequestException(message)
{
}
