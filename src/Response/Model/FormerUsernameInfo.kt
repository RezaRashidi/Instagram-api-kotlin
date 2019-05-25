<?php

package InstagramAPI.Response.Model;

import InstagramAPI.AutoPropertyMapper;

/**
 * FormerUsernameInfo.
 *
 * @method bool getHasFormerUsernames()
 * @method bool isHasFormerUsernames()
 * @method this setHasFormerUsernames(bool $value)
 * @method this unsetHasFormerUsernames()
 */
class FormerUsernameInfo : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'has_former_usernames' => 'bool',
    ];
}
