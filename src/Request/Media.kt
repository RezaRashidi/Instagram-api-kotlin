package instagramAPI.Request

import instagramAPI.*

/**
 * funs for interacting with media items from yourself and others.
 *
 * @see Usertag for funs that let you tag people in media.
 */
class Media(instagram: Instagram) : RequestCollection(instagram) {
	/**
	 * Get detailed media information.
	 *
	 * @param string mediaId The media ID in Instagram"s internal format (ie "3482384834_43294").
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.MediaInfoResponse
	 */
	fun getInfo(mediaId:String) {
		return this.ig.request("media/{mediaId}/info/").getResponse(Response.MediaInfoResponse())
	}

	/**
	 * Delete a media item.
	 *
	 * @param string     mediaId   The media ID in Instagram"s internal format (ie "3482384834_43294").
	 * @param string|int mediaType The type of the media item you are deleting. One of: "PHOTO", "VIDEO"
	 *                              "CAROUSEL", or the raw value of the Item"s "getMediaType()" fun.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.MediaDeleteResponse
	 */
	fun delete(mediaId:String, mediaType:String = "PHOTO") {
		mediaType = Utils.checkMediaType(mediaType)

		return this.ig.request("media/{mediaId}/delete/").addParam("media_type", mediaType)
			.addPost("igtv_feed_preview", false).addPost("media_id", mediaId)
			.addPost("_csrftoken", this.ig.client.getToken()).addPost("_uid", this.ig.account_id)
			.addPost("_uuid", this.ig.uuid).getResponse(Response.MediaDeleteResponse())
	}

	/**
	 * Edit media.
	 *
	 * @param string     mediaId     The media ID in Instagram"s internal format (ie "3482384834_43294").
	 * @param string     captionText Caption to import for the media.
	 * @param array|null metadata    (optional) Associative array of optional metadata to edit:
	 *                                "usertags" - special array with user tagging instructions,
	 *                                if you want to modify the user tags
	 *                                "location" - a Location model object to set the media location,
	 *                                or boolean FALSE to remove any location from the media.
	 * @param string|int mediaType   The type of the media item you are editing. One of: "PHOTO", "VIDEO"
	 *                                "CAROUSEL", or the raw value of the Item"s "getMediaType()" fun.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.EditMediaResponse
	 *
	 * @see Usertag.tagMedia() for an example of proper "usertags" metadata formatting.
	 * @see Usertag.untagMedia() for an example of proper "usertags" metadata formatting.
	 */
	fun edit(mediaId:String, captionText:String = "", array metadata = null, mediaType:String = "PHOTO") {
		mediaType = Utils.checkMediaType(mediaType)

		request = this.ig.request("media/{mediaId}/edit_media/").addPost("_uuid", this.ig.uuid)
			.addPost("_uid", this.ig.account_id).addPost("_csrftoken", this.ig.client.getToken())
			.addPost("caption_text", captionText)

		if (isset(metadata["usertags"])) {
			Utils.throwIfInvalidUsertags(metadata["usertags"])
			request.addPost("usertags", json_encode(metadata["usertags"]))
		}

		if (isset(metadata["location"])) {
			if (metadata["location"] === false) {
				// The user wants to remove the current location from the media.
				request.addPost("location", "{}")
			} else {
				// The user wants to add/change the location of the media.
				if (!metadata["location"] instanceof Response.Model.Location) {
					throw IllegalArgumentException(
						"The " location " metadata value must be an instance of .instagramAPI.Response.Model.Location.")
				}

				request.addPost("location", Utils.buildMediaLocationJSON(metadata["location"]))
					.addPost("geotag_enabled", "1").addPost("posting_latitude", metadata["location"].getLat())
					.addPost("posting_longitude", metadata["location"].getLng())
					.addPost("media_latitude", metadata["location"].getLat())
					.addPost("media_longitude", metadata["location"].getLng())

				if (mediaType === "CAROUSEL") { // Albums need special handling.
					request.addPost("exif_latitude", 0.0).addPost("exif_longitude", 0.0)
				} else { // All other types of media import "av_" instead of "exif_".
					request.addPost("av_latitude", 0.0).addPost("av_longitude", 0.0)
				}
			}
		}

		return request.getResponse(Response.EditMediaResponse())
	}

	/**
	 * Like a media item.
	 *
	 * @param string mediaId   The media ID in Instagram"s internal format (ie "3482384834_43294").
	 * @param string module    (optional) From which app module (page) you"re performing this action.
	 * @param array  extraData (optional) Depending on the module name, additional data is required.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.GenericResponse
	 *
	 * @see Media._parseLikeParameters() For all supported modules and required parameters.
	 */
	fun like(mediaId:String, module:String = "feed_timeline", array extraData = []) {
		request =
			this.ig.request("media/{mediaId}/like/").addPost("_uuid", this.ig.uuid).addPost("_uid", this.ig.account_id)
				.addPost("_csrftoken", this.ig.client.getToken()).addPost("media_id", mediaId)
				.addPost("radio_type", "wifi-none").addPost("module_name", module)
				.addPost("device_id", this.ig.device_id)

		this._parseLikeParameters("like", request, module, extraData)

		return request.getResponse(Response.GenericResponse())
	}

	/**
	 * Unlike a media item.
	 *
	 * @param string mediaId   The media ID in Instagram"s internal format (ie "3482384834_43294").
	 * @param string module    (optional) From which app module (page) you"re performing this action.
	 * @param array  extraData (optional) Depending on the module name, additional data is required.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.GenericResponse
	 *
	 * @see Media._parseLikeParameters() For all supported modules and required parameters.
	 */
	fun unlike(mediaId:String, module:String = "feed_timeline", array extraData = []) {
		request = this.ig.request("media/{mediaId}/unlike/").addPost("_uuid", this.ig.uuid)
			.addPost("_uid", this.ig.account_id).addPost("_csrftoken", this.ig.client.getToken())
			.addPost("media_id", mediaId).addPost("radio_type", "wifi-none").addPost("module_name", module)

		this._parseLikeParameters("unlike", request, module, extraData)

		return request.getResponse(Response.GenericResponse())
	}

	/**
	 * Get feed of your liked media.
	 *
	 * @param string|null maxId Next "maximum ID", used for pagination.
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.LikeFeedResponse
	 */
	fun getLikedFeed(maxId:String? = null) {
		request = this.ig.request("feed/liked/")
		if (maxId !== null) {
			request.addParam("max_id", maxId)
		}

		return request.getResponse(Response.LikeFeedResponse())
	}

	/**
	 * Get list of users who liked a media item.
	 *
	 * @param string mediaId The media ID in Instagram"s internal format (ie "3482384834_43294").
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.MediaLikersResponse
	 */
	fun getLikers(mediaId:String) {
		return this.ig.request("media/{mediaId}/likers/").getResponse(Response.MediaLikersResponse())
	}

	/**
	 * Get a simplified, chronological list of users who liked a media item.
	 *
	 * WARNING! DANGEROUS! Although this fun works, we don"t know
	 * whether it"s used by Instagram"s app right now. If it isn"t used by
	 * the app, then you can easily get BANNED for using this fun!
	 *
	 * If you call this fun, you do that AT YOUR OWN RISK and you
	 * risk losing your Instagram account! This notice will be removed if
	 * the fun is safe to use. Otherwise this whole fun will
	 * be removed someday, if it wasn"t safe.
	 *
	 * Only import this if you are OK with possibly losing your account!
	 *
	 * TODO: Research when/if the official app calls this fun.
	 *
	 * @param string mediaId The media ID in Instagram"s internal format (ie "3482384834_43294").
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.MediaLikersResponse
	 */
	fun getLikersChrono(mediaId:String) {
		return this.ig.request("media/{mediaId}/likers_chrono/").getResponse(Response.MediaLikersResponse())
	}

	/**
	 * Enable comments for a media item.
	 *
	 * @param string mediaId The media ID in Instagram"s internal format (ie "3482384834_43294").
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.GenericResponse
	 */
	fun enableComments(mediaId:String) {
		return this.ig.request("media/{mediaId}/enable_comments/").addPost("_uuid", this.ig.uuid)
			.addPost("_csrftoken", this.ig.client.getToken()).setSignedPost(false)
			.getResponse(Response.GenericResponse())
	}

	/**
	 * Disable comments for a media item.
	 *
	 * @param string mediaId The media ID in Instagram"s internal format (ie "3482384834_43294").
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.GenericResponse
	 */
	fun disableComments(mediaId:String) {
		return this.ig.request("media/{mediaId}/disable_comments/").addPost("_uuid", this.ig.uuid)
			.addPost("_csrftoken", this.ig.client.getToken()).setSignedPost(false)
			.getResponse(Response.GenericResponse())
	}

	/**
	 * Post a comment on a media item.
	 *
	 * @param string mediaId        The media ID in Instagram"s internal format (ie "3482384834_43294").
	 * @param string commentText    Your comment text.
	 * @param string replyCommentId (optional) The comment ID you are replying to, if this is a reply (ie "17895795823020906")
	 *                               when replying, your commentText MUST contain an @-mention at the start (ie "@theirusername Hello!").
	 * @param string module         (optional) From which app module (page) you"re performing this action.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.CommentResponse
	 */
	fun comment(mediaId:String commentText:String, replyCommentId :String?= null, module:String = "comments_feed_timeline") {
		request = this.ig.request("media/{mediaId}/comment/")
			.addPost("user_breadcrumb", Utils.generateUserBreadcrumb(mb_strlen(commentText)))
			.addPost("idempotence_token", Signatures.generateUUID(true)).addPost("_uuid", this.ig.uuid)
			.addPost("_uid", this.ig.account_id).addPost("_csrftoken", this.ig.client.getToken())
			.addPost("comment_text", commentText).addPost("containermodule", module).addPost("radio_type", "wifi-none")
			.addPost("device_id", this.ig.device_id)

		if (replyCommentId !== null) {
			if (commentText[0] !== "@") {
				throw IllegalArgumentException(
					"When replying to a comment, your text must begin with an @-mention to their username.")
			}
			request.addPost("replied_to_comment_id", replyCommentId)
		}

		return request.getResponse(Response.CommentResponse())
	}

	/**
	 * Get media comments.
	 *
	 * Note that this endpoint supports both backwards and forwards pagination.
	 * The only one you should really care about is "max_id" for backwards
	 * ("load older comments") pagination in normal cases. By default, if no
	 * parameter is provided, Instagram gives you the latest page of comments
	 * and then paginates backwards via the "max_id" parameter (and the correct
	 * value for it is the "next_max_id" in the response).
	 *
	 * However, if you come to the comments "from a Push notification" (uses the
	 * "target_comment_id" parameter), then the response will ALSO contain a
	 * "next_min_id" value. In that case, you can get newer comments (than the
	 * target comment) by using THAT value and the "min_id" parameter instead.
	 *
	 * @param string mediaId The media ID in Instagram"s internal format (ie "3482384834_43294").
	 * @param array  options An associative array of optional parameters, including:
	 *                        "max_id" - next "maximum ID" (get older comments, before this ID), used for backwards pagination
	 *                        "min_id" - next "minimum ID" (get newer comments, after this ID), used for forwards pagination
	 *                        "target_comment_id" - used by comment Push notifications to retrieve the page with the specific comment.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.MediaCommentsResponse
	 */
	fun getComments(mediaId:String, array options = []) {
		request = this.ig.request("media/{mediaId}/comments/").addParam("can_support_threading", true)

		// Pagination.
		if (isset(options["min_id"]) && isset(options["max_id"])) {
			throw IllegalArgumentException(
				"You can import either " min_id " or " max_id ", but not both at the same time.")
		}
		if (isset(options["min_id"])) {
			request.addParam("min_id", options["min_id"])
		}
		if (isset(options["max_id"])) {
			request.addParam("max_id", options["max_id"])
		}

		// Request specific comment (does NOT work together with pagination!).
		// NOTE: If you try pagination params together with this param, then the
		// server will reject the request completely and give nothing back!
		if (isset(options["target_comment_id"])) {
			if (isset(options["min_id"]) || isset(options["max_id"])) {
				throw IllegalArgumentException(
					"You cannot import the " target_comment_id " parameter together with the " min_id " or " max_id " parameters.")
			}
			request.addParam("target_comment_id", options["target_comment_id"])
		}

		return request.getResponse(Response.MediaCommentsResponse())
	}

	/**
	 * Get the replies to a specific media comment.
	 *
	 * You should be sure that the comment actually HAS more replies before
	 * calling this endpoint! In that case, the comment itself will have a
	 * non-zero "child comment count" value, as well as some "preview comments".
	 *
	 * If the number of preview comments doesn"t match the full "child comments"
	 * count, then you are ready to call this endpoint to retrieve the rest of
	 * them. Do NOT call it frivolously for comments that have no child comments
	 * or where you already have all of them via the child comment previews!
	 *
	 * @param string mediaId   The media ID in Instagram"s internal format (ie "3482384834_43294").
	 * @param string commentId The parent comment"s ID.
	 * @param array  options   An associative array of optional parameters, including:
	 *                          "max_id" - next "maximum ID" (get older comments, before this ID), used for backwards pagination
	 *                          "min_id" - next "minimum ID" (get newer comments, after this ID), used for forwards pagination.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.MediaCommentRepliesResponse
	 */
	fun getCommentReplies(mediaId:String, commentId:String, array options = []) {
		request = this.ig.request("media/{mediaId}/comments/{commentId}/inline_child_comments/")

		if (isset(options["min_id"], options["max_id"])) {
			throw IllegalArgumentException(
				"You can import either " min_id " or " max_id ", but not both at the same time.")
		}

		if (isset(options["max_id"])) {
			request.addParam("max_id", options["max_id"])
		} elseif (isset(options["min_id"])) {
			request.addParam("min_id", options["min_id"])
		}

		return request.getResponse(Response.MediaCommentRepliesResponse())
	}

	/**
	 * Delete a comment.
	 *
	 * @param string mediaId   The media ID in Instagram"s internal format (ie "3482384834_43294").
	 * @param string commentId The comment"s ID.
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.DeleteCommentResponse
	 */
	fun deleteComment(mediaId:String, commentId:String) {
		return this.ig.request("media/{mediaId}/comment/{commentId}/delete/").addPost("_uuid", this.ig.uuid)
			.addPost("_uid", this.ig.account_id).addPost("_csrftoken", this.ig.client.getToken())
			.getResponse(Response.DeleteCommentResponse())
	}

	/**
	 * Delete multiple comments.
	 *
	 * @param string          mediaId    The media ID in Instagram"s internal format (ie "3482384834_43294").
	 * @param string|string[] commentIds The IDs of one or more comments to delete.
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.DeleteCommentResponse
	 */
	fun deleteComments(mediaId:String, commentIds) {
		if (is_array(commentIds)) {
			commentIds = implode(",", commentIds)
		}

		return this.ig.request("media/{mediaId}/comment/bulk_delete/").addPost("_uuid", this.ig.uuid)
			.addPost("_uid", this.ig.account_id).addPost("_csrftoken", this.ig.client.getToken())
			.addPost("comment_ids_to_delete", commentIds).getResponse(Response.DeleteCommentResponse())
	}

	/**
	 * Like a comment.
	 *
	 * @param string commentId The comment"s ID.
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.CommentLikeUnlikeResponse
	 */
	fun likeComment(commentId:String) {
		return this.ig.request("media/{commentId}/comment_like/").addPost("_uuid", this.ig.uuid)
			.addPost("_uid", this.ig.account_id).addPost("_csrftoken", this.ig.client.getToken())
			.getResponse(Response.CommentLikeUnlikeResponse())
	}

	/**
	 * Unlike a comment.
	 *
	 * @param string commentId The comment"s ID.
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.CommentLikeUnlikeResponse
	 */
	fun unlikeComment(commentId:String) {
		return this.ig.request("media/{commentId}/comment_unlike/").addPost("_uuid", this.ig.uuid)
			.addPost("_uid", this.ig.account_id).addPost("_csrftoken", this.ig.client.getToken())
			.getResponse(Response.CommentLikeUnlikeResponse())
	}

	/**
	 * Get list of users who liked a comment.
	 *
	 * @param string commentId The comment"s ID.
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.CommentLikersResponse
	 */
	fun getCommentLikers(commentId:String) {
		return this.ig.request("media/{commentId}/comment_likers/").getResponse(Response.CommentLikersResponse())
	}

	/**
	 * Translates comments and/or media captions.
	 *
	 * Note that the text will be translated to American English (en-US).
	 *
	 * @param string|string[] commentIds The IDs of one or more comments and/or media IDs
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.TranslateResponse
	 */
	fun translateComments(commentIds) {
		if (is_array(commentIds)) {
			commentIds = implode(",", commentIds)
		}

		return this.ig.request("language/bulk_translate/?comment_ids={commentIds}")
			.getResponse(Response.TranslateResponse())
	}

	/**
	 * Validate a web URL for acceptable import as external link.
	 *
	 * This endpoint lets you check if the URL is allowed by Instagram, and is
	 * helpful to call before you try to import a web URL in your media links.
	 *
	 * @param string url The URL you want to validate.
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.ValidateURLResponse
	 */
	fun validateURL(url:String) {
		return this.ig.request("media/validate_reel_url/").addPost("_uuid", this.ig.uuid)
			.addPost("_uid", this.ig.account_id).addPost("_csrftoken", this.ig.client.getToken()).addPost("url", url)
			.getResponse(Response.ValidateURLResponse())
	}

	/**
	 * Save a media item.
	 *
	 * @param string mediaId The media ID in Instagram"s internal format (ie "3482384834_43294").
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.SaveAndUnsaveMedia
	 */
	fun save(mediaId:String) {
		return this.ig.request("media/{mediaId}/save/").addPost("_uuid", this.ig.uuid)
			.addPost("_uid", this.ig.account_id).addPost("_csrftoken", this.ig.client.getToken())
			.getResponse(Response.SaveAndUnsaveMedia())
	}

	/**
	 * Unsave a media item.
	 *
	 * @param string mediaId The media ID in Instagram"s internal format (ie "3482384834_43294").
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.SaveAndUnsaveMedia
	 */
	fun unsave(mediaId:String) {
		return this.ig.request("media/{mediaId}/unsave/").addPost("_uuid", this.ig.uuid)
			.addPost("_uid", this.ig.account_id).addPost("_csrftoken", this.ig.client.getToken())
			.getResponse(Response.SaveAndUnsaveMedia())
	}

	/**
	 * Get saved media items feed.
	 *
	 * @param string|null maxId Next "maximum ID", used for pagination.
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.SavedFeedResponse
	 */
	fun getSavedFeed(maxId:String? = null) {
		request = this.ig.request("feed/saved/")
		if (maxId !== null) {
			request.addParam("max_id", maxId)
		}

		return request.getResponse(Response.SavedFeedResponse())
	}

	/**
	 * Get blocked media.
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.BlockedMediaResponse
	 */
	fun getBlockedMedia() {
		return this.ig.request("media/blocked/").getResponse(Response.BlockedMediaResponse())
	}

	/**
	 * Report media as spam.
	 *
	 * @param string mediaId    The media ID in Instagram"s internal format (ie "3482384834_43294").
	 * @param string sourceName (optional) Source of the media.
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.GenericResponse
	 */
	fun report(mediaId:String, sourceName :String= "feed_contextual_chain") {
		return this.ig.request("media/{mediaId}/flag_media/").addPost("media_id", mediaId)
			.addPost("source_name", sourceName).addPost("reason_id", "1").addPost("_uuid", this.ig.uuid)
			.addPost("_uid", this.ig.account_id).addPost("_csrftoken", this.ig.client.getToken())
			.getResponse(Response.GenericResponse())
	}

	/**
	 * Report a media comment as spam.
	 *
	 * @param string mediaId   The media ID in Instagram"s internal format (ie "3482384834_43294").
	 * @param string commentId The comment"s ID.
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.GenericResponse
	 */
	fun reportComment(mediaId:String, commentId:String) {
		return this.ig.request("media/{mediaId}/comment/{commentId}/flag/").addPost("media_id", mediaId)
			.addPost("comment_id", commentId).addPost("reason", "1").addPost("_uuid", this.ig.uuid)
			.addPost("_uid", this.ig.account_id).addPost("_csrftoken", this.ig.client.getToken())
			.getResponse(Response.GenericResponse())
	}

	/**
	 * Get media permalink.
	 *
	 * @param string mediaId The media ID in Instagram"s internal format (ie "3482384834_43294").
	 *
	 * @throws .instagramAPI.Exception.InstagramException
	 *
	 * @return .instagramAPI.Response.PermalinkResponse
	 */
	fun getPermalink(mediaId:String) {
		return this.ig.request("media/{mediaId}/permalink/").addParam("share_to_app", "copy_link")
			.getResponse(Response.PermalinkResponse())
	}

	/**
	 * Validate and update the parameters for a like or unlike request.
	 *
	 * @param string  type      What type of request this is (can be "like" or "unlike").
	 * @param Request request   The request to fill with the parsed data.
	 * @param string  module    From which app module (page) you"re performing this action.
	 * @param array   extraData Depending on the module name, additional data is required.
	 *
	 * @throws  IllegalArgumentException
	 */
	protected fun _parseLikeParameters(type:String,  request:Request, module:String, array extraData) {
		// Is this a "double-tap to like"? Note that Instagram doesn"t have
		// "double-tap to unlike". So this can only be "1" if it"s a "like".
		if (type === "like" && isset(extraData["double_tap"]) && extraData["double_tap"]) {
			request.addUnsignedPost("d", "1")
		} else {
			request.addUnsignedPost("d", "0") // Must always be 0 for "unlike".
		}

		// Now parse the necessary parameters for the selected module.
		switch(module) {
			case "feed_contextual_post": // "Explore" tab.
			if (isset(extraData["explore_source_token"])) {
				// The explore media `Item.getExploreSourceToken()` value.
				request.addPost("explore_source_token", extraData["explore_source_token"])
			} else {
				throw IllegalArgumentException(sprintf("Missing extra data for module " % s".", module))
			}
			break
			case "profile": // LIST VIEW (when posts are shown vertically by the app
			// one at a time (as in the Timeline tab)): Any media on
			// a user profile (their timeline) in list view mode.
			case "media_view_profile": // GRID VIEW (standard 3x3): Album (carousel)
			// on a user profile (their timeline).
			case "video_view_profile": // GRID VIEW (standard 3x3): Video on a user
			// profile (their timeline).
			case "photo_view_profile": // GRID VIEW (standard 3x3): Photo on a user
			// profile (their timeline).
			if (isset(extraData["username"]) && isset(extraData["user_id"])) {
				// Username and id of the media"s owner (the profile owner).
				request.addPost("username", extraData["username"]).addPost("user_id", extraData["user_id"])
			} else {
				throw IllegalArgumentException(sprintf("Missing extra data for module " % s".", module))
			}
			break
			case "feed_contextual_hashtag": // "Hashtag" search result.
			if (isset(extraData["hashtag"])) {
				// The hashtag where the app found this media.
				Utils.throwIfInvalidHashtag(extraData["hashtag"])
				request.addPost("hashtag", extraData["hashtag"])
			} else {
				throw IllegalArgumentException(sprintf("Missing extra data for module " % s".", module))
			}
			break
			case "feed_contextual_location": // "Location" search result.
			if (isset(extraData["location_id"])) {
				// The location ID of this media.
				request.addPost("location_id", extraData["location_id"])
			} else {
				throw IllegalArgumentException(sprintf("Missing extra data for module " % s".", module))
			}
			break
			case "feed_timeline": // "Timeline" tab (the global Home-feed with all
			// kinds of mixed news).
			case "newsfeed": // "Followings Activity" feed tab. Used when
			// liking/unliking a post that we clicked on from a
			// single-activity "xyz liked abc"s post" entry.
			case "feed_contextual_newsfeed_multi_media_liked":  // "Followings
			// Activity" feed
			// tab. Used when
			// liking/unliking a
			// post that we
			// clicked on from a
			// multi-activity
			// "xyz liked 5
			// posts" entry.
			break
			default:
			throw IllegalArgumentException(
				sprintf("Invalid module name. %s does not correspond to any of the valid module names.", module))
		}
	}
}
