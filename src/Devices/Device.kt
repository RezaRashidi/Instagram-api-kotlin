

package instagramAPI.Devices

/**
 * Android hardware device representation.
 *
 * @author SteveJobzniak (https://github.com/SteveJobzniak)
 */
class Device// import the provided device if a valid good device. Otherwise import random.

// Initialize ourselves from the device string.
/**
 * Constructor.
 *
 * @param string      $appVersion   Instagram client app version.
 * @param string      $versionCode  Instagram client app version code.
 * @param string      $userLocale   The user"s locale, such as "en_US".
 * @param string|null $deviceString (optional) The device string to attempt
 *                                  to construct from. If NULL or not a good
 *                                  device, we"ll import a random good device.
 * @param bool        $autoFallback (optional) Toggle automatic fallback.
 *
 * @throws .RuntimeException If fallback is disabled and device is invalid.
 */(appVersion: String, versionCode: String, userLocale: String, deviceString: String? = null,
    autoFallback: Boolean = true) : DeviceInterface{
    /**
     * The Android version of Instagram currently runs on Android OS 2.2+.
     *
     * They may raise this requirement in the future.
     *
     * @var string
     *
     * @see https://help.instagram.com/513067452056347
     */
    val REQUIRED_ANDROID_VERSION: String = "2.2"

    /**
     * Which Instagram client app version this "device" is running.
     *
     * @var string
     */
    protected lateinit var _appVersion: String = appVersion

    /**
     * Which Instagram client app version code this "device" is running.
     *
     * @var string
     */
    protected lateinit var _versionCode: String = versionCode

    /**
     * The device user"s locale, such as "en_US".
     *
     * @var string
     */
    protected lateinit var _userLocale: String = userLocale

    /**
     * Which device string we were built with internally.
     *
     * @var string
     */
    protected lateinit var _deviceString: String

    /**
     * The user agent to import for this device. Built from properties.
     *
     * @var string
     */
    protected lateinit var _userAgent: String

    /**
     * The FB user agents to import for this device. Built from properties.
     *
     * @var array
     */
    protected lateinit var _fbUserAgents: Array

    // Properties parsed from the device string...

    /** @var string Android SDK/API version. */
    protected lateinit var _androidVersion: String

    /** @var string Android release version. */
    protected lateinit var _androidRelease: String

    /** @var string Display DPI. */
    protected lateinit var _dpi: String

    /** @var string Display resolution. */
    protected lateinit var _resolution: String

    /** @var string Manufacturer. */
    protected lateinit var _manufacturer: String

    /** @var string|null Manufacturer"s sub-brand (optional). */
    protected var _brand: String? = null

    /** @var string Hardware MODEL. */
    protected lateinit var _model: String

    /** @var string Hardware DEVICE. */
    protected lateinit var _device: String

    /** @var string Hardware CPU. */
    protected lateinit var _cpu: String

    init {
        if (autoFallback && (deviceString !is String || !GoodDevices.isGoodDevice(deviceString))) {
            deviceString = GoodDevices.getRandomGoodDevice()
        }
        _initFromDeviceString(deviceString)
    }

    /**
     * Parses a device string into its component parts and sets internal fields.
     *
     * Does no validation to make sure the string is one of the good devices.
     *
     * @param string $deviceString
     *
     * @throws .RuntimeException If the device string is invalid.
     */
    protected fun _initFromDeviceString(deviceString: String){
        if (deviceString !is String || deviceString.isEmpty()) {
            throw RuntimeException("Device string is empty.")
        }

        // Split the device identifier into its components and verify it.
        val parts = explode(" ", deviceString)
        if (parts.count() !== 7) {
            throw RuntimeException("Device string \"$deviceString\" does not conform to the required device format.")
        }

        // Check the android version.
        val androidOS = explode("/", parts[0], 2)
        if (version_compare(androidOS[1], REQUIRED_ANDROID_VERSION, "<")) {
            throw RuntimeException("Device string \"$deviceString\" does not meet the minimum required Android version \"$REQUIRED_ANDROID_VERSION\" for Instagram.")
        }

        // Check the screen resolution.
        val resolution = explode("x", parts[2], 2)
        val pixelCount = resolution[0].toInt() * resolution[1].toInt()
        if (pixelCount < 2073600) { // 1920x1080.
            throw RuntimeException("Device string \"$deviceString\" does not meet the minimum resolution requirement of 1920x1080.")
        }

        // Extract "Manufacturer/Brand" string into separate fields.
        val manufacturerAndBrand = explode("/", parts[3], 2)

        // Store all field values.
        _deviceString   = deviceString
        _androidVersion = androidOS[0] // "23".
        _androidRelease = androidOS[1] // "6.0.1".
        _dpi            = parts[1]
        _resolution     = parts[2]
        _manufacturer   = manufacturerAndBrand[0]
        _brand  = ( if(!(manufacturerAndBrand[1].isBlank())) manufacturerAndBrand[1] else null)
        _model  = parts[4]
        _device = parts[5]
        _cpu    = parts[6]

        // Build our user agent.
        _userAgent = UserAgent.buildUserAgent(_appVersion, _userLocale, this)

        _fbUserAgents = []
    }

    // Getters for all properties...

    /** {@inheritdoc} */
    fun getDeviceString(): String {
        return _deviceString
    }

    /** {@inheritdoc} */
    fun getUserAgent(): String {
        return _userAgent
    }

    /** {@inheritdoc} */
    fun getFbUserAgent(appName){
        if ( _fbUserAgents[appName].isBlank() ) {
            _fbUserAgents[appName] = UserAgent.buildFbUserAgent(
                appName,
                _appVersion,
                _versionCode,
                _userLocale,
                this
            )
        }

        return _fbUserAgents[appName]
    }

    /** {@inheritdoc} */
    fun getAndroidVersion(): String {
        return _androidVersion
    }

    /** {@inheritdoc} */
    fun getAndroidRelease(): String {
        return _androidRelease
    }

    /** {@inheritdoc} */
    fun getDPI(): String {
        return _dpi
    }

    /** {@inheritdoc} */
    fun getResolution(): String {
        return _resolution
    }

    /** {@inheritdoc} */
    fun getManufacturer(): String {
        return _manufacturer
    }

    /** {@inheritdoc} */
    fun getBrand(): String? {
        return _brand
    }

    /** {@inheritdoc} */
    fun getModel(): String {
        return _model
    }

    /** {@inheritdoc} */
    fun getDevice(): String {
        return _device
    }

    /** {@inheritdoc} */
    fun getCPU(): String {
        return _cpu
    }
}
