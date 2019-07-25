

package instagramAPI.requests.metadata

import instagramAPI.media.Constraints.ConstraintsFactory
import instagramAPI.media.Photo.PhotoDetails
import instagramAPI.media.Video.VideoDetails
import instagramAPI.Utils
import instagramAPI.responses.UploadJobVideoResponse
import instagramAPI.responses.UploadPhotoResponse
import instagramAPI.responses.UploadVideoResponse
import instagramAPI.responses.model.VideoUploadUrl

final class Internal
{
    /** @var PhotoDetails */
    private lateinit var _photoDetails: PhotoDetails

    /** @var VideoDetails */
    private lateinit var _videoDetails: VideoDetails

    /** @var string */
    private lateinit var _uploadId: String

    /** @var VideoUploadUrl[] */
    private lateinit var _videoUploadUrls: MutableList<VideoUploadUrl>

    /** @var UploadVideoResponse */
    private lateinit var _videoUploadResponse: UploadVideoResponse

    /** @var UploadPhotoResponse */
    private lateinit var _photoUploadResponse: UploadPhotoResponse

    /** @var string */
    private lateinit var _directThreads: String

    /** @var string */
    private lateinit var _directUsers: String

    /** @var bool */
    private var _bestieMedia: Boolean = false

    /**
     * Constructor.
     *
     * @param string|null $uploadId
     */
    fun constructor(uploadId: String? = null){
        _uploadId = if (uploadId !== null) {
            uploadId
        } else {
            Utils.generateUploadId()
        }
        _bestieMedia = false
    }

    /**
     * @return PhotoDetails
     */
    fun getPhotoDetails(): PhotoDetails {
        return _photoDetails
    }

    /**
     * @return VideoDetails
     */
    fun getVideoDetails(): VideoDetails {
        return _videoDetails
    }

    /**
     * Set video details from the given filename.
     *
     * @param int    $targetFeed    One of the FEED_X constants.
     * @param string $videoFilename
     *
     * @throws  IllegalArgumentException If the video file is missing or invalid, or Instagram won"t allow this video.
     * @throws .RuntimeException         In case of various processing errors.
     *
     * @return VideoDetails
     */
    fun setVideoDetails(targetFeed: Int, videoFilename: String): VideoDetails {
        // Figure out the video file details.
        // NOTE: We do this first, since it validates whether the video file is
        // valid and lets us avoid wasting time uploading totally invalid files!
        _videoDetails = VideoDetails(videoFilename)

        // Validate the video details and throw if Instagram won"t allow it.
        _videoDetails.validate(ConstraintsFactory.createFor(targetFeed))

        return _videoDetails
    }

    /**
     * Set photo details from the given filename.
     *
     * @param int    $targetFeed    One of the FEED_X constants.
     * @param string $photoFilename
     *
     * @throws  IllegalArgumentException If the photo file is missing or invalid, or Instagram won"t allow this photo.
     * @throws .RuntimeException         In case of various processing errors.
     *
     * @return PhotoDetails
     */
    fun setPhotoDetails(targetFeed: Int, photoFilename: String): PhotoDetails {
        // Figure out the photo file details.
        // NOTE: We do this first, since it validates whether the photo file is
        // valid and lets us avoid wasting time uploading totally invalid files!
        _photoDetails = PhotoDetails(photoFilename)

        // Validate the photo details and throw if Instagram won"t allow it.
        _photoDetails.validate(ConstraintsFactory.createFor(targetFeed))

        return _photoDetails
    }

    /**
     * @return string
     */
    fun getUploadId(): String {
        return _uploadId
    }

    /**
     * Set upload URLs from a UploadJobVideoResponse response.
     *
     * @param UploadJobVideoResponse $response
     *
     * @return VideoUploadUrl[]
     */
    fun setVideoUploadUrls(response: UploadJobVideoResponse): MutableList<VideoUploadUrl> {
        _videoUploadUrls = mutableListOf()
        if (response.getVideoUploadUrls() !== null) {
            _videoUploadUrls = response.getVideoUploadUrls()
        }

        return _videoUploadUrls
    }

    /**
     * @return VideoUploadUrl[]
     */
    fun getVideoUploadUrls(): MutableList<VideoUploadUrl> {
        return _videoUploadUrls
    }

    /**
     * @return UploadVideoResponse
     */
    fun getVideoUploadResponse(): UploadVideoResponse {
        return _videoUploadResponse
    }

    /**
     * @param UploadVideoResponse $videoUploadResponse
     */
    fun setVideoUploadResponse(videoUploadResponse: UploadVideoResponse){
        _videoUploadResponse = videoUploadResponse
    }

    /**
     * @return UploadPhotoResponse
     */
    fun getPhotoUploadResponse(): UploadPhotoResponse {
        return _photoUploadResponse
    }

    /**
     * @param UploadPhotoResponse $photoUploadResponse
     */
    fun setPhotoUploadResponse(photoUploadResponse: UploadPhotoResponse){
        _photoUploadResponse = photoUploadResponse
    }

    /**
     * Add Direct recipients to metadata.
     *
     * @param array $recipients
     *
     * @throws  IllegalArgumentException
     *
     * @return self
     */
    fun setDirectRecipients(recipients: Map<String, String>): Internal {
        when {
            recipients.keys.contains("users") -> {
                _directUsers = recipients["users"].toString()
                _directThreads = "[]"
            }
            recipients.keys.contains("thread") -> {
                _directUsers = "[]"
                _directThreads = recipients["thread"].toString()
            }
            else -> throw  IllegalArgumentException("Please provide at least one recipient.")
        }

        return this
    }

    /**
     * @return string
     */
    fun getDirectThreads(): String {
        return _directThreads
    }

    /**
     * @return string
     */
    fun getDirectUsers(): String {
        return _directUsers
    }

    /**
     * Set bestie media state.
     *
     * @param bool $bestieMedia
     */
    fun setBestieMedia(bestieMedia: Boolean){
        _bestieMedia = bestieMedia
    }

    /**
     * @return bool
     */
    fun isBestieMedia(): Boolean {
        return _bestieMedia
    }
}
