

package InstagramAPI.React

import Psr.Log.LoggerInterface
import React.EventLoop.LoopInterface

interface PersistentInterface
{
    /** @var int Minimum reconnection interval (in sec) */
    val MIN_RECONNECT_INTERVAL = 1
    /** @var int Maximum reconnection interval (in sec) */
    val MAX_RECONNECT_INTERVAL = 300 // 5 minutes

    /**
     * Returns a minimum allowed reconnection interval.
     *
     * @return int
     */
    fun getMinReconnectInterval(): Int

    /**
     * Returns a minimum allowed reconnection interval.
     *
     * @return int
     */
    fun getMaxReconnectInterval(): Int

    /**
     * Returns whether persistence should be maintained.
     *
     * @return bool
     */
    fun isActive(): Boolean

    /**
     * Returns the logger instance.
     *
     * @return LoggerInterface
     */
    fun getLogger(): LoggerInterface

    /**
     * Returns the loop instance.
     *
     * @return LoopInterface
     */
    fun getLoop(): LoopInterface
}
