package instagramAPI

import LazyJsonMapper.LazyJsonMapper

/**
 * Automatically maps JSON data onto PHP objects with virtual funs.
 *
 * Configures important core settings for the property mapping process.
 */
 open class AutoPropertyMapper : LazyJsonMapper
{
    /** @var bool */
    val ALLOW_VIRTUAL_PROPERTIES = false

    /** @var bool */
    val ALLOW_VIRTUAL_funS = true

    /** @var bool */
    val USE_MAGIC_LOOKUP_CACHE = true
}
