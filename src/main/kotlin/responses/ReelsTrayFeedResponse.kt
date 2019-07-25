

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Broadcast
import instagramAPI.responses.model.PostLive
import instagramAPI.responses.model.StoryTray
import instagramAPI.responses.model.TraySuggestions

/**
 * ReelsTrayFeedResponse.
 *
 * @method model.Broadcast[] getBroadcasts()
 * @method int getFaceFilterNuxVersion()
 * @method bool getHasNewNuxStory()
 * @method mixed getMessage()
 * @method model.PostLive getPostLive()
 * @method string getStatus()
 * @method int getStickerVersion()
 * @method bool getStoriesViewerGesturesNuxEligible()
 * @method string getStoryRankingToken()
 * @method model.TraySuggestions[] getSuggestions()
 * @method model.StoryTray[] getTray()
 * @method model._Message[] get_Messages()
 * @method bool isBroadcasts()
 * @method bool isFaceFilterNuxVersion()
 * @method bool isHasNewNuxStory()
 * @method bool isMessage()
 * @method bool isPostLive()
 * @method bool isStatus()
 * @method bool isStickerVersion()
 * @method bool isStoriesViewerGesturesNuxEligible()
 * @method bool isStoryRankingToken()
 * @method bool isSuggestions()
 * @method bool isTray()
 * @method bool is_Messages()
 * @method this setBroadcasts(model.Broadcast[] $value)
 * @method this setFaceFilterNuxVersion(int $value)
 * @method this setHasNewNuxStory(bool $value)
 * @method this setMessage(mixed $value)
 * @method this setPostLive(model.PostLive $value)
 * @method this setStatus(string $value)
 * @method this setStickerVersion(int $value)
 * @method this setStoriesViewerGesturesNuxEligible(bool $value)
 * @method this setStoryRankingToken(string $value)
 * @method this setSuggestions(model.TraySuggestions[] $value)
 * @method this setTray(model.StoryTray[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetBroadcasts()
 * @method this unsetFaceFilterNuxVersion()
 * @method this unsetHasNewNuxStory()
 * @method this unsetMessage()
 * @method this unsetPostLive()
 * @method this unsetStatus()
 * @method this unsetStickerVersion()
 * @method this unsetStoriesViewerGesturesNuxEligible()
 * @method this unsetStoryRankingToken()
 * @method this unsetSuggestions()
 * @method this unsetTray()
 * @method this unset_Messages()
 */
data class ReelsTrayFeedResponse (
    val story_ranking_token                  : String,
    val broadcasts                           : MutableList<Broadcast>,
    val tray                                 : MutableList<StoryTray>,
    val post_live                            : PostLive,
    val sticker_version                      : Int,
    val face_filter_nux_version              : Int,
    val stories_viewer_gestures_nux_eligible : Boolean,
    val has_new_nux_story                    : Boolean,
    val suggestions                          : MutableList<TraySuggestions>
){
//    val JSON_PROPERTY_MAP = [
//        "story_ranking_token"                  => "string",
//        "broadcasts"                           => "model.Broadcast[]",
//        "tray"                                 => "model.StoryTray[]",
//        "post_live"                            => "model.PostLive",
//        "sticker_version"                      => "int",
//        "face_filter_nux_version"              => "int",
//        "stories_viewer_gestures_nux_eligible" => "bool",
//        "has_new_nux_story"                    => "bool",
//        "suggestions"                          => "model.TraySuggestions[]",
//    ]
}
