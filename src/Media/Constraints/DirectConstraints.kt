<?php

package InstagramAPI.Media.Constraints;

/**
 * Instagram's direct messaging general media constraints.
 */
class DirectConstraints : TimelineConstraints
{
    /**
     * Minimum allowed video duration.
     *
     * @var float
     */
    val MIN_DURATION = 0.1;

    /**
     * Maximum allowed video duration.
     *
     * @var float
     */
    val MAX_DURATION = 15.0;

    /** {@inheritdoc} */
    public fun getTitle()
    {
        return 'direct';
    }

    /** {@inheritdoc} */
    public fun getMinDuration()
    {
        return self::MIN_DURATION;
    }

    /** {@inheritdoc} */
    public fun getMaxDuration()
    {
        return self::MAX_DURATION;
    }
}
