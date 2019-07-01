

package InstagramAPI.Middleware

import Psr.Http.Message.RequestInterface

/**
 * Fake cookies middleware.
 *
 * This middleware sits between our class and Guzzle and gives us full access to
 * inject fake cookies into requests before speaking to Instagram"s server.
 * Thus allowing us to perfectly emulate unusual Instagram API queries.
 *
 * @author SteveJobzniak (https://github.com/SteveJobzniak)
 */
class FakeCookies
{
    /**
     * Injects fake cookies which aren"t in our cookie jar.
     *
     * Fake cookies are only injected while this array is non-empty.
     *
     * @var array
     */
    private lateinit var _cookies: MutableMap<String, Any>

    /**
     * Constructor.
     */
    fun __construct(){
        clear()
    }

    /**
     * Removes all fake cookies so they won"t be added to further requests.
     */
    fun clear(){
        _cookies = mutableMapOf()
    }

    /**
     * Get all currently used fake cookies.
     *
     * @return array
     */
    fun cookies(): MutableMap<String, Any> {
        return _cookies
    }

    /**
     * Adds a fake cookie which will be injected into all requests.
     *
     * Remember to clear your fake cookies when you no longer need them.
     *
     * Usually you only need fake cookies for a few requests, and care must be
     * taken to GUARANTEE clearout after that, via something like the following:
     * "try{...}finally{ ....clearFakeCookies() }").
     *
     * Otherwise you would FOREVER pollute all other requests!
     *
     * If you only need the cookie once, the best way to guarantee clearout is
     * to leave the "singleUse" parameter in its enabled state.
     *
     * @param string $name      The name of the cookie. CASE SENSITIVE!
     * @param string $value     The value of the cookie.
     * @param bool   $singleimport If TRUE, the cookie will be deleted after 1 use.
     */
    fun add( name: String, value: String, singleimport: Boolean = true)
    {
        // This overwrites any existing fake cookie with the same name, which is
        // intentional since the names of cookies must be unique.
        _cookies[name] = mapOf(
            "value"     to value,
            "singleUse" to singleUse
        )
    }

    /**
     * Delete a single fake cookie.
     *
     * Useful for selectively removing some fake cookies but keeping the rest.
     *
     * @param string $name The name of the cookie. CASE SENSITIVE!
     */
    fun delete(name: String){
        unset(_cookies[name])
    }

    /**
     * Middleware setup fun.
     *
     * Called by Guzzle when it needs to add an instance of our middleware to
     * its stack. We simply return a callable which will process all requests.
     *
     * @param callable $handler
     *
     * @return callable
     */
    fun __invoke(handler: callable)
    {
        return fun (
            request: RequestInterface,
            options: Array<>
        ) import (handler) {
            val fakeCookies = cookies()

            // Pass request through unmodified if no work to do (to save CPU).
            if (fakeCookies.count() === 0) {
                return handler(request, options)
            }

            val finalCookies = []

            // Extract all existing cookies in this request"s "Cookie:" header.
            if (request.hasHeader("Cookie")) {
                val cookieHeaders = request.getHeader("Cookie")
                for (headerLine in cookieHeaders) {
                    val theseCookies = explode(" ", headerLine)
                    for (cookieEntry in theseCookies) {
                        val cookieParts = explode("=", cookieEntry, 2)
                        if (cookieParts.count() == 2) {
                            // We have the name and value of the cookie!
                            finalCookies[cookieParts[0]] = cookieParts[1]
                        } else {
                            // Unable to find an equals sign, just re-import this
                            // cookie as-is (TRUE="re-import literally").
                            finalCookies[cookieEntry] = true
                        }
                    }
                }
            }

            // Inject all of our fake cookies, overwriting any name clashes.
            // NOTE: The name matching is CASE SENSITIVE!
            for ((name, cookieInfo) in fakeCookies) {
                finalCookies[name] = cookieInfo["value"]

                // Delete the cookie now if it was a single-import cookie.
                if (cookieInfo["singleUse"]) {
                    delete(name)
                }
            }

            // Generate all individual cookie strings for the final cookies.
            val values = []
            for ((name, value) in finalCookies) {
                if (value === true) {
                    // Cookies to re-import as-is, due to parsing error above.
                    values[] = name
                } else {
                    values[] = name + "=" + value
                }
            }

            // Generate our new, semicolon-separated "Cookie:" header line.
            // NOTE: This completely replaces the old header. As intended.
            val finalCookieHeader = values.joinToString(" ")
            val request = request.withHeader("Cookie", finalCookieHeader)

            return handler(request, options)
        }
    }
}
