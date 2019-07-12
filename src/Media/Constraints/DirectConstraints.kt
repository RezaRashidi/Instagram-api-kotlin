

package instagramAPI.Media.Constraints

/**
 * Instagram"s direct messaging general media constraints.
 */
class DirectConstraints : TimelineConstraints() {
    /**
     * Minimum allowed video duration.
     *
     * @var float
     */
    val MIN_DURATION: Float = 0.1f

    /**
     * Maximum allowed video duration.
     *
     * @var float
     */
    val MAX_DURATION: Float = 15.0f

    /** {@inheritdoc} */
    fun getTitle(): String {
        return "direct"
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
