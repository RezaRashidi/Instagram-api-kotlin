<?php

package InstagramAPI.Response.Model;

import InstagramAPI.AutoPropertyMapper;

/**
 * Bold.
 *
 * @method mixed getEnd()
 * @method mixed getStart()
 * @method bool isEnd()
 * @method bool isStart()
 * @method this setEnd(mixed $value)
 * @method this setStart(mixed $value)
 * @method this unsetEnd()
 * @method this unsetStart()
 */
class Bold : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'start' => '',
        'end'   => '',
    ];
}
