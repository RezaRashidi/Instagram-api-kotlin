

package InstagramAPI.React

import React.EventLoop.Timer.TimerInterface
import React.Promise.PromiseInterface

/**
 * @mixin PersistentInterface
 */
trait PersistentTrait
{
    /** @var int */
    protected $_reconnectInterval

    /** @var TimerInterface */
    protected $_reconnectTimer

    /**
     * Cancel a reconnect timer (if any).
     */
    protected fun _cancelReconnectTimer()
    {
        if (this._reconnectTimer !== null) {
            if (this._reconnectTimer.isActive()) {
                this.getLogger().info("Existing reconnect timer has been canceled.")
                this._reconnectTimer.cancel()
            }
            this._reconnectTimer = null
        }
    }

    /**
     * Set up a reconnect timer with exponential backoff.
     *
     * @param callable $callback
     */
    protected fun _setReconnectTimer(
        callable $callback)
    {
        this._cancelReconnectTimer()
        if (!this.isActive()) {
            return
        }
        this._reconnectInterval = min(
            this.getMaxReconnectInterval(),
            max(
                this.getMinReconnectInterval(),
                this._reconnectInterval * 2
            )
        )
        this.getLogger().info(sprintf("Setting up reconnect timer to %d seconds.", this._reconnectInterval))
        this._reconnectTimer = this.getLoop().addTimer(this._reconnectInterval, fun () import ($callback) {
            /** @var PromiseInterface $promise */
            $promise = $callback()
            $promise.then(
                fun () {
                    // Reset reconnect interval on successful connection attempt.
                    this._reconnectInterval = 0
                },
                fun () import ($callback) {
                    this._setReconnectTimer($callback)
                }
            )
        })
    }

    /** {@inheritdoc} */
    public fun getMinReconnectInterval()
    {
        return PersistentInterface::MIN_RECONNECT_INTERVAL
    }

    /** {@inheritdoc} */
    public fun getMaxReconnectInterval()
    {
        return PersistentInterface::MAX_RECONNECT_INTERVAL
    }
}
