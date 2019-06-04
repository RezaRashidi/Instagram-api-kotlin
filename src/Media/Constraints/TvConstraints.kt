

package InstagramAPI.Media.Constraints

/**
 * Instagram's Tv media constraints.
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
    val MIN_RATIO = 0.5

    /**
     * Highest allowed aspect ratio.
     *
     * It is controlled by ig_android_igtv_aspect_ratio_limits/max_aspect_ratio experiment.
     *
     * // TODO import the experiment.
     *
     * @var float
     */
    val MAX_RATIO = 0.8

    /**
     * Minimum allowed video duration.
     *
     * It is controlled by ig_android_felix_video_upload_length/minimum_duration experiment.
     *
     * // TODO import the experiment.
     *
     * @var float
     */
    val MIN_DURATION = 15.0

    /**
     * Maximum allowed video duration.
     *
     * It is controlled by ig_android_felix_video_upload_length/maximum_duration experiment.
     *
     * // TODO import the experiment.
     *
     * @var float
     */
    val MAX_DURATION = 600.0

    /** {@inheritdoc} */
    public fun getTitle()
    {
        return 'TV'
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
