

package instagramAPI.settings.Storage.Components

import instagramAPI.exception.SettingsException
import instagramAPI.settings.StorageInterface
import PDO
import java.lang.reflect.Constructor

/**
 * Re-usable PDO storage component, for easily building PDO-based backends.
 *
 * Any class derived from this base must implement the abstract methods.
 *
 * @author SteveJobzniak (https://github.com/SteveJobzniak)
 */
abstract class PDOStorage : StorageInterface{
    /** @var string Human name of the backend, such as "MySQL" or "SQLite". */
    protected lateinit var _backendName: String

    /** @var .PDO Our connection to the database. */
    protected lateinit var _pdo: PDO

    /** @var bool Whether we own the PDO connection or are borrowing it. */
    protected var _isSharedPDO: Boolean

    /** @var string Which table to store the settings in. */
    protected lateinit var _dbTableName: String

    /** @var string Current Instagram username that all settings belong to. */
    protected lateinit var _username: String

    /** @var array A cache of important columns from the user"s database row. */
    protected lateinit var _cache: MutableMap<String, String?>

    /**
     * Constructor.
     *
     * @param string $backendName Human name of the backend, such as "MySQL" or "SQLite".
     *
     * @throws .instagramAPI.exception.SettingsException
     */
    fun constructor(backendName: String = "PDO"){
        _backendName = backendName
    }

    /**
     * Connect to a storage location and perform necessary startup preparations.
     *
     * {@inheritdoc}
     */
    fun openLocation(array locationConfig){
        _dbTableName = if(!locationConfig["dbtablename"].isBlank()) locationConfig["dbtablename"] else "user_sessions"

        if (!locationConfig["pdo"].isBlank()) {
            // Pre-provided connection to re-import instead of creating a one.
            if (locationConfig["pdo"] !is PDO) {
                throw SettingsException("The custom PDO object is invalid.")
            }
            _isSharedPDO = true
            _pdo = locationConfig["pdo"]
        } else {
            // We should connect for the user, by creating our own PDO object.
            _isSharedPDO = false

            try {
                _pdo = _createPDO(locationConfig)
            } catch (e: Exception) {
                throw SettingsException(_backendName + " Connection Failed: " + e.message)
            }
        }

        try {
            _configurePDO()
        } catch (e: Exception) {
            throw SettingsException(_backendName + " Configuration Failed: " + e.message)
        }

        try {
            _autoCreateTable()
        } catch (e: Exception) {
            throw SettingsException(_backendName + " Error: " + e.message)
        }
    }

    /**
     * Create a PDO connection to the database.
     *
     * @param array $locationConfig Configuration parameters for the location.
     *
     * @throws .exception
     *
     * @return .PDO The database connection.
     */
    abstract protected fun _createPDO(array locationConfig): PDO

    /**
     * Configures the connection for our needs.
     *
     * Warning for those who re-used a PDO object: Beware that we WILL change
     * attributes on the PDO connection to suit our needs! Primarily turning all
     * error reporting into exceptions, and setting the charset to UTF-8. If you
     * want to re-import a PDO connection, you MUST accept the fact that WE NEED
     * exceptions and UTF-8 in our PDO! If that is not acceptable to you then DO
     * NOT re-import your own PDO object!
     *
     * @throws .exception
     */
    protected fun _configurePDO(){
        _pdo.setAttribute(PDO.ATTR_EMULATE_PREPARES, false)
        _pdo.setAttribute(PDO.ATTR_ERRMODE, PDO.ERRMODE_EXCEPTION)
        _enableUTF8()
    }

    /**
     * Enable UTF-8 encoding on the connection.
     *
     * This is database-specific and usually requires some kind of query.
     *
     * @throws .exception
     */
    abstract protected fun _enableUTF8()

    /**
     * Automatically create the database table if necessary.
     *
     * @throws .exception
     */
    abstract protected fun _autoCreateTable()

    /**
     * Automatically writes to the correct user"s row and caches the value.
     *
     * @param string $column The database column.
     * @param string $data   Data to be written.
     *
     * @throws .instagramAPI.exception.SettingsException
     */
    protected fun _setUserColumn( column: String, data: String){
        if (column != "settings" && column != "cookies") {
            throw SettingsException("Attempt to write to illegal database column \"$column\".")
        }

        try {
            // Update if the user row already exists, otherwise insert.
            val binds = mutableMapOf(":data" to data)
            val sql: String
            if (_cache["id"] !== null) {
                sql = "UPDATE `{this._dbTableName}` SET {$column}=:data WHERE (id=:id)"
                binds[":id"] = _cache["id"]
            } else {
                sql = "INSERT INTO `{this._dbTableName}` (username, {$column}) VALUES (:username, :data)"
                binds[":username"] = _username
            }

            val sth = _pdo.prepare(sql)
            sth.execute(binds)

            // Keep track of the database row ID for the user.
            if (_cache["id"] === null) {
                _cache["id"] = _pdo.lastinsertid()
            }

            sth.closeCursor()

            // Cache the value.
            _cache[column] = data
        } catch (e: Exception) {
            throw SettingsException(_backendName + " Error: " + e.message)
        }
    }

    /**
     * Whether the storage backend contains a specific user.
     *
     * {@inheritdoc}
     */
    fun hasUser(username: String): Boolean {
        // Check whether a row exists for that username.
        val sth = _pdo.prepare("SELECT EXISTS(SELECT 1 FROM `{this._dbTableName}` WHERE (username=:username))")
        sth.execute( mapOf(":username" to username))
        val result = sth.fetchColumn()
        sth.closeCursor()

        return result > 0
    }

    /**
     * Move the internal data for a username to a username.
     *
     * {@inheritdoc}
     */
    fun moveUser( oldUsername: String, newUsername: String){
        try {
            // Verify that the old username exists.
            if (!hasUser(oldUsername)) {
                throw SettingsException("Cannot move non-existent user \"$oldUsername\".")
            }

            // Verify that the username does not exist.
            if (hasUser(newUsername)) {
                throw SettingsException("Refusing to overwrite existing user \"$newUsername\".")
            }

            // Now attempt to rename the old username column to the name.
            val sth = _pdo.prepare("UPDATE `{this._dbTableName}` SET username=:newusername WHERE (username=:oldusername)")
            sth.execute(mapOf(":oldusername" to oldUsername, ":newusername" to newUsername))
            sth.closeCursor()
        } catch (e: SettingsException) {
            throw e // Ugly but necessary to re-throw only our own messages.
        } catch (e: Exception) {
            throw SettingsException(_backendName + " Error: " + e.message)
        }
    }

    /**
     * Delete all internal data for a given username.
     *
     * {@inheritdoc}
     */
    fun deleteUser(username: String){
        try {
            // Just attempt to delete the row. Doesn"t error if already missing.
            val sth = _pdo.prepare("DELETE FROM `{this._dbTableName}` WHERE (username=:username)")
            sth.execute( mapOf(":username" to username) )
            sth.closeCursor()
        } catch (e: Exception) {
            throw SettingsException(_backendName + " Error: " + e.message)
        }
    }

    /**
     * Open the data storage for a specific user.
     *
     * {@inheritdoc}
     */
    fun openUser(username: String){
        _username = username

        // Retrieve and cache the existing user data row if available.
        try {
            val sth = _pdo.prepare("SELECT id, settings, cookies FROM `{this._dbTableName}` WHERE (username=:username)")
            sth.execute( mapOf(":username" to _username) )
            val result = sth.fetch(PDO.FETCH_ASSOC)
            sth.closeCursor()

            if (is_array(result)) {
                _cache = result
            } else {
                _cache = mutableMapOf(
                    "id"       to null,
                    "settings" to null,
                    "cookies"  to null
                )
            }
        } catch (e: Exception) {
            throw SettingsException(_backendName + " Error: " + e.message)
        }
    }

    /**
     * Load all settings for the currently active user.
     *
     * {@inheritdoc}
     */
    fun loadUserSettings(){
        val userSettings = []

        if (_cache["settings"]!!.isNotEmpty()) {
            userSettings = json_decode(_cache["settings"], true, 512, JSON_BIGINT_AS_STRING)
            if (!is_array(userSettings)) {
                throw SettingsException("Failed to decode corrupt settings for account \"$_username\".")
            }
        }

        return userSettings
    }

    /**
     * Save the settings for the currently active user.
     *
     * {@inheritdoc}
     */
    fun saveUserSettings(array userSettings, triggerKey){
        // Store the settings as a JSON blob.
        val encodedData = json_encode(userSettings)
        _setUserColumn("settings", encodedData)
    }

    /**
     * Whether the storage backend has cookies for the currently active user.
     *
     * {@inheritdoc}
     */
    fun hasUserCookies(): Boolean {
        return !_cache["cookies"]!!.isBlank() && _cache["cookies"]!!.isNotEmpty()
    }

    /**
     * Get the cookiefile disk path (only if a file-based cookie jar is wanted).
     *
     * {@inheritdoc}
     */
    fun getUserCookiesFilePath(){
        // NULL = We (the backend) will handle the cookie loading/saving.
        return null
    }

    /**
     * (Non-cookiefile) Load all cookies for the currently active user.
     *
     * {@inheritdoc}
     */
    fun loadUserCookies(): String? {
        return if(!_cache["cookies"]!!.isBlank()) _cache["cookies"] else null
    }

    /**
     * (Non-cookiefile) Save all cookies for the currently active user.
     *
     * {@inheritdoc}
     */
    fun saveUserCookies(rawData: String){
        // Store the raw cookie data as-provided.
        _setUserColumn("cookies", rawData)
    }

    /**
     * Close the settings storage for the currently active user.
     *
     * {@inheritdoc}
     */
    fun closeUser(){
        _username = null
        _cache = null
    }

    /**
     * Disconnect from a storage location and perform necessary shutdown steps.
     *
     * {@inheritdoc}
     */
    fun closeLocation(){
        // Delete our reference to the PDO object. If nobody else references
        // it, the PDO connection will now be terminated. In case of shared
        // objects, the original owner still has their reference (as intended).
        _pdo = null
    }
}
