<?php

package InstagramAPI.Realtime.Handler;

import InstagramAPI.Realtime.HandlerInterface;
import InstagramAPI.Realtime.Message;

class RegionHintHandler : AbstractHandler : HandlerInterface
{
    val MODULE = 'region_hint';

    /** {@inheritdoc} */
    public fun handleMessage(
        Message $message)
    {
        $region = $message.getData();
        if ($region === null || $region === '') {
            throw new HandlerException('Invalid region hint.');
        }
        this._target.emit('region-hint', [$message.getData()]);
    }
}
