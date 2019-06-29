

package InstagramAPI.Media.Constraints

import InstagramAPI.Media.ConstraintsInterface

/**
 * Instagram"s timeline general media constraints.
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
    val MIN_RATIO: Float = 0.8f

    /**
     * Highest allowed general media aspect ratio (1.91:1, meaning landscape).
     *
     * These are decided by Instagram. Not by us!
     *
     * @var float
     */
    val MAX_RATIO: Float = 1.91f

    /**
     * The recommended aspect ratio for timeline media.
     *
     * This creates square media, which is Instagram"s "standard" format.
     *
     * @var float
     */
    val RECOMMENDED_RATIO: Float = 1.0f

    /**
     * The deviation for the recommended aspect ratio.
     *
     * We can always resize to 1:1, so we allow no deviation here.
     *
     * @var float
     */
    val RECOMMENDED_RATIO_DEVIATION: Float = 0.0f

    /**
     * Minimum allowed video duration.
     *
     * @var float
     *
     * @see https://help.instagram.com/270963803047681
     */
    val MIN_DURATION: Float = 3.0f

    /**
     * Maximum allowed video duration.
     *
     * @var float
     *
     * @see https://help.instagram.com/270963803047681
     */
    val MAX_DURATION: Float = 60.0f

    /** {@inheritdoc} */
    fun getTitle(): String {
        return "timeline"
    }

    /** {@inheritdoc} */
    fun getMinAspectRatio(): Float {
        return MIN_RATIO
    }

    /** {@inheritdoc} */
    fun getMaxAspectRatio(): Float {
        return MAX_RATIO
    }

    /** {@inheritdoc} */
    fun getRecommendedRatio(): Float {
        return RECOMMENDED_RATIO
    }

    /** {@inheritdoc} */
    fun getRecommendedRatioDeviation(): Float {
        return RECOMMENDED_RATIO_DEVIATION
    }

    /** {@inheritdoc} */
    fun useRecommendedRatioByDefault(): Boolean {
        return false
    }

    /** {@inheritdoc} */
    fun getMinDuration(): Float {
        return MIN_DURATION
    }

    /** {@inheritdoc} */
    fun getMaxDuration(): Float {
        return MAX_DURATION
    }
}
