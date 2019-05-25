<?php

package InstagramAPI.Response.Model;

import InstagramAPI.AutoPropertyMapper;

/**
 * Aymf.
 *
 * @method AymfItem[] getItems()
 * @method mixed getMoreAvailable()
 * @method bool isItems()
 * @method bool isMoreAvailable()
 * @method this setItems(AymfItem[] $value)
 * @method this setMoreAvailable(mixed $value)
 * @method this unsetItems()
 * @method this unsetMoreAvailable()
 */
class Aymf : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'items'          => 'AymfItem[]',
        'more_available' => '',
    ];
}
