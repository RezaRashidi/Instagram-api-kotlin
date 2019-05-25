<?php

package InstagramAPI.Response.Model;

import InstagramAPI.AutoPropertyMapper;

/**
 * StaticStickers.
 *
 * @method string getId()
 * @method mixed getIncludeInRecent()
 * @method Stickers[] getStickers()
 * @method bool isId()
 * @method bool isIncludeInRecent()
 * @method bool isStickers()
 * @method this setId(string $value)
 * @method this setIncludeInRecent(mixed $value)
 * @method this setStickers(Stickers[] $value)
 * @method this unsetId()
 * @method this unsetIncludeInRecent()
 * @method this unsetStickers()
 */
class StaticStickers : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'include_in_recent' => '',
        'id'                => 'string',
        'stickers'          => 'Stickers[]',
    ];
}
