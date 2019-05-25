<?php

package InstagramAPI.Response.Model;

import InstagramAPI.AutoPropertyMapper;

/**
 * Megaphone.
 *
 * @method GenericMegaphone getGenericMegaphone()
 * @method bool isGenericMegaphone()
 * @method this setGenericMegaphone(GenericMegaphone $value)
 * @method this unsetGenericMegaphone()
 */
class Megaphone : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'generic_megaphone' => 'GenericMegaphone',
    ];
}
