package instagramAPI.requests

import GuzzleHttp.Psr7.LimitStream
import GuzzleHttp.Psr7.Stream
import instagramAPI.exception.*
import instagramAPI.media.MediaDetails
import instagramAPI.media.Video.FFmpeg
import instagramAPI.media.Video.InstagramThumbnail
import instagramAPI.media.Video.VideoDetails
import instagramAPI.requests.Metadata.Internal as InternalMetadata
import Winbox.Args
import com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date
import com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time
import instagramAPI.*
import java.lang.Thread.sleep
import kotlin.math.*
import fun GuzzleHttp.Psr7.stream_for

/**
 * Collection of various INTERNAL library funs.
 *
 * THESE funS ARE NOT FOR USE! DO NOT TOUCH!
 */
class Internal(instagram: Instagram) : RequestCollection(instagram) {
	/** @var int Number of retries for each video chunk. */
	val MAX_CHUNK_RETRIES = 5

	/** @var int Number of retries for resumable uploader. */
	val MAX_RESUMABLE_RETRIES = 15

	/** @var int Number of retries for each media configuration. */
	val MAX_CONFIGURE_RETRIES = 5

	/** @var int Minimum video chunk size in bytes. */
	val MIN_CHUNK_SIZE = 204800

	/** @var int Maximum video chunk size in bytes. */
	val MAX_CHUNK_SIZE = 5242880

	/**
	 * UPLOADS A *SINGLE* PHOTO.
	 *
	 * This is NOT used for albums!
	 *
	 * @param int                   targetFeed       One of the FEED_X constants.
	 * @param string                photoFilename    The photo filename.
	 * @param InternalMetadata|null internalMetadata (optional) Internal library-generated metadata object.
	 * @param array                 externalMetadata (optional) User-provided metadata key-value pairs.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 * @throws .instagramAPI.exception.UploadFailedException
	 *
	 * @return .instagramAPI.responses.ConfigureResponse
	 *
	 * @see Internal.configureSinglePhoto() for available metadata fields.
	 */
	fun uploadSinglePhoto(targetFeed:Int, photoFilename:String, internalMetadataRE: InternalMetadata? = null, array externalMetadata = []) {
		// Make sure we only allow these particular feeds for this fun.
		if (targetFeed !== Constants.FEED_TIMELINE && targetFeed !== Constants.FEED_STORY && targetFeed !== Constants.FEED_DIRECT_STORY) {
			throw IllegalArgumentException("Bad target feed \"$targetFeed\".")
		}

		var internalMetadata = internalMetadataRE
		// Validate and prepare internal metadata object.
		if (internalMetadata === null) {
			internalMetadata = InternalMetadata(Utils.generateUploadId(true))
		}

		try {
			if (internalMetadata.getPhotoDetails() === null) {
				internalMetadata.setPhotoDetails(targetFeed, photoFilename)
			}
		} catch (e: Exception) {
			throw IllegalArgumentException("Failed to get photo details: ${e.message}")
		}

		// Perform the upload.
		this.uploadPhotoData(targetFeed, internalMetadata)

		// Configure the uploaded image and attach it to our timeline/story.

		return configureSinglePhoto(targetFeed, internalMetadata, externalMetadata)
	}

	/**
	 * Upload the data for a photo to Instagram.
	 *
	 * @param int              targetFeed       One of the FEED_X constants.
	 * @param InternalMetadata internalMetadata Internal library-generated metadata object.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 * @throws .instagramAPI.exception.UploadFailedException
	 */
	fun uploadPhotoData(targetFeed:Int, internalMetadata: InternalMetadata) {
		// Make sure we disallow some feeds for this fun.
		if (targetFeed === Constants.FEED_DIRECT) {
			throw IllegalArgumentException("Bad target feed \"$targetFeed\".")
		}

		// Make sure we have photo details.
		if (internalMetadata.getPhotoDetails() === null) {
			throw IllegalArgumentException("Photo details are missing from the internal metadata.")
		}

		try {
			// Upload photo file with one of our photo uploaders.
			if (_useResumablePhotoUploader(targetFeed, internalMetadata)) {
				_uploadResumablePhoto(targetFeed, internalMetadata)
			} else {
				internalMetadata.setPhotoUploadResponse(_uploadPhotoInOnePiece(targetFeed, internalMetadata))
			}
		} catch (e: InstagramException) {
			// Pass Instagram"s error as is.
			throw e
		} catch (e: Exception) {
			// Wrap runtime errors.
			throw UploadFailedException("Upload of \"${internalMetadata.getPhotoDetails().getBasename()}\" failed: ${e.message}")
		}
	}

	/**
	 * Configures parameters for a *SINGLE* uploaded photo file.
	 *
	 * WARNING TO CONTRIBUTORS: THIS IS ONLY FOR *TIMELINE* AND *STORY* -PHOTOS-.
	 * import "configureTimelineAlbum()" FOR ALBUMS and "configureSingleVideo()" FOR VIDEOS.
	 * AND IF FUTURE INSTAGRAM FEATURES NEED CONFIGURATION AND ARE NON-TRIVIAL,
	 * GIVE THEM THEIR OWN fun LIKE WE DID WITH "configureTimelineAlbum()",
	 * TO AVOID ADDING BUGGY AND UNMAINTAINABLE SPIDERWEB CODE!
	 *
	 * @param int              targetFeed       One of the FEED_X constants.
	 * @param InternalMetadata internalMetadata Internal library-generated metadata object.
	 * @param array            externalMetadata (optional) User-provided metadata key-value pairs.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.ConfigureResponse
	 */
	fun configureSinglePhoto(targetFeed:Int, internalMetadata: InternalMetadata,  array externalMetadata = []) {
		// Determine the target endpoint for the photo.
		val endpoint = when(targetFeed) {
			Constants.FEED_TIMELINE ->"media/configure/"
			Constants.FEED_DIRECT_STORY, Constants.FEED_STORY -> "media/configure_to_story/"
			else -> throw IllegalArgumentException("Bad target feed \"$targetFeed\".")
		}

		// Available external metadata parameters:
		/** @var string Caption to import for the media. */
		var captionText = if (!externalMetadata["caption"].isBlank()) externalMetadata["caption"] else ""
		/** @var string Accesibility caption to import for the media. */
        val altText = if (!externalMetadata["custom_accessibility_caption"].isBlank()) externalMetadata["custom_accessibility_caption"] else null
		/** @var responses.model.Location|null A Location object describing where
		 * the media was taken. */
        val location = if (!externalMetadata["location"].isBlank()) externalMetadata["location"] else null
		/** @var array|null Array of story location sticker instructions. ONLY
		 * USED FOR STORY MEDIA! */
        val locationSticker = if (!externalMetadata["location_sticker"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["location_sticker"] else null
		/** @var array|null Array of usertagging instructions, in the format
		 * [["position"=>[0.5,0.5], "user_id"=>"123"], ...]. ONLY FOR TIMELINE PHOTOS! */
        val usertags = if (!externalMetadata["usertags"].isBlank()&& targetFeed == Constants.FEED_TIMELINE) externalMetadata["usertags"] else null
		/** @var string|null Link to attach to the media. ONLY USED FOR STORY MEDIA,
		 * AND YOU MUST HAVE A BUSINESS INSTAGRAM ACCOUNT TO POST A STORY LINK! */
        val link = if (!externalMetadata["link"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["link"] else null
		/** @var void Photo filter. THIS DOES NOTHING! All real filters are done in the mobile app. */
		// filter = isset(externalMetadata["filter"]) ? externalMetadata["filter"] : null
		var filter = null // COMMENTED OUT SO USERS UNDERSTAND THEY CAN"T import THIS!
		/** @var array Hashtags to import for the media. ONLY STORY MEDIA! */
        val hashtags = if (!externalMetadata["hashtags"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["hashtags"] else null
		/** @var array Mentions to import for the media. ONLY STORY MEDIA! */
        val storyMentions = if (!externalMetadata["story_mentions"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["story_mentions"] else null
		/** @var array Story poll to import for the media. ONLY STORY MEDIA! */
        val storyPoll = if (!externalMetadata["story_polls"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["story_polls"] else null
		/** @var array Story slider to import for the media. ONLY STORY MEDIA! */
        val storySlider = if (!externalMetadata["story_sliders"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["story_sliders"] else null
		/** @var array Story question to import for the media. ONLY STORY MEDIA */
        val storyQuestion = if (!externalMetadata["story_questions"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["story_questions"] else null
		/** @var array Story countdown to import for the media. ONLY STORY MEDIA */
        val storyCountdown = if (!externalMetadata["story_countdowns"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["story_countdowns"] else null
		/** @var array Attached media used to share media to story feed. ONLY STORY MEDIA! */
        val attachedMedia = if (!externalMetadata["attached_media"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["attached_media"] else null
		/** @var array Product Tags to import for the media. ONLY FOR TIMELINE PHOTOS! */
        val productTags = if (!externalMetadata["product_tags"].isBlank() && targetFeed == Constants.FEED_TIMELINE) externalMetadata["product_tags"] else null

		// Fix very bad external user-metadata values.
		if (captionText !is String) {
			captionText = ""
		}

		// Critically important internal library-generated metadata parameters:
		/** @var string The ID of the entry to configure. */
		var uploadId = internalMetadata.getUploadId()
		/** @var int Width of the photo. */
		var photoWidth = internalMetadata.getPhotoDetails().getWidth()
		/** @var int Height of the photo. */
		var photoHeight = internalMetadata.getPhotoDetails().getHeight()

		// Build the request...
		var request = this.ig.request(endpoint)
				.addPost("_csrftoken", this.ig.client.getToken())
				.addPost("_uid",this.ig.account_id)
				.addPost("_uuid", this.ig.uuid)
				.addPost("edits", mapOf(
						"crop_original_size"    to listOf(photoWidth, photoHeight),
						"crop_zoom"             to 1,
						"crop_center"           to listOf(0.0, -0.0)
				))
				.addPost("device", mapOf(
					"manufacturer"      to this.ig.device.getManufacturer(),
					"model"             to this.ig.device.getModel(),
					"android_version"   to this.ig.device.getAndroidVersion(),
					"android_release"   to this.ig.device.getAndroidRelease()
				))
				.addPost("extra", mapOf<String, Double>(
					"source_width"  to photoWidth,
					"source_height" to photoHeight
				))

		when(targetFeed) {
			Constants.FEED_TIMELINE -> {
				var date = date("Y:m:d H:i:s")
				request.addParam("timezone_offset", date("Z"))
					.addPost("date_time_original", date)
					.addPost("date_time_digitalized", date)
					.addPost("caption", captionText)
					.addPost("source_type", "4")
					.addPost("media_folder", "Camera")
					.addPost("upload_id", uploadId)

				if (usertags !== null) {
					Utils.throwIfInvalidUsertags(usertags)
					request.addPost("usertags", json_encode(usertags))
				}
				if (productTags !== null) {
					Utils.throwIfInvalidProductTags(productTags)
					request.addPost("product_tags", json_encode(productTags))
				}
				if (altText !== null) {
					request.addPost("custom_accessibility_caption", altText)
				}
			}

			Constants.FEED_STORY -> {
				if (internalMetadata.isBestieMedia()) {
					request.addPost("audience", "besties")
				}

				request.addPost("client_shared_at", (string) time ())
					.addPost("source_type", "3")
					.addPost("configure_mode", "1")
					.addPost("client_timestamp", (string)(time() - (3..10).random()))
					.addPost("upload_id", uploadId)

				if (link is String && Utils.hasValidWebURLSyntax(link)) {
					var story_cta = "[{\"links\":[{\"linkType\": 1, \"webUri\":" + json_encode(link) + ", \"androidClass\": \"\", \"package\": \"\", \"deeplinkUri\": \"\", \"callToActionTitle\": \"\", \"redirectUri\": null, \"leadGenFormId\": \"\", \"igUserId\": \"\", \"appInstallObjectiveInvalidationBehavior\": null}]}]"
					request.addPost("story_cta", story_cta)
				}
				if (hashtags !== null && captionText !== "") {
					Utils.throwIfInvalidStoryHashtags(captionText, hashtags)
					request.addPost("story_hashtags", json_encode(hashtags))
						.addPost("caption", captionText)
						.addPost("mas_opt_in", "NOT_PROMPTED")
				}
				if (locationSticker !== null && location !== null) {
					Utils.throwIfInvalidStoryLocationSticker(locationSticker)
					request.addPost("story_locations", json_encode([locationSticker]))
						.addPost("mas_opt_in", "NOT_PROMPTED")
				}
				if (storyMentions !== null && captionText !== "") {
					Utils.throwIfInvalidStoryMentions(storyMentions)
					request.addPost("reel_mentions", json_encode(storyMentions))
						.addPost("caption", captionText.replace(" ", "+") + "+")
						.addPost("mas_opt_in", "NOT_PROMPTED")
				}
				if (storyPoll !== null) {
					Utils.throwIfInvalidStoryPoll(storyPoll)
					request.addPost("story_polls", json_encode(storyPoll))
						.addPost("internal_features", "polling_sticker")
						.addPost("mas_opt_in", "NOT_PROMPTED")
				}
				if (storySlider !== null) {
					Utils.throwIfInvalidStorySlider(storySlider)
					request.addPost("story_sliders", json_encode(storySlider))
						.addPost("story_sticker_ids", "emoji_slider_" + storySlider[0]["emoji"])
				}
				if (storyQuestion !== null) {
					Utils.throwIfInvalidStoryQuestion(storyQuestion)
					request.addPost("story_questions", json_encode(storyQuestion))
						.addPost("story_sticker_ids", "question_sticker_ama")
				}
				if (storyCountdown !== null) {
					Utils.throwIfInvalidStoryCountdown(storyCountdown)
					request.addPost("story_countdowns", json_encode(storyCountdown))
						.addPost("story_sticker_ids", "countdown_sticker_time")
				}
				if (attachedMedia !== null) {
					Utils.throwIfInvalidAttachedMedia(attachedMedia)
					request.addPost("attached_media", json_encode(attachedMedia))
						.addPost("story_sticker_ids", "media_simple_" + reset(attachedMedia)["media_id"])
				}
			}
			Constants.FEED_DIRECT_STORY -> {
				request.addPost("recipient_users", internalMetadata.getDirectUsers())
					.addPost("thread_ids", internalMetadata.getDirectThreads())
					.addPost("client_shared_at", (string) time ()).addPost("source_type", "3")
					.addPost("configure_mode", "2").addPost("client_timestamp", (string)(time() - (3..10).random()))
					.addPost("upload_id", uploadId)
			}
		}

		if (location instanceof Response.Model.Location) {
			if (targetFeed === Constants.FEED_TIMELINE) {
				request.addPost("location", Utils.buildMediaLocationJSON(location))
			}
			if (targetFeed === Constants.FEED_STORY && locationSticker === null) {
				throw IllegalArgumentException("You must provide a location_sticker together with your story location.")
			}
			request.addPost("geotag_enabled", "1")
				.addPost("posting_latitude", location.getLat())
				.addPost("posting_longitude", location.getLng())
				.addPost("media_latitude", location.getLat())
				.addPost("media_longitude", location.getLng())
		}

		return request.getResponse(Response.ConfigureResponse())
	}

	/**
	 * Uploads a raw video file.
	 *
	 * @param int                   targetFeed       One of the FEED_X constants.
	 * @param string                videoFilename    The video filename.
	 * @param InternalMetadata|null internalMetadata (optional) Internal library-generated metadata object.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 * @throws .instagramAPI.exception.UploadFailedException If the video upload fails.
	 *
	 * @return InternalMetadata Updated internal metadata object.
	 */
	fun uploadVideo(targetFeed:Int, videoFilename:String,  internalMetadata:InternalMetadata? = null) {
		if (internalMetadata === null) {
			internalMetadata = InternalMetadata()
		}

		try {
			if (internalMetadata.getVideoDetails() === null) {
				internalMetadata.setVideoDetails(targetFeed, videoFilename)
			}
		} catch (e: Exception) {
			throw IllegalArgumentException(sprintf("Failed to get photo details: %s", e.getMessage()), e.getCode(), e)
		}

		try {
			when {
				_useSegmentedVideoUploader(targetFeed, internalMetadata) -> _uploadSegmentedVideo(targetFeed, internalMetadata)
				_useResumableVideoUploader(targetFeed, internalMetadata) -> _uploadResumableVideo(targetFeed, internalMetadata)
				else -> {
					// Request parameters for uploading a video.
					internalMetadata.setVideoUploadUrls(_requestVideoUploadURL(targetFeed, internalMetadata))

					// Attempt to upload the video data.
					internalMetadata.setVideoUploadResponse(_uploadVideoChunks(targetFeed, internalMetadata))
				}
			}
		} catch (e: InstagramException) {
			// Pass Instagram"s error as is.
			throw e
		} catch (e: Exception) {
			// Wrap runtime errors.
			throw UploadFailedException(sprintf("Upload of " % s" failed: %s", basename(videoFilename), e.getMessage()),e.getCode(), e)
		}

		return internalMetadata
	}

	/**
	 * UPLOADS A *SINGLE* VIDEO.
	 *
	 * This is NOT used for albums!
	 *
	 * @param int                   targetFeed       One of the FEED_X constants.
	 * @param string                videoFilename    The video filename.
	 * @param InternalMetadata|null internalMetadata (optional) Internal library-generated metadata object.
	 * @param array                 externalMetadata (optional) User-provided metadata key-value pairs.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 * @throws .instagramAPI.exception.UploadFailedException If the video upload fails.
	 *
	 * @return .instagramAPI.responses.ConfigureResponse
	 *
	 * @see Internal.configureSingleVideo() for available metadata fields.
	 */
	fun uploadSingleVideo(targetFeed:Int, videoFilename:String,  internalMetadata:InternalMetadata? = null, array externalMetadata = []) {
		// Make sure we only allow these particular feeds for this fun.
		if (targetFeed !== Constants.FEED_TIMELINE && targetFeed !== Constants.FEED_STORY && targetFeed !== Constants.FEED_DIRECT_STORY && targetFeed !== Constants.FEED_TV) {
			throw IllegalArgumentException("Bad target feed \"$targetFeed\".")
		}

		// Attempt to upload the video.
		internalMetadata = uploadVideo(targetFeed, videoFilename, internalMetadata)

		// Attempt to upload the thumbnail, associated with our video"s ID.
		uploadVideoThumbnail(targetFeed, internalMetadata, externalMetadata)

		// Configure the uploaded video and attach it to our timeline/story.
		try {
			/** @var .instagramAPI.responses.ConfigureResponse configure */
			var configure = ig.internal.configureWithRetries(fun() import (targetFeed, internalMetadata, externalMetadata) {
					// Attempt to configure video parameters.
					return configureSingleVideo(targetFeed, internalMetadata, externalMetadata)
				}
			)
		} catch (e: InstagramException) {
			// Pass Instagram"s error as is.
			throw e
		} catch (e: Exception) {
			// Wrap runtime errors.
			throw UploadFailedException(sprintf("Upload of " % s" failed: %s", basename(videoFilename), e.getMessage()),
			                            e.getCode(), e)
		}

		return configure
	}

	/**
	 * Performs a resumable upload of a photo file, with support for retries.
	 *
	 * @param int              targetFeed       One of the FEED_X constants.
	 * @param InternalMetadata internalMetadata Internal library-generated metadata object.
	 * @param array            externalMetadata (optional) User-provided metadata key-value pairs.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 * @throws .instagramAPI.exception.UploadFailedException
	 */
	fun uploadVideoThumbnail(targetFeed:Int,  internalMetadata:InternalMetadata, array externalMetadata = []) {
		if (internalMetadata.getVideoDetails() === null) {
			throw IllegalArgumentException("Video details are missing from the internal metadata.")
		}

		try {
			// Automatically crop&resize the thumbnail to Instagram"s requirements.
			var options = mutableMapOf("targetFeed" to targetFeed)
			if (!externalMetadata["thumbnail_timestamp"].isBlank()) {
				options["thumbnailTimestamp"] = externalMetadata["thumbnail_timestamp"]
			}
			var videoThumbnail = InstagramThumbnail(internalMetadata.getVideoDetails().getFilename(), options)
			// Validate and upload the thumbnail.
			internalMetadata.setPhotoDetails(targetFeed, videoThumbnail.getFile())
			uploadPhotoData(targetFeed, internalMetadata)
		} catch (e: InstagramException) {
			// Pass Instagram"s error as is.
			throw e
		} catch (e: Exception) {
			// Wrap runtime errors.
			throw UploadFailedException(sprintf("Upload of video thumbnail failed: %s", e.getMessage()), e.getCode(), e)
		}
	}

	/**
	 * Asks Instagram for parameters for uploading a video.
	 *
	 * @param int              targetFeed       One of the FEED_X constants.
	 * @param InternalMetadata internalMetadata Internal library-generated metadata object.
	 *
	 * @throws .instagramAPI.exception.InstagramException If the request fails.
	 *
	 * @return .instagramAPI.responses.UploadJobVideoResponse
	 */
	protected fun _requestVideoUploadURL(:Int,  internalMetadata:InternalMetadata) {
		request = ig.request("upload/video/").setSignedPost(false)
			.addPost("_csrftoken", ig.client.getToken())
			.addPost("_uuid", ig.uuid)

		for((key to value) in _getVideoUploadParams(targetFeed, internalMetadata)) {
			request.addPost(key, value)
		}

		// Perform the "pre-upload" API request.
		/** @var responses.UploadJobVideoResponse response */
		return request.getResponse(Response.UploadJobVideoResponse())
	}

	/**
	 * Configures parameters for a *SINGLE* uploaded video file.
	 *
	 * WARNING TO CONTRIBUTORS: THIS IS ONLY FOR *TIMELINE* AND *STORY* -VIDEOS-.
	 * import "configureTimelineAlbum()" FOR ALBUMS and "configureSinglePhoto()" FOR PHOTOS.
	 * AND IF FUTURE INSTAGRAM FEATURES NEED CONFIGURATION AND ARE NON-TRIVIAL,
	 * GIVE THEM THEIR OWN fun LIKE WE DID WITH "configureTimelineAlbum()",
	 * TO AVOID ADDING BUGGY AND UNMAINTAINABLE SPIDERWEB CODE!
	 *
	 * @param int              targetFeed       One of the FEED_X constants.
	 * @param InternalMetadata internalMetadata Internal library-generated metadata object.
	 * @param array            externalMetadata (optional) User-provided metadata key-value pairs.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.ConfigureResponse
	 */
	fun configureSingleVideo(targetFeed:Int,  internalMetadata:InternalMetadata, array externalMetadata = []) {
		// Determine the target endpoint for the video.
		val endpoint = when(targetFeed) {
			Constants.FEED_TIMELINE -> "media/configure/"
			Constants.FEED_DIRECT_STORY, Constants.FEED_STORY -> "media/configure_to_story/"
			Constants.FEED_TV -> "media/configure_to_igtv/"
			else -> throw IllegalArgumentException("Bad target feed \"$targetFeed\".")
		}

		// Available external metadata parameters:
		/** @var string Caption to import for the media. */
		var captionText = if( !externalMetadata["caption"].isBlank() ) externalMetadata["caption"] else ""
		/** @var string[]|null Array of numerical UserPK IDs of people tagged in
		 * your video. ONLY USED IN STORY VIDEOS! TODO: Actually, it"s not even
		 * implemented for stories. */
        val usertags = if(!externalMetadata["usertags"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["usertags"] else null
		/** @var responses.model.Location|null A Location object describing where
		 * the media was taken. */
		val location = if(!externalMetadata["location"].isBlank()) externalMetadata["location"] else null
		/** @var array|null Array of story location sticker instructions. ONLY
		 * USED FOR STORY MEDIA! */
		val locationSticker = if(!externalMetadata["location_sticker"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["location_sticker"] else null
		/** @var string|null Link to attach to the media. ONLY USED FOR STORY MEDIA,
		 * AND YOU MUST HAVE A BUSINESS INSTAGRAM ACCOUNT TO POST A STORY LINK! */
		val link = if(!externalMetadata["link"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["link"] else null
		/** @var array Hashtags to import for the media. ONLY STORY MEDIA! */
		val hashtags = if(!externalMetadata["hashtags"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["hashtags"] else null
		/** @var array Mentions to import for the media. ONLY STORY MEDIA! */
		val storyMentions = if(!externalMetadata["story_mentions"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["story_mentions"] else null
		/** @var array Story poll to import for the media. ONLY STORY MEDIA! */
		val storyPoll = if(!externalMetadata["story_polls"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["story_polls"] else null
		/** @var array Attached media used to share media to story feed. ONLY STORY MEDIA! */
		val storySlider = if(!externalMetadata["story_sliders"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["story_sliders"] else null
		/** @var array Story question to import for the media. ONLY STORY MEDIA */
		val storyQuestion = if(!externalMetadata["story_questions"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["story_questions"] else null
		/** @var array Story countdown to import for the media. ONLY STORY MEDIA */
		val storyCountdown = if(!externalMetadata["story_countdowns"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["story_countdowns"] else null
		/** @var array Attached media used to share media to story feed. ONLY STORY MEDIA! */
		val attachedMedia = if(!externalMetadata["attached_media"].isBlank() && targetFeed == Constants.FEED_STORY) externalMetadata["attached_media"] else null
		/** @var array Title of the media uploaded to your channel. ONLY TV MEDIA! */
		val title = if(!externalMetadata["title"] && targetFeed == Constants.FEED_TV) externalMetadata["title"] else null

		// Fix very bad external user-metadata values.
		if (captionText !is String) {
			captionText = ""
		}

		val uploadId = internalMetadata.getUploadId()
		val videoDetails = internalMetadata.getVideoDetails()

		// Build the request...
		val request = ig.request(endpoint)
            .addParam("video", 1)
            .addPost("supported_capabilities_new", json_encode(Constants.SUPPORTED_CAPABILITIES))
            .addPost("video_result",if(internalMetadata.getVideoUploadResponse() !== null) internalMetadata.getVideoUploadResponse().getResult().toString() else "")
		    .addPost("upload_id", uploadId).addPost("poster_frame_index", 0)
			.addPost("length", round(videoDetails.getDuration(), 1))
			.addPost("audio_muted", videoDetails.getAudioCodec() === null).addPost("filter_type", 0)
			.addPost("source_type", 4)
            .addPost("device", mapOf(
                "manufacturer"      to ig.device.getManufacturer(),
                "model"             to ig.device.getModel(),
                "android_version"   to ig.device.getAndroidVersion(),
                "android_release"   to ig.device.getAndroidRelease()
            ))
		    .addPost("extra", mapOf(
                "source_width"  to videoDetails.getWidth(),
                "source_height" to videoDetails.getHeight()
            ))
            .addPost("_csrftoken", ig.client.getToken())
            .addPost("_uuid", ig.uuid)
            .addPost("_uid", ig.account_id)

		when(targetFeed) {
			Constants.FEED_TIMELINE -> request.addPost("caption", captionText)
			Constants.FEED_STORY -> {
                if (internalMetadata.isBestieMedia()) {
                    request.addPost("audience", "besties")
                }

                request.addPost("configure_mode", 1) // 1 - REEL_SHARE
                    .addPost("story_media_creation_date", time() - (10..20).random())
                    .addPost("client_shared_at", time() - (3..10).random())
                    .addPost("client_timestamp", time())

                if (link is String && Utils.hasValidWebURLSyntax(link)) {
                    var story_cta = "[{\"links\":[{\"linkType\": 1, \"webUri\":" + json_encode(link) + ", \"androidClass\": \"\", \"package\": \"\", \"deeplinkUri\": \"\", \"callToActionTitle\": \"\", \"redirectUri\": null, \"leadGenFormId\": \"\", \"igUserId\": \"\", \"appInstallObjectiveInvalidationBehavior\": null}]}]"
                    request.addPost("story_cta", story_cta)
                }
                if (hashtags !== null && captionText !== "") {
                    Utils.throwIfInvalidStoryHashtags(captionText, hashtags)
                    request.addPost("story_hashtags", json_encode(hashtags))
                        .addPost("caption", captionText)
                        .addPost("mas_opt_in", "NOT_PROMPTED")
                }
                if (locationSticker !== null && location !== null) {
                    Utils.throwIfInvalidStoryLocationSticker(locationSticker)
                    request.addPost("story_locations", json_encode([locationSticker]))
                        .addPost("mas_opt_in", "NOT_PROMPTED")
                }
                if (storyMentions !== null && captionText !== "") {
                    Utils.throwIfInvalidStoryMentions(storyMentions)
                    request.addPost("reel_mentions", json_encode(storyMentions))
                        .addPost("caption", str_replace(" ", "+", captionText) + "+")
                        .addPost("mas_opt_in", "NOT_PROMPTED")
                }
                if (storyPoll !== null) {
                    Utils.throwIfInvalidStoryPoll(storyPoll)
                    request.addPost("story_polls", json_encode(storyPoll))
                        .addPost("internal_features", "polling_sticker")
                        .addPost("mas_opt_in", "NOT_PROMPTED")
                }
                if (storySlider !== null) {
                    Utils.throwIfInvalidStorySlider(storySlider)
                    request.addPost("story_sliders", json_encode(storySlider))
                        .addPost("story_sticker_ids", "emoji_slider_" + storySlider[0]["emoji"])
                }
                if (storyQuestion !== null) {
                    Utils.throwIfInvalidStoryQuestion(storyQuestion)
                    request.addPost("story_questions", json_encode(storyQuestion))
                        .addPost("story_sticker_ids", "question_sticker_ama")
                }
                if (storyCountdown !== null) {
                    Utils.throwIfInvalidStoryCountdown(storyCountdown)
                    request.addPost("story_countdowns", json_encode(storyCountdown))
                        .addPost("story_sticker_ids", "countdown_sticker_time")
                }
                if (attachedMedia !== null) {
                    Utils.throwIfInvalidAttachedMedia(attachedMedia)
                    request.addPost("attached_media", json_encode(attachedMedia))
                        .addPost("story_sticker_ids", "media_simple_".reset(attachedMedia)["media_id"])
                }
            }
			Constants.FEED_DIRECT_STORY -> {
                request.addPost("configure_mode", 2) // 2 - DIRECT_STORY_SHARE
                    .addPost("recipient_users", internalMetadata.getDirectUsers())
                    .addPost("thread_ids", internalMetadata.getDirectThreads())
                    .addPost("story_media_creation_date", time() - (10..20).random())
                    .addPost("client_shared_at", time() - (3..10).random())
                    .addPost("client_timestamp", time())
            }
			Constants.FEED_TV -> {
                if (title === null) {
                    throw IllegalArgumentException("You must provide a title for the media.")
                }
                request.addPost("title", title)
                    .addPost("caption", captionText)
            }
		}

		if (targetFeed == Constants.FEED_STORY) {
			request.addPost("story_media_creation_date", time())
			if (usertags !== null) {
				// Reel Mention example:
				// [{."y.":0.3407772676161919,."rotation.":0,."user_id.":."USER_ID.",."x.":0.39892578125,."width.":0.5619921875,."height.":0.06011525487256372}]
				// NOTE: The backslashes are just double JSON encoding, ignore
				// that and just give us an array with these clean values, don"t
				// try to encode it in any way, we do all encoding to match the above.
				// This post field will get wrapped in another json_encode call during transfer.
				request.addPost("reel_mentions", json_encode(usertags))
			}
		}

		if (location instanceof Response.Model.Location) {
			if (targetFeed === Constants.FEED_TIMELINE) {
				request.addPost("location", Utils.buildMediaLocationJSON(location))
			}
			if (targetFeed === Constants.FEED_STORY && locationSticker === null) {
				throw IllegalArgumentException("You must provide a location_sticker together with your story location.")
			}
			request.addPost("geotag_enabled", "1").addPost("posting_latitude", location.getLat())
				.addPost("posting_longitude", location.getLng()).addPost("media_latitude", location.getLat())
				.addPost("media_longitude", location.getLng())
		}

		configure = request.getResponse(Response.ConfigureResponse())

		return configure
	}

	/**
	 * Configures parameters for a whole album of uploaded media files.
	 *
	 * WARNING TO CONTRIBUTORS: THIS IS ONLY FOR *TIMELINE ALBUMS*. DO NOT MAKE
	 * IT DO ANYTHING ELSE, TO AVOID ADDING BUGGY AND UNMAINTAINABLE SPIDERWEB
	 * CODE!
	 *
	 * @param array            media            Extended media array coming from Timeline.uploadAlbum(),
	 *                                           containing the user"s per-file metadata,
	 *                                           and internally generated per-file metadata.
	 * @param InternalMetadata internalMetadata Internal library-generated metadata object for the album itself.
	 * @param array            externalMetadata (optional) User-provided metadata key-value pairs
	 *                                           for the album itself (its caption, location, etc).
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.ConfigureResponse
	 */
	fun configureTimelineAlbum(array media,  internalMetadata:InternalMetadata, array externalMetadata = []) {
		val endpoint = "media/configure_sidecar/"

		val albumUploadId = internalMetadata.getUploadId()

		// Available external metadata parameters:
		/** @var string Caption to import for the album. */
		var captionText = if(!externalMetadata["caption"].isBlank()) externalMetadata["caption"] else ""
		/** @var responses.model.Location|null A Location object describing where
		 * the album was taken. */
		val location = if(externalMetadata["location"].isBlank()) externalMetadata["location"] else null

		// Fix very bad external user-metadata values.
		if (captionText !is String) {
			captionText = ""
		}

		// Build the album"s per-children metadata.
		var date = date("Y:m:d H:i:s")
		var childrenMetadata = []
		for(item in media) {
			/** @var InternalMetadata itemInternalMetadata */
			var itemInternalMetadata = item["internalMetadata"]
			// Get all of the common, INTERNAL per-file metadata.
			var uploadId = itemInternalMetadata.getUploadId()

			when(item["type"]) {
				"photo" -> {
					// Build this item"s configuration.
					val photoConfig = mutableMapOf(
						"date_time_original"  to date,
						"scene_type"          to 1,
						"disable_comments"    to false,
						"upload_id"           to uploadId,
						"source_type"         to 0,
						"scene_capture_type"  to "standard",
						"date_time_digitized" to date,
						"geotag_enabled"      to false,
						"camera_position"     to "back",
						"edits"               to mapOf(
							"filter_strength" to 1,
							"filter_name"     to "IGNormalFilter"
						)
					)

					if (!item["usertags"].isBlank()) {
						// NOTE: These usertags were validated in Timeline.uploadAlbum.
						photoConfig["usertags"] = json_encode(["in" to item ["usertags"]])
					}

					childrenMetadata[] = photoConfig
				}
				"video" -> {
					// Get all of the INTERNAL per-VIDEO metadata.
					val videoDetails = itemInternalMetadata.getVideoDetails()

					// Build this item"s configuration.
					val videoConfig = mutableMapOf(
						"length"              to videoDetails.getDuration().roundToInt(),
						"date_time_original"  to date,
						"scene_type"          to 1,
						"poster_frame_index"  to 0,
						"trim_type"           to 0,
						"disable_comments"    to false,
						"upload_id"           to uploadId,
						"source_type"         to "library",
						"geotag_enabled"      to false,
						"edits"               to mapOf(
							"length"          to videoDetails.getDuration().roundToInt(),
							"cinema"          to "unsupported",
							"original_length" to videoDetails.getDuration().roundToInt(),
							"source_type"     to "library",
							"start_time"      to 0,
							"camera_position" to "unknown",
							"trim_type"       to 0
						)
					)

					if (!item["usertags"].isBlank()) {
						// NOTE: These usertags were validated in Timeline.uploadAlbum.
						videoConfig["usertags"] = json_encode(["in" to item ["usertags"]])
					}

					childrenMetadata[] = videoConfig
				}
			}
		}

		// Build the request...
		val request = ig.request(endpoint)
			.addPost("_csrftoken", ig.client.getToken())
			.addPost("_uid", ig.account_id)
			.addPost("_uuid", ig.uuid)
			.addPost("client_sidecar_id", albumUploadId)
			.addPost("caption", captionText)
			.addPost("children_metadata", childrenMetadata)

		if (location instanceof Response.Model.Location) {
			request.addPost("location", Utils.buildMediaLocationJSON(location))
				.addPost("geotag_enabled", "1")
				.addPost("posting_latitude", location.getLat())
				.addPost("posting_longitude", location.getLng())
				.addPost("media_latitude", location.getLat())
				.addPost("media_longitude", location.getLng())
				.addPost("exif_latitude", 0.0).addPost("exif_longitude", 0.0)
		}

		return request.getResponse(Response.ConfigureResponse())
	}

	/**
	 * Saves active experiments.
	 *
	 * @param Response.SyncResponse syncResponse
	 *
	 * @throws .instagramAPI.exception.SettingsException
	 */
	protected fun _saveExperiments( syncResponse:Response.SyncResponse)
	{
		var experiments = []
		for(experiment in syncResponse.getExperiments()) {
			val group = experiment.getName()
			val params = experiment.getParams()

			if (group === null || params === null) {
				continue
			}

			if (experiments[group].isBlank()) {
				experiments[group] = []
			}

			for(param in params) {
				val paramName = param.getName()
				if (paramName === null) {
					continue
				}

				experiments[group][paramName] = param.getValue()
			}
		}

		// Save the experiments and the last time we refreshed them.
		ig.experiments = ig.settings.setExperiments(experiments)
		ig.settings.set("last_experiments", time())
	}

	/**
	 * Perform an Instagram "feature synchronization" call for device.
	 *
	 * @param bool prelogin
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.SyncResponse
	 */
	fun syncDeviceFeatures(prelogin:Boolean = false) {
		var request = ig.request("qe/sync/")
			.addHeader("X-DEVICE-ID", ig.uuid)
			.addPost("id", ig.uuid)
			.addPost("experiments", Constants.LOGIN_EXPERIMENTS)
		if (prelogin) {
			request.setNeedsAuth(false)
		} else {
			request.addPost("_uuid", ig.uuid)
				.addPost("_uid", ig.account_id)
				.addPost("_csrftoken", ig.client.getToken())
		}

		return request.getResponse(Response.SyncResponse())
	}

	/**
	 * Perform an Instagram "feature synchronization" call for account.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.SyncResponse
	 */
	fun syncUserFeatures() {
		var result = ig.request("qe/sync/")
			.addHeader("X-DEVICE-ID", ig.uuid)
			.addPost("_uuid", ig.uuid)
			.addPost("_uid", ig.account_id)
			.addPost("_csrftoken", ig.client.getToken())
			.addPost("id", ig.account_id)
			.addPost("experiments", Constants.EXPERIMENTS)
			.getResponse(Response.SyncResponse())

		// Save the updated experiments for this user.
		_saveExperiments(result)

		return result
	}

	/**
	 * Send launcher sync.
	 *
	 * @param bool prelogin Indicates if the request is done before login request.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.LauncherSyncResponse
	 */
	fun sendLauncherSync(prelogin:Boolean) {
		var request = ig.request("launcher/sync/")
			.addPost("_csrftoken", ig.client.getToken())
			.addPost("configs","ig_android_felix_release_players,ig_user_mismatch_soft_error,ig_android_os_version_blocking_config,ig_android_carrier_signals_killswitch,fizz_ig_android,ig_mi_block_expired_events,ig_android_killswitch_perm_direct_ssim,ig_fbns_blocked")
		if (prelogin) {
			request.setNeedsAuth(false).addPost("id", ig.uuid)
		} else {
			request.addPost("id", ig.account_id)
				.addPost("_uuid", ig.uuid)
				.addPost("_uid", ig.account_id)
				.addPost("_csrftoken", ig.client.getToken())
		}

		return request.getResponse(Response.LauncherSyncResponse())
	}

	/**
	 * Registers advertising identifier.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.GenericResponse
	 */
	fun logAttribution() {
		return ig.request("attribution/log_attribution/")
			.setNeedsAuth(false)
			.addPost("adid", ig.advertising_id)
			.getResponse(Response.GenericResponse())
	}

	/**
	 * TODO.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.GenericResponse
	 */
	fun logResurrectAttribution() {
		return ig.request("attribution/log_resurrect_attribution/")
			.addPost("adid", ig.advertising_id)
			.addPost("_uuid", ig.uuid)
			.addPost("_uid", ig.account_id)
			.addPost("_csrftoken", ig.client.getToken())
			.getResponse(Response.GenericResponse())
	}

	/**
	 * Reads MSISDN header.
	 *
	 * @param string      usage    Desired usage, either "ig_select_app" or "default".
	 * @param string|null subnoKey Encoded subscriber number.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.MsisdnHeaderResponse
	 */
	fun readMsisdnHeader(usage:String, subnoKey:String? = null) {
		var request = ig.request("accounts/read_msisdn_header/")
			.setNeedsAuth(false)
			.addHeader("X-DEVICE-ID", ig.uuid)
				// UUID is used as device_id intentionally.
			.addPost("device_id", ig.uuid)
			.addPost("mobile_subno_usage", usage)
		if (subnoKey !== null) {
			request.addPost("subno_key", subnoKey)
		}

		return request.getResponse(Response.MsisdnHeaderResponse())
	}

	/**
	 * Bootstraps MSISDN header.
	 *
	 * @param string usage Mobile subno usage.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.MsisdnHeaderResponse
	 *
	 * @since 10.24.0 app version.
	 */
	fun bootstrapMsisdnHeader(usage:String = "ig_select_app") {
		request = this.ig.request("accounts/msisdn_header_bootstrap/")
			.setNeedsAuth(false)
			.addPost("mobile_subno_usage", usage)
			// UUID is used as device_id intentionally.
			.addPost("device_id", ig.uuid)
			.addPost("_csrftoken", ig.client.getToken())

		return request.getResponse(Response.MsisdnHeaderResponse())
	}

	/**
	 * @param Response.Model.Token|null token
	 */
	protected fun _saveZeroRatingToken( token:Response.Model.Token? = null)
	{
		if (token === null) {
			return
		}

		var rules = []
		for(rule in token.getRewriteRules()) {
			rules[rule.getMatcher()] = rule.getReplacer()
		}
		ig.client.zeroRating().update(rules)

		try {
			ig.settings.setRewriteRules(rules)
			ig.settings.set("zr_token", token.getTokenHash())
			ig.settings.set("zr_expires", token.expiresAt())
		} catch (e: SettingsException) {
			// Ignore storage errors.
		}
	}

	/**
	 * Get zero rating token hash result.
	 *
	 * @param string reason One of: "token_expired", "mqtt_token_push", "token_stale", "provisioning_time_mismatch".
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.TokenResultResponse
	 */
	fun fetchZeroRatingToken(reason:String = "token_expired") {
		val request = ig.request("zr/token/result/")
			.setNeedsAuth(false)
			.addParam("custom_device_id", ig.uuid)
			.addParam("device_id", ig.device_id)
			.addParam("fetch_reason", reason)
			.addParam("token_hash", ig.settings.get("zr_token").toString())

		/** @var responses.TokenResultResponse result */
		val result = request.getResponse(Response.TokenResultResponse())
		_saveZeroRatingToken(result.getToken())

		return result
	}

	/**
	 * Get megaphone log.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.MegaphoneLogResponse
	 */
	fun getMegaphoneLog() {
		return ig.request("megaphone/log/")
			.setSignedPost(false)
			.addPost("type", "feed_aysf")
			.addPost("action", "seen")
			.addPost("reason", "")
			.addPost("_uuid", ig.uuid)
			.addPost("device_id", ig.device_id)
			.addPost("_csrftoken", ig.client.getToken())
			.addPost("uuid", time().md5())
			.getResponse(Response.MegaphoneLogResponse())
	}

	/**
	 * Get hidden entities for users, places and hashtags via Facebook"s algorithm.
	 *
	 * TODO: We don"t know what this fun does. If we ever discover that it
	 * has a useful purpose, then we should move it somewhere else.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.FacebookHiddenEntitiesResponse
	 */
	fun getFacebookHiddenSearchEntities() {
		return ig.request("fbsearch/get_hidden_search_entities/")
			.getResponse(Response.FacebookHiddenEntitiesResponse())
	}

	/**
	 * Get Facebook OTA (Over-The-Air) update information.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.FacebookOTAResponse
	 */
	fun getFacebookOTA() {
		return ig.request("facebook_ota/").addParam("fields", Constants.FACEBOOK_OTA_FIELDS)
			.addParam("custom_user_id", ig.account_id)
			.addParam("signed_body", Signatures.generateSignature("") + ".")
			.addParam("ig_sig_key_version", Constants.SIG_KEY_VERSION)
			.addParam("version_code", Constants.VERSION_CODE)
			.addParam("version_name", Constants.IG_VERSION)
			.addParam("custom_app_id", Constants.FACEBOOK_ORCA_APPLICATION_ID)
			.addParam("custom_device_id", ig.uuid)
			.getResponse(Response.FacebookOTAResponse())
	}

	/**
	 * Fetch profiler traces config.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.LoomFetchConfigResponse
	 *
	 * @see https://github.com/facebookincubator/profilo
	 */
	fun getLoomFetchConfig() {
		return ig.request("loom/fetch_config/").getResponse(Response.LoomFetchConfigResponse())
	}

	/**
	 * Get profile "notices".
	 *
	 * This is just for some internal state information, such as
	 * "has_change_password_megaphone". It"s not for use.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.ProfileNoticeResponse
	 */
	fun getProfileNotice() {
		return ig.request("users/profile_notice/").getResponse(Response.ProfileNoticeResponse())
	}

	/**
	 * Fetch quick promotions data.
	 *
	 * This is used by Instagram to fetch internal promotions or changes
	 * about the platform. Latest quick promotion known was the GDPR
	 * policy where Instagram asks you to accept policy and accept that
	 * you have 18 years old or more.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.FetchQPDataResponse
	 */
	fun getQPFetch() {
		var query = "viewer() {eligible_promotions.surface_nux_id(<surface>).external_gating_permitted_qps(<external_gating_permitted_qps>).supports_client_filters(true) {edges {priority,time_range {start,end},node {id,promotion_id,max_impressions,triggers,contextual_filters {clause_type,filters {filter_type,unknown_action,value {name,required,bool_value,int_value, string_value},extra_datas {name,required,bool_value,int_value, string_value}},clauses {clause_type,filters {filter_type,unknown_action,value {name,required,bool_value,int_value, string_value},extra_datas {name,required,bool_value,int_value, string_value}},clauses {clause_type,filters {filter_type,unknown_action,value {name,required,bool_value,int_value, string_value},extra_datas {name,required,bool_value,int_value, string_value}},clauses {clause_type,filters {filter_type,unknown_action,value {name,required,bool_value,int_value, string_value},extra_datas {name,required,bool_value,int_value, string_value}}}}}},template {name,parameters {name,required,bool_value,string_value,color_value,}},creatives {title {text},content {text},footer {text},social_context {text},primary_action{title {text},url,limit,dismiss_promotion},secondary_action{title {text},url,limit,dismiss_promotion},dismiss_action{title {text},url,limit,dismiss_promotion},image.scale(<scale>) {uri,width,height}}}}}}"

		return ig.request("qp/batch_fetch/")
			.addPost("vc_policy", "default")
			.addPost("_csrftoken", ig.client.getToken())
			.addPost("_uid", ig.account_id)
			.addPost("_uuid", ig.uuid)
			.addPost("surfaces_to_queries", json_encode([Constants.SURFACE_PARAM[0] => query, Constants.SURFACE_PARAM[1] => query]))
			.addPost("version", 1)
			.addPost("scale", 2)
			.getResponse(Response.FetchQPDataResponse())
	}

	/**
	 * Get quick promotions cooldowns.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.QPCooldownsResponse
	 */
	fun getQPCooldowns() {
		return ig.request("qp/get_cooldowns/")
			.addParam("signed_body", Signatures.generateSignature(json_encode((object)[]) + ".{}"))
			.addParam("ig_sig_key_version", Constants.SIG_KEY_VERSION)
			.getResponse(Response.QPCooldownsResponse())
	}

	/**
	 * Internal helper for marking story media items as seen.
	 *
	 * This is used by story-related funs in other request-collections!
	 *
	 * @param responses.Model.Item[] items    Array of one or more story media Items.
	 * @param string|null           sourceId Where the story was seen from,
	 *                                        such as a location story-tray ID.
	 *                                        If NULL, we automatically import the
	 *                                        user"s profile ID from each Item
	 *                                        object as the source ID.
	 * @param string                module   Module where the story was found.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.MediaSeenResponse
	 *
	 * @see Story.markMediaSeen()
	 * @see Location.markStoryMediaSeen()
	 * @see Hashtag.markStoryMediaSeen()
	 */
	fun markStoryMediaSeen(array items, sourceId:String? = null, module:String = "feed_timeline") {
		// Build the list of seen media, with human randomization of seen-time.
		var reels = []
		val maxSeenAt = System.currentTimeMillis() / 1000L // Get current global UTC timestamp.
		var seenAt = maxSeenAt - (3 * items.count()) // Start seenAt in the past.
		for(item in items) {
			if (!item instanceof Response.Model.Item) {
				throw IllegalArgumentException("All story items must be instances of .instagramAPI.responses.model.Item.")
			}

			// Raise "seenAt" if it"s somehow older than the item"s "takenAt".
			// NOTE: Can only happen if you see a story instantly when posted.
			val itemTakenAt = item.getTakenAt()
			if (seenAt < itemTakenAt) {
				seenAt = itemTakenAt + 2
			}

			// Do not let "seenAt" exceed the current global UTC time.
			if (seenAt > maxSeenAt) {
				seenAt = maxSeenAt
			}

			// Determine the source ID for this item. This is where the item was
			// seen from, such as a UserID or a Location-StoryTray ID.
			val itemSourceId = if(sourceId === null) item.getUser().getPk() else sourceId

			// Key Format: "mediaPk_userPk_sourceId".
			// NOTE: In case of seeing stories on a user"s profile, their
			// userPk is used as the sourceId, as "mediaPk_userPk_userPk".
			val reelId = item.getId() + "_" + itemSourceId

			// Value Format: ["mediaTakenAt_seenAt"] (array with single string).
			reels[reelId] = arrayOf<String>(itemTakenAt + "_" + seenAt)

			// Randomly add 1-3 seconds to next seenAt timestamp, to act human.
			seenAt = seenAt + (1..3).random()
		}

		return ig.request("media/seen/")
			.setVersion(2)
			.addPost("_uuid", ig.uuid)
			.addPost("_uid", ig.account_id)
			.addPost("_csrftoken", ig.client.getToken())
			.addPost("container_module", module)
			.addPost("reels", reels)
			.addPost("reel_media_skipped", [])
			.addPost("live_vods", [])
			.addPost("live_vods_skipped", [])
			.addPost("nuxes", [])
			.addPost("nuxes_skipped", [])
			.addParam("reel", 1)
			.addParam("live_vod", 0)
			.getResponse(Response.MediaSeenResponse())
	}

	/**
	 * Configure media entity (album, video, ...) with retries.
	 *
	 * @param callable configurator Configurator fun.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .RuntimeException
	 * @throws .LogicException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return responses
	 */
	fun configureWithRetries(configurator: callable) {
		var attempt = 0
		var lastError = null
		while (true) {
			// Check for max retry-limit, and throw if we exceeded it.
			if (++attempt > self.MAX_CONFIGURE_RETRIES) {
				if (lastError === null) {
					throw RuntimeException("All configuration retries have failed.")
				}

				throw RuntimeException("All configuration retries have failed. Last error: $lastError")
			}

			var result = null

			try {
				/** @var responses result */
				result = configurator()
			} catch (e: ThrottledException) {
				throw e
			} catch (e: LoginRequiredException) {
				throw e
			} catch (e: FeedbackRequiredException) {
				throw e
			} catch (ConsentRequiredException) {
				throw e
			} catch (e: CheckpointRequiredException) {
				throw e
			} catch (e: InstagramException) {
				if (e.hasResponse()) {
					result = e.getResponse()
				}
				lastError = e
			} catch (e: Exception) {
				lastError = e
				// Ignore everything else.
			}

			// We had a network error or something like that, let"s continue to the next attempt.
			if (result === null) {
				sleep(1)
				continue
			}

			var httpResponse = result.getHttpResponse()
			var delay = 1
			when(httpResponse.getStatusCode()) {
				200 -> {
					// Instagram uses "ok" status for this error, so we need to check it first:
					// {"message": "media_needs_reupload", "error_title": "staged_position_not_found", "status": "ok"}
					if ((result.getMessage()).toLowerCase() === "media_needs_reupload") {
						throw RuntimeException("You need to reupload the media (${if(result.hasErrorTitle() && result.getErrorTitle() is String) result.getErrorTitle() else "unknown error"})."
								// We are reading a property that isn"t defined in the class
								// property map, so we must import "has" first, to ensure it exists.
						)
					} else if (result.isOk()) {
						return result
					}
					// Continue to the next attempt.
				}
				202 -> {
					// We are reading a property that isn"t defined in the class
					// property map, so we must import "has" first, to ensure it exists.
					if (result.hasCooldownTimeInSeconds() && result.getCooldownTimeInSeconds() !== null) {
						delay = max(result.getCooldownTimeInSeconds().toInt(), 1)
					}
				}
			}
			sleep(delay)
		}

		// We are never supposed to get here!
		throw LogicException("Something went wrong during configuration.")
	}

	/**
	 * Performs a resumable upload of a media file, with support for retries.
	 *
	 * @param MediaDetails mediaDetails
	 * @param Request      offsetTemplate
	 * @param Request      uploadTemplate
	 * @param bool         skipGet
	 *
	 * @throws  IllegalArgumentException
	 * @throws .RuntimeException
	 * @throws .LogicException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return responses.ResumableUploadResponse
	 */
	protected fun _uploadResumableMedia( mediaDetails:MediaDetails,  offsetTemplate:Request,  uploadTemplate:Request,
	                                    skipGet:Boolean) {
		// Open file handle.
		var handle = fopen(mediaDetails.getFilename(), "rb")
		if (handle === false) {
			throw RuntimeException("Failed to open media file for reading.")
		}

		try {
			val length = mediaDetails.getFilesize()

			// Create a stream for the opened file handle.
			val stream = Stream(handle, ["size" => length])

			var attempt = 0
			while (true) {
				// Check for max retry-limit, and throw if we exceeded it.
				if (++attempt > self.MAX_RESUMABLE_RETRIES) {
					throw RuntimeException("All retries have failed.")
				}

				try {
					var offset
					if (attempt === 1 && skipGet) {
						// It is obvious that the first attempt is always at 0, so we can skip a request.
						offset = 0
					} else {
						// Get current offset.
						val offsetRequest = clone offsetTemplate
						/** @var responses.ResumableOffsetResponse offsetResponse */
						val offsetResponse = offsetRequest.getResponse(Response.ResumableOffsetResponse())
						offset = offsetResponse.getOffset()
					}

					// Resume upload from given offset.
					val uploadRequest = clone uploadTemplate uploadRequest
						.addHeader("Offset", offset)
						.setBody(LimitStream(stream, length - offset, offset))
					/** @var responses.ResumableUploadResponse response */
					return uploadRequest.getResponse(Response.ResumableUploadResponse())

				} catch (e: ThrottledException) {
					throw e
				} catch (e: LoginRequiredException) {
					throw e
				} catch (e: FeedbackRequiredException) {
					throw e
				} catch (e: ConsentRequiredException) {
					throw e
				} catch (e: CheckpointRequiredException) {
					throw e
				} catch (e: Exception) {
					// Ignore everything else.
				}
			}
		} finally {
			Utils.safe_fclose(handle)
		}

		// We are never supposed to get here!
		throw LogicException("Something went wrong during media upload.")
	}

	/**
	 * Performs an upload of a photo file, without support for retries.
	 *
	 * @param int              targetFeed       One of the FEED_X constants.
	 * @param InternalMetadata internalMetadata Internal library-generated metadata object.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .RuntimeException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.UploadPhotoResponse
	 */
	protected fun _uploadPhotoInOnePiece(targetFeed:Int,  internalMetadata:InternalMetadata) {
		// Prepare payload for the upload request.
		request = ig.request("upload/photo/")
			.setSignedPost(false)
			.addPost("_uuid", ig.uuid)
			.addPost("_csrftoken", ig.client.getToken())
			.addFile("photo", internalMetadata.getPhotoDetails().getFilename(),"pending_media_" + Utils.generateUploadId() + ".jpg")

		for((key, value) in _getPhotoUploadParams(targetFeed, internalMetadata)) {
			request.addPost(key, value)
		}

		return request.getResponse(Response.UploadPhotoResponse())
	}

	/**
	 * Performs a resumable upload of a photo file, with support for retries.
	 *
	 * @param int              targetFeed       One of the FEED_X constants.
	 * @param InternalMetadata internalMetadata Internal library-generated metadata object.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .RuntimeException
	 * @throws .LogicException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.GenericResponse
	 */
	protected fun _uploadResumablePhoto(targetFeed:Int,  internalMetadata:InternalMetadata) {
		val photoDetails = internalMetadata.getPhotoDetails()

		val endpoint = "https://i.instagram.com/rupload_igphoto/${internalMetadata.getUploadId().toInt()}_0_${Utils.hashCode(photoDetails.getFilename()).toInt()}"

		var uploadParams = _getPhotoUploadParams(targetFeed, internalMetadata)
		uploadParams = Utils.reorderByHashCode(uploadParams)

		val offsetTemplate = Request(ig, endpoint)
		offsetTemplate.setAddDefaultHeaders(false)
			.addHeader("X_FB_PHOTO_WATERFALL_ID", Signatures.generateUUID(true))
			.addHeader("X-Instagram-Rupload-Params", json_encode(uploadParams))

		val uploadTemplate = clone offsetTemplate uploadTemplate
			.addHeader("X-Entity-Type", "image/jpeg")
			.addHeader("X-Entity-Name",basename(parse_url(endpoint,PHP_URL_PATH)))
			.addHeader("X-Entity-Length", photoDetails.getFilesize())

		return _uploadResumableMedia(photoDetails, offsetTemplate, uploadTemplate,ig.isExperimentEnabled("ig_android_skip_get_fbupload_photo_universe","photo_skip_get"))
	}

	/**
	 * Determine whether to import resumable photo uploader based on target feed and internal metadata.
	 *
	 * @param int              targetFeed       One of the FEED_X constants.
	 * @param InternalMetadata internalMetadata Internal library-generated metadata object.
	 *
	 * @return bool
	 */
	protected fun _useResumablePhotoUploader(targetFeed:Int,  internalMetadata:InternalMetadata): Boolean {

		return when(targetFeed) {
			Constants.FEED_TIMELINE_ALBUM -> ig.isExperimentEnabled("ig_android_sidecar_photo_fbupload_universe",
			                                     "is_enabled_fbupload_sidecar_photo")
			else -> ig.isExperimentEnabled("ig_android_photo_fbupload_universe", "is_enabled_fbupload_photo")
		}
	}

	/**
	 * Get the first missing range (start-end) from a HTTP "Range" header.
	 *
	 * @param string ranges
	 *
	 * @return array|null
	 */
	protected fun _getFirstMissingRange(ranges:String) {
		preg_match_all("/(?<start>.d+)-(?<end>.d+)./(?<total>.d+)/", ranges, matches, PREG_SET_ORDER)
		if (!matches.count()) {
			return
		}
		val pairs = listOf<Int>()
		var length = 0
		for(match in matches) {
			pairs[] = [match["start"], match["end"]]
			length = match["total"]
		}
		// Sort pairs by start.
		usort(pairs, fun(array pair1, array pair2) {
			return pair1[0] - pair2[0]
		})
		val first = pairs[0]
		val second = if(pairs.count() > 1) pairs[1] else null

		return if (first[0] == 0) {
			listOf( first[1] + 1, (if (second === null) length else second[0])-1 )
		} else {
			listOf(0, first[0] - 1)
		}
	}

	/**
	 * Performs a chunked upload of a video file, with support for retries.
	 *
	 * Note that chunk uploads often get dropped when their server is overloaded
	 * at peak hours, which is why our chunk-retry mechanism exists. We will
	 * try several times to upload all chunks. The retries will only re-upload
	 * the exact chunks that have been dropped from their server, and it won"t
	 * waste time with chunks that are already successfully uploaded.
	 *
	 * @param int              targetFeed       One of the FEED_X constants.
	 * @param InternalMetadata internalMetadata Internal library-generated metadata object.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .RuntimeException
	 * @throws .LogicException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.UploadVideoResponse
	 */
	protected fun _uploadVideoChunks(targetFeed:Int,  internalMetadata:InternalMetadata) {
		val videoFilename = internalMetadata.getVideoDetails().getFilename()

		// To support video uploads to albums, we MUST fake-inject the
		// "sessionid" cookie from "i.instagram" into our "upload.instagram"
		// request, otherwise the server will reply with a "StagedUpload not
		// found" error when the final chunk has been uploaded.
		var sessionIDCookie = null
		if (targetFeed === Constants.FEED_TIMELINE_ALBUM) {
			val foundCookie = ig.client.getCookie("sessionid", "i.instagram.com")
			if (foundCookie !== null) {
				sessionIDCookie = foundCookie.getValue()
			}
			if (sessionIDCookie === null || sessionIDCookie === "") { // Verify value.
				throw RuntimeException("Unable to find the necessary SessionID cookie for uploading video album chunks.")
			}
		}

		// Verify the upload URLs.
		val uploadUrls = internalMetadata.getVideoUploadUrls()
		if (!is_array(uploadUrls) || !count(uploadUrls)) {
			throw RuntimeException("No video upload URLs found.")
		}

		// Init state.
		val length = internalMetadata.getVideoDetails().getFilesize()
		val uploadId = internalMetadata.getUploadId()
		val sessionId = "$uploadId-${Utils.hashCode(videoFilename)}"
		var uploadUrl = array_shift(uploadUrls)
		var offset = 0
		var chunk = Math.min(length, MIN_CHUNK_SIZE)
		var attempt = 0

		// Open file handle.
		val handle = fopen(videoFilename, "rb")
		if (handle === false) {
			throw RuntimeException("Failed to open file for reading.")
		}

		try {
			// Create a stream for the opened file handle.
			var stream = Stream(handle)
			while (true) {
				// Check for this server"s max retry-limit, and switch server?
				if (++attempt > MAX_CHUNK_RETRIES) {
					uploadUrl = null
				}

				// Try to switch to another server.
				if (uploadUrl === null) {
					uploadUrl = array_shift(uploadUrls)
					// Fail if there are no upload URLs left.
					if (uploadUrl === null) {
						throw RuntimeException("There are no more upload URLs.")
					}
					// Reset state.
					attempt = 1 // As if "++attempt" had ran once, above.
					offset = 0
					chunk = Math.min(length, MIN_CHUNK_SIZE)
				}

				// Prepare request.
				val request = Request(ig, uploadUrl.getUrl())
				request.setAddDefaultHeaders(false)
					.addHeader("Content-Type", "application/octet-stream")
					.addHeader("Session-ID", sessionId)
					.addHeader("Content-Disposition", "attachment; filename=\"video.mov\"")
					.addHeader("Content-Range", "bytes " + offset + "-" + (offset + chunk - 1) + "/" + length)
					.addHeader("job", uploadUrl.getJob())
					.setBody(LimitStream(stream, chunk, offset))

				// When uploading videos to albums, we must fake-inject the
				// "sessionid" cookie (the official app fake-injects it too).
				if (targetFeed === Constants.FEED_TIMELINE_ALBUM && sessionIDCookie !== null) {
					// We"ll add it with the default options ("single use")
					// so the fake cookie is only added to THIS request.
					ig.client.fakeCookies().add("sessionid", sessionIDCookie)
				}

				// Perform the upload of the current chunk.
				val start = System.nanoTime()

				try {
					var httpResponse = request.getHttpResponse()
				} catch (NetworkException e) {
					// Ignore network exceptions.
					continue
				}

				// Determine chunk size based on upload duration.
				var newChunkSize = (chunk /( System.nanoTime() - start) * 5).toInt()
				// Ensure that the chunk size is in valid range.
				newChunkSize = min(MAX_CHUNK_SIZE, max(MIN_CHUNK_SIZE, newChunkSize))

				var result = null

				try {
					/** @var responses.UploadVideoResponse result */
					result = request.getResponse(Response.UploadVideoResponse())
				} catch (e: CheckpointRequiredException) {
					throw e
				} catch (e: LoginRequiredException) {
					throw e
				} catch (e: FeedbackRequiredException) {
					throw e
				} catch (e: ConsentRequiredException) {
					throw e
				} catch (e: Exception) {
					// Ignore everything else.
				}

				// Process the server response...
				when(httpResponse.getStatusCode()) {
					200 -> {
						// All chunks are uploaded, but if we don"t have a
						// response-result now then we must retry a server.
						if (result === null) {
							uploadUrl = null
							break
						}

						// SUCCESS! :-)
						return result
					}
					201 -> {
						// The server has given us a regular reply. We expect it
						// to be a range-reply, such as "0-3912399/23929393".
						// Their server often drops chunks during peak hours,
						// and in that case the first range may not start at
						// zero, or there may be gaps or multiple ranges, such
						// as "0-4076155/8152310,6114234-8152309/8152310". We"ll
						// handle that by re-uploading whatever they"ve dropped.
						if (!httpResponse.hasHeader("Range")) {
							uploadUrl = null
							break
						}
						val range = _getFirstMissingRange(httpResponse.getHeaderLine("Range"))
						if (range !== null) {
							offset = range[0]
							chunk = min(newChunkSize, range[1] - range[0] + 1)
						} else {
							chunk = min(newChunkSize, length - offset)
						}

						// Reset attempts count on successful upload.
						attempt = 0
					}
					400, 403, 511 -> throw RuntimeException("Instagram's server returned HTTP status \"${httpResponse.getStatusCode().toInt()}\".")
					422 -> throw RuntimeException("Instagram's server says that the video is corrupt.")
				}
			}
		} finally {
			// Guaranteed to release handle even if something bad happens above!
			Utils.safe_fclose(handle)
		}

		// We are never supposed to get here!
		throw LogicException("Something went wrong during video upload.")
	}

	/**
	 * Performs a segmented upload of a video file, with support for retries.
	 *
	 * @param int              targetFeed       One of the FEED_X constants.
	 * @param InternalMetadata internalMetadata Internal library-generated metadata object.
	 *
	 * @throws .exception
	 * @throws  IllegalArgumentException
	 * @throws .RuntimeException
	 * @throws .LogicException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.GenericResponse
	 */
	protected fun _uploadSegmentedVideo(targetFeed:Int,  internalMetadata:InternalMetadata) {
		val videoDetails = internalMetadata.getVideoDetails()

		// We must split the video into segments before running any requests.
		val segments = _splitVideoIntoSegments(targetFeed, videoDetails)

		var uploadParams = _getVideoUploadParams(targetFeed, internalMetadata)
		uploadParams = Utils.reorderByHashCode(uploadParams)

		// This request gives us a stream identifier.
		val startRequest = Request(ig, "https://i.instagram.com/rupload_igvideo/${Signatures.generateUUID()}?segmented=true&phase=start")
		startRequest.setAddDefaultHeaders(false)
			.addHeader("X-Instagram-Rupload-Params", json_encode(uploadParams))
			// Dirty hack to make a POST request.
			.setBody(stream_for())
		/** @var responses.SegmentedStartResponse startResponse */
		val startResponse = startRequest.getResponse(Response.SegmentedStartResponse())
		val streamId = startResponse.getStreamId()

		// Upload the segments.
		try {
			var offset = 0
			// Yep, no UUID here like in other resumable uploaders. Seems like a bug.
			val waterfallId = Utils.generateUploadId()
			for(segment in segments) {
				val endpoint = "https://i.instagram.com/rupload_igvideo/${(segment.getFilename()).md5()}-0-${segment.getFilesize().toInt()}?segmented=true&phase=transfer"

				val offsetTemplate = Request(ig, endpoint)
				offsetTemplate.setAddDefaultHeaders(false)
					.addHeader("Segment-Start-Offset", offset)
					// 1 => Audio, 2 => Video, 3 => Mixed.
					.addHeader("Segment-Type", if (segment.getAudioCodec() !== null) 1 else 2)
					.addHeader("Stream-Id", streamId)
					.addHeader("X_FB_VIDEO_WATERFALL_ID", waterfallId)
					.addHeader("X-Instagram-Rupload-Params", json_encode(uploadParams))

				val uploadTemplate = clone offsetTemplate uploadTemplate
					.addHeader("X-Entity-Type", "video/mp4")
					.addHeader("X-Entity-Name", basename(parse_url(endpoint, PHP_URL_PATH)))
					.addHeader("X-Entity-Length",segment.getFilesize())

				this._uploadResumableMedia(segment, offsetTemplate, uploadTemplate, false)
				// Offset seems to be used just for ordering the segments.
				offset += segment.getFilesize()
			}
		} finally {
			// Remove the segments, becaimport we don"t need them anymore.
			for(segment in segments) {
				@unlink(segment.getFilename())
			}
		}

		// Finalize the upload.
		val endRequest = Request(ig, "https://i.instagram.com/rupload_igvideo/${Signatures.generateUUID()}?segmented=true&phase=end")
		endRequest.setAddDefaultHeaders(false)
			.addHeader("Stream-Id", streamId)
			.addHeader("X-Instagram-Rupload-Params", json_encode(uploadParams))
			// Dirty hack to make a POST request.
			.setBody(stream_for())

		return endRequest.getResponse(Response.GenericResponse())
	}

	/**
	 * Performs a resumable upload of a video file, with support for retries.
	 *
	 * @param int              targetFeed       One of the FEED_X constants.
	 * @param InternalMetadata internalMetadata Internal library-generated metadata object.
	 *
	 * @throws  IllegalArgumentException
	 * @throws .RuntimeException
	 * @throws .LogicException
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.GenericResponse
	 */
	protected fun _uploadResumableVideo(targetFeed:Int,  internalMetadata:InternalMetadata) {
		val rurCookie = ig.client.getCookie("rur", "i.instagram.com")
		if (rurCookie === null || rurCookie.getValue() === "") {
			throw RuntimeException("Unable to find the necessary \"rur\" cookie for uploading video.")
		}

		val videoDetails = internalMetadata.getVideoDetails()

		val endpoint = "https://i.instagram.com/rupload_igvideo/${internalMetadata.getUploadId()}_0_${Utils.hashCode(videoDetails.getFilename())}?target=${rurCookie.getValue()}"

		var uploadParams = this._getVideoUploadParams(targetFeed, internalMetadata)
		uploadParams = Utils.reorderByHashCode(uploadParams)

		val offsetTemplate = Request(ig, endpoint)
		offsetTemplate.setAddDefaultHeaders(false)
			.addHeader("X_FB_VIDEO_WATERFALL_ID", Signatures.generateUUID(true))
			.addHeader("X-Instagram-Rupload-Params", json_encode(uploadParams))

		val uploadTemplate =
			clone offsetTemplate uploadTemplate
				.addHeader("X-Entity-Type", "video/mp4")
				.addHeader("X-Entity-Name", basename(parse_url(endpoint,PHP_URL_PATH)))
				.addHeader("X-Entity-Length", videoDetails.getFilesize())

		return _uploadResumableMedia(videoDetails, offsetTemplate, uploadTemplate,
		                                  ig.isExperimentEnabled("ig_android_skip_get_fbupload_universe","video_skip_get"))
	}

	/**
	 * Determine whether to import segmented video uploader based on target feed and internal metadata.
	 *
	 * @param int              targetFeed       One of the FEED_X constants.
	 * @param InternalMetadata internalMetadata Internal library-generated metadata object.
	 *
	 * @return bool
	 */
	protected fun _useSegmentedVideoUploader(targetFeed:Int,  internalMetadata:InternalMetadata): Boolean {
		// No segmentation for album video.
		if (targetFeed === Constants.FEED_TIMELINE_ALBUM) {
			return false
		}

		// ffmpeg is required for video segmentation.
		try {
			FFmpeg.factory()
		} catch (e: Exception) {
			return false
		}

		// There is no need to segment short videos.
		val minDuration = when(targetFeed) {
			Constants.FEED_TIMELINE -> {
				ig.getExperimentParam(
					"ig_android_video_segmented_upload_universe",
					// NOTE: This typo is intentional. Instagram named it that way.
					"segment_duration_threashold_feed", 10
				).toInt()
			}

			Constants.FEED_STORY, Constants.FEED_DIRECT_STORY -> {
				ig.getExperimentParam(
					"ig_android_video_segmented_upload_universe",
					// NOTE: This typo is intentional. Instagram named it that way.
					"segment_duration_threashold_story_raven", 0
				).toInt()
			}

			Constants.FEED_TV -> 150

			else -> 31536000 // 1 year.
		}

		if (internalMetadata.getVideoDetails().getDuration().toInt() < minDuration) {
			return false
		}

		// Check experiments for the target feed.

		return when(targetFeed) {
			Constants.FEED_TIMELINE -> ig.isExperimentEnabled("ig_android_video_segmented_upload_universe", "segment_enabled_feed", true)

			Constants.FEED_DIRECT -> ig.isExperimentEnabled("ig_android_direct_video_segmented_upload_universe","is_enabled_segment_direct")

			Constants.FEED_STORY, Constants.FEED_DIRECT_STORY -> ig.isExperimentEnabled("ig_android_reel_raven_video_segmented_upload_universe","segment_enabled_story_raven")

			Constants.FEED_TV -> true

			else -> ig.isExperimentEnabled("ig_android_video_segmented_upload_universe", "segment_enabled_unknown",true)
		}
	}

	/**
	 * Determine whether to import resumable video uploader based on target feed and internal metadata.
	 *
	 * @param int              targetFeed       One of the FEED_X constants.
	 * @param InternalMetadata internalMetadata Internal library-generated metadata object.
	 *
	 * @return bool
	 */
	protected fun _useResumableVideoUploader(targetFeed:Int,  internalMetadata:InternalMetadata): Boolean{
		return when(targetFeed) {
			Constants.FEED_TIMELINE_ALBUM -> ig.isExperimentEnabled(
				"ig_android_fbupload_sidecar_video_universe",
				"is_enabled_fbupload_sidecar_video"
			)

			Constants.FEED_TIMELINE -> ig.isExperimentEnabled(
				"ig_android_upload_reliability_universe",
				"is_enabled_fbupload_followers_share"
			)

			Constants.FEED_DIRECT -> ig.isExperimentEnabled(
				"ig_android_upload_reliability_universe",
				"is_enabled_fbupload_direct_share"
			)

			Constants.FEED_STORY -> ig.isExperimentEnabled(
				"ig_android_upload_reliability_universe",
				"is_enabled_fbupload_reel_share"
			)

			Constants.FEED_DIRECT_STORY -> ig.isExperimentEnabled(
				"ig_android_upload_reliability_universe",
				"is_enabled_fbupload_story_share"
			)

			Constants.FEED_TV -> true

			else -> ig.isExperimentEnabled("ig_android_upload_reliability_universe", "is_enabled_fbupload_unknown")
		}
	}

	/**
	 * Get retry context for media upload.
	 *
	 * @return array
	 */
	protected fun _getRetryContext(): MutableMap<String, Int> {
		return mutableMapOf(
			// TODO increment it with every fail.
			"num_step_auto_retry"   to 0,
			"num_reupload"          to 0,
			"num_step_manual_retry" to 0
		)
	}

	/**
	 * Get params for photo upload job.
	 *
	 * @param int              targetFeed       One of the FEED_X constants.
	 * @param InternalMetadata internalMetadata Internal library-generated metadata object.
	 *
	 * @return array
	 */
	protected fun _getPhotoUploadParams(targetFeed:Int,  internalMetadata:InternalMetadata): MutableMap<String, String> {
		// Common params.
		val result = mutableMapOf(
			"upload_id"         to internalMetadata.getUploadId().toString(),
			"retry_context"     to json_encode(_getRetryContext()),
			"image_compression" to "{\"lib_name\":\"moz\",\"lib_version\":\"3.1.m\",\"quality\":\"87\"}",
			"xsharing_user_ids" to json_encode([]),
			"media_type"        to if (internalMetadata.getVideoDetails() !== null) {
				Response.Model.Item.VIDEO.toString()
				}else {Response.Model.Item.PHOTO.toString()}
		)
		// Target feed"s specific params.
		when(targetFeed) {
			Constants.FEED_TIMELINE_ALBUM -> result["is_sidecar"] = "1"
		}

		return result
	}

	/**
	 * Get params for video upload job.
	 *
	 * @param int              targetFeed       One of the FEED_X constants.
	 * @param InternalMetadata internalMetadata Internal library-generated metadata object.
	 *
	 * @return array
	 */
	protected fun _getVideoUploadParams(targetFeed:Int,  internalMetadata:InternalMetadata): MutableMap<String, String> {
		val videoDetails = internalMetadata.getVideoDetails()
		// Common params.
		val result = mutableMapOf<String, Any>(
			"upload_id"                to internalMetadata.getUploadId().toString(),
			"retry_context"            to json_encode(this._getRetryContext()),
			"xsharing_user_ids"        to json_encode([]),
			"upload_media_height"      to videoDetails.getHeight().toString(),
			"upload_media_width"       to videoDetails.getWidth().toString(),
			"upload_media_duration_ms" to videoDetails.getDurationInMsec().toString(),
			"media_type"               to Response.Model.Item.VIDEO.toString(),
			// TODO select with targetFeed (?)
			"potential_share_types"    to json_encode(["not supported type"])
		)
		// Target feed"s specific params.
		when(targetFeed) {
			Constants.FEED_TIMELINE_ALBUM -> result["is_sidecar"] = "1"
			Constants.FEED_DIRECT -> {
				result["direct_v2"] = "1"
				result["rotate"] = "0"
				result["hflip"] = "false"
			}
			Constants.FEED_STORY 		-> result["for_album"] = "1"
			Constants.FEED_DIRECT_STORY -> result["for_direct_story"] = "1"
			Constants.FEED_TV 			-> result["is_igtv_video"] = "1"
		}

		return result
	}

	/**
	 * Find the segments after ffmpeg processing.
	 *
	 * @param string outputDirectory The directory to look in.
	 * @param string prefix          The filename prefix.
	 *
	 * @return array
	 */
	protected fun _findSegments(outputDirectory:String, prefix:String) {
		// Video segments will be uploaded before the audio one.
		val result = glob("{$outputDirectory}/{$prefix}.video.*.mp4")

		// Audio always goes into one segment, so we can import is_file() here.
		val audioTrack = "{outputDirectory}/{prefix}.audio.mp4"
		if (is_file(audioTrack)) {
			result[] = audioTrack
		}

		return result
	}

	/**
	 * Split the video file into segments.
	 *
	 * @param int          targetFeed      One of the FEED_X constants.
	 * @param VideoDetails videoDetails
	 * @param FFmpeg|null  ffmpeg
	 * @param string|null  outputDirectory
	 *
	 * @throws .exception
	 *
	 * @return VideoDetails[]
	 */
	protected fun _splitVideoIntoSegments(targetFeed: Int,  videoDetails: VideoDetails,  ffmpegRE: FFmpeg? = null,
	                                      outputDirectoryRE: String? = null) {
		var ffmpeg = ffmpegRE
		var outputDirectory = outputDirectoryRE
		if (ffmpeg === null) {
			ffmpeg = FFmpeg.factory()
		}
		if (outputDirectory === null) {
			outputDirectory = if (Utils.defaultTmpPath === null) sys_get_temp_dir() else Utils.defaultTmpPath
		}
		// Check whether the output directory is valid.
		val targetDirectory = realpath(outputDirectory)
		if (targetDirectory === false || !is_dir(targetDirectory) || !is_writable(targetDirectory)) {
			throw RuntimeException("Directory \"$outputDirectory\" is missing or is not writable.")
		}

		val prefix = sha1(videoDetails.getFilename().uniqid("", true))

		try {
			// Split the video stream into a multiple segments by time.
			ffmpeg.run("-i ${Args.escape(videoDetails.getFilename())} -c:v " +
					"copy -an -dn -sn -f segment -segment_time ${_getTargetSegmentDuration(targetFeed)} -segment_format mp4 " +
					"${Args.escape("$outputDirectory$DIRECTORY_SEPARATOR$prefix.video.%%03d.mp4")}")

			if (videoDetails.getAudioCodec() !== null) {
				// Save the audio stream in one segment.
				ffmpeg.run("-i ${Args.escape(videoDetails.getFilename())} -c:a copy -vn -dn -sn -f mp4 " +
						"${Args.escape("$outputDirectory$DIRECTORY_SEPARATOR$prefix.audio.mp4")}")
			}
		} catch (e: RuntimeException) {
			// Find and remove all segments (if any).
			var files = _findSegments(outputDirectory, prefix)
			for(file in files) {
				@unlink(file)
			}
			// Re-throw the exception.
			throw e
		}

		// Collect segments.
		val files = _findSegments(outputDirectory, prefix)
		if (empty(files)) {
			throw RuntimeException("Something went wrong while splitting the video into segments.")
		}
		var result = []

		try {
			// Wrap them into VideoDetails.
			for(file in files) {
				result[] = VideoDetails(file)
			}
		} catch (e: Exception) {
			// Cleanup when something went wrong.
			for(file in files) {
				@unlink(file)
			}

			throw e
		}

		return result
	}

	/**
	 * Get target segment duration in seconds.
	 *
	 * @param int targetFeed One of the FEED_X constants.
	 *
	 * @throws  IllegalArgumentException
	 *
	 * @return int
	 */
	protected fun _getTargetSegmentDuration(targetFeed: Int): Int {
		val duration = when(targetFeed) {
			Constants.FEED_TIMELINE -> {
				ig.getExperimentParam("ig_android_video_segmented_upload_universe",
					"target_segment_duration_feed",5)
			}
			Constants.FEED_STORY, Constants.FEED_DIRECT_STORY -> {
				ig.getExperimentParam(
					"ig_android_video_segmented_upload_universe",
					"target_segment_duration_story_raven", 2
				)
			}
			Constants.FEED_TV -> {
				100
			}
			else -> {
				throw IllegalArgumentException("Unsupported feed {targetFeed}.")
			}
		}

		return duration.toInt()
	}
}
