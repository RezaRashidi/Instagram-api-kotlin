

package instagramAPI.Push

import Evenement.EventEmitterInterface
import Evenement.EventEmitterTrait
import Fbns.Client.AuthInterface
import Fbns.Client.Connection
import Fbns.Client.Lite
import Fbns.Client.Message.Push as PushMessage
import Fbns.Client.Message.Register
import instagramAPI.Constants
import instagramAPI.Devices.DeviceInterface
import instagramAPI.React.PersistentInterface
import instagramAPI.React.PersistentTrait
import Psr.Log.LoggerInterface
import React.EventLoop.LoopInterface
import React.Socket.ConnectorInterface

/**
 * The following events are emitted:
 *  - token - PUSH token has been received.
 *  - push - PUSH notification has been received.
 */
class Fbns : PersistentInterface, EventEmitterInterface
{
    import PersistentTrait
    import EventEmitterTrait

    val CONNECTION_TIMEOUT = 5

    val DEFAULT_HOST = "mqtt-mini.facebook.com"
    val DEFAULT_PORT = 443

    /** @var EventEmitterInterface */
    protected lateinit var _target: EventEmitterInterface

    /** @var ConnectorInterface */
    protected lateinit var _connector: ConnectorInterface

    /** @var AuthInterface */
    protected lateinit var _auth: AuthInterface

    /** @var DeviceInterface */
    protected lateinit var _device: DeviceInterface

    /** @var LoopInterface */
    protected lateinit var _loop: LoopInterface

    /** @var Lite */
    protected lateinit var _client: Lite

    /** @var LoggerInterface */
    protected lateinit var _logger: LoggerInterface

    /** @var bool */
    protected var _isActive: Boolean

    /**
     * Fbns constructor.
     *
     * @param EventEmitterInterface $target
     * @param ConnectorInterface    $connector
     * @param AuthInterface         $auth
     * @param DeviceInterface       $device
     * @param LoopInterface         $loop
     * @param LoggerInterface       $logger
     */
    fun __construct(
        target: EventEmitterInterface,
        connector: ConnectorInterface,
        auth: AuthInterface,
        device: DeviceInterface,
        loop: LoopInterface,
        logger: LoggerInterface
    ){
        _target    = target
        _connector = connector
        _auth      = auth
        _device    = device
        _loop      = loop
        _logger    = logger

        _client = _getClient()
    }

    /**
     * Create a FBNS client instance.
     *
     * @return Lite
     */
    protected fun _getClient(){
        val client = Lite(_loop, _connector, _logger)

        // Bind events.
        client
            .on("connect", fun (Lite.ConnectResponsePacket responsePacket) {
                // Update auth credentials.
                val authJson = responsePacket.getAuth()
                if (authJson.length > 0) {
                    _logger.info("Received a non-empty auth.", [authJson])
                    emit("fbns_auth", [authJson])
                }

                // Register an application.
                _client.register(Constants.PACKAGE_NAME, Constants.FACEBOOK_ANALYTICS_APPLICATION_ID)
            })
            .on("disconnect", fun () {
                // Try to reconnect.
                if (!_reconnectInterval) {
                    _connect()
                }
            })
            .on("register", fun (Register message) {
                if (!message.getError().isEmpty() ) {
                    _target.emit("error", [RuntimeException(message.getError())])

                    return
                }
                _logger.info("Received a non-empty token.", [message.getToken()])
                emit("fbns_token", [message.getToken()])
            })
            .on("push", fun (PushMessage message) {
                val payload = message.getPayload()

                try {
                    val notification = Notification(payload)
                } catch (e: Exception) {
                    _logger.error(sprintf("Failed to decode push: %s", $e.getMessage()), [$payload])

                    return
                }
                emit("push", [notification])
            })

        return client
    }

    /**
     * Try to establish a connection.
     */
    protected fun _connect()
    {
        this._setReconnectTimer(fun () {
            val connection = Connection(
                _auth,
                _device.getFbUserAgent(Constants.FBNS_APPLICATION_NAME)
            )

            return _client.connect(DEFAULT_HOST, DEFAULT_PORT, connection, CONNECTION_TIMEOUT)
        })
    }

    /**
     * Start Push receiver.
     */
    fun start()
    {
        _logger.info("Starting FBNS client...")
        _isActive = true
        _reconnectInterval = 0
        _connect()
    }

    /**
     * Stop Push receiver.
     */
    fun stop()
    {
        _logger.info("Stopping FBNS client...")
        _isActive = false
        _cancelReconnectTimer()
        _client.disconnect()
    }

    /** {@inheritdoc} */
    fun isActive(): Boolean {
        return _isActive
    }

    /** {@inheritdoc} */
    fun getLogger(){
        return _logger
    }

    /** {@inheritdoc} */
    fun getLoop(){
        return _loop
    }
}
