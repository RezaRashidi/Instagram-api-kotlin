

package InstagramAPI.Media.Constraints

/**
 * Instagram"s Tv media constraints.
 */
class TvConstraints : StoryConstraints
{
    /**
     * Lowest allowed aspect ratio.
     *
     * It is controlled by ig_android_igtv_aspect_ratio_limits/min_aspect_ratio experiment.
     *
     * // TODO import the experiment.
     *
     * @var float
     */
    val MIN_RATIO: Float = 0.5f

    /**
     * Highest allowed aspect ratio.
     *
     * It is controlled by ig_android_igtv_aspect_ratio_limits/max_aspect_ratio experiment.
     *
     * // TODO import the experiment.
     *
     * @var float
     */
    val MAX_RATIO: Float = 0.8f

    /**
     * Minimum allowed video duration.
     *
     * It is controlled by ig_android_felix_video_upload_length/minimum_duration experiment.
     *
     * // TODO import the experiment.
     *
     * @var float
     */
    val MIN_DURATION: Float = 15.0f

    /**
     * Maximum allowed video duration.
     *
     * It is controlled by ig_android_felix_video_upload_length/maximum_duration experiment.
     *
     * // TODO import the experiment.
     *
     * @var float
     */
    val MAX_DURATION: Float = 600.0f

    /** {@inheritdoc} */
    fun getTitle(): String {
        return "TV"
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
    fun getMinDuration(): Float {
        return MIN_DURATION
    }

    /** {@inheritdoc} */
    fun getMaxDuration(): Float {
        return MAX_DURATION
    }
}
