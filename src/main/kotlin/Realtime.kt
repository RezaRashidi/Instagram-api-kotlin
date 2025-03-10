

package instagramAPI

//import Evenement.EventEmitterInterface
//import Evenement.EventEmitterTrait
import instagramAPI.React.Connector
import instagramAPI.realtimes.Command.IrisSubscribe
import instagramAPI.realtimes.Mqtt.Auth
//import Psr.Log.LoggerInterface
//import Psr.Log.NullLogger
//import React.EventLoop.LoopInterface
import instagramAPI.realtimes.Command.DirectCommand

/**
 * The following events are emitted:
 *  - live-started - live broadcast has been started.
 *  - live-stopped - An existing live broadcast has been stopped.
 *  - direct-story-created - direct story has been created.
 *  - direct-story-updated - item has been created in direct story.
 *  - direct-story-screenshot - Someone has taken a screenshot of your direct story.
 *  - direct-story-action - Direct story badge has been updated with some action.
 *  - thread-created - thread has been created.
 *  - thread-updated - An existing thread has been updated.
 *  - thread-notify - Someone has created ActionLog item in thread.
 *  - thread-seen - Someone has updated their last seen position.
 *  - thread-activity - Someone has created an activity (like start/stop typing) in thread.
 *  - thread-item-created - item has been created in thread.
 *  - thread-item-updated - An existing item has been updated in thread.
 *  - thread-item-removed - An existing item has been removed from thread.
 *  - client-context-ack - Acknowledgment for client_context has been received.
 *  - unseen-count-update - Unseen count indicator has been updated.
 *  - region-hint - Preferred data center has been changed.
 *  - zero-provision - Zero rating token has been updated.
 *  - warning - An exception of severity "warning" occurred.
 *  - error - An exception of severity "error" occurred.
 */
class Realtime : EventEmitterInterface
{
    import EventEmitterTrait

    /** @var Instagram */
    protected $_instagram

    /** @var LoopInterface */
    protected $_loop

    /** @var LoggerInterface */
    protected $_logger

    /** @var realtimes.Mqtt */
    protected $_client

    /**
     * Constructor.
     *
     * @param Instagram            $instagram
     * @param LoopInterface        $loop
     * @param LoggerInterface|null $logger
     *
     * @throws .RuntimeException
     */
    public fun __construct(
        Instagram $instagram,
        LoopInterface $loop,
        LoggerInterface $logger = null)
    {
        if (PHP_SAPI !== "cli") {
            throw .RuntimeException("The realtimes client can only run from the command line.")
        }

        this._instagram = $instagram
        this._loop = $loop
        this._logger = $logger
        if (this._logger === null) {
            this._logger = NullLogger()
        }

        this._client = this._buildMqttClient()
        this.on("region-hint", fun ($region) {
            this._instagram.settings.set("datacenter", $region)
            this._client.setAdditionalOption("datacenter", $region)
        })
        this.on("zero-provision", fun (ZeroProvisionEvent $event) {
            if ($event.getZeroProvisionedTime() === null) {
                return
            }
            if ($event.getProductName() !== "select") {
                return
            }
            // TODO check whether we already have a fresh token.

            this._instagram.client.zeroRating().reset()
            this._instagram.internal.fetchZeroRatingToken("mqtt_token_push")
        })
    }

    /**
     * Build a MQTT client.
     *
     * @return realtimes.Mqtt
     */
    protected fun _buildMqttClient()
    {
        $additionalOptions = [
            "datacenter"       => this._instagram.settings.get("datacenter"),
            "disable_presence" => (bool) this._instagram.settings.get("presence_disabled"),
        ]

        return Realtime.Mqtt(
            this,
            Connector(this._instagram, this._loop),
            Auth(this._instagram),
            this._instagram.device,
            this._instagram,
            this._loop,
            this._logger,
            $additionalOptions
        )
    }

    /**
     * Starts underlying client.
     */
    public fun start()
    {
        this._client.start()
    }

    /**
     * Stops underlying client.
     */
    public fun stop()
    {
        this._client.stop()
    }

    /**
     * Marks thread item as seen.
     *
     * @param string $threadId
     * @param string $threadItemId
     *
     * @return bool
     */
    public fun markDirectItemSeen(
        $threadId,
        $threadItemId)
    {
        try {
            this._client.sendCommand(DirectCommand.MarkSeen($threadId, $threadItemId))
        } catch (.Exception $e) {
            this._logger.warning($e.getMessage())

            return false
        }

        return true
    }

    /**
     * Indicate activity in thread.
     *
     * @param string $threadId
     * @param bool   $activityFlag
     *
     * @return bool|string Client context or false if sending is unavailable.
     */
    public fun indicateActivityInDirectThread(
        $threadId,
        $activityFlag)
    {
        try {
            $command = DirectCommand.IndicateActivity($threadId, $activityFlag)
            this._client.sendCommand($command)

            return $command.getClientContext()
        } catch (.Exception $e) {
            this._logger.warning($e.getMessage())

            return false
        }
    }

    /**
     * Sends text message to a given direct thread.
     *
     * @param string $threadId Thread ID.
     * @param string $message  Text message.
     * @param array  $options  An associative array of optional parameters, including:
     *                         "client_context" - predefined UUID used to prevent double-posting
     *
     * @return bool|string Client context or false if sending is unavailable.
     */
    public fun sendTextToDirect(
        $threadId,
        $message,
        array $options = [])
    {
        try {
            $command = DirectCommand.SendText($threadId, $message, $options)
            this._client.sendCommand($command)

            return $command.getClientContext()
        } catch (.Exception $e) {
            this._logger.warning($e.getMessage())

            return false
        }
    }

    /**
     * Sends like to a given direct thread.
     *
     * @param string $threadId Thread ID.
     * @param array  $options  An associative array of optional parameters, including:
     *                         "client_context" - predefined UUID used to prevent double-posting
     *
     * @return bool|string Client context or false if sending is unavailable.
     */
    public fun sendLikeToDirect(
        $threadId,
        array $options = [])
    {
        try {
            $command = DirectCommand.SendLike($threadId, $options)
            this._client.sendCommand($command)

            return $command.getClientContext()
        } catch (.Exception $e) {
            this._logger.warning($e.getMessage())

            return false
        }
    }

    /**
     * Share an existing media post to a given direct thread.
     *
     * @param string $threadId Thread ID.
     * @param string $mediaId  The media ID in Instagram"s internal format (ie "3482384834_43294").
     * @param array  $options  An associative array of additional parameters, including:
     *                         "client_context" (optional) - predefined UUID used to prevent double-posting
     *                         "text" (optional) - text message.
     *
     * @return bool|string Client context or false if sending is unavailable.
     */
    public fun sendPostToDirect(
        $threadId,
        $mediaId,
        array $options = [])
    {
        if (!this._isRtcReshareEnabled()) {
            return false
        }

        try {
            $command = DirectCommand.SendPost($threadId, $mediaId, $options)
            this._client.sendCommand($command)

            return $command.getClientContext()
        } catch (.Exception $e) {
            this._logger.warning($e.getMessage())

            return false
        }
    }

    /**
     * Share an existing story to a given direct thread.
     *
     * @param string $threadId Thread ID.
     * @param string $storyId  The story ID in Instagram"s internal format (ie "3482384834_43294").
     * @param array  $options  An associative array of additional parameters, including:
     *                         "client_context" (optional) - predefined UUID used to prevent double-posting
     *                         "text" (optional) - text message.
     *
     * @return bool|string Client context or false if sending is unavailable.
     */
    public fun sendStoryToDirect(
        $threadId,
        $storyId,
        array $options = [])
    {
        if (!this._isRtcReshareEnabled()) {
            return false
        }

        try {
            $command = DirectCommand.SendStory($threadId, $storyId, $options)
            this._client.sendCommand($command)

            return $command.getClientContext()
        } catch (.Exception $e) {
            this._logger.warning($e.getMessage())

            return false
        }
    }

    /**
     * Share a profile to a given direct thread.
     *
     * @param string $threadId Thread ID.
     * @param string $userId   Numerical UserPK ID.
     * @param array  $options  An associative array of additional parameters, including:
     *                         "client_context" (optional) - predefined UUID used to prevent double-posting
     *                         "text" (optional) - text message.
     *
     * @return bool|string Client context or false if sending is unavailable.
     */
    public fun sendProfileToDirect(
        $threadId,
        $userId,
        array $options = [])
    {
        if (!this._isRtcReshareEnabled()) {
            return false
        }

        try {
            $command = DirectCommand.SendProfile($threadId, $userId, $options)
            this._client.sendCommand($command)

            return $command.getClientContext()
        } catch (.Exception $e) {
            this._logger.warning($e.getMessage())

            return false
        }
    }

    /**
     * Share a location to a given direct thread.
     *
     * You must provide a valid Instagram location ID, which you get via other
     * funs such as Location::search().
     *
     * @param string $threadId   Thread ID.
     * @param string $locationId Instagram"s internal ID for the location.
     * @param array  $options    An associative array of additional parameters, including:
     *                           "client_context" (optional) - predefined UUID used to prevent double-posting
     *                           "text" (optional) - text message.
     *
     * @return bool|string Client context or false if sending is unavailable.
     */
    public fun sendLocationToDirect(
        $threadId,
        $locationId,
        array $options = [])
    {
        if (!this._isRtcReshareEnabled()) {
            return false
        }

        try {
            $command = DirectCommand.SendLocation($threadId, $locationId, $options)
            this._client.sendCommand($command)

            return $command.getClientContext()
        } catch (.Exception $e) {
            this._logger.warning($e.getMessage())

            return false
        }
    }

    /**
     * Share a hashtag to a given direct thread.
     *
     * @param string $threadId Thread ID.
     * @param string $hashtag  Hashtag to share.
     * @param array  $options  An associative array of additional parameters, including:
     *                         "client_context" (optional) - predefined UUID used to prevent double-posting
     *                         "text" (optional) - text message.
     *
     * @return bool|string Client context or false if sending is unavailable.
     */
    public fun sendHashtagToDirect(
        $threadId,
        $hashtag,
        array $options = [])
    {
        if (!this._isRtcReshareEnabled()) {
            return false
        }

        try {
            $command = DirectCommand.SendHashtag($threadId, $hashtag, $options)
            this._client.sendCommand($command)

            return $command.getClientContext()
        } catch (.Exception $e) {
            this._logger.warning($e.getMessage())

            return false
        }
    }

    /**
     * Sends reaction to a given direct thread item.
     *
     * @param string $threadId     Thread ID.
     * @param string $threadItemId Thread ID.
     * @param string $reactionType One of: "like".
     * @param array  $options      An associative array of optional parameters, including:
     *                             "client_context" - predefined UUID used to prevent double-posting
     *
     * @return bool|string Client context or false if sending is unavailable.
     */
    public fun sendReactionToDirect(
        $threadId,
        $threadItemId,
        $reactionType,
        array $options = [])
    {
        try {
            $command = DirectCommand.SendReaction(
                $threadId,
                $threadItemId,
                $reactionType,
                DirectCommand.SendReaction::STATUS_CREATED,
                $options
            )
            this._client.sendCommand($command)

            return $command.getClientContext()
        } catch (.Exception $e) {
            this._logger.warning($e.getMessage())

            return false
        }
    }

    /**
     * Removes reaction to a given direct thread item.
     *
     * @param string $threadId     Thread ID.
     * @param string $threadItemId Thread ID.
     * @param string $reactionType One of: "like".
     * @param array  $options      An associative array of optional parameters, including:
     *                             "client_context" - predefined UUID used to prevent double-posting
     *
     * @return bool|string Client context or false if sending is unavailable.
     */
    public fun deleteReactionFromDirect(
        $threadId,
        $threadItemId,
        $reactionType,
        array $options = [])
    {
        try {
            $command = DirectCommand.SendReaction(
                $threadId,
                $threadItemId,
                $reactionType,
                DirectCommand.SendReaction::STATUS_DELETED,
                $options
            )
            this._client.sendCommand($command)

            return $command.getClientContext()
        } catch (.Exception $e) {
            this._logger.warning($e.getMessage())

            return false
        }
    }

    /**
     * Receive offline messages starting from the sequence ID.
     *
     * @param int $sequenceId
     */
    public fun receiveOfflineMessages(
        $sequenceId)
    {
        try {
            this._client.sendCommand(IrisSubscribe($sequenceId))
        } catch (.Exception $e) {
            this._logger.warning($e.getMessage())
        }
    }

    /**
     * Check whether sharing via the realtime client is enabled.
     *
     * @return bool
     */
    protected fun _isRtcReshareEnabled()
    {
        return this._instagram.isExperimentEnabled("ig_android_rtc_reshare", "is_rtc_reshare_enabled")
    }
}
