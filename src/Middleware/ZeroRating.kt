

package instagramAPI.Middleware

import GuzzleHttp.Psr7.Uri
import Psr.Http.Message.RequestInterface

/**
 * Zero rating rewrite middleware.
 */
class ZeroRating
{
    /**
     * Default rewrite rules.
     *
     * @var array
     */
    val DEFAULT_REWRITE = mapOf(
        "^(https?:././)(i)(..instagram..com/.*)$" to "$1b.$2$3"
    )

    /**
     * Rewrite rules.
     *
     * @var array
     */
    private lateinit var _rules: Array

    /**
     * Constructor.
     */
    fun __construct(){
        reset()
    }

    /**
     * Reset rules to default ones.
     */
    fun reset(){
        update(DEFAULT_REWRITE)
    }

    /**
     * Update rules.
     *
     * @param array $rules
     */
    fun update(rules: Array = arrayOf()){
        _rules = []
        for ((from, to) in rules) {
            val regex = "#{$from}#"
            var test = @preg_match(regex, "")
            if (test === false) {
                continue
            }
            _rules[regex] = strtr(to, (
                ".." to "."
            ))
        }
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
    fun __invoke(handler: callable){
        return fun (
            request: RequestInterface,
            options: Array
        ) import (handler) {
            if (_rules.isEmpty()) {
                return handler(request, options)
            }

            val oldUri = request.getUri().toString()
            val uri = rewrite(oldUri)
            if (uri !== oldUri) {
                val request = request.withUri(Uri(uri))
            }

            return handler(request, options)
        }
    }

    /**
     * Do a rewrite.
     *
     * @param string $uri
     *
     * @return string
     */
    public fun rewrite(uri: String): String {
        for ((from, to) in _rules) {
            val result = @preg_replace(from, to, uri)
            if (result !is String) {
                continue
            }
            // We must break at the first succeeded replace.
            if (result !== uri) {
                return result
            }
        }

        return uri
    }
}
