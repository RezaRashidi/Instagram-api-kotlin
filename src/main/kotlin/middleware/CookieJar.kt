package instagramAPI.middleware

import com.github.kittinunf.fuel.core.Request



/**
 * Cookie jar that stores cookies as an array
 */
class CookieJar
/**
 * @param bool strictMode   Set to true to throw exceptions when invalid
 *                           cookies are added to the cookie jar.
 * @param array cookieArray Array of SetCookie objects or a hash of
 *                           arrays that can be used with the SetCookie
 *                           constructor
 */(private var strictMode: Boolean = false, cookieArray: MutableList<SetCookie> = mutableListOf()) {
	/** @var SetCookie[] Loaded cookie data */
	private var cookies = mutableListOf<SetCookie>()


	init {
		strictMode = strictMode
		for (cookie in cookieArray) {
//			if (!(cookie instanceof SetCookie)) {
//				cookie = new SetCookie (cookie)
//			}
			setCookie(cookie)
		}
	}

	/**
	 * Create a new Cookie jar from an associative array and domain.
	 *
	 * @param array  cookies Cookies to create the jar from
	 * @param string domain  Domain to set the cookies to
	 *
	 * @return self
	 */
	fun fromArray(cookies: MutableMap<String, String>, domain: Boolean): CookieJar {
		var cookieJar = CookieJar()
		for ((name, value) in cookies) {
			cookieJar.setCookie(SetCookie(
				mutableMapOf("Domain" to domain.toString(), "Name" to name, "Value" to value, "Discard" to "true")))
		}

		return cookieJar
	}

//	/**
//	 * @deprecated
//	 */
//	fun getCookieValue(value) {
//		return value
//	}

	/**
	 * Evaluate if this cookie should be persisted to storage
	 * that survives between requests.
	 *
	 * @param SetCookie cookie Being evaluated.
	 * @param bool allowSessionCookies If we should persist session cookies
	 * @return bool
	 */
	fun shouldPersist(cookie: SetCookie, allowSessionCookies: Boolean = false): Boolean {
		if (cookie.getExpiresBoolean() || allowSessionCookies) {
			if (!cookie.getDiscard()) return true

		}

		return false
	}

	/**
	 * Finds and returns the cookie based on the name
	 *
	 * @param string name cookie name to search for
	 * @return SetCookie|null cookie that was found or null if not found
	 */
	fun getCookieByName(name: String): SetCookie? {
		// don"t allow a null name
		var cookieset: SetCookie? = null
		for (cookie in cookies) {
			if (cookie.getName() !== null && cookie.getName() == name) {
				cookieset = cookie
				break

			}

		}
		return cookieset

	}

//	fun toArray() {
//
//		return array_map(fun(SetCookie cookie) {
//			return cookie.toArray()
//		}, getIterator().getArrayCopy())
//	}

	fun clear(domain: String? = null, path: String? = null, name: String? = null) {
		if (domain.isNullOrBlank()) {
			cookies = mutableListOf<SetCookie>()
			return
		} else if (path.isNullOrBlank()) {
			cookies = cookies.filter {
				!it.matchesDomain(domain)
			} as MutableList<SetCookie>

		} else if (name.isNullOrBlank()) {
			cookies = cookies.filter {
				!(it.matchesPath(path) && it.matchesDomain(domain))
			} as MutableList<SetCookie>
		} else {
			cookies = cookies.filter {
				!(it.getName() == name && it.matchesPath(path) && it.matchesDomain(domain))
			} as MutableList<SetCookie>
		}
	}

	fun clearSessionCookies() {
		cookies = cookies.filter {
			!it.getDiscard() && it.getExpiresBoolean()
		} as MutableList<SetCookie>
	}

	fun setCookie(cookie: SetCookie): Boolean {
		// If the name string is empty (but not 0), ignore the set-cookie
		// string entirely.
		var name = cookie.getName()
		if (name.isNullOrBlank() && name !== "0") {
			return false
		}

		// Only allow cookies with set and valid domain, name, value
		val result = cookie.validate()
		if (result !== "true") {
			if (strictMode) {
				throw  RuntimeException("Invalid cookie: " + result)
			} else {
				removeCookieIfEmpty(cookie)
				return false
			}
		}

		// Resolve conflicts with previously set cookies
		for (c in cookies) {

			// Two cookies are identical, when their path, and domain are
			// identical.
			if (c.getPath() != cookie.getPath() || c.getDomain() != cookie.getDomain() || c.getName() != cookie.getName()) {
				continue
			}

			// The previously set cookie is a discard cookie and this one is
			// not so allow the new cookie to be set
			if (!cookie.getDiscard() && c.getDiscard()) {
				cookies.remove(c)
				continue
			}

			// If the new cookie"s expiration is further into the future, then
			// replace the old cookie
			if (cookie.getExpiresDate()!! > c.getExpiresDate()) {
				cookies.remove(c)
				continue
			}

			// If the value has changed, we better change it
			if (cookie.getValue() !== c.getValue()) {
				cookies.remove(c)
				continue
			}

			// The cookie exists, so no need to continue
			return false
		}

		cookies.add(cookie)

		return true
	}

	fun count(): Int {
		return cookies.size
	}

//	fun getIterator() {
//		return new \ArrayIterator(array_values(cookies))
//	}

	fun extractCookies(request: Request, response: com.github.kittinunf.fuel.core.Response) {
		val cookieHeader = response.header("Set-Cookie")
		if (cookieHeader.isNullOrEmpty()) {
			for (cookie in cookieHeader) {
				val sc = SetCookie.fromString(cookie)
				if (sc.getDomain().isNullOrBlank()) {
					sc.setDomain(request.url.host)
				}
				if (0 != sc.getPath()?.indexOfLast { it == '/' }) {
					sc.setPath(getCookiePathFromRequest(request))
				}
				setCookie(sc)
			}
		}
	}

	/**
	 * Computes cookie path following RFC 6265 section 5.1.4
	 *
	 * @link https://tools.ietf.org/html/rfc6265#section-5.1.4
	 *
	 * @param RequestInterface request
	 * @return string
	 */
	private fun getCookiePathFromRequest(request: Request): String {
		val uriPath = request.url.path
		if ("" === uriPath) {
			return "/"
		}
		if (0 != uriPath.indexOfLast { it == '/' }) {
			return "/"
		}
		if ("/" === uriPath) {
			return "/"
		}
		val lastSlashPos = uriPath.indexOfLast { it == '/' }
		if (0 == lastSlashPos) {
			return "/"
		}

		return uriPath
	}

	fun withCookieHeader(request: Request): Request {
		val values = mutableListOf<String>()
		val uri = request.url
		val scheme = uri.protocol
		val host = uri.host
		val path = uri.path ?: "/"

		for (cookie in cookies) {
			if (cookie.matchesPath(path) && cookie.matchesDomain(
					host) && !cookie.isExpired() && (!cookie.getSecure() || scheme === "https")
			) {
				values.add(cookie.getName() + "=" + cookie.getValue())
			}
		}

		return if (values.size == 0) request.header("Cookie", values) else request
	}

	/**
	 * If a cookie already exists and the server asks to set it again with a
	 * null value, the cookie must be deleted.
	 *
	 * @param SetCookie cookie
	 */
	private fun removeCookieIfEmpty(cookie: SetCookie) {
		val cookieValue = cookie.getValue()
		if (cookieValue.isNullOrBlank()) {
			clear(cookie.getDomain(), cookie.getPath(), cookie.getName())
		}
	}
}
