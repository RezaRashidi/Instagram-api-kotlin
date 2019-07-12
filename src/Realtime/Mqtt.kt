

package instagramAPI.Realtime

import BinSoul.Net.Mqtt.Client.React.ReactMqttClient
import BinSoul.Net.Mqtt.DefaultConnection
import BinSoul.Net.Mqtt.DefaultMessage
import BinSoul.Net.Mqtt.Message
import Evenement.EventEmitterInterface
import Fbns.Client.AuthInterface
import instagramAPI.Constants
import instagramAPI.React.PersistentInterface
import instagramAPI.React.PersistentTrait
import instagramAPI.Realtime.Command.UpdateSubscriptions
import instagramAPI.Realtime.Subscription.GraphQl.AppPresenceSubscription
import instagramAPI.Realtime.Subscription.Skywalker.DirectSubscription
import Psr.Log.LoggerInterface
import React.EventLoop.LoopInterface
import React.EventLoop.Timer.TimerInterface
import React.Socket.ConnectorInterface

class Mqtt : PersistentInterface
{
    import PersistentTrait

    val REALTIME_CLIENT_TYPE = "mqtt"

    /** @var EventEmitterInterface */
    protected $_target

    /** @var ConnectorInterface */
    protected $_connector

    /** @var AuthInterface */
    protected $_auth

    /** @var DeviceInterface */
    protected $_device

    /** @var ExperimentsInterface */
    protected $_experiments

    /** @var LoopInterface */
    protected $_loop

    /** @var array */
    protected $_additionalOptions

    /** @var TimerInterface */
    protected $_keepaliveTimer

    /** @var bool */
    protected $_shutdown

    /** @var LoggerInterface */
    protected $_logger

    /** @var SubscriptionInterface[][] */
    protected $_subscriptions

    /** @var ReactMqttClient */
    protected $_client

    /** @var bool */
    protected $_mqttLiveEnabled
    /** @var bool */
    protected $_irisEnabled
    /** @var string|null */
    protected $_msgTypeBlacklist
    /** @var bool */
    protected $_graphQlTypingEnabled
    /** @var bool */
    protected $_inboxPresenceEnabled
    /** @var bool */
    protected $_threadPresenceEnabled

    /** @var ParserInterface[] */
    protected $_parsers
    /** @var HandlerInterface[] */
    protected $_handlers

    /**
     * Constructor.
     *
     * @param EventEmitterInterface $target
     * @param ConnectorInterface    $connector
     * @param AuthInterface         $auth
     * @param DeviceInterface       $device
     * @param ExperimentsInterface  $experiments
     * @param LoopInterface         $loop
     * @param LoggerInterface       $logger
     * @param array                 $additionalOptions Supported options:
     *                                                 - disable_presence
     *                                                 - datacenter
     */
    public fun __construct(
        EventEmitterInterface $target,
        ConnectorInterface $connector,
        AuthInterface $auth,
        DeviceInterface $device,
        ExperimentsInterface $experiments,
        LoopInterface $loop,
        LoggerInterface $logger,
        array $additionalOptions = [])
    {
        this._target = $target
        this._connector = $connector
        this._auth = $auth
        this._device = $device
        this._loop = $loop
        this._logger = $logger
        this._additionalOptions = $additionalOptions

        this._subscriptions = []

        this._loadExperiments($experiments)
        this._initSubscriptions()

        this._shutdown = false
        this._client = this._getClient()

        this._parsers = [
            Mqtt.Topics::PUBSUB                => Parser.SkywalkerParser(),
            Mqtt.Topics::SEND_MESSAGE_RESPONSE => Parser.JsonParser(Handler.DirectHandler::MODULE),
            Mqtt.Topics::IRIS_SUB_RESPONSE     => Parser.JsonParser(Handler.IrisHandler::MODULE),
            Mqtt.Topics::MESSAGE_SYNC          => Parser.IrisParser(),
            Mqtt.Topics::REALTIME_SUB          => Parser.GraphQlParser(),
            Mqtt.Topics::GRAPHQL               => Parser.GraphQlParser(),
            Mqtt.Topics::REGION_HINT           => Parser.RegionHintParser(),
        ]
        this._handlers = [
            Handler.DirectHandler::MODULE        => Handler.DirectHandler(this._target),
            Handler.LiveHandler::MODULE          => Handler.LiveHandler(this._target),
            Handler.IrisHandler::MODULE          => Handler.IrisHandler(this._target),
            Handler.PresenceHandler::MODULE      => Handler.PresenceHandler(this._target),
            Handler.RegionHintHandler::MODULE    => Handler.RegionHintHandler(this._target),
            Handler.ZeroProvisionHandler::MODULE => Handler.ZeroProvisionHandler(this._target),
        ]
    }

    /** {@inheritdoc} */
    public fun getLoop()
    {
        return this._loop
    }

    /** {@inheritdoc} */
    public fun isActive()
    {
        return !this._shutdown
    }

    /**
     * Add a subscription to the list.
     *
     * @param SubscriptionInterface $subscription
     */
    public fun addSubscription(
        SubscriptionInterface $subscription)
    {
        this._doAddSubscription($subscription, true)
    }

    /**
     * Remove a subscription from the list.
     *
     * @param SubscriptionInterface $subscription
     */
    public fun removeSubscription(
        SubscriptionInterface $subscription)
    {
        this._doRemoveSubscription($subscription, true)
    }

    /**
     * Set an additional option.
     *
     * @param string $option
     * @param mixed  $value
     */
    public fun setAdditionalOption(
        $option,
        $value)
    {
        this._additionalOptions[$option] = $value
    }

    /**
     * Add a subscription to the list and send a command (optional).
     *
     * @param SubscriptionInterface $subscription
     * @param bool                  $sendCommand
     */
    protected fun _doAddSubscription(
        SubscriptionInterface $subscription,
        $sendCommand)
    {
        $topic = $subscription.getTopic()
        $id = $subscription.getId()

        // Check whether we already subscribed to it.
        if (isset(this._subscriptions[$topic][$id])) {
            return
        }

        // Add the subscription to the list.
        if (!isset(this._subscriptions[$topic])) {
            this._subscriptions[$topic] = []
        }
        this._subscriptions[$topic][$id] = $subscription

        // Send a command when needed.
        if (!$sendCommand || this._isConnected()) {
            return
        }

        this._updateSubscriptions($topic, [$subscription], [])
    }

    /**
     * Remove a subscription from the list and send a command (optional).
     *
     * @param SubscriptionInterface $subscription
     * @param bool                  $sendCommand
     */
    protected fun _doRemoveSubscription(
        SubscriptionInterface $subscription,
        $sendCommand)
    {
        $topic = $subscription.getTopic()
        $id = $subscription.getId()

        // Check whether we are subscribed to it.
        if (!isset(this._subscriptions[$topic][$id])) {
            return
        }

        // Remove the subscription from the list.
        unset(this._subscriptions[$topic][$id])
        if (!count(this._subscriptions[$topic])) {
            unset(this._subscriptions[$topic])
        }

        // Send a command when needed.
        if (!$sendCommand || this._isConnected()) {
            return
        }

        this._updateSubscriptions($topic, [], [$subscription])
    }

    /**
     * Cancel a keepalive timer (if any).
     */
    protected fun _cancelKeepaliveTimer()
    {
        if (this._keepaliveTimer !== null) {
            if (this._keepaliveTimer.isActive()) {
                this._logger.debug("Existing keepalive timer has been canceled.")
                this._keepaliveTimer.cancel()
            }
            this._keepaliveTimer = null
        }
    }

    /**
     * Set up a keepalive timer.
     */
    protected fun _setKeepaliveTimer()
    {
        this._cancelKeepaliveTimer()
        $keepaliveInterval = Mqtt.Config::MQTT_KEEPALIVE
        this._logger.debug(sprintf("Setting up keepalive timer to %d seconds", $keepaliveInterval))
        this._keepaliveTimer = this._loop.addTimer($keepaliveInterval, fun () {
            this._logger.info("Keepalive timer has been fired.")
            this._disconnect()
        })
    }

    /**
     * Try to establish a connection.
     */
    protected fun _connect()
    {
        this._setReconnectTimer(fun () {
            this._logger.info(sprintf("Connecting to %s:%d...", Mqtt.Config::DEFAULT_HOST, Mqtt.Config::DEFAULT_PORT))

            $connection = DefaultConnection(
                this._getMqttUsername(),
                this._auth.getPassword(),
                null,
                this._auth.getClientId(),
                Mqtt.Config::MQTT_KEEPALIVE,
                Mqtt.Config::MQTT_VERSION,
                true
            )

            return this._client.connect(Mqtt.Config::DEFAULT_HOST, Mqtt.Config::DEFAULT_PORT, $connection, Mqtt.Config::CONNECTION_TIMEOUT)
        })
    }

    /**
     * Perform first connection in a row.
     */
    public fun start()
    {
        this._shutdown = false
        this._reconnectInterval = 0
        this._connect()
    }

    /**
     * Whether connection is established.
     *
     * @return bool
     */
    protected fun _isConnected()
    {
        return this._client.isConnected()
    }

    /**
     * Disconnect from server.
     */
    protected fun _disconnect()
    {
        this._cancelKeepaliveTimer()
        this._client.disconnect()
    }

    /**
     * Proxy for _disconnect().
     */
    public fun stop()
    {
        this._logger.info("Shutting down...")
        this._shutdown = true
        this._cancelReconnectTimer()
        this._disconnect()
    }

    /**
     * Send the command.
     *
     * @param CommandInterface $command
     *
     * @throws .LogicException
     */
    public fun sendCommand(
        CommandInterface $command)
    {
        if (!this._isConnected()) {
            throw .LogicException("Tried to send the command while offline.")
        }

        this._publish(
            $command.getTopic(),
            json_encode($command, JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE),
            $command.getQosLevel()
        )
    }

    /**
     * Load the experiments.
     *
     * @param ExperimentsInterface $experiments
     */
    protected fun _loadExperiments(
        ExperimentsInterface $experiments)
    {
        this._experiments = $experiments

        // Direct features.
        this._irisEnabled = $experiments.isExperimentEnabled(
            "ig_android_realtime_iris",
            "is_direct_over_iris_enabled"
        )
        this._msgTypeBlacklist = $experiments.getExperimentParam(
            "ig_android_realtime_iris",
            "pubsub_msg_type_blacklist",
            "null"
        )

        // Live features.
        this._mqttLiveEnabled = $experiments.isExperimentEnabled(
            "ig_android_skywalker_live_event_start_end",
            "is_enabled"
        )

        // GraphQL features.
        this._graphQlTypingEnabled = $experiments.isExperimentEnabled(
            "ig_android_gqls_typing_indicator",
            "is_enabled"
        )

        // Presence features.
        this._inboxPresenceEnabled = $experiments.isExperimentEnabled(
            "ig_android_direct_inbox_presence",
            "is_enabled"
        )
        this._threadPresenceEnabled = $experiments.isExperimentEnabled(
            "ig_android_direct_thread_presence",
            "is_enabled"
        )
    }

    protected fun _initSubscriptions()
    {
        $subscriptionId = Signatures::generateUUID()
        // Set up PubSub topics.
        $liveSubscription = LiveSubscription(this._auth.getUserId())
        if (this._mqttLiveEnabled) {
            this._doAddSubscription($liveSubscription, false)
        } else {
            this._doRemoveSubscription($liveSubscription, false)
        }
        // Direct subscription is always enabled.
        this._doAddSubscription(DirectSubscription(this._auth.getUserId()), false)

        // Set up GraphQL topics.
        $zeroProvisionSubscription = ZeroProvisionSubscription(this._auth.getDeviceId())
        this._doAddSubscription($zeroProvisionSubscription, false)
        $graphQlTypingSubscription = DirectTypingSubscription(this._auth.getUserId())
        if (this._graphQlTypingEnabled) {
            this._doAddSubscription($graphQlTypingSubscription, false)
        } else {
            this._doRemoveSubscription($graphQlTypingSubscription, false)
        }
        $appPresenceSubscription = AppPresenceSubscription($subscriptionId)
        if ((this._inboxPresenceEnabled || this._threadPresenceEnabled) && empty(this._additionalOptions["disable_presence"])) {
            this._doAddSubscription($appPresenceSubscription, false)
        } else {
            this._doRemoveSubscription($appPresenceSubscription, false)
        }
    }

    /**
     * Returns application specific info.
     *
     * @return array
     */
    protected fun _getAppSpecificInfo()
    {
        $result = [
            "platform"      => Constants::PLATFORM,
            "app_version"   => Constants::IG_VERSION,
            "capabilities"  => Constants::X_IG_Capabilities,
        ]
        // PubSub message type blacklist.
        $msgTypeBlacklist = ""
        if (this._msgTypeBlacklist !== null && this._msgTypeBlacklist !== "") {
            $msgTypeBlacklist = this._msgTypeBlacklist
        }
        if (this._graphQlTypingEnabled) {
            if ($msgTypeBlacklist !== "") {
                $msgTypeBlacklist .= ", typing_type"
            } else {
                $msgTypeBlacklist = "typing_type"
            }
        }
        if ($msgTypeBlacklist !== "") {
            $result["pubsub_msg_type_blacklist"] = $msgTypeBlacklist
        }
        // Everclear subscriptions.
        $everclearSubscriptions = []
        if (this._inboxPresenceEnabled) {
            $everclearSubscriptions[AppPresenceSubscription::ID] = AppPresenceSubscription::QUERY
        }
        if (count($everclearSubscriptions)) {
            $result["everclear_subscriptions"] = json_encode($everclearSubscriptions)
        }
        // Maintaining the order.
        $result["User-Agent"] = this._device.getUserAgent()
        $result["ig_mqtt_route"] = "django"
        // Accept-Language must be the last one.
        $result["Accept-Language"] = Constants::ACCEPT_LANGUAGE

        return $result
    }

    /**
     * Get a list of topics to subscribe at connect.
     *
     * @return string[]
     */
    protected fun _getSubscribeTopics()
    {
        $topics = [
            Mqtt.Topics::PUBSUB,
        ]
        $topics[] = Mqtt.Topics::REGION_HINT
        if (this._graphQlTypingEnabled) {
            $topics[] = Mqtt.Topics::REALTIME_SUB
        }
        $topics[] = Mqtt.Topics::SEND_MESSAGE_RESPONSE
        if (this._irisEnabled) {
            $topics[] = Mqtt.Topics::IRIS_SUB_RESPONSE
            $topics[] = Mqtt.Topics::MESSAGE_SYNC
        }

        return $topics
    }

    /**
     * Returns username for MQTT connection.
     *
     * @return string
     */
    protected fun _getMqttUsername()
    {
        // Session ID is uptime in msec.
        $sessionId = (microtime(true) - strtotime("Last Monday")) * 1000
        // Random buster-string to avoid clashing with other data.
        $randNum = mt_rand(1000000, 9999999)
        $fields = [
            // USER_ID
            "u"                 => "%ACCOUNT_ID_".$randNum."%",
            // AGENT
            "a"                 => this._device.getFbUserAgent(Constants::INSTAGRAM_APPLICATION_NAME),
            // CAPABILITIES
            "cp"                => Mqtt.Capabilities::DEFAULT_SET,
            // CLIENT_MQTT_SESSION_ID
            "mqtt_sid"          => "%SESSION_ID_".$randNum."%",
            // NETWORK_TYPE
            "nwt"               => Mqtt.Config::NETWORK_TYPE_WIFI,
            // NETWORK_SUBTYPE
            "nwst"              => 0,
            // MAKE_USER_AVAILABLE_IN_FOREGROUND
            "chat_on"           => false,
            // NO_AUTOMATIC_FOREGROUND
            "no_auto_fg"        => true,
            // DEVICE_ID
            "d"                 => this._auth.getDeviceId(),
            // DEVICE_SECRET
            "ds"                => this._auth.getDeviceSecret(),
            // INITIAL_FOREGROUND_STATE
            "fg"                => false,
            // ENDPOINT_CAPABILITIES
            "ecp"               => 0,
            // PUBLISH_FORMAT
            "pf"                => Mqtt.Config::PUBLISH_FORMAT,
            // CLIENT_TYPE
            "ct"                => Mqtt.Config::CLIENT_TYPE,
            // APP_ID
            "aid"               => Constants::FACEBOOK_ANALYTICS_APPLICATION_ID,
            // SUBSCRIBE_TOPICS
            "st"                => this._getSubscribeTopics(),
        ]
        // REGION_PREFERENCE
        if (!empty(this._additionalOptions["datacenter"])) {
            $fields["dc"] = this._additionalOptions["datacenter"]
        }
        // Maintaining the order.
        $fields["clientStack"] = 3
        $fields["app_specific_info"] = this._getAppSpecificInfo()
        // Account and session IDs must be a number, but int size is platform dependent in PHP,
        // so we are replacing JSON strings with plain strings in JSON encoded data.
        $result = strtr(json_encode($fields), [
            json_encode("%ACCOUNT_ID_".$randNum."%") => this._auth.getUserId(),
            json_encode("%SESSION_ID_".$randNum."%") => round($sessionId),
        ])

        return $result
    }

    /**
     * Create a MQTT client.
     *
     * @return ReactMqttClient
     */
    protected fun _getClient()
    {
        $client = ReactMqttClient(this._connector, this._loop, null, Mqtt.StreamParser())

        $client.on("error", fun (.Exception $e) {
            this._logger.error($e.getMessage())
        })
        $client.on("warning", fun (.Exception $e) {
            this._logger.warning($e.getMessage())
        })
        $client.on("open", fun () {
            this._logger.info("Connection has been established")
        })
        $client.on("close", fun () {
            this._logger.info("Connection has been closed")
            this._cancelKeepaliveTimer()
            if (!this._reconnectInterval) {
                this._connect()
            }
        })
        $client.on("connect", fun () {
            this._logger.info("Connected to a broker")
            this._setKeepaliveTimer()
            this._restoreAllSubscriptions()
        })
        $client.on("ping", fun () {
            this._logger.debug("Ping flow completed")
            this._setKeepaliveTimer()
        })
        $client.on("publish", fun () {
            this._logger.debug("Publish flow completed")
            this._setKeepaliveTimer()
        })
        $client.on("message", fun (Message $message) {
            this._setKeepaliveTimer()
            this._onReceive($message)
        })
        $client.on("disconnect", fun () {
            this._logger.info("Disconnected from broker")
        })

        return $client
    }

    /**
     * Mass update subscriptions statuses.
     *
     * @param string                  $topic
     * @param SubscriptionInterface[] $subscribe
     * @param SubscriptionInterface[] $unsubscribe
     */
    protected fun _updateSubscriptions(
        $topic,
        array $subscribe,
        array $unsubscribe)
    {
        if (count($subscribe)) {
            this._logger.info(sprintf("Subscribing to %s topics %s", $topic, implode(", ", $subscribe)))
        }
        if (count($unsubscribe)) {
            this._logger.info(sprintf("Unsubscribing from %s topics %s", $topic, implode(", ", $subscribe)))
        }

        try {
            this.sendCommand(UpdateSubscriptions($topic, $subscribe, $unsubscribe))
        } catch (.Exception $e) {
            this._logger.warning($e.getMessage())
        }
    }

    /**
     * Subscribe to all topics.
     */
    protected fun _restoreAllSubscriptions()
    {
        foreach (this._subscriptions as $topic => $subscriptions) {
            this._updateSubscriptions($topic, $subscriptions, [])
        }
    }

    /**
     * Unsubscribe from all topics.
     */
    protected fun _removeAllSubscriptions()
    {
        foreach (this._subscriptions as $topic => $subscriptions) {
            this._updateSubscriptions($topic, [], $subscriptions)
        }
    }

    /**
     * Maps human readable topic to its identifier.
     *
     * @param string $topic
     *
     * @return string
     */
    protected fun _mapTopic(
        $topic)
    {
        if (array_key_exists($topic, Mqtt.Topics::TOPIC_TO_ID_MAP)) {
            $result = Mqtt.Topics::TOPIC_TO_ID_MAP[$topic]
            this._logger.debug(sprintf("Topic "%s" has been mapped to "%s"", $topic, $result))
        } else {
            $result = $topic
            this._logger.warning(sprintf("Topic "%s" does not exist in the enum", $topic))
        }

        return $result
    }

    /**
     * Maps topic ID to human readable name.
     *
     * @param string $topic
     *
     * @return string
     */
    protected fun _unmapTopic(
        $topic)
    {
        if (array_key_exists($topic, Mqtt.Topics::ID_TO_TOPIC_MAP)) {
            $result = Mqtt.Topics::ID_TO_TOPIC_MAP[$topic]
            this._logger.debug(sprintf("Topic ID "%s" has been unmapped to "%s"", $topic, $result))
        } else {
            $result = $topic
            this._logger.warning(sprintf("Topic ID "%s" does not exist in the enum", $topic))
        }

        return $result
    }

    /**
     * @param string $topic
     * @param string $payload
     * @param int    $qosLevel
     */
    protected fun _publish(
        $topic,
        $payload,
        $qosLevel)
    {
        this._logger.info(sprintf("Sending message "%s" to topic "%s"", $payload, $topic))
        $payload = zlib_encode($payload, ZLIB_ENCODING_DEFLATE, 9)
        // We need to map human readable topic name to its ID becaimport of bandwidth saving.
        $topic = this._mapTopic($topic)
        this._client.publish(DefaultMessage($topic, $payload, $qosLevel))
    }

    /**
     * Incoming message handler.
     *
     * @param Message $msg
     */
    protected fun _onReceive(
        Message $msg)
    {
        $payload = @zlib_decode($msg.getPayload())
        if ($payload === false) {
            this._logger.warning("Failed to inflate the payload")

            return
        }
        this._handleMessage(this._unmapTopic($msg.getTopic()), $payload)
    }

    /**
     * @param string $topic
     * @param string $payload
     */
    protected fun _handleMessage(
        $topic,
        $payload)
    {
        this._logger.debug(
            sprintf("Received a message from topic "%s"", $topic),
            [base64_encode($payload)]
        )
        if (!isset(this._parsers[$topic])) {
            this._logger.warning(
                sprintf("No parser for topic "%s" found, skipping the message(s)", $topic),
                [base64_encode($payload)]
            )

            return
        }

        try {
            $messages = this._parsers[$topic].parseMessage($topic, $payload)
        } catch (.Exception $e) {
            this._logger.warning($e.getMessage(), [$topic, base64_encode($payload)])

            return
        }

        foreach ($messages as $message) {
            $module = $message.getModule()
            if (!isset(this._handlers[$module])) {
                this._logger.warning(
                    sprintf("No handler for module "%s" found, skipping the message", $module),
                    [$message.getData()]
                )

                continue
            }

            this._logger.info(
                sprintf("Processing a message for module "%s"", $module),
                [$message.getData()]
            )

            try {
                this._handlers[$module].handleMessage($message)
            } catch (Handler.HandlerException $e) {
                this._logger.warning($e.getMessage(), [$message.getData()])
            } catch (.Exception $e) {
                this._target.emit("warning", [$e])
            }
        }
    }

    /** {@inheritdoc} */
    public fun getLogger()
    {
        return this._logger
    }
}
