<?php

package InstagramAPI.Realtime.Handler;

import Evenement.EventEmitterInterface;

abstract class AbstractHandler
{
    /**
     * @var EventEmitterInterface
     */
    protected $_target;

    /**
     * Constructor.
     *
     * @param EventEmitterInterface $target
     */
    public fun __construct(
        EventEmitterInterface $target)
    {
        this._target = $target;
    }

    /**
     * Checks if target has at least one listener for specific event.
     *
     * @param string $event
     *
     * @return bool
     */
    protected fun _hasListeners(
        $event)
    {
        return (bool) count(this._target.listeners($event));
    }
}
