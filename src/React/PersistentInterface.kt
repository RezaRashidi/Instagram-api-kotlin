<?php

package InstagramAPI.React;

import Psr.Log.LoggerInterface;
import React.EventLoop.LoopInterface;

interface PersistentInterface
{
    /** @var int Minimum reconnection interval (in sec) */
    val MIN_RECONNECT_INTERVAL = 1;
    /** @var int Maximum reconnection interval (in sec) */
    val MAX_RECONNECT_INTERVAL = 300; // 5 minutes

    /**
     * Returns a minimum allowed reconnection interval.
     *
     * @return int
     */
    public fun getMinReconnectInterval();

    /**
     * Returns a minimum allowed reconnection interval.
     *
     * @return int
     */
    public fun getMaxReconnectInterval();

    /**
     * Returns whether persistence should be maintained.
     *
     * @return bool
     */
    public fun isActive();

    /**
     * Returns the logger instance.
     *
     * @return LoggerInterface
     */
    public fun getLogger();

    /**
     * Returns the loop instance.
     *
     * @return LoopInterface
     */
    public fun getLoop();
}
