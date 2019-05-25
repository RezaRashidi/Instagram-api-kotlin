

package InstagramAPI.Devices

/**
 * Android hardware device representation.
 *
 * @author SteveJobzniak (https://github.com/SteveJobzniak)
 */
class Device : DeviceInterface
{
    /**
     * The Android version of Instagram currently runs on Android OS 2.2+.
     *
     * They may raise this requirement in the future.
     *
     * @var string
     *
     * @see https://help.instagram.com/513067452056347
     */
    val REQUIRED_ANDROID_VERSION = '2.2'

    /**
     * Which Instagram client app version this "device" is running.
     *
     * @var string
     */
    protected $_appVersion

    /**
     * Which Instagram client app version code this "device" is running.
     *
     * @var string
     */
    protected $_versionCode

    /**
     * The device user's locale, such as "en_US".
     *
     * @var string
     */
    protected $_userLocale

    /**
     * Which device string we were built with internally.
     *
     * @var string
     */
    protected $_deviceString

    /**
     * The user agent to import for this device. Built from properties.
     *
     * @var string
     */
    protected $_userAgent

    /**
     * The FB user agents to import for this device. Built from properties.
     *
     * @var array
     */
    protected $_fbUserAgents

    // Properties parsed from the device string...

    /** @var string Android SDK/API version. */
    protected $_androidVersion

    /** @var string Android release version. */
    protected $_androidRelease

    /** @var string Display DPI. */
    protected $_dpi

    /** @var string Display resolution. */
    protected $_resolution

    /** @var string Manufacturer. */
    protected $_manufacturer

    /** @var string|null Manufacturer's sub-brand (optional). */
    protected $_brand

    /** @var string Hardware MODEL. */
    protected $_model

    /** @var string Hardware DEVICE. */
    protected $_device

    /** @var string Hardware CPU. */
    protected $_cpu

    /**
     * Constructor.
     *
     * @param string      $appVersion   Instagram client app version.
     * @param string      $versionCode  Instagram client app version code.
     * @param string      $userLocale   The user's locale, such as "en_US".
     * @param string|null $deviceString (optional) The device string to attempt
     *                                  to construct from. If NULL or not a good
     *                                  device, we'll import a random good device.
     * @param bool        $autoFallback (optional) Toggle automatic fallback.
     *
     * @throws .RuntimeException If fallback is disabled and device is invalid.
     */
    public fun __construct(
        $appVersion,
        $versionCode,
        $userLocale,
        $deviceString = null,
        $autoFallback = true)
    {
        this._appVersion = $appVersion
        this._versionCode = $versionCode
        this._userLocale = $userLocale

        // import the provided device if a valid good device. Otherwise import random.
        if ($autoFallback && (!is_string($deviceString) || !GoodDevices::isGoodDevice($deviceString))) {
            $deviceString = GoodDevices::getRandomGoodDevice()
        }

        // Initialize ourselves from the device string.
        this._initFromDeviceString($deviceString)
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
    protected fun _initFromDeviceString(
        $deviceString)
    {
        if (!is_string($deviceString) || empty($deviceString)) {
            throw .RuntimeException('Device string is empty.')
        }

        // Split the device identifier into its components and verify it.
        $parts = explode(' ', $deviceString)
        if (count($parts) !== 7) {
            throw .RuntimeException(sprintf('Device string "%s" does not conform to the required device format.', $deviceString))
        }

        // Check the android version.
        $androidOS = explode('/', $parts[0], 2)
        if (version_compare($androidOS[1], self::REQUIRED_ANDROID_VERSION, '<')) {
            throw .RuntimeException(sprintf('Device string "%s" does not meet the minimum required Android version "%s" for Instagram.', $deviceString, self::REQUIRED_ANDROID_VERSION))
        }

        // Check the screen resolution.
        $resolution = explode('x', $parts[2], 2)
        $pixelCount = (int) $resolution[0] * (int) $resolution[1]
        if ($pixelCount < 2073600) { // 1920x1080.
            throw .RuntimeException(sprintf('Device string "%s" does not meet the minimum resolution requirement of 1920x1080.', $deviceString))
        }

        // Extract "Manufacturer/Brand" string into separate fields.
        $manufacturerAndBrand = explode('/', $parts[3], 2)

        // Store all field values.
        this._deviceString = $deviceString
        this._androidVersion = $androidOS[0] // "23".
        this._androidRelease = $androidOS[1] // "6.0.1".
        this._dpi = $parts[1]
        this._resolution = $parts[2]
        this._manufacturer = $manufacturerAndBrand[0]
        this._brand = (isset($manufacturerAndBrand[1])
                         ? $manufacturerAndBrand[1] : null)
        this._model = $parts[4]
        this._device = $parts[5]
        this._cpu = $parts[6]

        // Build our user agent.
        this._userAgent = UserAgent::buildUserAgent(this._appVersion, this._userLocale, this)

        this._fbUserAgents = []
    }

    // Getters for all properties...

    /** {@inheritdoc} */
    public fun getDeviceString()
    {
        return this._deviceString
    }

    /** {@inheritdoc} */
    public fun getUserAgent()
    {
        return this._userAgent
    }

    /** {@inheritdoc} */
    public fun getFbUserAgent(
        $appName)
    {
        if (!isset(this._fbUserAgents[$appName])) {
            this._fbUserAgents[$appName] = UserAgent::buildFbUserAgent(
                $appName,
                this._appVersion,
                this._versionCode,
                this._userLocale,
                this
            )
        }

        return this._fbUserAgents[$appName]
    }

    /** {@inheritdoc} */
    public fun getAndroidVersion()
    {
        return this._androidVersion
    }

    /** {@inheritdoc} */
    public fun getAndroidRelease()
    {
        return this._androidRelease
    }

    /** {@inheritdoc} */
    public fun getDPI()
    {
        return this._dpi
    }

    /** {@inheritdoc} */
    public fun getResolution()
    {
        return this._resolution
    }

    /** {@inheritdoc} */
    public fun getManufacturer()
    {
        return this._manufacturer
    }

    /** {@inheritdoc} */
    public fun getBrand()
    {
        return this._brand
    }

    /** {@inheritdoc} */
    public fun getModel()
    {
        return this._model
    }

    /** {@inheritdoc} */
    public fun getDevice()
    {
        return this._device
    }

    /** {@inheritdoc} */
    public fun getCPU()
    {
        return this._cpu
    }
}
