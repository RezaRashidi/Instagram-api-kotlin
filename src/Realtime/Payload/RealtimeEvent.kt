<?php

package InstagramAPI.Realtime.Payload;

import InstagramAPI.AutoPropertyMapper;

/**
 * RealtimeEvent.
 *
 * @method string getEvent()
 * @method bool isEvent()
 * @method this setEvent(string $value)
 * @method this unsetEvent()
 */
abstract class RealtimeEvent : AutoPropertyMapper
{
    val SUBSCRIBED = 'subscribed';
    val UNSUBSCRIBED = 'unsubscribed';
    val KEEPALIVE = 'keepalive';
    val PATCH = 'patch';
    val BROADCAST_ACK = 'broadcast-ack';
    val ERROR = 'error';

    val JSON_PROPERTY_MAP = [
        'event' => 'string',
    ];
}
