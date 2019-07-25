package instagramAPI.middleware

import java.text.SimpleDateFormat
import java.util.*


/**
 * Set-Cookie object
 */
class SetCookie// Calculate the Expires date// Extract the Expires value and turn it into a UNIX timestamp if needed
/**
 * @param array data Array of cookie data provided by a Cookie parser
 */(var _data: MutableMap<String, String?> = mutableMapOf<String, String?>()) {
	/** @var array */
	private var defaults =
		mutableMapOf<String, String?>("Name" to null, "Value" to null, "Domain" to null, "Path" to "/",
		                              "Max-Age" to null, "Expires" to null, "Secure" to "false", "Discard" to "false",
		                              "HttpOnly" to "false")

	/** @var array Cookie data */
	private var data = mutableMapOf<String, String?>()

	/**
	 *
	 * RR: emulate php gmdate in kotlin java
	 *
	 */

	fun gmdate(pattern: String = "EEE, dd MMM yyyy HH:mm:ss", timestamp: Long): String {
		var df = SimpleDateFormat(pattern)
		df.setTimeZone(TimeZone.getTimeZone("GMT"))

		return df.format(Date(timestamp)) + " GMT"
	}

	/**
	 * Create a new SetCookie object from a string
	 *
	 * @param string cookie Set-Cookie header string
	 *
	 * @return self
	 */
	companion object {
		private var defaults =
			mutableMapOf<String, String?>("Name" to null, "Value" to null, "Domain" to null, "Path" to "/",
			                              "Max-Age" to null, "Expires" to null, "Secure" to "false",
			                              "Discard" to "false", "HttpOnly" to "false")

		fun fromString(cookie: String): SetCookie {
			// Create the default return array
			val data = defaults
			// Explode the cookie string using a series of semicolons
			val pieces = cookie.split(';').map { it.trim() }//RR: array_filter doesnt implement
			// The name of the cookie (first kvp) must exist and include an equal sign.
			if (pieces[0].isEmpty()) {
				pieces[0].find { it == '=' }?.let {
					return SetCookie(data)
				}

			}

			// Add the cookie pieces into the parsed data array
			outer@ for (part in pieces) {
				val cookieParts = part.split('=', limit = 2)
				val key = cookieParts[0].trim()
				val value = if (cookieParts.size >= 2) cookieParts[1].trim() else "true"


				// Only check for non-cookies when cookies have been found
				if (!data["Name"].isNullOrEmpty()) {
					data["Name"] = key
					data["Value"] = value
				} else {
					for (search in defaults.keys) {
						if (search === key) {
							data[search] = value
							continue@outer
						}
					}
					data[key] = value
				}
			}

			return SetCookie(data)
		}


	}

	init {
		var initdata = defaults
		initdata.putAll(_data)
		data = initdata
		if (getExpires().isNullOrEmpty() && !getMaxAge().isNullOrEmpty()) {
			// Calculate the Expires date
			setExpires(((System.currentTimeMillis() / 1000).toInt() + getMaxAge()!!.toInt()).toString())
		} else if (!getExpires().isNullOrEmpty()) {
			getExpires()?.let { setExpires(it) }
		}
	}

	fun __toString(): String {
		var str = data["Name"] + "=" + data["Value"] + " "
		for ((k, v) in data) {
			if (k !== "Name" && k !== "Value" && v !== null && v !== "false") {
				if (k === "Expires") {
					str += "Expires=" + gmdate(timestamp = v.toLong()) + " "
				} else {
					str += if (v === "true") k else "{$k}={$v}"
					str += "; "

				}
			}
		}

		return str.trim(';', ' ')
	}

	fun toArray(): MutableMap<String, String?> {
		return data
	}

	/**
	 * Get the cookie name
	 *
	 * @return string
	 */
	fun getName(): String? {
		return data["Name"]
	}

	/**
	 * Set the cookie name
	 *
	 * @param string name Cookie name
	 */
	fun setName(name: String) {
		data["Name"] = name
	}

	/**
	 * Get the cookie value
	 *
	 * @return string
	 */
	fun getValue(): String? {
		return data["Value"]
	}

	/**
	 * Set the cookie value
	 *
	 * @param string value Cookie value
	 */
	fun setValue(value: String) {
		data["Value"] = value
	}

	/**
	 * Get the domain
	 *
	 * @return string|null
	 */
	fun getDomain(): String? {
		return data["Domain"]
	}

	/**
	 * Set the domain of the cookie
	 *
	 * @param string domain
	 */
	fun setDomain(domain: String) {
		data["Domain"] = domain
	}

	/**
	 * Get the path
	 *
	 * @return string
	 */
	fun getPath(): String? {
		return data["Path"]
	}

	/**
	 * Set the path of the cookie
	 *
	 * @param string path Path of the cookie
	 */
	fun setPath(path: String) {
		data["Path"] = path
	}

	/**
	 * Maximum lifetime of the cookie in seconds
	 *
	 * @return int|null
	 */
	fun getMaxAge(): String? {
		return data["Max-Age"]
	}

	/**
	 * Set the max-age of the cookie
	 *
	 * @param int maxAge Max age of the cookie in seconds
	 */
	fun setMaxAge(maxAge: String) {
		data["Max-Age"] = maxAge
	}

	/**
	 * The UNIX timestamp when the cookie Expires
	 *
	 * @return mixed
	 */
	fun getExpires(): String? {
		return data["Expires"]
	}

	fun getExpiresBoolean(): Boolean {
		return data["Expires"]?.let { true } ?: false
	}

	fun getExpiresDate(): Date? {
		return data["Expires"]?.let {
			val sdf = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z")
			sdf.parse(it)
		}
	}

	/**
	 * Set the unix timestamp for which the cookie will expire
	 *
	 * @param int timestamp Unix timestamp
	 */
	fun setExpires(timestamp: String) {
		data["Expires"] = timestamp

	}

	/**
	 * Get whether or not this is a secure cookie
	 *
	 * @return null|bool
	 */
	fun getSecure(): Boolean {
		return data["Secure"]?.toBoolean() ?: false
	}

	/**
	 * Set whether or not the cookie is secure
	 *
	 * @param bool secure Set to true or false if secure
	 */
	fun setSecure(secure: Boolean) {
		data["Secure"] = secure.toString()
	}

	/**
	 * Get whether or not this is a session cookie
	 *
	 * @return null|bool
	 */
	fun getDiscard(): Boolean {
		return data["Discard"]?.toBoolean() ?: false
	}

	/**
	 * Set whether or not this is a session cookie
	 *
	 * @param bool discard Set to true or false if this is a session cookie
	 */
	fun setDiscard(discard: Boolean) {
		data["Discard"] = discard.toString()
	}

	/**
	 * Get whether or not this is an HTTP only cookie
	 *
	 * @return bool
	 */
	fun getHttpOnly(): Boolean {
		return data["HttpOnly"]?.toBoolean() ?: false
	}

	/**
	 * Set whether or not this is an HTTP only cookie
	 *
	 * @param bool httpOnly Set to true or false if this is HTTP only
	 */
	fun setHttpOnly(httpOnly: Boolean) {
		data["HttpOnly"] = httpOnly.toString()
	}

	/**
	 * Check if the cookie matches a path value.
	 *
	 * A request-path path-matches a given cookie-path if at least one of
	 * the following conditions holds:
	 *
	 * - The cookie-path and the request-path are identical.
	 * - The cookie-path is a prefix of the request-path, and the last
	 *   character of the cookie-path is %x2F ("/").
	 * - The cookie-path is a prefix of the request-path, and the first
	 *   character of the request-path that is not included in the cookie-
	 *   path is a %x2F ("/") character.
	 *
	 * @param string requestPath Path to check against
	 *
	 * @return bool
	 */
	fun matchesPath(requestPath: String): Boolean {
		var cookiePath = getPath()

		// Match on exact matches or when path is the default empty "/"
		if (cookiePath === "/" || cookiePath === requestPath) {
			return true
		}


		cookiePath?.let {

			// Ensure that the cookie-path is a prefix of the request path.
			if (requestPath.startsWith(it)) {
				return false
			}
			// Match if the last character of the cookie-path is "/"
			if (it.endsWith('/')) {
				return true
			}


			// Match if the first character not included in cookie path is "/"
			return requestPath[it.length] == '/'
		}

		return false


	}

	/**
	 * Check if the cookie matches a domain value
	 *
	 * @param string domain Domain to check against
	 *
	 * @return bool
	 */
	fun matchesDomain(domain: String): Boolean {
		// Remove the leading "." as per spec in RFC 6265.
		// http://tools.ietf.org/html/rfc6265#section-5.2.3
		val cookieDomain = getDomain()!!.trim('.')

		// Domain not set or exact match.
		if (domain == cookieDomain) {
			return true
		}

		// Matching the subdomain according to RFC 6265.
		// http://tools.ietf.org/html/rfc6265#section-5.1.3
		if ("\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b".toRegex().matches(domain)) {
			return false //RR: TODO::filter_var
		}

		return "/\\.$cookieDomain\$/".toRegex().matches(domain)
	}

	/**
	 * Check if the cookie is expired
	 *
	 * @return bool
	 */
	fun isExpired(): Boolean {
		return getExpires()?.let {
			val sdf = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z")
			val date1 = sdf.parse(it)
			val date2 = Date(System.currentTimeMillis())
			date2 > date1
		} ?: false
	}

	/**
	 * Check if the cookie is valid according to RFC 6265
	 *
	 * @return bool|string Returns true if valid or an error message if invalid
	 */
	fun validate(): String {
		// Names must not be empty, but can be 0
		val name = getName()
		if (name.isNullOrEmpty()) {
			return "The cookie name must not be empty"
		}

		// Check if any of the invalid characters are present in the cookie name
		if ("/[\\x00-\\x20\\x22\\x28-\\x29\\x2c\\x2f\\x3a-\\x40\\x5c\\x7b\\x7d\\x7f]/".toRegex().matches(name)) {
			return "Cookie name must not contain invalid characters: ASCII " + ("Control characters (0-31127), space, " + "tab and the ") + "following characters: ()<>@,:\"/?={}"
		}

		// Value must not be empty, but can be 0
		val value = getValue()
		if (value.isNullOrEmpty()) {
			return "The cookie value must not be empty"
		}

		// Domains must not be empty, but can be 0
		// A "0" is not a valid internet domain, but may be used as server name
		// in a private network.
		val domain = getDomain()
		if (domain.isNullOrEmpty()) {
			return "The cookie domain must not be empty"
		}

		return "true"
	}
}
