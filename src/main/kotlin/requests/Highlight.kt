package instagramAPI.requests

import instagramAPI.Constants
import instagramAPI.Response

/**
 * funs related to creating and managing highlights of your media.
 */
class Highlight(instagram: Instagram) : RequestCollection(instagram) {
	/**
	 * Get highlight feed.
	 *
	 * NOTE: Sometimes, a highlight doesn"t have any `items` property. To get
	 * the list of items in that situation, you must submit its `id` (such as
	 * `highlight:123882132324123`) to the `Story::getReelsMediaFeed()` API.
	 *
	 * @param string userId Numerical UserPK ID.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.HighlightFeedResponse
	 *
	 * @see Story::getReelsMediaFeed() To get highlight items when they aren"t included in this response.
	 */
	fun getUserFeed(userId:String) {
		return this.ig.request("highlights/{userId}/highlights_tray/")
			.addParam("supported_capabilities_new", json_encode(Constants::SUPPORTED_CAPABILITIES))
			.addParam("phone_id", this.ig.phone_id).addParam("battery_level", "100").addParam("is_charging", "1")
			.addParam("will_sound_on", "1").getResponse(Response.HighlightFeedResponse())
	}

	/**
	 * Get self highlight feed.
	 *
	 * NOTE: Sometimes, a highlight doesn"t have any `items` property. Read
	 * `Highlight::getUserFeed()` for more information about what to do.
	 * Note 2: if user has a igtv post reponse will include "tv_channel" property
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.HighlightFeedResponse
	 *
	 * @see Highlight::getUserFeed()
	 * @see Story::getReelsMediaFeed() To get highlight items when they aren"t included in this response.
	 */
	fun getSelfUserFeed() {
		return this.getUserFeed(this.ig.account_id)
	}

	/**
	 * Create a highlight reel.
	 *
	 * @param string[]    mediaIds     Array with one or more media IDs in Instagram"s internal format (ie ["3482384834_43294"]).
	 * @param string      title        Title for the highlight.
	 * @param string|null coverMediaId One media ID in Instagram"s internal format (ie "3482384834_43294").
	 * @param string      module
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.CreateHighlightResponse
	 */
	fun create(array mediaIds, title:String = "Highlights", coverMediaId:String? = null, module:String = "self_profile") {
		if (empty(mediaIds)) {
			throw IllegalArgumentException("You must provide at least one media ID.")
		}
		if (coverMediaId === null) {
			coverMediaId = reset(mediaIds)
		}
		if (title === null || title === "") {
			title = "Highlights"
		} elseif (mb_strlen(title, "utf8") > 16) {
			throw IllegalArgumentException("Title must be between 1 and 16 characters.")
		}

		cover = ["media_id"  => coverMediaId,
		]

		return this.ig.request("highlights/create_reel/")
			.addPost("supported_capabilities_new", json_encode(Constants::SUPPORTED_CAPABILITIES))
			.addPost("source", module).addPost("creation_id", round(microtime(true) * 1000))
			.addPost("_csrftoken", this.ig.client.getToken()).addPost("_uid", this.ig.account_id)
			.addPost("_uuid", this.ig.uuid).addPost("cover", json_encode(cover)).addPost("title", title)
			.addPost("media_ids", json_encode(array_values(mediaIds))).getResponse(Response.CreateHighlightResponse())
	}

	/**
	 * Edit a highlight reel.
	 *
	 * @param string highlightReelId Highlight ID, using internal format (ie "highlight:12345678901234567").
	 * @param array  params          User-provided highlight key-value pairs. string "title", string "cover_media_id", string[] "add_media", string[] "remove_media".
	 * @param string module          (optional) From which app module (page) you"re performing this action.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.HighlightFeedResponse
	 */
	fun edit(highlightReelId:String, array params, module:String = "self_profile") {
		if (!isset(params["cover_media_id"])) {
			throw IllegalArgumentException("You must provide one media ID for the cover.")
		}
		if (!isset(params["title"])) {
			params["title"] = "Highlights"
		} elseif (mb_strlen(params["title"], "utf8") > 16) {
			throw IllegalArgumentException("Title length must be between 1 and 16 characters.")
		}
		if (!isset(params["add_media"]) || !is_array(params["add_media"])) {
			params["add_media"] = []
		}
		if (!isset(params["remove_media"]) || !is_array(params["remove_media"])) {
			params["remove_media"] = []
		}
		cover = ["media_id"  => params["cover_media_id"],
		]

		return this.ig.request("highlights/{highlightReelId}/edit_reel/")
			.addPost("supported_capabilities_new", json_encode(Constants::SUPPORTED_CAPABILITIES))
			.addPost("source", module).addPost("_uuid", this.ig.uuid).addPost("_uid", this.ig.account_id)
			.addPost("_csrftoken", this.ig.client.getToken()).addPost("title", params["title"])
			.addPost("cover", json_encode(cover))
			.addPost("added_media_ids", json_encode(array_values(params["add_media"])))
			.addPost("removed_media_ids", json_encode(array_values(params["remove_media"])))
			.getResponse(Response.HighlightFeedResponse())
	}

	/**
	 * Delete a highlight reel.
	 *
	 * @param string highlightReelId Highlight ID, using internal format (ie "highlight:12345678901234567").
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.GenericResponse
	 */
	fun delete(highlightReelId:String) {
		return this.ig.request("highlights/{highlightReelId}/delete_reel/").addPost("_uuid", this.ig.uuid)
			.addPost("_uid", this.ig.account_id).addPost("_csrftoken", this.ig.client.getToken())
			.getResponse(Response.GenericResponse())
	}
}
