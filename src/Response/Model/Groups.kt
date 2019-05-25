<?php

package InstagramAPI.Response.Model;

import InstagramAPI.AutoPropertyMapper;

/**
 * Groups.
 *
 * @method Item[] getItems()
 * @method mixed getType()
 * @method bool isItems()
 * @method bool isType()
 * @method this setItems(Item[] $value)
 * @method this setType(mixed $value)
 * @method this unsetItems()
 * @method this unsetType()
 */
class Groups : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'type'  => '',
        'items' => 'Item[]',
    ];
}
