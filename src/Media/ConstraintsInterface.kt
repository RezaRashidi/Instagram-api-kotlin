

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
    public fun getTitle()

    /**
     * Get the minimum allowed media aspect ratio.
     *
     * @return float
     */
    public fun getMinAspectRatio()

    /**
     * Get the maximum allowed media aspect ratio.
     *
     * @return float
     */
    public fun getMaxAspectRatio()

    /**
     * Get the recommended media aspect ratio.
     *
     * @return float
     */
    public fun getRecommendedRatio()

    /**
     * Get the deviation for recommended media aspect ratio.
     *
     * @return float
     */
    public fun getRecommendedRatioDeviation()

    /**
     * Whether to import the recommended media aspect ratio by default.
     *
     * @return bool
     */
    public fun useRecommendedRatioByDefault()

    /**
     * Get the minimum allowed video duration.
     *
     * @return float
     */
    public fun getMinDuration()

    /**
     * Get the maximum allowed video duration.
     *
     * @return float
     */
    public fun getMaxDuration()
}
