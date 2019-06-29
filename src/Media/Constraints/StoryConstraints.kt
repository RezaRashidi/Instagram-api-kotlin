

package InstagramAPI.Media.Constraints

import InstagramAPI.Media.ConstraintsInterface

/**
 * Instagram"s story media constraints.
 */
class StoryConstraints : ConstraintsInterface{
    /**
     * Lowest allowed story aspect ratio.
     *
     * This range was decided through community research, which revealed that
     * all Instagram stories are in ~9:16 (0.5625, widescreen portrait) ratio,
     * with a small range of similar portrait ratios also being used sometimes.
     *
     * We have selected a photo/video story aspect range which supports all
     * story media aspects that are commonly used by the app: 0.56 - 0.67.
     * (That"s ~1080x1611 to ~1080x1928.)
     *
     * However, if you"re using our media processing classes, please note that
     * they will target the "best story aspect ratio range" by default, and
     * that you must manually disable that constructor option in the class
     * to get this extended story aspect range, if you REALLY want it...
     *
     * @var float
     *
     * @see https://github.com/mgp25/Instagram-API/issues/1420#issuecomment-318146010
     */
    val MIN_RATIO: Float = 0.56f

    /**
     * Highest allowed story aspect ratio.
     *
     * This range was decided through community research.
     *
     * @var float
     */
    val MAX_RATIO: Float = 0.67f

    /**
     * The recommended story aspect ratio.
     *
     * This is exactly 9:16 ratio, meaning a standard widescreen phone viewed in
     * portrait mode. It is the most common story ratio on Instagram, and it"s
     * the one that looks the best on most devices. All other ratios will look
     * "cropped" when viewed on 16:9 widescreen devices, since the app "zooms"
     * the story until it fills the screen without any black bars. So unless the
     * story is exactly 16:9, it won"t look great on 16:9 screens.
     *
     * Every manufacturer uses 16:9 screens. Even Apple since the iPhone 5.
     *
     * Therefore, this will be the final target aspect ratio used EVERY time
     * that media destined for a story feed is outside of the allowed range!
     * That"s becaimport it doesn"t make sense to let people target non-9:16 final
     * story aspect ratios, since only 9:16 stories look good on most devices!
     *
     * @var float
     */
    val RECOMMENDED_RATIO: Float = 0.5625f

    /**
     * The deviation for the recommended aspect ratio.
     *
     * We need to allow a bit above/below it to prevent pointless processing
     * when the media is a few pixels off from the perfect ratio, since the
     * perfect story ratio is often impossible to hit unless the input media
     * is already exactly 720x1280 or 1080x1920.
     *
     * @var float
     */
    val RECOMMENDED_RATIO_DEVIATION: Float = 0.0025f

    /**
     * Minimum allowed video duration.
     *
     * @var float
     */
    val MIN_DURATION: Float = 1.0f

    /**
     * Maximum allowed video duration.
     *
     * @var float
     */
    val MAX_DURATION: Float = 15.0f

    /** {@inheritdoc} */
    fun getTitle(): String {
        return "story"
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
        return true
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
