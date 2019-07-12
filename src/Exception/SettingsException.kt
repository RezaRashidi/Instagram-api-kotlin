

package instagramAPI.Exception

/**
 * Used for all problems with the Settings storage.
 */
class SettingsException ( override  val message: String?): InternalException(message)
{
}
