

package InstagramAPI.Devices

interface DeviceInterface
{
    /**
     * Get the device identity string.
     *
     * @return string
     */
    public fun getDeviceString()

    /**
     * Get the HTTP user-agent string.
     *
     * @return string
     */
    public fun getUserAgent()

    /**
     * Get the Facebook user-agent string.
     *
     * @param string $appName Application name.
     *
     * @return string
     */
    public fun getFbUserAgent(
        $appName)

    /**
     * Get the Android SDK/API version.
     *
     * @return string
     */
    public fun getAndroidVersion()

    /**
     * Get the Android release version.
     *
     * @return string
     */
    public fun getAndroidRelease()

    /**
     * Get the display DPI (with "dpi" suffix).
     *
     * @return string
     */
    public fun getDPI()

    /**
     * Get the display resolution (width x height).
     *
     * @return string
     */
    public fun getResolution()

    /**
     * Get the manufacturer.
     *
     * @return string
     */
    public fun getManufacturer()

    /**
     * Get the brand (optional).
     *
     * @return string|null
     */
    public fun getBrand()

    /**
     * Get the hardware model.
     *
     * @return string
     */
    public fun getModel()

    /**
     * Get the hardware device code.
     *
     * @return string
     */
    public fun getDevice()

    /**
     * Get the hardware CPU code.
     *
     * @return string
     */
    public fun getCPU()
}
