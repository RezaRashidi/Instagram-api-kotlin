

package instagramAPI.requests.metadata

import instagramAPI.media.Constraints.ConstraintsFactory
import instagramAPI.media.Photo.PhotoDetails
import instagramAPI.media.Video.VideoDetails
import instagramAPI.Utils

final class Internal
{
    /** @var PhotoDetails */
    private $_photoDetails

    /** @var VideoDetails */
    private $_videoDetails

    /** @var string */
    private $_uploadId

    /** @var VideoUploadUrl[] */
    private $_videoUploadUrls

    /** @var UploadVideoResponse */
    private $_videoUploadResponse

    /** @var UploadPhotoResponse */
    private $_photoUploadResponse

    /** @var string */
    private $_directThreads

    /** @var string */
    private $_directUsers

    /** @var bool */
    private $_bestieMedia

    /**
     * Constructor.
     *
     * @param string|null $uploadId
     */
    fun __construct(
        $uploadId = null)
    {
        if ($uploadId !== null) {
            this._uploadId = $uploadId
        } else {
            this._uploadId = Utils::generateUploadId()
        }
        this._bestieMedia = false
    }

    /**
     * @return PhotoDetails
     */
    fun getPhotoDetails()
    {
        return this._photoDetails
    }

    /**
     * @return VideoDetails
     */
    fun getVideoDetails()
    {
        return this._videoDetails
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
    fun setVideoDetails(
        $targetFeed,
        $videoFilename)
    {
        // Figure out the video file details.
        // NOTE: We do this first, since it validates whether the video file is
        // valid and lets us avoid wasting time uploading totally invalid files!
        this._videoDetails = VideoDetails($videoFilename)

        // Validate the video details and throw if Instagram won"t allow it.
        this._videoDetails.validate(ConstraintsFactory::createFor($targetFeed))

        return this._videoDetails
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
    fun setPhotoDetails(
        $targetFeed,
        $photoFilename)
    {
        // Figure out the photo file details.
        // NOTE: We do this first, since it validates whether the photo file is
        // valid and lets us avoid wasting time uploading totally invalid files!
        this._photoDetails = PhotoDetails($photoFilename)

        // Validate the photo details and throw if Instagram won"t allow it.
        this._photoDetails.validate(ConstraintsFactory::createFor($targetFeed))

        return this._photoDetails
    }

    /**
     * @return string
     */
    fun getUploadId()
    {
        return this._uploadId
    }

    /**
     * Set upload URLs from a UploadJobVideoResponse response.
     *
     * @param UploadJobVideoResponse $response
     *
     * @return VideoUploadUrl[]
     */
    fun setVideoUploadUrls(
        UploadJobVideoResponse $response)
    {
        this._videoUploadUrls = []
        if ($response.getVideoUploadUrls() !== null) {
            this._videoUploadUrls = $response.getVideoUploadUrls()
        }

        return this._videoUploadUrls
    }

    /**
     * @return VideoUploadUrl[]
     */
    fun getVideoUploadUrls()
    {
        return this._videoUploadUrls
    }

    /**
     * @return UploadVideoResponse
     */
    fun getVideoUploadResponse()
    {
        return this._videoUploadResponse
    }

    /**
     * @param UploadVideoResponse $videoUploadResponse
     */
    fun setVideoUploadResponse(
        UploadVideoResponse $videoUploadResponse)
    {
        this._videoUploadResponse = $videoUploadResponse
    }

    /**
     * @return UploadPhotoResponse
     */
    fun getPhotoUploadResponse()
    {
        return this._photoUploadResponse
    }

    /**
     * @param UploadPhotoResponse $photoUploadResponse
     */
    fun setPhotoUploadResponse(
        UploadPhotoResponse $photoUploadResponse)
    {
        this._photoUploadResponse = $photoUploadResponse
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
    fun setDirectRecipients(
        array $recipients)
    {
        if (isset($recipients["users"])) {
            this._directUsers = $recipients["users"]
            this._directThreads = "[]"
        } elseif (isset($recipients["thread"])) {
            this._directUsers = "[]"
            this._directThreads = $recipients["thread"]
        } else {
            throw  IllegalArgumentException("Please provide at least one recipient.")
        }

        return this
    }

    /**
     * @return string
     */
    fun getDirectThreads()
    {
        return this._directThreads
    }

    /**
     * @return string
     */
    fun getDirectUsers()
    {
        return this._directUsers
    }

    /**
     * Set bestie media state.
     *
     * @param bool $bestieMedia
     */
    fun setBestieMedia(
        $bestieMedia)
    {
        this._bestieMedia = $bestieMedia
    }

    /**
     * @return bool
     */
    fun isBestieMedia()
    {
        return this._bestieMedia
    }
}
