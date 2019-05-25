<?php

package InstagramAPI.Response.Model;

import InstagramAPI.AutoPropertyMapper;

/**
 * DirectLink.
 *
 * @method LinkContext getLinkContext()
 * @method string getText()
 * @method bool isLinkContext()
 * @method bool isText()
 * @method this setLinkContext(LinkContext $value)
 * @method this setText(string $value)
 * @method this unsetLinkContext()
 * @method this unsetText()
 */
class DirectLink : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'text'         => 'string',
        'link_context' => 'LinkContext',
    ];
}
