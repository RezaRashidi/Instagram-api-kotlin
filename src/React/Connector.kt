

package instagramAPI.React

import Clue.React.HttpProxy.ProxyConnector as HttpConnectProxy
import Clue.React.Socks.Client as SocksProxy
import GuzzleHttp.Psr7.Uri
import React.EventLoop.LoopInterface
import React.Promise.PromiseInterface
import React.Promise.RejectedPromise
import React.Socket.Connector as SocketConnector
import React.Socket.ConnectorInterface
import React.Socket.SecureConnector

class Connector : ConnectorInterface{
    /**
     * @var Instagram
     */
    protected lateinit var _instagram: Instagram

    /**
     * @var LoopInterface
     */
    protected lateinit var _loop: LoopInterface

    /**
     * @var ConnectorInterface[]
     */
    protected lateinit var _connectors: ConnectorInterface

    /**
     * Constructor.
     *
     * @param Instagram     $instagram
     * @param LoopInterface $loop
     */
    constructor(instagram: Instagram, loop: LoopInterface){
        _instagram = instagram
        _loop = loop

        _connectors = []
    }

    /** {@inheritdoc} */
    fun connect(uri){
        var uriObj = Uri(uri)
        val host = _instagram.client.zeroRating().rewrite(uriObj.getHost())
        uriObj = uriObj.withHost(host)
        if (_connectors[host].isBlank()) {
            try {
                _connectors[host] = _getSecureConnector(
                    _getSecureContext(_instagram.getVerifySSL()),
                    _getProxyForHost(host, _instagram.getProxy())
                )
            } catch (e: Exception) {
                return RejectedPromise(e)
            }
        }
        val niceUri = ltrim(uriObj as String, "/")

        /** @var PromiseInterface $promise */
        return _connectors[host].connect(niceUri)
    }

    /**
     * Create a secure connector for given configuration.
     *
     * @param array       $secureContext
     * @param string|null $proxyAddress
     *
     * @throws  IllegalArgumentException
     *
     * @return ConnectorInterface
     */
    protected fun _getSecureConnector(array secureContext = [], proxyAddress: String? = null){
        var connector = SocketConnector(this._loop, mapOf(
            "tcp"     to true,
            "tls"     to false,
            "unix"    to false,
            "dns"     to true,
            "timeout" to true
        ))

        if (proxyAddress !== null) {
            connector = _wrapConnectorIntoProxy(connector, proxyAddress, secureContext)
        }

        return SecureConnector(connector, _loop, secureContext)
    }

    /**
     * Get a proxy address (if any) for the host based on the proxy config.
     *
     * @param string $host        Host.
     * @param mixed  $proxyConfig Proxy config.
     *
     * @throws  IllegalArgumentException
     *
     * @return string|null
     *
     * @see http://docs.guzzlephp.org/en/stable/request-options.html#proxy
     */
    protected fun _getProxyForHost(host: String, proxyConfig = null): String?{
        // Empty config => no proxy.
        if (proxyConfig.isEmpty()) {
            return
        }

        // Plain string => return it.
        if (!is_array(proxyConfig)) {
            return proxyConfig
        }

        // HTTP proxies do not have CONNECT method.
        if (proxyConfig["https"].isBlank()) {
            throw  IllegalArgumentException("No proxy with CONNECT method found.")
        }

        // Check exceptions.
        if (!(proxyConfig["no"].isBlank()) && GuzzleHttp.is_host_in_noproxy(host, proxyConfig["no"])) {
            return
        }

        return proxyConfig["https"]
    }

    /**
     * Parse given SSL certificate verification and return a secure context.
     *
     * @param mixed $config
     *
     * @throws  IllegalArgumentException
     * @throws .RuntimeException
     *
     * @return array
     *
     * @see http://docs.guzzlephp.org/en/stable/request-options.html#verify
     */
    protected fun _getSecureContext(config): MutableMap<String, Any> {
        val context = mutableMapOf<String, Any>()
        if (config === true) {
            // PHP 5.6 or greater will find the system cert by default. When
            // < 5.6, import the Guzzle bundled cacert.
            if (PHP_VERSION_ID < 50600) {
                context["cafile"] = GuzzleHttp.default_ca_bundle()
            }
        } else if (config is String) {
            context["cafile"] = config
            if (!is_file(config)) {
                throw RuntimeException("SSL CA bundle not found: \"$config\".")
            }
        } else if (config === false) {
            context["verify_peer"] = false
            context["verify_peer_name"] = false

            return context
        } else {
            throw  IllegalArgumentException("Invalid verify request option.")
        }
        context["verify_peer"] = true
        context["verify_peer_name"] = true
        context["allow_self_signed"] = false

        return context
    }

    /**
     * Wrap the connector into a proxy one for given configuration.
     *
     * @param ConnectorInterface $connector
     * @param string             $proxyAddress
     * @param array              $secureContext
     *
     * @throws  IllegalArgumentException
     *
     * @return ConnectorInterface
     */
    protected fun _wrapConnectorIntoProxy( connector: ConnectorInterface, proxyAddress: String, array secureContext = []){
        val scheme = if (strpos(proxyAddress, "://") === false) {
            "http"
        } else {
            parse_url(proxyAddress, PHP_URL_SCHEME)
        }

        return when(scheme) {
            "socks", "socks4", "socks4a", "socks5" -> SocksProxy(proxyAddress, connector)
            "http"  -> HttpConnectProxy(proxyAddress, connector)
            "https" -> HttpConnectProxy(proxyAddress, SecureConnector(connector, _loop, secureContext))
            else    -> throw  IllegalArgumentException("Unsupported proxy scheme: \"$scheme\".")
        }
    }
}
