

package InstagramAPI.Media.Constraints

/**
 * Instagram's direct messaging story media constraints.
 */
class DirectStoryConstraints : StoryConstraints
{
    /** {@inheritdoc} */
    public fun getTitle()
    {
        return 'direct story'
    }

    /** {@inheritdoc} */
    public fun getMinDuration()
    {
        return DirectConstraints::MIN_DURATION
    }

    /** {@inheritdoc} */
    public fun getMaxDuration()
    {
        return DirectConstraints::MAX_DURATION
    }
}
