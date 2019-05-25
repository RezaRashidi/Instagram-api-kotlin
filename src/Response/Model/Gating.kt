<?php

package InstagramAPI.Response.Model;

import InstagramAPI.AutoPropertyMapper;

/**
 * Gating.
 *
 * @method mixed getButtons()
 * @method mixed getDescription()
 * @method mixed getGatingType()
 * @method mixed getTitle()
 * @method bool isButtons()
 * @method bool isDescription()
 * @method bool isGatingType()
 * @method bool isTitle()
 * @method this setButtons(mixed $value)
 * @method this setDescription(mixed $value)
 * @method this setGatingType(mixed $value)
 * @method this setTitle(mixed $value)
 * @method this unsetButtons()
 * @method this unsetDescription()
 * @method this unsetGatingType()
 * @method this unsetTitle()
 */
class Gating : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'gating_type' => '',
        'description' => '',
        'buttons'     => '',
        'title'       => '',
    ];
}
