<?php

package InstagramAPI.Realtime;

import InstagramAPI.Realtime.Handler.HandlerException;

interface HandlerInterface
{
    /**
     * Handle the message.
     *
     * @param Message $message
     *
     * @throws HandlerException
     */
    public fun handleMessage(
        Message $message);
}
