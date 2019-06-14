package InstagramAPI.Request

import InstagramAPI.Response
import InstagramAPI.Signatures
import InstagramAPI.Utils

/**
 * funs for exploring and interacting with live broadcasts.
 */
class Live(instagram: Instagram) : RequestCollection(instagram) {
	/**
	 * Get suggested broadcasts.
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.SuggestedBroadcastsResponse
	 */
	fun getSuggestedBroadcasts() {
		endpoint = "live/get_suggested_broadcasts/"
		if (this.ig.isExperimentEnabled("ig_android_live_suggested_live_expansion", "is_enabled")) {
			endpoint = "live/get_suggested_live_and_post_live/"
		}

		return this.ig.request(endpoint).getResponse(Response.SuggestedBroadcastsResponse())
	}

	/**
	 * Get broadcast information.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.BroadcastInfoResponse
	 */
	fun getInfo(broadcastId:String) {
		return this.ig.request("live/{broadcastId}/info/").getResponse(Response.BroadcastInfoResponse())
	}

	/**
	 * Get the viewer list of a broadcast.
	 *
	 * WARNING: You MUST be the owner of the broadcast. Otherwise Instagram won"t send any API reply!
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.ViewerListResponse
	 */
	fun getViewerList(broadcastId:String) {
		return this.ig.request("live/{broadcastId}/get_viewer_list/").getResponse(Response.ViewerListResponse())
	}

	/**
	 * Get the final viewer list of a broadcast after it has ended.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.FinalViewerListResponse
	 */
	fun getFinalViewerList(broadcastId:String) {
		return this.ig.request("live/{broadcastId}/get_final_viewer_list/")
			.getResponse(Response.FinalViewerListResponse())
	}

	/**
	 * Get the viewer list of a post-live (saved replay) broadcast.
	 *
	 * @param string      broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 * @param string|null maxId       Next "maximum ID", used for pagination.
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.PostLiveViewerListResponse
	 */
	fun getPostLiveViewerList(broadcastId:String, maxId:String? = null) {
		request = this.ig.request("live/{broadcastId}/get_post_live_viewers_list/")
		if (maxId !== null) {
			request.addParam("max_id", maxId)
		}

		return request.getResponse(Response.PostLiveViewerListResponse())
	}

	/**
	 * Get a live broadcast"s heartbeat and viewer count.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.BroadcastHeartbeatAndViewerCountResponse
	 */
	fun getHeartbeatAndViewerCount(broadcastId:String) {
		return this.ig.request("live/{broadcastId}/heartbeat_and_get_viewer_count/").setSignedPost(false)
			.addPost("_uuid", this.ig.uuid).addPost("_csrftoken", this.ig.client.getToken())
			.addPost("offset_to_video_start", 0).getResponse(Response.BroadcastHeartbeatAndViewerCountResponse())
	}

	/**
	 * Get a live broadcast"s join request counts.
	 *
	 * Note: This request **will** return null if there have been no pending
	 * join requests have been made. Please have your code check for null.
	 *
	 * @param string broadcastId    The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 * @param int    lastTotalCount Last join request count (optional).
	 * @param int    lastSeenTs     Last seen timestamp (optional).
	 * @param int    lastFetchTs    Last fetch timestamp (optional).
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.BroadcastJoinRequestCountResponse|null
	 */
	fun getJoinRequestCounts(broadcastId:String, lastTotalCount:Int = 0, lastSeenTs:Int = 0, lastFetchTs:Int = 0) {
		try {
			return this.ig.request("live/{broadcastId}/get_join_request_counts/")
				.addParam("last_total_count", lastTotalCount).addParam("last_seen_ts", lastSeenTs)
				.addParam("last_fetch_ts", lastFetchTs).getResponse(Response.BroadcastJoinRequestCountResponse())
		} catch (.InstagramAPI. Exception . EmptyResponseException e) {
			return null
		}
	}

	/**
	 * Show question in a live broadcast.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 * @param string questionId  The question ID in Instagram"s internal format (ie "17854587811139572").
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.GenericResponse
	 */
	fun showQuestion(broadcastId:String, questionId:String) {
		return this.ig.request("live/{broadcastId}/question/{questionId}/activate/").setSignedPost(false)
			.addPost("_uuid", this.ig.uuid).addPost("_csrftoken", this.ig.client.getToken())
			.getResponse(Response.GenericResponse())
	}

	/**
	 * Hide question in a live broadcast.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 * @param string questionId  The question ID in Instagram"s internal format (ie "17854587811139572").
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.GenericResponse
	 */
	fun hideQuestion(broadcastId:String, questionId:String) {
		return this.ig.request("live/{broadcastId}/question/{questionId}/deactivate/").setSignedPost(false)
			.addPost("_uuid", this.ig.uuid).addPost("_csrftoken", this.ig.client.getToken())
			.getResponse(Response.GenericResponse())
	}

	/**
	 * Asks a question to the host of the broadcast.
	 *
	 * Note: This fun is only used by the viewers of a broadcast.
	 *
	 * @param string broadcastId  The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 * @param string questionText Your question text.
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.GenericResponse
	 */
	fun question(broadcastI:String, questionText:String) {
		return this.ig.request("live/{broadcastId}/questions").setSignedPost(false)
			.addPost("_csrftoken", this.ig.client.getToken()).addPost("text", questionText)
			.addPost("_uuid", this.ig.uuid).getResponse(Response.GenericResponse())
	}

	/**
	 * Get all received responses from a story question.
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.BroadcastQuestionsResponse
	 */
	fun getQuestions() {
		return this.ig.request("live/get_questions/").getResponse(Response.BroadcastQuestionsResponse())
	}

	/**
	 * Get all received responses from the current broadcast and a story question.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.BroadcastQuestionsResponse
	 */
	fun getLiveBroadcastQuestions(broadcastId:String) {
		return this.ig.request("live/{broadcastId}/questions/").addParam("sources", "story_and_live")
			.getResponse(Response.BroadcastQuestionsResponse())
	}

	/**
	 * Acknowledges (waves at) a user after they join.
	 *
	 * Note: This can only be done once to a user, per stream. Additionally, the user must have joined the stream.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 * @param string viewerId    Numerical UserPK ID of the user to wave to.
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.GenericResponse
	 */
	fun wave(broadcastId:String, viewerId:String) {
		return this.ig.request("live/{broadcastId}/wave/").addPost("viewer_id", viewerId)
			.addPost("_csrftoken", this.ig.client.getToken()).addPost("_uid", this.ig.account_id)
			.addPost("_uuid", this.ig.uuid).getResponse(Response.GenericResponse())
	}

	/**
	 * Post a comment to a live broadcast.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 * @param string commentText Your comment text.
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.CommentBroadcastResponse
	 */
	fun comment(broadcastId:String, commentText:String) {
		return this.ig.request("live/{broadcastId}/comment/")
			.addPost("user_breadcrumb", Utils::generateUserBreadcrumb(mb_strlen(commentText)))
			.addPost("idempotence_token", Signatures::generateUUID(true)).addPost("comment_text", commentText)
			.addPost("live_or_vod", 1).addPost("offset_to_video_start", 0)
			.getResponse(Response.CommentBroadcastResponse())
	}

	/**
	 * Pin a comment on live broadcast.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 * @param string commentId   Target comment ID.
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.PinCommentBroadcastResponse
	 */
	fun pinComment(broadcastId:String, commentId:String) {
		return this.ig.request("live/{broadcastId}/pin_comment/").addPost("offset_to_video_start", 0)
			.addPost("comment_id", commentId).addPost("_uuid", this.ig.uuid).addPost("_uid", this.ig.account_id)
			.addPost("_csrftoken", this.ig.client.getToken()).getResponse(Response.PinCommentBroadcastResponse())
	}

	/**
	 * Unpin a comment on live broadcast.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 * @param string commentId   Pinned comment ID.
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.UnpinCommentBroadcastResponse
	 */
	fun unpinComment(broadcastId:String, commentId:String) {
		return this.ig.request("live/{broadcastId}/unpin_comment/").addPost("offset_to_video_start", 0)
			.addPost("comment_id", commentId).addPost("_uuid", this.ig.uuid).addPost("_uid", this.ig.account_id)
			.addPost("_csrftoken", this.ig.client.getToken()).getResponse(Response.UnpinCommentBroadcastResponse())
	}

	/**
	 * Get broadcast comments.
	 *
	 * @param string broadcastId       The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 * @param int    lastCommentTs     Last comments timestamp (optional).
	 * @param int    commentsRequested Number of comments requested (optional).
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.BroadcastCommentsResponse
	 */
	fun getComments(broadcastId:String, lastCommentTs:Int = 0, commentsRequested:Int = 3) {
		return this.ig.request("live/{broadcastId}/get_comment/").addParam("last_comment_ts", lastCommentTs)
			.addParam("num_comments_requested", commentsRequested).getResponse(Response.BroadcastCommentsResponse())
	}

	/**
	 * Get post-live (saved replay) broadcast comments.
	 *
	 * @param string broadcastId    The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 * @param int    startingOffset (optional) The time-offset to start at when retrieving the comments.
	 * @param string encodingTag    (optional) TODO: ?.
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.PostLiveCommentsResponse
	 */
	fun getPostLiveComments(broadcastId:String, startingOffset:Int = 0, encodingTag:String = "instagram_dash_remuxed") {
		return this.ig.request("live/{broadcastId}/get_post_live_comments/").addParam("starting_offset", startingOffset)
			.addParam("encoding_tag", encodingTag).getResponse(Response.PostLiveCommentsResponse())
	}

	/**
	 * Enable viewer comments on your live broadcast.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.EnableDisableLiveCommentsResponse
	 */
	fun enableComments(broadcastId:String) {
		return this.ig.request("live/{broadcastId}/unmute_comment/").addPost("_uid", this.ig.account_id)
			.addPost("_uuid", this.ig.uuid).addPost("_csrftoken", this.ig.client.getToken())
			.getResponse(Response.EnableDisableLiveCommentsResponse())
	}

	/**
	 * Disable viewer comments on your live broadcast.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.EnableDisableLiveCommentsResponse
	 */
	fun disableComments(broadcastId:String) {
		return this.ig.request("live/{broadcastId}/mute_comment/").addPost("_uid", this.ig.account_id)
			.addPost("_uuid", this.ig.uuid).addPost("_csrftoken", this.ig.client.getToken())
			.getResponse(Response.EnableDisableLiveCommentsResponse())
	}

	/**
	 * Like a broadcast.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 * @param int    likeCount   Number of likes ("hearts") to send (optional).
	 *
	 * @throws . IllegalArgumentException
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.BroadcastLikeResponse
	 */
	fun like(broadcastId:String, likeCount:Int = 1) {
		if (likeCount < 1 || likeCount > 6) {
			throw. IllegalArgumentException("Like count must be a number from 1 to 6.")
		}

		return this.ig.request("live/{broadcastId}/like/").addPost("_uuid", this.ig.uuid)
			.addPost("_uid", this.ig.account_id).addPost("_csrftoken", this.ig.client.getToken())
			.addPost("user_like_count", likeCount).getResponse(Response.BroadcastLikeResponse())
	}

	/**
	 * Get a live broadcast"s like count.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 * @param int    likeTs      Like timestamp.
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.BroadcastLikeCountResponse
	 */
	fun getLikeCount(broadcastId:String, likeTs:Int = 0) {
		return this.ig.request("live/{broadcastId}/get_like_count/").addParam("like_ts", likeTs)
			.getResponse(Response.BroadcastLikeCountResponse())
	}

	/**
	 * Get post-live (saved replay) broadcast likes.
	 *
	 * @param string broadcastId    The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 * @param int    startingOffset (optional) The time-offset to start at when retrieving the likes.
	 * @param string encodingTag    (optional) TODO: ?.
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.PostLiveLikesResponse
	 */
	fun getPostLiveLikes(broadcastId:String, startingOffset:Int = 0, encodingTag:String  = "instagram_dash_remuxed") {
		return this.ig.request("live/{broadcastId}/get_post_live_likes/").addParam("starting_offset", startingOffset)
			.addParam("encoding_tag", encodingTag).getResponse(Response.PostLiveLikesResponse())
	}

	/**
	 * Create a live broadcast.
	 *
	 * Read the description of `start()` for proper usage.
	 *
	 * @param int previewWidth  (optional) Width.
	 * @param int previewHeight (optional) Height.
	 *
	 * @throws . IllegalArgumentException
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.CreateLiveResponse
	 *
	 * @see Live::start()
	 * @see Live::end()
	 */
	fun create(previewWidth :Int= 720, previewHeight:Int = 1184) {
		return this.ig.request("live/create/").setSignedPost(false).addPost("_uuid", this.ig.uuid)
			.addPost("_csrftoken", this.ig.client.getToken()).addPost("preview_height", previewHeight)
			.addPost("preview_width", previewWidth).addPost("broadcast_message", "")
			.addPost("broadcast_type", "RTMP_SWAP_ENABLED").addPost("internal_only", 0)
			.getResponse(Response.CreateLiveResponse())
	}

	/**
	 * Start a live broadcast.
	 *
	 * Note that you MUST first call `create()` to get a broadcast-ID and its
	 * RTMP upload-URL. Next, simply begin sending your actual video broadcast
	 * to the stream-upload URL. And then call `start()` with the broadcast-ID
	 * to make the stream available to viewers.
	 *
	 * Also note that broadcasting to the video stream URL must be done via
	 * other software, since it ISN"T (and won"t be) handled by this library!
	 *
	 * Lastly, note that stopping the stream is done either via RTMP signals,
	 * which your broadcasting software MUST output properly (FFmpeg DOESN"T do
	 * it without special patching!), OR by calling the `end()` fun.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.StartLiveResponse
	 *
	 * @see Live::create()
	 * @see Live::end()
	 */
	fun start(broadcastId:String) {
		response = this.ig.request("live/{broadcastId}/start/").setSignedPost(false).addPost("_uuid", this.ig.uuid)
			.addPost("_csrftoken", this.ig.client.getToken()).getResponse(Response.StartLiveResponse())

		if (this.ig.isExperimentEnabled("ig_android_live_qa_broadcaster_v1_universe", "is_enabled")) {
			this.ig.request("live/{broadcastId}/question_status/").setSignedPost(false)
				.addPost("_csrftoken", this.ig.client.getToken()).addPost("_uuid", this.ig.uuid)
				.addPost("allow_question_submission", true).getResponse(Response.GenericResponse())
		}

		return response
	}

	/**
	 * Acknowledges a copyright warning from Instagram after detected via a heartbeat request.
	 *
	 * `NOTE:` It is recommended that you view the `liveBroadcast` example
	 * to see the proper usage of this fun.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.GenericResponse
	 */
	fun resumeBroadcastAfterContentMatch(broadcastId:String) {
		return this.ig.request("live/{broadcastId}/resume_broadcast_after_content_match/")
			.addPost("_csrftoken", this.ig.client.getToken()).addPost("_uid", this.ig.account_id)
			.addPost("_uuid", this.ig.uuid).getResponse(Response.GenericResponse())
	}

	/**
	 * End a live broadcast.
	 *
	 * `NOTE:` To end your broadcast, you MUST import the `broadcast_id` value
	 * which was assigned to you in the `create()` response.
	 *
	 * @param string broadcastId      The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 * @param bool   copyrightWarning True when broadcast is ended via a copyright notice (optional).
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.GenericResponse
	 *
	 * @see Live::create()
	 * @see Live::start()
	 */
	fun end(broadcastId:String, copyrightWarning:Boolean = false) {
		return this.ig.request("live/{broadcastId}/end_broadcast/").addPost("_uid", this.ig.account_id)
			.addPost("_uuid", this.ig.uuid).addPost("_csrftoken", this.ig.client.getToken())
			.addPost("end_after_copyright_warning", copyrightWarning).getResponse(Response.GenericResponse())
	}

	/**
	 * Add a finished broadcast to your post-live feed (saved replay).
	 *
	 * The broadcast must have ended before you can call this fun.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.GenericResponse
	 */
	fun addToPostLive(broadcastId:String) {
		return this.ig.request("live/{broadcastId}/add_to_post_live/").addPost("_uid", this.ig.account_id)
			.addPost("_uuid", this.ig.uuid).addPost("_csrftoken", this.ig.client.getToken())
			.getResponse(Response.GenericResponse())
	}

	/**
	 * Delete a saved post-live broadcast.
	 *
	 * @param string broadcastId The broadcast ID in Instagram"s internal format (ie "17854587811139572").
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.GenericResponse
	 */
	fun deletePostLive(broadcastId:String) {
		return this.ig.request("live/{broadcastId}/delete_post_live/").addPost("_uid", this.ig.account_id)
			.addPost("_uuid", this.ig.uuid).addPost("_csrftoken", this.ig.client.getToken())
			.getResponse(Response.GenericResponse())
	}
}
