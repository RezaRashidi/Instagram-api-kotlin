

package InstagramAPI.Media.Constraints

import InstagramAPI.Constants
import InstagramAPI.Media.ConstraintsInterface

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
    public static fun createFor(
        $targetFeed)
    {
        switch ($targetFeed) {
            case Constants::FEED_STORY:
                $result = StoryConstraints()
                break
            case Constants::FEED_DIRECT:
                $result = DirectConstraints()
                break
            case Constants::FEED_DIRECT_STORY:
                $result = DirectStoryConstraints()
                break
            case Constants::FEED_TV:
                $result = TvConstraints()
                break
            case Constants::FEED_TIMELINE_ALBUM:
                $result = AlbumConstraints()
                break
            case Constants::FEED_TIMELINE:
            default:
                $result = TimelineConstraints()
        }

        return $result
    }
}
