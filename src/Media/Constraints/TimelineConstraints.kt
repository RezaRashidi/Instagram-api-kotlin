

package InstagramAPI.Media.Constraints

import InstagramAPI.Media.ConstraintsInterface

/**
 * Instagram's timeline general media constraints.
 */
class TimelineConstraints : ConstraintsInterface
{
    /**
     * Lowest allowed general media aspect ratio (4:5, meaning portrait).
     *
     * These are decided by Instagram. Not by us!
     *
     * @var float
     *
     * @see https://help.instagram.com/1469029763400082
     */
    val MIN_RATIO = 0.8

    /**
     * Highest allowed general media aspect ratio (1.91:1, meaning landscape).
     *
     * These are decided by Instagram. Not by us!
     *
     * @var float
     */
    val MAX_RATIO = 1.91

    /**
     * The recommended aspect ratio for timeline media.
     *
     * This creates square media, which is Instagram's "standard" format.
     *
     * @var float
     */
    val RECOMMENDED_RATIO = 1.0

    /**
     * The deviation for the recommended aspect ratio.
     *
     * We can always resize to 1:1, so we allow no deviation here.
     *
     * @var float
     */
    val RECOMMENDED_RATIO_DEVIATION = 0.0

    /**
     * Minimum allowed video duration.
     *
     * @var float
     *
     * @see https://help.instagram.com/270963803047681
     */
    val MIN_DURATION = 3.0

    /**
     * Maximum allowed video duration.
     *
     * @var float
     *
     * @see https://help.instagram.com/270963803047681
     */
    val MAX_DURATION = 60.0

    /** {@inheritdoc} */
    public fun getTitle()
    {
        return 'timeline'
    }

    /** {@inheritdoc} */
    public fun getMinAspectRatio()
    {
        return self::MIN_RATIO
    }

    /** {@inheritdoc} */
    public fun getMaxAspectRatio()
    {
        return self::MAX_RATIO
    }

    /** {@inheritdoc} */
    public fun getRecommendedRatio()
    {
        return self::RECOMMENDED_RATIO
    }

    /** {@inheritdoc} */
    public fun getRecommendedRatioDeviation()
    {
        return self::RECOMMENDED_RATIO_DEVIATION
    }

    /** {@inheritdoc} */
    public fun useRecommendedRatioByDefault()
    {
        return false
    }

    /** {@inheritdoc} */
    public fun getMinDuration()
    {
        return self::MIN_DURATION
    }

    /** {@inheritdoc} */
    public fun getMaxDuration()
    {
        return self::MAX_DURATION
    }
}
