

package instagramAPI.Media.Constraints

/**
 * Instagram"s direct messaging story media constraints.
 */
class DirectStoryConstraints : StoryConstraints{
    /** {@inheritdoc} */
    fun getTitle(): String {
        return "direct story"
    }

    /** {@inheritdoc} */
    fun getMinDuration(): Float {
        return DirectConstraints.MIN_DURATION
    }

    /** {@inheritdoc} */
    fun getMaxDuration(): Float {
        return DirectConstraints.MAX_DURATION
    }
}
