<?php

package InstagramAPI.Response.Model;

import InstagramAPI.AutoPropertyMapper;

/**
 * DirectSeenItemPayload.
 *
 * @method mixed getCount()
 * @method string getTimestamp()
 * @method bool isCount()
 * @method bool isTimestamp()
 * @method this setCount(mixed $value)
 * @method this setTimestamp(string $value)
 * @method this unsetCount()
 * @method this unsetTimestamp()
 */
class DirectSeenItemPayload : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'count'     => '',
        'timestamp' => 'string',
    ];
}
