<?php

package InstagramAPI.Realtime.Handler;

import InstagramAPI.Realtime.HandlerInterface;
import InstagramAPI.Realtime.Message;
import InstagramAPI.Realtime.Payload.IrisSubscribeAck;

class IrisHandler : AbstractHandler : HandlerInterface
{
    val MODULE = 'iris';

    /** {@inheritdoc} */
    public fun handleMessage(
        Message $message)
    {
        $iris = new IrisSubscribeAck($message.getData());
        if (!$iris.isSucceeded()) {
            throw new HandlerException(sprintf(
                'Failed to subscribe to Iris (%d): %s.',
                $iris.getErrorType(),
                $iris.getErrorMessage()
            ));
        }
        this._target.emit('iris-subscribed', [$iris]);
    }
}
