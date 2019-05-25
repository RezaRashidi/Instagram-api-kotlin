<?php

package InstagramAPI.Response.Model;

import InstagramAPI.AutoPropertyMapper;

/**
 * StoryCta.
 *
 * @method AndroidLinks[] getLinks()
 * @method bool isLinks()
 * @method this setLinks(AndroidLinks[] $value)
 * @method this unsetLinks()
 */
class StoryCta : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'links'          => 'AndroidLinks[]',
    ];
}
