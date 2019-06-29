

package InstagramAPI.Media

/**
 * Represents Instagram"s media constraints.
 */
interface ConstraintsInterface
{
    /**
     * Get the title for exception messages.
     *
     * @return string
     */
    fun getTitle(): String

    /**
     * Get the minimum allowed media aspect ratio.
     *
     * @return float
     */
    fun getMinAspectRatio(): Float

    /**
     * Get the maximum allowed media aspect ratio.
     *
     * @return float
     */
    fun getMaxAspectRatio(): Float

    /**
     * Get the recommended media aspect ratio.
     *
     * @return float
     */
    fun getRecommendedRatio(): Float

    /**
     * Get the deviation for recommended media aspect ratio.
     *
     * @return float
     */
    fun getRecommendedRatioDeviation(): Float

    /**
     * Whether to import the recommended media aspect ratio by default.
     *
     * @return bool
     */
    fun useRecommendedRatioByDefault(): Boolean

    /**
     * Get the minimum allowed video duration.
     *
     * @return float
     */
    fun getMinDuration(): Float

    /**
     * Get the maximum allowed video duration.
     *
     * @return float
     */
    fun getMaxDuration(): Float
}
