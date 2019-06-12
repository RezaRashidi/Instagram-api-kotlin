package InstagramAPI.Devices

interface DeviceInterface {
	/**
	 * Get the device identity string.
	 *
	 * @return string
	 */
	fun getDeviceString():String

	/**
	 * Get the HTTP user-agent string.
	 *
	 * @return string
	 */
	fun getUserAgent():String

	/**
	 * Get the Facebook user-agent string.
	 *
	 * @param string appName Application name.
	 *
	 * @return string
	 */
	fun getFbUserAgent(appName:String):String

	/**
	 * Get the Android SDK/API version.
	 *
	 * @return string
	 */
	fun getAndroidVersion():String

	/**
	 * Get the Android release version.
	 *
	 * @return string
	 */
	fun getAndroidRelease():String

	/**
	 * Get the display DPI (with "dpi" suffix).
	 *
	 * @return string
	 */
	fun getDPI():String

	/**
	 * Get the display resolution (width x height).
	 *
	 * @return string
	 */
	fun getResolution():String

	/**
	 * Get the manufacturer.
	 *
	 * @return string
	 */
	fun getManufacturer():String

	/**
	 * Get the brand (optional).
	 *
	 * @return string|null
	 */
	fun getBrand():String?

	/**
	 * Get the hardware model.
	 *
	 * @return string
	 */
	fun getModel():String

	/**
	 * Get the hardware device code.
	 *
	 * @return string
	 */
	fun getDevice():String

	/**
	 * Get the hardware CPU code.
	 *
	 * @return string
	 */
	fun getCPU():String
}
