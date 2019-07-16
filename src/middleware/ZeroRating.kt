

package instagramAPI.middleware

import GuzzleHttp.Psr7.Uri
import Psr.Http.Message.RequestInterface

/**
 * Zero rating rewrite middleware.
 */
object ZeroRating
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
    private lateinit var _rules: MutableMap<String,String>

    /**
     * Constructor.
     */
    init{
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
    fun update(rules: MutableMap<String,String> = mutableMapOf()){
        this._rules = mutableMapOf()
        for ((from, to) in rules) {
            val regex = "#{$from}#"
            val test = regex.toRegex().matches("")
            if (!test ) {
                continue
            }
            _rules[regex] = strtr(to, (
                ".." to "."
            ))
        }
    }

    /**
     * middleware setup fun.
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
