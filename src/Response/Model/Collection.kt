<?php

package InstagramAPI.Response.Model;

import InstagramAPI.AutoPropertyMapper;

/**
 * Collection.
 *
 * @method string getCollectionId()
 * @method string getCollectionName()
 * @method Item getCoverMedia()
 * @method bool isCollectionId()
 * @method bool isCollectionName()
 * @method bool isCoverMedia()
 * @method this setCollectionId(string $value)
 * @method this setCollectionName(string $value)
 * @method this setCoverMedia(Item $value)
 * @method this unsetCollectionId()
 * @method this unsetCollectionName()
 * @method this unsetCoverMedia()
 */
class Collection : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'collection_id'   => 'string',
        'collection_name' => 'string',
        'cover_media'     => 'Item',
    ];
}
