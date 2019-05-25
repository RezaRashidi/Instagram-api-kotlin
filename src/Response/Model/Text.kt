<?php

package InstagramAPI.Response.Model;

import InstagramAPI.AutoPropertyMapper;

/**
 * Text.
 *
 * @method string getText()
 * @method bool isText()
 * @method this setText(string $value)
 * @method this unsetText()
 */
class Text : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'text' => 'string',
    ];
}
