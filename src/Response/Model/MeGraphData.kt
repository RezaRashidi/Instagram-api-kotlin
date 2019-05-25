<?php

package InstagramAPI.Response.Model;

import InstagramAPI.AutoPropertyMapper;

/**
 * MeGraphData.
 *
 * @method string getId()
 * @method CatalogData getTaggableCatalogs()
 * @method bool isId()
 * @method bool isTaggableCatalogs()
 * @method this setId(string $value)
 * @method this setTaggableCatalogs(CatalogData $value)
 * @method this unsetId()
 * @method this unsetTaggableCatalogs()
 */
class MeGraphData : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'taggable_catalogs' => 'CatalogData',
        'id'                => 'string',
    ];
}
