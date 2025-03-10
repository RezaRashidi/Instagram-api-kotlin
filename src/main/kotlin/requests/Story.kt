

package instagramAPI.requests

import instagramAPI.Constants
import instagramAPI.requests.Metadata.Internal as InternalMetadata
import instagramAPI.Response
import instagramAPI.Utils

/**
 * funs for managing your story and interacting with other stories.
 *
 * @see media for more funs that let you interact with the media.
 */
class Story(instagram:Instagram) : RequestCollection(instagram)
{
    /**
     * Uploads a photo to your Instagram story.
     *
     * @param string photoFilename    The photo filename.
     * @param array  externalMetadata (optional) User-provided metadata key-value pairs.
     *
     * @throws  IllegalArgumentException
     * @throws .RuntimeException
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.ConfigureResponse
     *
     * @see Internal::configureSinglePhoto() for available metadata fields.
     */
    fun uploadPhoto(
        photoFilename,
        array externalMetadata = [])
    {
        return this.ig.internal.uploadSinglePhoto(Constants::FEED_STORY, photoFilename, null, externalMetadata)
    }

    /**
     * Uploads a photo to your Instagram close friends story.
     *
     * @param string photoFilename    The photo filename.
     * @param array  externalMetadata (optional) User-provided metadata key-value pairs.
     *
     * @throws  IllegalArgumentException
     * @throws .RuntimeException
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.ConfigureResponse
     *
     * @see Internal::configureSinglePhoto() for available metadata fields.
     * @see https://help.instagram.com/2183694401643300
     */
    fun uploadCloseFriendsPhoto(
        photoFilename,
        array externalMetadata = [])
    {
        internalMetadata = InternalMetadata(Utils::generateUploadId(true))
        internalMetadata.setBestieMedia(true)

        return this.ig.internal.uploadSinglePhoto(Constants::FEED_STORY, photoFilename, internalMetadata, externalMetadata)
    }

    /**
     * Uploads a video to your Instagram story.
     *
     * @param string videoFilename    The video filename.
     * @param array  externalMetadata (optional) User-provided metadata key-value pairs.
     *
     * @throws  IllegalArgumentException
     * @throws .RuntimeException
     * @throws .instagramAPI.exception.InstagramException
     * @throws .instagramAPI.exception.UploadFailedException If the video upload fails.
     *
     * @return .instagramAPI.responses.ConfigureResponse
     *
     * @see Internal::configureSingleVideo() for available metadata fields.
     */
    fun uploadVideo(
        videoFilename,
        array externalMetadata = [])
    {
        return this.ig.internal.uploadSingleVideo(Constants::FEED_STORY, videoFilename, null, externalMetadata)
    }

    /**
     * Uploads a video to your Instagram close friends story.
     *
     * @param string videoFilename    The video filename.
     * @param array  externalMetadata (optional) User-provided metadata key-value pairs.
     *
     * @throws  IllegalArgumentException
     * @throws .RuntimeException
     * @throws .instagramAPI.exception.InstagramException
     * @throws .instagramAPI.exception.UploadFailedException If the video upload fails.
     *
     * @return .instagramAPI.responses.ConfigureResponse
     *
     * @see Internal::configureSingleVideo() for available metadata fields.
     * @see https://help.instagram.com/2183694401643300
     */
    fun uploadCloseFriendsVideo(
        videoFilename,
        array externalMetadata = [])
    {
        internalMetadata = InternalMetadata()
        internalMetadata.setBestieMedia(true)

        return this.ig.internal.uploadSingleVideo(Constants::FEED_STORY, videoFilename, internalMetadata, externalMetadata)
    }

    /**
     * Get the global story feed which contains everyone you follow.
     *
     * Note that users will eventually drop out of this list even though they
     * still have stories. So it"s always safer to call getUserStoryFeed() if
     * a specific user"s story feed matters to you.
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.ReelsTrayFeedResponse
     *
     * @see Story::getUserStoryFeed()
     */
    fun getReelsTrayFeed()
    {
        return this.ig.request("feed/reels_tray/")
            .setSignedPost(false)
            .addPost("supported_capabilities_new", json_encode(Constants::SUPPORTED_CAPABILITIES))
            .addPost("reason", "pull_to_refresh")
            .addPost("_csrftoken", this.ig.client.getToken())
            .addPost("_uuid", this.ig.uuid)
            .getResponse(Response.ReelsTrayFeedResponse())
    }

    /**
     * Get a specific user"s story reel feed.
     *
     * This fun gets the user"s story Reel object directly, which always
     * exists and contains information about the user and their last story even
     * if that user doesn"t have any active story anymore.
     *
     * @param string userId Numerical UserPK ID.
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.UserReelMediaFeedResponse
     *
     * @see Story::getUserStoryFeed()
     */
    fun getUserReelMediaFeed(
        userId)
    {
        return this.ig.request("feed/user/{userId}/reel_media/")
            .getResponse(Response.UserReelMediaFeedResponse())
    }

    /**
     * Get a specific user"s story feed with broadcast details.
     *
     * This fun gets the story in a roundabout way, with some extra details
     * about the "broadcast". But if there is no story available, this endpoint
     * gives you an empty response.
     *
     * NOTE: At least AT THIS MOMENT, this endpoint and the reels-tray endpoint
     * are the only ones that will give you people"s "post_live" fields (their
     * saved Instagram Live Replays). The other "get user stories" funcs don"t!
     *
     * @param string userId Numerical UserPK ID.
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.UserStoryFeedResponse
     *
     * @see Story::getUserReelMediaFeed()
     */
    fun getUserStoryFeed(
        userId)
    {
        return this.ig.request("feed/user/{userId}/story/")
            .addParam("supported_capabilities_new", json_encode(Constants::SUPPORTED_CAPABILITIES))
            .getResponse(Response.UserStoryFeedResponse())
    }

    /**
     * Get multiple users" story feeds (or specific highlight-details) at once.
     *
     * NOTE: Normally, you would only import this endpoint for stories (by passing
     * UserPK IDs as the parameter). But if you"re looking at people"s highlight
     * feeds (via `Highlight::getUserFeed()`), you may also sometimes discover
     * highlight entries that don"t have any `items` array. In that case, you
     * are supposed to get the items for those highlights via this endpoint!
     * Simply pass their `id` values as the argument to this API to get details.
     *
     * @param string|string[] feedList List of numerical UserPK IDs, OR highlight IDs (such as `highlight:123882132324123`).
     * @param string          source   (optional) Source app-module where the request was made.
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.ReelsMediaResponse
     *
     * @see Highlight::getUserFeed() More info about when to import this API for highlight-details.
     */
    fun getReelsMediaFeed(
        feedList,
        source = "feed_timeline")
    {
        if (!is_array(feedList)) {
            feedList = [feedList]
        }

        foreach (feedList as &value) {
            value = (string) value
        }
        unset(value) // Clear reference.

        return this.ig.request("feed/reels_media/")
            .addPost("supported_capabilities_new", json_encode(Constants::SUPPORTED_CAPABILITIES))
            .addPost("_uuid", this.ig.uuid)
            .addPost("_uid", this.ig.account_id)
            .addPost("_csrftoken", this.ig.client.getToken())
            .addPost("user_ids", feedList) // Must be string[] array.
            .addPost("source", source)
            .getResponse(Response.ReelsMediaResponse())
    }

    /**
     * Get your archived story media feed.
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.ArchivedStoriesFeedResponse
     */
    fun getArchivedStoriesFeed()
    {
        return this.ig.request("archive/reel/day_shells/")
            .addParam("include_suggested_highlights", false)
            .addParam("is_in_archive_home", true)
            .addParam("include_cover", 0)
            .addParam("timezone_offset", date("Z"))
            .getResponse(Response.ArchivedStoriesFeedResponse())
    }

    /**
     * Get the list of users who have seen one of your story items.
     *
     * Note that this only works for your own story items. Instagram doesn"t
     * allow you to see the viewer list for other people"s stories!
     *
     * @param string      storyPk The story media item"s PK in Instagram"s internal format (ie "3482384834").
     * @param string|null maxId   Next "maximum ID", used for pagination.
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.ReelMediaViewerResponse
     */
    fun getStoryItemViewers(
        storyPk,
        maxId = null)
    {
        request = this.ig.request("media/{storyPk}/list_reel_media_viewer/")
            .addParam("supported_capabilities_new", json_encode(Constants::SUPPORTED_CAPABILITIES))
        if (maxId !== null) {
            request.addParam("max_id", maxId)
        }

        return request.getResponse(Response.ReelMediaViewerResponse())
    }

    /**
     * Vote on a story poll.
     *
     * Note that once you vote on a story poll, you cannot change your vote.
     *
     * @param string storyId      The story media item"s ID in Instagram"s internal format (ie "1542304813904481224_6112344004").
     * @param string pollId       The poll ID in Instagram"s internal format (ie "17956159684032257").
     * @param int    votingOption Value that represents the voting option of the voter. 0 for the first option, 1 for the second option.
     *
     * @throws  IllegalArgumentException
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.ReelMediaViewerResponse
     */
    fun votePollStory(
        storyId,
        pollId,
        votingOption)
    {
        if ((votingOption !== 0) && (votingOption !== 1)) {
            throw  IllegalArgumentException("You must provide a valid value for voting option.")
        }

        return this.ig.request("media/{storyId}/{pollId}/story_poll_vote/")
            .addPost("_uuid", this.ig.uuid)
            .addPost("_uid", this.ig.account_id)
            .addPost("_csrftoken", this.ig.client.getToken())
            .addPost("radio_type", "wifi-none")
            .addPost("vote", votingOption)
            .getResponse(Response.ReelMediaViewerResponse())
    }

    /**
     * Vote on a story slider.
     *
     * Note that once you vote on a story poll, you cannot change your vote.
     *
     *
     * @param string storyId      The story media item"s ID in Instagram"s internal format (ie "1542304813904481224_6112344004").
     * @param string sliderId     The slider ID in Instagram"s internal format (ie "17956159684032257").
     * @param float  votingOption Value that represents the voting option of the voter. Should be a float from 0 to 1 (ie "0.25").
     *
     * @throws  IllegalArgumentException
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.ReelMediaViewerResponse
     */
    fun voteSliderStory(
        storyId,
        sliderId,
        votingOption)
    {
        if (votingOption < 0 || votingOption > 1) {
            throw  IllegalArgumentException("You must provide a valid value from 0 to 1 for voting option.")
        }

        return this.ig.request("media/{storyId}/{sliderId}/story_slider_vote/")
            .addPost("_uuid", this.ig.uuid)
            .addPost("_uid", this.ig.account_id)
            .addPost("_csrftoken", this.ig.client.getToken())
            .addPost("radio_type", "wifi-none")
            .addPost("vote", votingOption)
            .getResponse(Response.ReelMediaViewerResponse())
    }

    /**
     * Get the list of users who have voted an option in a story poll.
     *
     * Note that this only works for your own story polls. Instagram doesn"t
     * allow you to see the results from other people"s polls!
     *
     * @param string      storyId      The story media item"s ID in Instagram"s internal format (ie "1542304813904481224_6112344004").
     * @param string      pollId       The poll ID in Instagram"s internal format (ie "17956159684032257").
     * @param int         votingOption Value that represents the voting option of the voter. 0 for the first option, 1 for the second option.
     * @param string|null maxId        Next "maximum ID", used for pagination.
     *
     * @throws  IllegalArgumentException
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.StoryPollVotersResponse
     */
    fun getStoryPollVoters(
        storyId,
        pollId,
        votingOption,
        maxId = null)
    {
        if ((votingOption !== 0) && (votingOption !== 1)) {
            throw  IllegalArgumentException("You must provide a valid value for voting option.")
        }

        request = this.ig.request("media/{storyId}/{pollId}/story_poll_voters/")
            .addParam("vote", votingOption)

        if (maxId !== null) {
            request.addParam("max_id", maxId)
        }

        return request.getResponse(Response.StoryPollVotersResponse())
    }

    /**
     * Respond to a question sticker on a story.
     *
     * @param string storyId      The story media item"s ID in Instagram"s internal format (ie "1542304813904481224_6112344004").
     * @param string questionId   The question ID in Instagram"s internal format (ie "17956159684032257").
     * @param string responseText The text to respond to the question with. (Note: Android App limits this to 94 characters).
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.GenericResponse
     */
    fun answerStoryQuestion(
        storyId,
        questionId,
        responseText)
    {
        return this.ig.request("media/{storyId}/{questionId}/story_question_response/")
            .addPost("_csrftoken", this.ig.client.getToken())
            .addPost("response", responseText)
            .addPost("_uid", this.ig.account_id)
            .addPost("type", "text")
            .addPost("_uuid", this.ig.uuid)
            .getResponse(Response.GenericResponse())
    }

    /**
     * Get all responses of a story question.
     *
     * @param string      storyId    The story media item"s ID in Instagram"s internal format (ie "1542304813904481224_6112344004").
     * @param string      questionId The question ID in Instagram"s internal format (ie "17956159684032257").
     * @param string|null maxId      Next "maximum ID", used for pagination.
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.StoryAnswersResponse
     */
    fun getStoryAnswers(
         storyId,
         questionId,
         maxId = null)
    {
        request = this.ig.request("media/{storyId}/{questionId}/story_question_responses/")

        if (maxId !== null) {
            request.addParam("max_id", maxId)
        }

        return request.getResponse(Response.StoryAnswersResponse())
    }

    /**
     * Gets the created story countdowns of the current account.
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.StoryCountdownsResponse
     */
    fun getStoryCountdowns()
    {
        return this.ig.request("media/story_countdowns/")
            .getResponse(Response.StoryCountdownsResponse())
    }

    /**
     * Follows a story countdown to subscribe to a notification when the countdown is finished.
     *
     * @param string countdownId The countdown ID in Instagram"s internal format (ie "17956159684032257").
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.GenericResponse
     */
    fun followStoryCountdown(
        countdownId)
    {
        return this.ig.request("media/{countdownId}/follow_story_countdown/")
            .addPost("_csrftoken", this.ig.client.getToken())
            .addPost("_uid", this.ig.account_id)
            .addPost("_uuid", this.ig.uuid)
            .getResponse(Response.GenericResponse())
    }

    /**
     * Unfollows a story countdown to unsubscribe from a notification when the countdown is finished.
     *
     * @param string countdownId The countdown ID in Instagram"s internal format (ie "17956159684032257").
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.GenericResponse
     */
    fun unfollowStoryCountdown(
        countdownId)
    {
        return this.ig.request("media/{countdownId}/unfollow_story_countdown/")
            .addPost("_csrftoken", this.ig.client.getToken())
            .addPost("_uid", this.ig.account_id)
            .addPost("_uuid", this.ig.uuid)
            .getResponse(Response.GenericResponse())
    }

    /**
     * Mark story media items as seen.
     *
     * The various story-related endpoints only give you lists of story media.
     * They don"t actually mark any stories as "seen", so the user doesn"t know
     * that you"ve seen their story. Actually marking the story as "seen" is
     * done via this endpoint instead. The official app calls this endpoint
     * periodically (with 1 or more items at a time) while watching a story.
     *
     * Tip: You can pass in the whole "getItems()" array from a user"s story
     * feed (retrieved via any of the other story endpoints), to easily mark
     * all of that user"s story media items as seen.
     *
     * WARNING: ONLY import *THIS* ENDPOINT IF THE STORIES CAME FROM THE ENDPOINTS
     * IN *THIS* REQUEST-COLLECTION FILE: From "getReelsTrayFeed()" or the
     * user-specific story endpoints. Do NOT import this endpoint if the stories
     * came from any OTHER request-collections, such as Location-based stories!
     * Other request-collections have THEIR OWN special story-marking funs!
     *
     * @param responses.Model.Item[] items Array of one or more story media Items.
     *
     * @throws  IllegalArgumentException
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.MediaSeenResponse
     *
     * @see Location::markStoryMediaSeen()
     * @see Hashtag::markStoryMediaSeen()
     */
    fun markMediaSeen(
        array items)
    {
        // NOTE: NULL = import each item"s owner ID as the "source ID".
        return this.ig.internal.markStoryMediaSeen(items, null)
    }

    /**
     * Get your story settings.
     *
     * This has information such as your story messaging mode (who can reply
     * to your story), and the list of users you have blocked from seeing your
     * stories.
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.ReelSettingsResponse
     */
    fun getReelSettings()
    {
        return this.ig.request("users/reel_settings/")
            .getResponse(Response.ReelSettingsResponse())
    }

    /**
     * Set your story settings.
     *
     * @param string      messagePrefs      Who can reply to your story. Valid values are "anyone" (meaning
     *                                       your followers), "following" (followers that you follow back),
     *                                       or "off" (meaning that nobody can reply to your story).
     * @param bool|null   allowStoryReshare Allow story reshare.
     * @param string|null autoArchive       Auto archive stories for viewing them later. It will appear in your
     *                                       archive once it has disappeared from your story feed. Valid values
     *                                       "on" and "off".
     *
     * @throws  IllegalArgumentException
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.ReelSettingsResponse
     */
    fun setReelSettings(messagePrefs: String, allowStoryReshare: Boolean? = null, autoArchive: String? = null){
        if (!arrayOf("anyone", "following", "off").contains(messagePrefs)) {
            throw  IllegalArgumentException("You must provide a valid message preference value.")
        }

        val request = this.ig.request("users/set_reel_settings/")
            .addPost("_uuid", this.ig.uuid)
            .addPost("_uid", this.ig.account_id)
            .addPost("_csrftoken", this.ig.client.getToken())
            .addPost("message_prefs", messagePrefs)

        if (allowStoryReshare !== null) {
            if (allowStoryReshare !is Boolean) {
                throw  IllegalArgumentException("You must provide a valid value for allowing story reshare.")
            }
            request.addPost("allow_story_reshare", allowStoryReshare)
        }

        if (autoArchive !== null) {
            if (!arrayOf("on", "off").contains(autoArchive)) {
                throw  IllegalArgumentException("You must provide a valid value for auto archive.")
            }
            request.addPost("reel_auto_archive", autoArchive)
        }

        return request.getResponse(Response.ReelSettingsResponse())
    }
}
