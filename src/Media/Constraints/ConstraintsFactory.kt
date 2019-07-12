

package instagramAPI.Media.Constraints

import instagramAPI.Constants

class ConstraintsFactory
{
    /**
     * Create a constraints set for a given target.
     *
     * @param int $targetFeed one of the FEED_X constants.
     *
     * @return ConstraintsInterface
     *
     * @see Constants
     */
    companion object{
         fun createFor( targetFeed: Int): ConstraintsInterface{

            return when (targetFeed) {
                Constants.FEED_STORY        -> StoryConstraints()
                Constants.FEED_DIRECT       -> DirectConstraints()
                Constants.FEED_DIRECT_STORY -> DirectStoryConstraints()
                Constants.FEED_TV           -> TvConstraints()
                Constants.FEED_TIMELINE_ALBUM -> AlbumConstraints()
                Constants.FEED_TIMELINE     -> TimelineConstraints()
                else                        -> TimelineConstraints()
            }
        }
    }
}
