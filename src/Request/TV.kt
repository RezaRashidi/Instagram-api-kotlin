

package instagramAPI.Request

import instagramAPI.Constants
import instagramAPI.Response

/**
 * funs related to Instagram TV.
 */
class TV(instagram:Instagram) : RequestCollection(instagram)
{
    /**
     * Get Instagram TV guide.
     *
     * It provides a catalogue of popular and suggested channels.
     *
     * @throws .instagramAPI.Exception.InstagramException
     *
     * @return .instagramAPI.Response.TVGuideResponse
     */
    fun getTvGuide()
    {
        return this.ig.request("igtv/tv_guide/")
            .getResponse(Response.TVGuideResponse())
    }

    /**
     * Get channel.
     *
     * You can filter the channel with different IDs: "for_you", "chrono_following", "popular", "continue_watching"
     * and using a user ID in the following format: "user_1234567891".
     *
     * @param string      id    ID used to filter channels.
     * @param string|null maxId Next "maximum ID", used for pagination.
     *
     * @throws  IllegalArgumentException
     * @throws .instagramAPI.Exception.InstagramException
     *
     * @return .instagramAPI.Response.TVChannelsResponse
     */
    fun getChannel(id: String = "for_you", maxId: String? = null){
        if (!arrayOf("for_you", "chrono_following", "popular", "continue_watching").contains(id)
        && !preg_match("/^user_[1-9].d*/", id)) {
            throw  IllegalArgumentException("Invalid ID type.")
        }

        request = this.ig.request("igtv/channel/")
            .addPost("id", id)
            .addPost("_uuid", this.ig.uuid)
            .addPost("_uid", this.ig.account_id)
            .addPost("_csrftoken", this.ig.client.getToken())

        if (maxId !== null) {
            request.addPost("max_id", maxId)
        }

        return request.getResponse(Response.TVChannelsResponse())
    }

    /**
     * Uploads a video to your Instagram TV.
     *
     * @param string videoFilename    The video filename.
     * @param array  externalMetadata (optional) User-provided metadata key-value pairs.
     *
     * @throws  IllegalArgumentException
     * @throws .RuntimeException
     * @throws .instagramAPI.Exception.InstagramException
     * @throws .instagramAPI.Exception.UploadFailedException If the video upload fails.
     *
     * @return .instagramAPI.Response.ConfigureResponse
     *
     * @see Internal::configureSingleVideo() for available metadata fields.
     */
    fun uploadVideo(
        videoFilename,
        array externalMetadata = [])
    {
        return this.ig.internal.uploadSingleVideo(Constants::FEED_TV, videoFilename, null, externalMetadata)
    }

    /**
     * Searches for channels.
     *
     * @param string query The username or channel you are looking for.
     *
     * @throws .instagramAPI.Exception.InstagramException
     *
     * @return .instagramAPI.Response.TVSearchResponse
     */
    fun search(
        query = "")
    {
        if (query !== "") {
            endpoint = "igtv/search/"
        } else {
            endpoint = "igtv/suggested_searches/"
        }

        return this.ig.request(endpoint)
            .addParam("query", query)
            .getResponse(Response.TVSearchResponse())
    }

    /**
     * Write seen state on a video.
     *
     * @param string impression      Format: 1813637917462151382
     * @param int    viewProgress    Video view progress in seconds.
     * @param mixed  gridImpressions TODO No info yet.
     *
     * @throws  IllegalArgumentException
     * @throws .instagramAPI.Exception.InstagramException
     *
     * @return .instagramAPI.Response.GenericResponse
     */
    fun writeSeenState(
        impression,
        viewProgress = 0,
        gridImpressions = [])
    {
        if (!(viewProgress.toIntOrNull() && viewProgress > 0) && (viewProgress !is Int || viewProgress < 0)) {
            throw  IllegalArgumentException("View progress must be a positive integer.")
        }

        seenState = json_encode([
            "impressions"       => [
                impression => [
                    "view_progress_s"   => viewProgress,
                ],
            ],
            "grid_impressions"  => gridImpressions,
        ])

        return this.ig.request("igtv/write_seen_state/")
            .addPost("seen_state", seenState)
            .addPost("_uuid", this.ig.uuid)
            .addPost("_uid", this.ig.account_id)
            .addPost("_csrftoken", this.ig.client.getToken())
            .getResponse(Response.GenericResponse())
    }
}
