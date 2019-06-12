package InstagramAPI.Devices

import InstagramAPI.Constants
import kotlin.math.roundToInt

/**
 * Android device User-Agent builder.
 *
 * @author SteveJobzniak (https://github.com/SteveJobzniak)
 */
object UserAgent {
	/**
	 * How to format the user agent string.
	 *
	 * @var string
	 */
	val USER_AGENT_FORMAT: String = "Instagram %s Android (%s/%s %s %s %s %s %s %s %s %s)"

	/**
	 * Generates a User Agent string from a DeviceInterface.
	 *
	 * @param string          appVersion Instagram client app version.
	 * @param string          userLocale The user"s locale, such as "en_US".
	 * @param DeviceInterface device
	 *
	 * @return string
	 */
	fun buildUserAgent(appVersion: String, userLocale: String, device: DeviceInterface): String {
		// Build the appropriate "Manufacturer" or "Manufacturer/Brand" string.
		var manufacturerWithBrand = device.getManufacturer()
		if (device.getBrand() !== null) {
			manufacturerWithBrand += "/"+device.getBrand()
		}

		// Generate the final User-Agent string.
		return USER_AGENT_FORMAT.format(appVersion, // App version ("27.0.0.7.97").
		                                device.getAndroidVersion(), device.getAndroidRelease(), device.getDPI(),
		                                device.getResolution(), manufacturerWithBrand, device.getModel(),
		                                device.getDevice(), device.getCPU(), userLocale, // Locale ("en_US").
		                                Constants.VERSION_CODE)
	}

	/**
	 * Escape string for Facebook User-Agent string.
	 *
	 * @param string
	 *
	 * @return string
	 */
	fun _escapeFbString(string: String?): String {
		var result = ""
		if (string != null) {
			for (char in string) {
				if (char == '&') {
					result += "&amp"
				} else if (char.toInt() < " ".toInt() || char.toInt() > "~".toInt()) {
					result += "&#%d".format(char.toInt())
				} else {
					result += char
				}
			}
		}
		return result.replace('/', '-').replace(';', '-')


	}

	/**
	 * Generates a FB User Agent string from a DeviceInterface.
	 *
	 * @param string          appName     Application name.
	 * @param string          appVersion  Instagram client app version.
	 * @param string          versionCode Instagram client app version code.
	 * @param string          userLocale  The user"s locale, such as "en_US".
	 * @param DeviceInterface device
	 *
	 * @throws .InvalidArgumentException If the device parameter is invalid.
	 *
	 * @return string
	 */
	fun buildFbUserAgent(appName: String, appVersion: String, versionCode: String, userLocale: String,
	                     device: DeviceInterface):String {
		val width = device.getResolution().split("x")[0]
		val height = device.getResolution().split("x")[1]
		var density = (device.getDPI().replace("dpi", "").toDouble() / 160).roundToInt()
		var result = mutableMapOf<String, String>("FBAN" to appName, "FBAV" to appVersion, "FBBV" to versionCode,
		                                                  "FBDM" to "{density=%.1f,width=%d,height=%d}".format(density, width, height),
		                                                  "FBLC" to userLocale, "FBCR" to "", // We don"t have cellular.
		                                                  "FBMF" to _escapeFbString(device.getManufacturer()),
		                                                  "FBBD" to _escapeFbString(
			                                   if (device.getBrand().isNullOrBlank()) device.getBrand() else device.getManufacturer()),
		                                                  "FBPN" to Constants.PACKAGE_NAME,
		                                                  "FBDV" to _escapeFbString(device.getModel()),
		                                                  "FBSV" to _escapeFbString(device.getAndroidRelease()),
		                                                  "FBLR" to "0", // android.hardware.ram.low
		                                                  "FBBK" to "1", // val (at least in 10.12.0).
		                                                  "FBCA" to _escapeFbString(GoodDevices.CPU_ABI))

		var ValueJoin:String =""
		for ((key,value) in result) {
				result[key]="%s/%s".format(key, value)
				ValueJoin += result[key] + ";"
		}

		// Trailing semicolon is essential.
		return "[$ValueJoin]"
	}
}

