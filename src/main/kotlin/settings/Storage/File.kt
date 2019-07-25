

package instagramAPI.settings.Storage

import instagramAPI.Constants
import instagramAPI.exception.SettingsException
import instagramAPI.settings.StorageInterface
import instagramAPI.Utils
import java.io.File

/**
 * Persistent storage backend which keeps settings in a reliable binary file.
 *
 * @author SteveJobzniak (https://github.com/SteveJobzniak)
 */
class File : StorageInterface
{
    /** @var int Current storage format version. */
    val STORAGE_VERSION = 2

    /** @var string Format for settings filename. */
    val SETTINGSFILE_NAME = "%s-settings.dat"

    /** @var string Format for cookie jar filename. */
    val COOKIESFILE_NAME = "%s-cookies.dat"

    /** @var string The base folder for all storage files. */
    private lateinit var _baseFolder: String

    /** @var string The folder for the current user"s storage. */
    private lateinit var _userFolder: String

    /** @var string Path to the current user"s settings file. */
    private lateinit var _settingsFile: String

    /** @var string Path to the current user"s cookie jar file. */
    private lateinit var _cookiesFile: String

    /** @var string Current Instagram username that all settings belong to. */
    private lateinit var _username: String

    /**
     * Connect to a storage location and perform necessary startup preparations.
     *
     * {@inheritdoc}
     */
    override fun openLocation(locationConfig: MutableMap<String, String>) {
        // Determine which base folder to store all per-user data in.
        val baseFolder = if (locationConfig.keys.contains("basefolder"))
                    locationConfig["basefolder"] as String else Constants.SRC_DIR + "/../sessions"
        // Create the base folder and normalize its path to a clean value.
        _baseFolder = _createFolder(baseFolder)
    }

    /**
     * Whether the storage backend contains a specific user.
     *
     * {@inheritdoc}
     */
    override fun hasUser(username: String): Boolean {
        // Check whether the user"s settings-file exists.
        val hasUser = _generateUserPaths(username)

        return File(hasUser["settingsFile"]).isFile
    }

    /**
     * Move the internal data for a username to a username.
     *
     * {@inheritdoc}
     */
    override fun moveUser( oldUsername: String, newUsername: String){
        // Verify the old and username parameters.
        val oldUser = _generateUserPaths(oldUsername)
        val newUser = _generateUserPaths(newUsername)
        if (!File(oldUser["userFolder"]).isDirectory) {
            throw SettingsException("Cannot move non-existent user folder \"${oldUser["userFolder"]}\".")
        }
        if (File(newUser["userFolder"]).isDirectory) {
            throw SettingsException("Refusing to overwrite existing user folder \"${newUser["userFolder"]}\".")
        }

        // Create the destination folder and migrate all data.
        _createFolder("${newUser["userFolder"]}")
        if (File(oldUser["settingsFile"]).isFile && !File(oldUser["settingsFile"]).renameTo(File(newUser["settingsFile"])) ) {
            throw SettingsException("Failed to move \"${oldUser["settingsFile"]}\" to \"${newUser["settingsFile"]}\".")
        }
        if (File(oldUser["cookiesFile"]).isFile && !File(oldUser["cookiesFile"]).renameTo(File(newUser["cookiesFile"])) ) {
            throw SettingsException("Failed to move \"${oldUser["cookiesFile"]}\" to \"${newUser["cookiesFile"]}\".")
        }

        // Delete all files in the old folder, and the folder itself.
        Utils.deleteTree("${oldUser["userFolder"]}")
    }

    /**
     * Delete all internal data for a given username.
     *
     * {@inheritdoc}
     */
    override fun deleteUser(username: String) {
        // Delete all files in the user folder, and the folder itself.
        val delUser = _generateUserPaths(username)
        Utils.deleteTree("${delUser["userFolder"]}")
    }

    /**
     * Open the data storage for a specific user.
     *
     * {@inheritdoc}
     */
    override fun openUser(username: String){
        _username = username
        val userPaths = _generateUserPaths(username)
        _userFolder   = userPaths["userFolder"] as String
        _settingsFile = userPaths["settingsFile"] as String
        _cookiesFile  = userPaths["cookiesFile"] as String
        _createFolder(_userFolder)
    }

    /**
     * Load all settings for the currently active user.
     *
     * {@inheritdoc}
     */
    override fun loadUserSettings(): MutableMap<String, String> {
        var userSettings = mutableMapOf<String, String>()

        if (!File(_settingsFile).isFile) {
            return userSettings // Nothing to load.
        }

        // Read from disk.
        var rawData: String = file_get_contents(_settingsFile)
        if (rawData === false) {
            throw SettingsException("Unable to read from settings file \"$_settingsFile\".")
        }

        // Fetch the data version ("FILESTORAGEv#") header.
        var dataVersion = 1 // Assume migration from v1 if no version.
        if (preg_match("/^FILESTORAGEv(.d+)/", rawData, matches)) {
            dataVersion = intval(matches[1])
            rawData = rawData.substring(rawData.indexOf("") + 1)
        }

        // Decode the key-value pairs regardless of data-storage version.
        userSettings = _decodeStorage(dataVersion, rawData)

        return userSettings
    }

    /**
     * Save the settings for the currently active user.
     *
     * {@inheritdoc}
     */
    override fun saveUserSettings(userSettings: Map<String, String>, triggerKey: String) {
        // Generate the storage version header.
        val versionHeader = "FILESTORAGEv$STORAGE_VERSION"

        // Encode a binary representation of all settings.
        // VERSION 2 STORAGE FORMAT: JSON-encoded blob.
        val encodedData = versionHeader + json_encode(userSettings)

        // Perform an atomic diskwrite, which prevents accidental truncation.
        // NOTE: If we had just written directly to settingsPath, the file would
        // have become corrupted if the script was killed mid-write. The atomic
        // write process guarantees that the data is fully written to disk.
        Utils.atomicWrite(_settingsFile, encodedData)
    }

    /**
     * Whether the storage backend has cookies for the currently active user.
     *
     * {@inheritdoc}
     */
    override fun hasUserCookies(): Boolean {
        return File(_cookiesFile).isFile && filesize(_cookiesFile) > 0
    }

    /**
     * Get the cookiefile disk path (only if a file-based cookie jar is wanted).
     *
     * {@inheritdoc}
     */
    override fun getUserCookiesFilePath(): String {
        // Tell the caller to import a file-based cookie jar.
        return _cookiesFile
    }

    /**
     * (Non-cookiefile) Load all cookies for the currently active user.
     *
     * {@inheritdoc}
     */
    override fun loadUserCookies(): String? {
        // Never called for "cookiefile" format.
    }

    /**
     * (Non-cookiefile) Save all cookies for the currently active user.
     *
     * {@inheritdoc}
     */
    override fun saveUserCookies(rawData: String) {
        // Never called for "cookiefile" format.
    }

    /**
     * Close the settings storage for the currently active user.
     *
     * {@inheritdoc}
     */
    override fun closeUser(){
        _userFolder = null
        _settingsFile = null
        _cookiesFile = null
        _username = null
    }

    /**
     * Disconnect from a storage location and perform necessary shutdown steps.
     *
     * {@inheritdoc}
     */
    override fun closeLocation(){
        // We don"t need to disconnect from anything since we are file-based.
    }

    /**
     * Decodes the data from any File storage format version.
     *
     * @param int    $dataVersion Which data format to decode.
     * @param string $rawData     The raw data, encoded in version"s format.
     *
     * @throws .instagramAPI.exception.SettingsException
     *
     * @return array An array with all current key-value pairs for the user.
     */
    private fun _decodeStorage( dataVersion: Int, rawData: String): Map<Any, Any>{
        var loadedSettings: Map<Any, Any> = mutableMapOf()

        when (dataVersion) {
            1 -> {
                /**
                 * This is the old format from v1.x of Instagram-API.
                 * Terrible format. Basic "key=value.r.n" and very fragile.
                 */

                // Split by system-independent newlines. Tries .r.n (Win), then .r
                // (pre-2000s Mac), then .n.r, then .n (Mac OS X, UNIX, Linux).
                val lines = preg_split("/(.r.n?|.n.r?)/", rawData, -1, PREG_SPLIT_NO_EMPTY)
                if (lines !== false) {
                    for(line in lines) {
                        // Key must be at least one character. Allows empty values.
                        if (preg_match("/^([^=]+)=(.*)$/", line, matches)) {
                            val key = matches[1]
                            val value = rtrim(matches[2], ".r.n ")
                            loadedSettings[key] = value
                        }
                    }
                }
            }
            2 -> {
                /**
                 * Version 2 uses JSON encoding and perfectly stores any value.
                 * And file corruption can"t happen, thanks to the atomic writer.
                 */
                val loadedSettings = json_decode(rawData, true, 512, JSON_BIGINT_AS_STRING)
                if (!is_array(loadedSettings)) {
                    throw SettingsException("Failed to decode corrupt settings file for account \"$_username\".")
                }
            }
            else -> throw SettingsException("Invalid file settings storage format version \"$dataVersion\".")
        }

        return loadedSettings
    }

    /**
     * Generates all path strings for a given username.
     *
     * @param string $username The Instagram username.
     *
     * @return array An array with information about the user"s paths.
     */
    private fun _generateUserPaths(username: String): Map<String, String> {
        val userFolder = "$_baseFolder/$username"
        val settingsFile = userFolder + "/" + SETTINGSFILE_NAME.format(username)
        val cookiesFile = userFolder + "/" + COOKIESFILE_NAME.format(username)

        return mapOf(
            "userFolder"   to userFolder,
            "settingsFile" to settingsFile,
            "cookiesFile"  to cookiesFile
        )
    }

    /**
     * Creates a folder if missing, or ensures that it is writable.
     *
     * @param string $folder The directory path.
     *
     * @throws .instagramAPI.exception.SettingsException
     *
     * @return string The canonicalized absolute pathname of the folder, without
     *                any trailing slash.
     */
    private fun _createFolder(folder: String): String{
        if (!Utils.createFolder(folder)) {
            throw SettingsException("The \"$folder\" folder is not writable.")
        }

        // Determine the real path of the folder we created/checked.
        // NOTE: This ensures that the path will work even on stingy systems
        // such as Windows Server which chokes on multiple slashes in a row.
        //        if (realPath !is String) {
//            throw SettingsException("Unable to resolve real path to folder \"$folder\".")
//        }

        return realpath(folder)
    }
}
