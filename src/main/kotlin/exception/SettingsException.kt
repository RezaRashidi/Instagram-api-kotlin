

package instagramAPI.exception

/**
 * Used for all problems with the settings storage.
 */
class SettingsException ( override  val message: String?): InternalException(message)
{
}
