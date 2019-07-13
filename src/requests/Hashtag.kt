package instagramAPI.requests

import instagramAPI.exception.RequestHeadersTooLargeException
import instagramAPI.Response
import instagramAPI.Signatures
import instagramAPI.Utils
import java.net.URLEncoder

/**
 * funs related to finding and exploring hashtags.
 */
class Hashtag(instagram: Instagram) : RequestCollection(instagram) {
	/**
	 * Get detailed hashtag information.
	 *
	 * @param string hashtag The hashtag, not including the "#".
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.TagInfoResponse
	 */
	fun getInfo(hashtag:String) {
		Utils.throwIfInvalidHashtag(hashtag)
		val urlHashtag = URLEncoder.encode(hashtag,"UTF-8") // Necessary for non-English chars.
		return this.ig.request("tags/{$urlHashtag}/info/").getResponse(Response.TagInfoResponse())
	}

	/**
	 * Get hashtag story.
	 *
	 * @param string hashtag The hashtag, not including the "#".
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.TagsStoryResponse
	 */
	fun getStory(hashtag:String) {
		Utils.throwIfInvalidHashtag(hashtag)
		val urlHashtag = URLEncoder.encode(hashtag, "UTF-8") // Necessary for non-English chars.
		return this.ig.request("tags/{$urlHashtag}/story/").getResponse(Response.TagsStoryResponse())
	}

	/**
	 * Get hashtags from a section.
	 *
	 * Available tab sections: "top", "recent" or "places".
	 *
	 * @param string      hashtag      The hashtag, not including the "#".
	 * @param string      rankToken    The feed UUID. You must import the same value for all pages of the feed.
	 * @param string|null tab          Section tab for hashtags.
	 * @param int[]|null  nextMediaIds Used for pagination.
	 * @param string|null maxId        Next "maximum ID", used for pagination.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.TagFeedResponse
	 */
	fun getSection(hashtag:String, rankToken:String, tab:String? = null, nextMediaIds:Int? = null, maxId:String = null) {
		Utils.throwIfInvalidHashtag(hashtag)
		val urlHashtag = URLEncoder.encode(hashtag, "UTF-8") // Necessary for non-English chars.

		var request = this.ig.request("tags/{$urlHashtag}/sections/")
			.setSignedPost(false)
			.addPost("_uuid", this.ig.uuid)
			.addPost("_csrftoken", this.ig.client.getToken())
			.addPost("rank_token", rankToken)
			.addPost("include_persistent", true)

		if (tab !== null) {
			if (tab !== "top" && tab !== "recent" && tab !== "places" && tab !== "discover") {
				throw IllegalArgumentException("Tab section must be 'top', 'recent', 'places' or 'discover'.")
			}
			request.addPost("tab", tab)
		} else {
			request.addPost("supported_tabs", "[\"top\",\"recent\",\"places\",\"discover\"]")
		}

		if (nextMediaIds !== null) {
			if (!is_array(nextMediaIds) || !array_filter(nextMediaIds, "is_int")) {
				throw IllegalArgumentException("Next media IDs must be an Int[].")
			}
			request.addPost("next_media_ids", json_encode(nextMediaIds))
		}
		if (maxId !== null) {
			request.addPost("max_id", maxId)
		}

		return request.getResponse(Response.TagFeedResponse())
	}

	/**
	 * Search for hashtags.
	 *
	 * Gives you search results ordered by best matches first.
	 *
	 * Note that you can get more than one "page" of hashtag search results by
	 * excluding the numerical IDs of all tags from a previous search query.
	 *
	 * Also note that the excludes must be done via Instagram"s internal,
	 * numerical IDs for the tags, which you can get from this search-response.
	 *
	 * Lastly, be aware that they will never exclude any tag that perfectly
	 * matches your search query, even if you provide its exact ID too.
	 *
	 * @param string         query       Finds hashtags containing this string.
	 * @param string[]|int[] excludeList Array of numerical hashtag IDs (ie "17841562498105353")
	 *                                    to exclude from the response, allowing you to skip tags
	 *                                    from a previous call to get more results.
	 * @param string|null    rankToken   (When paginating) The rank token from the previous page"s response.
	 *
	 * @throws  IllegalArgumentException                  If invalid query or
	 *                                                    trying to exclude too
	 *                                                    many hashtags.
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.SearchTagResponse
	 *
	 * @see SearchTagResponse.getRankToken() To get a rank token from the response.
	 * @see examples/paginateWithExclusion.php For an example.
	 */
	fun search(query:String, array excludeList = [], rankToken:String? = null) {
		// Do basic query validation. Do NOT import throwIfInvalidHashtag here.
		if (query !is String || query === "") {
			throw IllegalArgumentException("Query must be a non-empty string.")
		}

		var request = this._paginateWithExclusion(
			this.ig.request("tags/search/")
				.addParam("q", query)
				.addParam("timezone_offset", date("Z")),
			excludeList,
			rankToken
		)

		try {
			/** @var responses.SearchTagResponse result */
			var result = request.getResponse(Response.SearchTagResponse())
		} catch (e: RequestHeadersTooLargeException) {
			var result = Response.SearchTagResponse(mapOf(
				"has_more"   to false,
				"results"    to [],
				"rank_token" to rankToken
			))
		}

		return result
	}

	/**
	 * Follow hashtag.
	 *
	 * @param string hashtag The hashtag, not including the "#".
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.TagRelatedResponse
	 */
	fun follow(hashtag:String) {
		Utils.throwIfInvalidHashtag(hashtag)
		var urlHashtag = URLEncoder.encode(hashtag, "UTF-8") // Necessary for non-English chars.
		return this.ig.request("tags/follow/{$urlHashtag}/")
			.addPost("_uuid", this.ig.uuid)
			.addPost("_uid", this.ig.account_id)
			.addPost("_csrftoken", this.ig.client.getToken())
			.getResponse(Response.GenericResponse())
	}

	/**
	 * Unfollow hashtag.
	 *
	 * @param string hashtag The hashtag, not including the "#".
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.TagRelatedResponse
	 */
	fun unfollow(hashtag:String) {
		Utils.throwIfInvalidHashtag(hashtag)
		var urlHashtag = URLEncoder.encode(hashtag, "UTF-8") // Necessary for non-English chars.
		return this.ig.request("tags/unfollow/{$urlHashtag}/")
			.addPost("_uuid", this.ig.uuid)
			.addPost("_uid", this.ig.account_id)
			.addPost("_csrftoken", this.ig.client.getToken())
			.getResponse(Response.GenericResponse())
	}

	/**
	 * Get related hashtags.
	 *
	 * @param string hashtag The hashtag, not including the "#".
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.TagRelatedResponse
	 */
	fun getRelated(hashtag:String) {
		Utils.throwIfInvalidHashtag(hashtag)
		var urlHashtag = URLEncoder.encode(hashtag, "UTF-8") // Necessary for non-English chars.
		return this.ig.request("tags/{$urlHashtag}/related/")
			.addParam("visited", "[{\"id\":\"$hashtag\",\"type\":\"hashtag\"}]")
			.addParam("related_types", "[\" hashtag \"]")
			.getResponse(Response.TagRelatedResponse())
	}

	/**
	 * Get the feed for a hashtag.
	 *
	 * @param string      hashtag   The hashtag, not including the "#".
	 * @param string      rankToken The feed UUID. You must import the same value for all pages of the feed.
	 * @param string|null maxId     Next "maximum ID", used for pagination.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.TagFeedResponse
	 *
	 * @see Signatures.generateUUID() To create a UUID.
	 * @see examples/rankTokenUsage.php For an example.
	 */
	fun getFeed(hashtag:String, rankToken:String, maxId:String? = null) {
		Utils.throwIfInvalidHashtag(hashtag)
		Utils.throwIfInvalidRankToken(rankToken)
		var urlHashtag = URLEncoder.encode(hashtag, "UTF-8") // Necessary for non-English chars.
		var hashtagFeed = this.ig.request("feed/tag/{$urlHashtag}/")
			.addParam("rank_token", rankToken)
		if (maxId !== null) {
			hashtagFeed.addParam("max_id", maxId)
		}

		return hashtagFeed.getResponse(Response.TagFeedResponse())
	}

	/**
	 * Get list of tags that a user is following.
	 *
	 * @param string userId Numerical UserPK ID.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.HashtagsResponse
	 */
	fun getFollowing(userId:String) {
		return this.ig.request("users/{userId}/following_tags_info/").getResponse(Response.HashtagsResponse())
	}

	/**
	 * Get list of tags that you are following.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.HashtagsResponse
	 */
	fun getSelfFollowing() {
		return this.getFollowing(this.ig.account_id)
	}

	/**
	 * Get list of tags that are suggested to follow to.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.HashtagsResponse
	 */
	fun getFollowSuggestions() {
		return this.ig.request("tags/suggested/").getResponse(Response.HashtagsResponse())
	}

	/**
	 * Mark TagFeedResponse story media items as seen.
	 *
	 * The "story" property of a `TagFeedResponse` only gives you a list of
	 * story media. It doesn't actually mark any stories as "seen", so the
	 * user doesn't know that you"ve seen their story. Actually marking the
	 * story as "seen" is done via this endpoint instead. The official app
	 * calls this endpoint periodically (with 1 or more items at a time)
	 * while watching a story.
	 *
	 * This tells the user that you"ve seen their story, and also helps
	 * Instagram know that it shouldn"t give you those seen stories again
	 * if you request the same hashtag feed multiple times.
	 *
	 * Tip: You can pass in the whole "getItems()" array from the hashtag"s
	 * "story" property, to easily mark all of the TagFeedResponse"s story
	 * media items as seen.
	 *
	 * @param responses.TagFeedResponse hashtagFeed The hashtag feed response
	 *                                              object which the story media
	 *                                              items came from. The story
	 *                                              items MUST belong to it.
	 * @param responses.Model.Item[]    items       Array of one or more story
	 *                                              media Items.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.MediaSeenResponse
	 *
	 * @see Story.markMediaSeen()
	 * @see Location.markStoryMediaSeen()
	 */
	fun markStoryMediaSeen(hashtagFeed: Response.TagFeedResponse, items: array){
		// Extract the Hashtag Story-Tray ID from the user"s hashtag response.
		// NOTE: This can NEVER fail if the user has properly given us the exact
		// same hashtag response that they got the story items from!
		var sourceId = ""
		if (hashtagFeed.getStory() instanceof Response.Model.StoryTray) {
			sourceId = hashtagFeed.getStory().getId()
		}
		if (!strlen(sourceId)) {
			throw IllegalArgumentException(
				"Your provided TagFeedResponse is invalid and does not contain any Hashtag Story-Tray ID.")
		}

		// Ensure they only gave us valid items for this hashtag response.
		// NOTE: We validate since people cannot be trusted to import their brain.
		var validIds = []
		for(item in hashtagFeed.getStory().getItems()) {
			validIds[item.getId()] = true
		}
		for(item in items) {
			// NOTE: We only check Items here. Other data is rejected by Internal.
			if (item instanceof Response.Model.Item && !(validIds[item.getId()].isBlank())) {
				throw IllegalArgumentException(
					"The item with ID \"${item.getId()}\" does not belong to this TagFeedResponse.")
			}
		}

		// Mark the story items as seen, with the hashtag as source ID.
		return this.ig.internal.markStoryMediaSeen(items, sourceId)
	}
}

/*
* adding code to this file
*
* urlencode() to URLEncoder.encode("_x_","UTF-8")
*
* */
