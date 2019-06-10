package InstagramAPI.Request

import InstagramAPI.Exception.RequestHeadersTooLargeException
import InstagramAPI.Response
import InstagramAPI.Signatures
import InstagramAPI.Utils

/**
 * funs related to finding and exploring locations.
 */
class Location(instagram: Instagram) : RequestCollection(instagram) {
	/**
	 * Search for nearby Instagram locations by geographical coordinates.
	 *
	 * NOTE: The locations found by this endpoint can be used for attaching
	 * locations to media uploads. This is the endpoint used by the real app!
	 *
	 * @param string      latitude  Latitude.
	 * @param string      longitude Longitude.
	 * @param string|null query     (optional) If provided, Instagram does a
	 *                               worldwide location text search, but lists
	 *                               locations closest to your lat/lng first.
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.LocationResponse
	 */
	fun search(latitude:String, longitude:String, query:String? = null) {
		locations = this.ig.request("location_search/")
			.addParam("rank_token", this.ig.account_id."_".Signatures.generateUUID()).addParam("latitude", latitude)
			.addParam("longitude", longitude)

		if (query === null) {
			locations.addParam("timestamp", time())
		} else {
			locations.addParam("search_query", query)
		}

		return locations.getResponse(Response.LocationResponse())
	}

	/**
	 * Search for Facebook locations by name.
	 *
	 * WARNING: The locations found by this fun DO NOT work for attaching
	 * locations to media uploads. import Location.search() instead!
	 *
	 * @param string         query       Finds locations containing this string.
	 * @param string[]|int[] excludeList Array of numerical location IDs (ie "17841562498105353")
	 *                                    to exclude from the response, allowing you to skip locations
	 *                                    from a previous call to get more results.
	 * @param string|null    rankToken   (When paginating) The rank token from the previous page"s response.
	 *
	 * @throws .InvalidArgumentException
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.FBLocationResponse
	 *
	 * @see FBLocationResponse.getRankToken() To get a rank token from the response.
	 * @see examples/paginateWithExclusion.php For an example.
	 */
	fun findPlaces(query:String, array excludeList = [], rankToken:String? = null) {
		// Do basic query validation. Do NOT import throwIfInvalidHashtag here.
		if (!is_string(query) || query === null) {
			throw.InvalidArgumentException("Query must be a non-empty string.")
		}
		location = this._paginateWithExclusion(
			this.ig.request("fbsearch/places/").addParam("timezone_offset", date("Z")).addParam("query", query),
			excludeList, rankToken)

		try {
			/** @var Response.FBLocationResponse result */
			result = location.getResponse(Response.FBLocationResponse())
		} catch (RequestHeadersTooLargeException e) {
			result = Response.FBLocationResponse(["has_more"   => false,
			"items"      => [],
			"rank_token" => rankToken,
			])
		}

		return result
	}

	/**
	 * Search for Facebook locations by geographical location.
	 *
	 * WARNING: The locations found by this fun DO NOT work for attaching
	 * locations to media uploads. import Location.search() instead!
	 *
	 * @param string         latitude    Latitude.
	 * @param string         longitude   Longitude.
	 * @param string|null    query       (Optional) Finds locations containing this string.
	 * @param string[]|int[] excludeList Array of numerical location IDs (ie "17841562498105353")
	 *                                    to exclude from the response, allowing you to skip locations
	 *                                    from a previous call to get more results.
	 * @param string|null    rankToken   (When paginating) The rank token from the previous page"s response.
	 *
	 * @throws .InvalidArgumentException
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.FBLocationResponse
	 *
	 * @see FBLocationResponse.getRankToken() To get a rank token from the response.
	 * @see examples/paginateWithExclusion.php For an example.
	 */
	fun findPlacesNearby(latitude:String, longitude:String, query:String? = null, excludeList = [], rankToken:String? = null) {
		location = this._paginateWithExclusion(
			this.ig.request("fbsearch/places/").addParam("lat", latitude).addParam("lng", longitude).addParam(
					"timezone_offset", date("Z")), excludeList, rankToken, 50)

		if (query !== null) {
			location.addParam("query", query)
		}

		try {
			/** @var Response.FBLocationResponse() result */
			result = location.getResponse(Response.FBLocationResponse())
		} catch (RequestHeadersTooLargeException e) {
			result = Response.FBLocationResponse(["has_more"   => false,
			"items"      => [],
			"rank_token" => rankToken,
			])
		}

		return result
	}

	/**
	 * Get related locations by location ID.
	 *
	 * Note that this endpoint almost never succeeds, becaimport most locations do
	 * not have ANY related locations!
	 *
	 * @param string locationId The internal ID of a location (from a field
	 *                           such as "pk", "external_id" or "facebook_places_id").
	 *
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.RelatedLocationResponse
	 */
	fun getRelated(locationId:String) {
		return this.ig.request("locations/{locationId}/related/")
			.addParam("visited", json_encode(["id" => locationId, "type" => "location"]))
		.addParam("related_types", json_encode(["location"])).getResponse(Response.RelatedLocationResponse())
	}

	/**
	 * Get the media feed for a location.
	 *
	 * Note that if your location is a "group" (such as a city), the feed will
	 * include media from multiple locations within that area. But if your
	 * location is a very specific place such as a specific night club, it will
	 * usually only include media from that exact location.
	 *
	 * @param string      locationId   The internal ID of a location (from a field
	 *                                  such as "pk", "external_id" or "facebook_places_id").
	 * @param string      rankToken    The feed UUID. import must import the same value for all pages of the feed.
	 * @param string|null tab          Section tab for locations. Values: "ranked" and "recent"
	 * @param int[]|null  nextMediaIds Used for pagination.
	 * @param int|null    nextPage     Used for pagination.
	 * @param string|null maxId        Next "maximum ID", used for pagination.
	 *
	 * @throws .InvalidArgumentException
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.LocationFeedResponse
	 *
	 * @see Signatures.generateUUID() To create a UUID.
	 * @see examples/rankTokenUsage.php For an example.
	 */
	fun getFeed(locationId:String, rankToken:String, tab:String? = "ranked", nextMediaIds:Int? = null, nextPage:Int? =
		null, maxId:Int? = null) {
		Utils.throwIfInvalidRankToken(rankToken)
		if (tab !== "ranked" && tab !== "recent") {
			throw.InvalidArgumentException("The provided section tab is invalid.")
		}

		locationFeed =
			this.ig.request("locations/{locationId}/sections/").setSignedPost(false).addPost("rank_token", rankToken)
				.addPost("_uuid", this.ig.uuid).addPost("_csrftoken", this.ig.client.getToken())
				.addPost("session_id", this.ig.session_id).addPost("tab", tab)

		if (nextMediaIds !== null) {
			if (!is_array(nextMediaIds) || !array_filter(nextMediaIds, "is_int")) {
				throw.InvalidArgumentException("Next media IDs must be an Int[].")
			}
			locationFeed.addPost("next_media_ids", json_encode(nextMediaIds))
		}

		if (nextPage !== null) {
			locationFeed.addPost("page", nextPage)
		}

		if (maxId !== null) {
			locationFeed.addPost("max_id", maxId)
		}

		return locationFeed.getResponse(Response.LocationFeedResponse())
	}

	/**
	 * Get the story feed for a location.
	 *
	 * @param string locationId The internal ID of a location (from a field
	 *                           such as "pk", "external_id" or "facebook_places_id").
	 *
	 * @throws .InvalidArgumentException
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.LocationStoryResponse
	 */
	fun getStoryFeed(locationId:String) {
		return this.ig.request("locations/{locationId}/story/").getResponse(Response.LocationStoryResponse())
	}

	/**
	 * Mark LocationStoryResponse story media items as seen.
	 *
	 * The "story" property of a `LocationStoryResponse` only gives you a
	 * list of story media. It doesn"t actually mark any stories as "seen",
	 * so the user doesn"t know that you"ve seen their story. Actually
	 * marking the story as "seen" is done via this endpoint instead. The
	 * official app calls this endpoint periodically (with 1 or more items
	 * at a time) while watching a story.
	 *
	 * This tells the user that you"ve seen their story, and also helps
	 * Instagram know that it shouldn"t give you those seen stories again
	 * if you request the same location feed multiple times.
	 *
	 * Tip: You can pass in the whole "getItems()" array from the location"s
	 * "story" property, to easily mark all of the LocationStoryResponse"s story
	 * media items as seen.
	 *
	 * @param Response.LocationStoryResponse locationFeed The location feed
	 *                                                     response object which
	 *                                                     the story media items
	 *                                                     came from. The story
	 *                                                     items MUST belong to it.
	 * @param Response.Model.Item[]          items        Array of one or more
	 *                                                     story media Items.
	 *
	 * @throws .InvalidArgumentException
	 * @throws .InstagramAPI.Exception.InstagramException
	 *
	 * @return .InstagramAPI.Response.MediaSeenResponse
	 *
	 * @see Story.markMediaSeen()
	 * @see Hashtag.markStoryMediaSeen()
	 */
	fun markStoryMediaSeen( locationFeed:Response.LocationStoryResponse,
	array items)
	{
		// Extract the Location Story-Tray ID from the user"s location response.
		// NOTE: This can NEVER fail if the user has properly given us the exact
		// same location response that they got the story items from!
		sourceId = ""
		if (locationFeed.getStory() instanceof Response.Model.StoryTray) {
			sourceId = locationFeed.getStory().getId()
		}
		if (!strlen(sourceId)) {
			throw.InvalidArgumentException(
				"Your provided LocationStoryResponse is invalid and does not contain any Location Story-Tray ID.")
		}

		// Ensure they only gave us valid items for this location response.
		// NOTE: We validate since people cannot be trusted to import their brain.
		validIds = []
		foreach(locationFeed.getStory().getItems() as item) {
			validIds[item.getId()] = true
		}
		foreach(items as item) {
			// NOTE: We only check Items here. Other data is rejected by Internal.
			if (item instanceof Response.Model.Item && !isset(validIds[item.getId()])) {
				throw.InvalidArgumentException(
					sprintf("The item with ID " % s" does not belong to this LocationStoryResponse.", item.getId()))
			}
		}

		// Mark the story items as seen, with the location as source ID.
		return this.ig.internal.markStoryMediaSeen(items, sourceId)
	}
}
