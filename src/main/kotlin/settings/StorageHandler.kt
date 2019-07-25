package instagramAPI.settings

//import Fbns.Client.Auth.DeviceAuth
//import Fbns.Client.AuthInterface
import instagramAPI.exception.SettingsException
import instagramAPI.Utils
import java.io.File
import kotlin.random.Random

/**
 * Advanced, modular settings storage engine.
 *
 * Connects to a StorageInterface and transfers data to/from the application,
 * with intelligent caching and data translation.
 *
 * @author SteveJobzniak (https://github.com/SteveJobzniak)
 */
class StorageHandler {
	/**
	 * Complete list of all settings that will be stored/retrieved persistently.
	 *
	 * This key list WILL be changed whenever we need to support features,
	 * so do NOT assume that it will stay the same forever.
	 *
	 * @var array
	 */
	val PERSISTENT_KEYS = arrayOf("account_id", // The numerical UserPK ID of the account.
	                              "devicestring", // Which Android device they"re identifying as.
	                              "device_id", // Hardware identifier.
	                              "phone_id", // Hardware identifier.
	                              "uuid", // Universally unique identifier.
	                              "advertising_id", // Google Play advertising ID.
	                              "session_id", // The user"s current application session ID.
	                              "experiments", // Interesting experiment variables for this account.
	                              "fbns_auth", // Serialized auth credentials for FBNS.
	                              "fbns_token", // Serialized FBNS token.
	                              "last_fbns_token", // Tracks time elapsed since our last FBNS token refresh.
	                              "last_login", // Tracks time elapsed since our last login state refresh.
	                              "last_experiments", // Tracks time elapsed since our last experiments refresh.
	                              "datacenter", // Preferred data center (region-based).
	                              "presence_disabled", // Whether the presence feature has been disabled by user.
	                              "zr_token", // Zero rating token.
	                              "zr_expires", // Zero rating token expiration timestamp.
	                              "zr_rules"// Zero rating rewrite rules.
	)

	/**
	 * List of important settings to keep when erasing device-specific settings.
	 *
	 * Whenever we are told to erase all device-specific settings, we will clear
	 * the values of all settings EXCEPT the keys listed here. It is therefore
	 * VERY important to list ALL important NON-DEVICE specific settings here!
	 *
	 * @var array
	 *
	 * @see StorageHandler::eraseDeviceSettings()
	 */
	val KEEP_KEYS_WHEN_ERASING_DEVICE = arrayOf("account_id")
	// We don"t really need to keep this, but it"s a good example.


	/**
	 * Whitelist for experiments.
	 *
	 * We will save ONLY the experiments mentioned in this list.
	 *
	 * @var array
	 */
	val EXPERIMENT_KEYS =
		arrayOf("ig_android_2fac", "ig_android_realtime_iris", "ig_android_skywalker_live_event_start_end",
		        "ig_android_gqls_typing_indicator", "ig_android_upload_reliability_universe",
		        "ig_android_photo_fbupload_universe", "ig_android_video_segmented_upload_universe",
		        "ig_android_direct_video_segmented_upload_universe",
		        "ig_android_reel_raven_video_segmented_upload_universe", "ig_android_ad_async_ads_universe",
		        "ig_android_direct_inbox_presence", "ig_android_direct_thread_presence", "ig_android_rtc_reshare",
		        "ig_android_sidecar_photo_fbupload_universe", "ig_android_fbupload_sidecar_video_universe",
		        "ig_android_skip_get_fbupload_photo_universe", "ig_android_skip_get_fbupload_universe",
		        "ig_android_loom_universe", "ig_android_live_suggested_live_expansion",
		        "ig_android_live_qa_broadcaster_v1_universe")

	/**
	 * Complete list of all supported callbacks.
	 *
	 * - "onCloseUser": Triggered before closing a user"s storage (at script
	 *   end or when switching to a different user). Can be used for bulk-saving
	 *   data at the end of a user"s session, to avoid constant micro-updates.
	 */
	val SUPPORTED_CALLBACKS = arrayOf("onCloseUser")

	/** @var StorageInterface The active storage backend. */
	private var _storage: StorageInterface

	/** @var array Optional callback funs. */
	private var _callbacks: MutableMap<String, String>

	/** @var string Current Instagram username that all settings belong to. */
	private lateinit var _username: String

	/** @var array Cache for the current user"s key-value settings pairs. */
	private var _userSettings = mutableMapOf<String,String>()

	/** @var string|null Location of the cookiefile if file-based jar wanted. */
	private var _cookiesFilePath: String? = null

	/**
	 * Constructor.
	 *
	 * @param StorageInterface storageInstance An instance of desired Storage.
	 * @param array            locationConfig  Configuration parameters for
	 *                                          the storage backend location.
	 * @param array            callbacks       Optional callback funs.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 */
	constructor(storageInstance: StorageInterface, locationConfig: MutableMap<String, String> = mutableMapOf(), callbacks: MutableMap<String, String> = mutableMapOf()) {
//		if (!storageInstance instanceof StorageInterface) {
//			throw SettingsException("You must provide an instance of a StorageInterface class.")
//		}
//		if (!is_array(locationConfig)) {
//			throw SettingsException("The storage location configuration must be an array.")
//		}

		// Store any user-provided callbacks.
		_callbacks = callbacks

		// Connect the storage instance to the user"s desired storage location.
		_storage = storageInstance
		_storage.openLocation(locationConfig)
	}

	/**
	 * Destructor.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 */
	public fun __destruct() {
		// The storage handler is being killed, so tell the location to close.
		if (this._username !== null) {
			this._triggerCallback("onCloseUser")
			this._storage.closeUser()
			this._username = null
		}
		this._storage.closeLocation()
	}

	/**
	 * Whether the storage backend contains a specific user.
	 *
	 * @param string username The Instagram username.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 *
	 * @return bool TRUE if user exists, otherwise FALSE.
	 */
	fun hasUser(username: String): Boolean {
		_throwIfEmptyValue(username)

		return _storage.hasUser(username)
	}

	/**
	 * Move the internal data for a username to a username.
	 *
	 * This fun is important becaimport of the fact that all per-user settings
	 * in all Storage implementations are retrieved and stored via its Instagram
	 * username, since their NAME is literally the ONLY thing we know about a
	 * user before we have loaded their settings or logged in! So if you later
	 * rename that Instagram account, it means that your old device settings
	 * WON"T follow along automatically, since the login username is seen
	 * as a brand user that isn"t in the settings storage.
	 *
	 * This fun conveniently tells your chosen Storage backend to move a
	 * user"s settings to a name, so that they WILL be found again when you
	 * later look for settings for your name.
	 *
	 * Bonus guide for easily confused people: YOU must manually rename your
	 * user on Instagram.com before you call this fun. We don"t do that.
	 *
	 * @param string oldUsername The old name that settings are stored as.
	 * @param string newUsername The name to move the settings to.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 */
	fun moveUser(oldUsername: String, newUsername: String) {
		_throwIfEmptyValue(oldUsername)
		_throwIfEmptyValue(newUsername)

		if (oldUsername === _username || newUsername === _username) {
			throw SettingsException("Attempted to move settings to/from the currently active user.")
		}

		_storage.moveUser(oldUsername, newUsername)
	}

	/**
	 * Delete all internal data for a given username.
	 *
	 * @param string username The Instagram username.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 */
	fun deleteUser(username: String) {
		_throwIfEmptyValue(username)

		if (username === _username) {
			throw SettingsException("Attempted to delete the currently active user.")
		}

		_storage.deleteUser(username)
	}

	/**
	 * Load all settings for a user from the storage and mark as current user.
	 *
	 * @param string username The Instagram username.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 */
	fun setActiveUser(username: String) {
		_throwIfEmptyValue(username)

		// If that user is already loaded, there"s no need to do anything.
		if (username === _username) {
			return
		}

		// If we"re switching away from a user, tell the backend to close the
		// current user"s storage (if it needs to do any special processing).
		if (_username !== null) {
			_triggerCallback("onCloseUser")
			_storage.closeUser()
		}

		// Set the user as the current user for this storage instance.
		_username = username
		_userSettings = mutableMapOf()
		_storage.openUser(username)

		// Retrieve any existing settings for the user from the backend.
		val loadedSettings = _storage.loadUserSettings()
		for((key, value) in loadedSettings) {
			// Map renamed old-school keys to key names.
			var keyF = key
			if (keyF == "username_id") {
				keyF = "account_id"
			} else if (keyF == "adid") {
				keyF = "advertising_id"
			}

			// Only keep values for keys that are still in use. Discard others.
			if (PERSISTENT_KEYS.contains(keyF)) {
				// Cast all values to strings to ensure we only import strings!
				// NOTE: THIS CAST IS EXTREMELY IMPORTANT AND *MUST* BE DONE!
				_userSettings[keyF] = value
			}
		}

		// Determine what type of cookie storage the backend wants for the user.
		// NOTE: Do NOT validate file existence, since we"ll create if missing.
		var cookiesFilePath: String? = _storage.getUserCookiesFilePath()
		if ( cookiesFilePath !== null && cookiesFilePath.isNotEmpty() ) {
			cookiesFilePath = null // Disable since it isn"t a non-empty string.
		}
		_cookiesFilePath = cookiesFilePath
	}

	/**
	 * Does a preliminary guess about whether the current user is logged in.
	 *
	 * Can only be executed after setActiveUser(). And the session it looks
	 * for may be expired, so there"s no guarantee that we are still logged in.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 *
	 * @return bool TRUE if possibly logged in, otherwise FALSE.
	 */
	fun isMaybeLoggedIn():Boolean{
		_throwIfNoActiveUser()

		return _storage.hasUserCookies() && get("account_id")!!.isNotEmpty()
	}

	/**
	 * Erase all device-specific settings and all cookies.
	 *
	 * This is useful when assigning a Android device to the account, upon
	 * which it"s very important that we erase all previous, device-specific
	 * settings so that our account still looks natural to Instagram.
	 *
	 * Note that ALL cookies will be erased too, to clear out the old session.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 */
	fun eraseDeviceSettings() {
		for(key in PERSISTENT_KEYS) {
			if (!KEEP_KEYS_WHEN_ERASING_DEVICE.contains(key)) {
				set(key, "") // Erase the setting.
			}
		}

		setCookies("") // Erase all cookies.
	}

	/**
	 * Retrieve the value of a setting from the current user"s memory cache.
	 *
	 * Can only be executed after setActiveUser().
	 *
	 * @param string key Name of the setting.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 *
	 * @return string|null The value as a string IF the setting exists AND is
	 *                     a NON-EMPTY string. Otherwise NULL.
	 */
	fun get(key: String):String? {
		_throwIfNoActiveUser()

		// Reject anything that isn"t in our list of VALID persistent keys.
		if (!PERSISTENT_KEYS.contains(key)) {
			throw SettingsException("The settings key \"$key\" is not a valid persistent key name.")
		}

		// Return value if it"s a NON-EMPTY string, otherwise return NULL.
		// NOTE: All values are cached as strings so no casting is needed.
		return if (_userSettings.contains(key)  && _userSettings[key] !== "") _userSettings[key] else null
	}

	/**
	 * Store a setting"s value for the current user.
	 *
	 * Can only be executed after setActiveUser(). To clear the value of a
	 * setting, simply pass in an empty string as value.
	 *
	 * @param string       key   Name of the setting.
	 * @param string|mixed value The data to store. MUST be castable to string.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 */
	fun set(key: String, value: Any) {
		_throwIfNoActiveUser()

		// Reject anything that isn"t in our list of VALID persistent keys.
		if (!PERSISTENT_KEYS.contains(key)) {
			throw SettingsException("The settings key \"$key\" is not a valid persistent key name.")
		}

		// Reject null values, since they may be accidental. To unset a setting,
		// the caller must explicitly pass in an empty string instead.
		if (value === null) {
			throw SettingsException("Illegal attempt to store null value in settings storage.")
		}

		// Cast the value to string to ensure we don"t try writing non-strings.
		// NOTE: THIS CAST IS EXTREMELY IMPORTANT AND *MUST* ALWAYS BE DONE!
		val valueF = value as String

				// Check if the value differs from our storage (cached representation).
				// NOTE: This optimizes writes by only writing when values change!
				if ( !_userSettings.keys.contains(key) || _userSettings[key] !== valueF ) {
					// The value differs, so save to memory cache and write to storage.
					_userSettings[key] = valueF
					_storage.saveUserSettings(_userSettings, key)
				}
	}

	/**
	 * Whether the storage backend has cookies for the currently active user.
	 *
	 * Can only be executed after setActiveUser().
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 *
	 * @return bool TRUE if cookies exist, otherwise FALSE.
	 */
	fun hasCookies(): Boolean {
		_throwIfNoActiveUser()

		return _storage.hasUserCookies()
	}

	/**
	 * Get all cookies for the currently active user.
	 *
	 * Can only be executed after setActiveUser().
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 *
	 * @return string|null A previously-stored, raw cookie data string
	 *                     (non-empty), or NULL if no cookies exist for
	 *                     the active user.
	 */
	fun getCookies():String? {
		_throwIfNoActiveUser()

		// Read the cookies via the appropriate backend method.
		var userCookies: String? = null
		if (_cookiesFilePath === null) { // Backend storage.
			userCookies = _storage.loadUserCookies()
		} else { // Cookiefile on disk.
			if (_cookiesFilePath!!.isEmpty()) { // Just for extra safety.
				throw SettingsException("Cookie file format requested, but no file path provided.")
			}

			// Ensure that the cookie file"s folder exists and is writable.
			_createCookiesFileDirectory()

			// Read the existing cookie jar file if it already exists.
			if ( File(_cookiesFilePath).isFile ) {
				val rawData = file_get_contents(_cookiesFilePath)
				if (rawData !== false) {
					userCookies = rawData
				}
			}
		}

		// Ensure that we"ll always return NULL if no cookies exist.
		if (userCookies !== null && userCookies.isNotEmpty()) {
			userCookies = null
		}

		return userCookies
	}

	/**
	 * Save all cookies for the currently active user.
	 *
	 * Can only be executed after setActiveUser(). Note that this fun is
	 * called frequently!
	 *
	 * NOTE: It is very important that the owner of this SettingsHandler either
	 * continuously calls "setCookies", or better yet listens to the "closeUser"
	 * callback to save all cookies in bulk to storage at the end of a session.
	 *
	 * @param string rawData An encoded string with all cookie data. import an
	 *                        empty string to erase currently stored cookies.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 */
	fun setCookies(rawData: String) {
		_throwIfNoActiveUser()
		_throwIfNotString(rawData)

		if (_cookiesFilePath === null) { // Backend storage.
			_storage.saveUserCookies(rawData)
		} else { // Cookiefile on disk.
			if (rawData.isNotEmpty()) { // Update cookies (value is non-empty).
				// Perform an atomic diskwrite, which prevents accidental
				// truncation if the script is ever interrupted mid-write.
				_createCookiesFileDirectory() // Ensures dir exists.
				val timeout = 5
				val init = System.currentTimeMillis()/1000
				val written: Int = Utils.atomicWrite(_cookiesFilePath as String, rawData)
				while (written > 0 ) {
					usleep( Random.nextInt(400000, 600000) )  // 0.4-0.6 sec
					if ( System.currentTimeMillis()/1000 - init > timeout) {
						break
					}
				}
				if (written === -1) {
					throw SettingsException("The \"$_cookiesFilePath\" cookie file is not writable.")
				}
			} else { // Delete cookies (empty string).
				// Delete any existing cookie jar since the data is empty.
				if ( File(_cookiesFilePath).isFile && !File(_cookiesFilePath).delete() ) {
					throw SettingsException("Unable to delete the \"$_cookiesFilePath\" cookie file.")
				}
			}
		}
	}

	/**
	 * Ensures the whole directory path to the cookie file exists/is writable.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 */
	protected fun _createCookiesFileDirectory() {
		if (_cookiesFilePath === null) {
			return
		}

		val cookieDir = File(_cookiesFilePath).parent // Can be "." in case of CWD.
		if (!Utils.createFolder(cookieDir)) {
			throw SettingsException("The \"$cookieDir\" cookie folder is not writable.")
		}
	}

	/**
	 * Internal: Ensures that a parameter is a string.
	 *
	 * @param mixed value The value to check.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 */
	protected fun _throwIfNotString(value: Any) {
		if (value !is String) {
			throw SettingsException("Parameter must be string.")
		}
	}

	/**
	 * Internal: Ensures that a parameter is a non-empty string.
	 *
	 * @param mixed value The value to check.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 */
	protected fun _throwIfEmptyValue(value: Any) {
		if (value !is String || value === "") {
			throw SettingsException("Parameter must be non-empty string.")
		}
	}

	/**
	 * Internal: Ensures that there is an active storage user.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 */
	protected fun _throwIfNoActiveUser() {
		if (_username === null) {
			throw SettingsException("Called user-related fun before setting the current storage user.")
		}
	}

	/**
	 * Internal: Triggers a callback.
	 *
	 * All callback funs are given the storage handler instance as their
	 * one and only argument.
	 *
	 * @param string cbName The name of the callback.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 */
	protected fun _triggerCallback(cbName: String) {
		// Reject anything that isn"t in our list of VALID callbacks.
		if (!SUPPORTED_CALLBACKS.contains(cbName)) {
			throw SettingsException("The string \"$cbName\" is not a valid callback name.")
		}

		// Trigger the callback with a reference to our StorageHandler instance.
		if ( !_callbacks[cbName].isBlank() ) {
			try {
				_callbacks[cbName](this)
			} catch (e: Exception) {
				// Re-wrap anything that isn"t already a SettingsException.
				if (e !is SettingsException) {
					e = SettingsException(e.message)
				}

				throw e // Re-throw
			}
		}
	}

	/**
	 * Process and save experiments.
	 *
	 * @param array experiments
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 *
	 * @return array A list of "good" experiments.
	 */
	fun setExperiments(experiments: MutableMap<String, String>): MutableMap<String, String> {
		val filtered = mutableMapOf<String, String>()
		for(key in EXPERIMENT_KEYS) {
			if ( !(experiments.contains(key)) ) {
				continue
			}
			filtered[key] = experiments[key] as String
		}
		set("experiments", _packJson(filtered))

		return filtered
	}

	/**
	 * Return saved experiments.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 *
	 * @return array
	 */
	fun getExperiments() {
		return _unpackJson( get("experiments") as String, true)
	}

	/**
	 * Save rewrite rules.
	 *
	 * @param array rules
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 */
	fun setRewriteRules(rules: MutableMap<String, String>) {
		set("zr_rules", _packJson(rules))
	}

	/**
	 * Return saved rewrite rules.
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 *
	 * @return array
	 */
	fun getRewriteRules():MutableMap<String,String> {
		return _unpackJson( get("zr_rules") as String, true)
	}

	/**
	 * Save FBNS authorization.
	 *
	 * @param AuthInterface auth
	 */
	fun setFbnsAuth(auth: AuthInterface) {
		set("fbns_auth", auth)
	}

	/**
	 * Get FBNS authorization.
	 *
	 * Will restore previously saved auth details if they exist. Otherwise it
	 * creates random authorization details.
	 *
	 * @return AuthInterface
	 */
	fun getFbnsAuth(): AuthInterface{
		val result = DeviceAuth()

		try {
			result.read(get("fbns_auth"))
		} catch (e: Exception) {}

		return result
	}

	/**
	 * Pack data as JSON, deflating it when it saves some space.
	 *
	 * @param array|object data
	 *
	 * @return string
	 */
	protected fun _packJson(data: Map<String,String>): String {
		val json: String = json_encode(data)
		val gzipped: String = zlib_encode(json, ZLIB_ENCODING_DEFLATE, 9).toString().base64_encode()
		// We must compare gzipped with double encoded JSON.
		val doubleJson: String = json_encode(json)
		return if (doubleJson.length > gzipped.length) {
			"Z$gzipped"
		} else {
			"J$json"
		}
	}

	/**
	 * Unpacks data from JSON encoded string, inflating it when necessary.
	 *
	 * @param string packed
	 * @param bool   assoc
	 *
	 * @return array|object
	 */
	protected fun _unpackJson(packedRE: String, assoc: Boolean = true) {
		if (packedRE === null || packedRE === "") {
			return if (assoc) [] else stdClass()
		}
		val format = packedRE[0]
		var packed = packedRE.substring(1)
		val json: String
		var data

		try {
			when (format) {
				'Z' -> {
					packed = packed.base64_decode()
					if (packed == "") {
						throw RuntimeException("Invalid Base64 encoded string.")
					}
					json = zlib_decode(packed)
					if (json == "") {
						throw RuntimeException("Invalid zlib encoded string.")
					}
				}
				'J'  -> json = packed
				else -> throw RuntimeException("Invalid packed type.")
			}
			data = json_decode(json, assoc)
			if (assoc && !is_array(data)) {
				throw RuntimeException("JSON is not an array.")
			}
			if (!assoc && !is_object(data)) {
				throw RuntimeException("JSON is not an object.")
			}
		} catch (e: RuntimeException) {
			data = if (assoc) [] else stdClass()
		}

		return data
	}
}
