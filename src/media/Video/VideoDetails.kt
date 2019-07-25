

package instagramAPI.media.Video

import instagramAPI.media.ConstraintsInterface
import instagramAPI.media.MediaDetails
import instagramAPI.Utils
import Winbox.Args
import java.io.File
import kotlin.math.roundToInt

class VideoDetails : MediaDetails(){
    /**
     * Minimum allowed video width.
     *
     * These are decided by Instagram. Not by us!
     *
     * This value is the same for both stories and general media.
     *
     * @var int
     */
    val MIN_WIDTH = 480

    /**
     * Maximum allowed video width.
     *
     * These are decided by Instagram. Not by us!
     *
     * This value is the same for both stories and general media.
     *
     * @var int
     */
    val MAX_WIDTH = 720

    /** @var float */
    private var _duration: Float

    /** @var string */
    private lateinit var _videoCodec: String

    /** @var string|null */
    private lateinit var _audioCodec: String?

    /** @var string */
    private lateinit var _container: String

    /** @var int */
    private var _rotation: Int

    /**
     * @return float
     */
    fun getDuration(): Float{
        return _duration
    }

    /**
     * @return int
     */
    fun getDurationInMsec(): Int{
        // NOTE: ceil() is to round up and get rid of any MS decimals.
        return Math.ceil(getDuration().toDouble() * 1000).toInt()
    }

    /**
     * @return string
     */
    fun getVideoCodec(): String{
        return _videoCodec
    }

    /**
     * @return string|null
     */
    fun getAudioCodec(): String?{
        return _audioCodec
    }

    /**
     * @return string
     */
    fun getContainer(): String{
        return _container
    }

    /** {@inheritdoc} */
    fun hasSwappedAxes(): Int {
        return _rotation % 180
    }

    /** {@inheritdoc} */
    fun isHorizontallyFlipped(): Boolean {
        return _rotation === 90 || _rotation === 180
    }

    /** {@inheritdoc} */
    fun isVerticallyFlipped(): Boolean {
        return _rotation === 180 || _rotation === 270
    }

    /** {@inheritdoc} */
    fun getMinAllowedWidth(): Int {
        return MIN_WIDTH
    }

    /** {@inheritdoc} */
    fun getMaxAllowedWidth(): Int {
        return MAX_WIDTH
    }

    /**
     * Constructor.
     *
     * @param string $filename Path to the video file.
     *
     * @throws  IllegalArgumentException If the video file is missing or invalid.
     * @throws .RuntimeException         If FFmpeg isn"t working properly.
     */
    fun constructor( filename: String){
        // Check if input file exists.
        if (filename.isEmpty() || !File(filename).isFile) {
            throw  IllegalArgumentException("The video file \"$filename\" does not exist on disk.")
        }

        // Determine video file size and throw when the file is empty.
        val filesize = filesize(filename)
        if (filesize < 1) {
            throw  IllegalArgumentException("The video \"$filename\" is empty.")
        }

        // The user must have FFprobe.
        val ffprobe = Utils.checkFFPROBE()
        if (ffprobe as Boolean === false) {
            throw RuntimeException("You must have FFprobe to analyze video details. Ensure that its binary-folder exists in your PATH environment variable, or manually set its full path via \"\\instagramAPI\\Utils.ffprobeBin = '/home/exampleuser/ffmpeg/bin/ffprobe';\" at the start of your script.")
        }

        // Load with FFPROBE. Shows details as JSON and exits.
        val command = "${Args.escape(ffprobe)} -v quiet -print_format json -show_format -show_streams ${Args.escape(filename)}"
        val jsonInfo = @shell_exec(command)

        // Check for processing errors.
        if (jsonInfo === null) {
            throw RuntimeException("FFprobe failed to analyze your video file \"$filename\".")
        }

        // Attempt to decode the JSON.
        val probeResult = @json_decode(jsonInfo, true)
        if (probeResult === null) {
            throw RuntimeException("FFprobe gave us invalid JSON for \"$filename\".")
        }

        if ((probeResult["streams"].isBlank()) || !is_array(probeResult["streams"])) {
            throw RuntimeException("FFprobe failed to detect any stream. Is \"$filename\" a valid media file?")
        }

        // Now analyze all streams to find the first video stream.
        var width: Int? = null
        var height: Int? = null
        for ((streamIdx, streamInfo) in probeResult["streams"]) {
            if ((streamInfo["codec_type"].isBlank())) {
                continue
            }
            when (streamInfo["codec_type"]) {
                "video" -> {
                    // TODO Mark media as invalid if more than one video stream found (?)
                    var foundVideoStream = true
                    _videoCodec = streamInfo["codec_name"].toString() // string
                    width = streamInfo["width"].toInt()
                    height = streamInfo["height"].toInt()
                    if ((streamInfo["duration"]).isBlank()) {
                        // NOTE: Duration is a float such as "230.138000".
                        _duration = streamInfo["duration"].toFloat()
                    }

                    // Read rotation angle from tags.
                    _rotation = 0
                    if (!(streamInfo["tags"].isBlank()) && is_array(streamInfo["tags"])) {
                        val tags = array_change_key_case(streamInfo["tags"], CASE_LOWER)
                        if ( !(tags["rotate"].isBlank()) ) {
                        _rotation = _normalizeRotation(tags["rotate"].toInt())
                    }
                    }
                }
                "audio" -> {
                    // TODO Mark media as invalid if more than one audio stream found (?)
                    _audioCodec = streamInfo["codec_name"].toString() // string
                }
                //default:
                    // TODO Mark media as invalid if unknown stream found (?)
            }
        }

        // Sometimes there is no duration in stream info, so we should check the format.
        if ( _duration === null && !(probeResult["format"]["duration"].isBlank()) ) {
            _duration = probeResult["format"]["duration"].toFloat()
        }

        if ( !(probeResult["format"]["format_name"].isblank()) ) {
            _container = probeResult["format"]["format_name"].toStrig()
        }

        // Make sure we have detected the video duration.
        if (_duration === null) {
            throw RuntimeException("FFprobe failed to detect video duration. Is \"$filename\" a valid video file?")
        }

        parent::__construct(filename, filesize, width, height)
    }

    /** {@inheritdoc} */
    fun validate( constraints: ConstraintsInterface){
        super validate(constraints)

        // WARNING TO CONTRIBUTORS: $mediaFilename is for ERROR DISPLAY to
        // users. Do NOT import it to read from the hard disk!
        val mediaFilename = getBasename()

        // Make sure we have found at least one video stream.
        if (_videoCodec === null) {
            throw IllegalArgumentException("Instagram requires video with at least one video stream. Your file \"$mediaFilename\" doesn't have any.")
        }

        // Check the video stream. We should have at least one.
        if (_videoCodec !== "h264") {
            throw IllegalArgumentException("Instagram only accepts videos encoded with the H.264 video codec. Your file \"$mediaFilename\" has \"$_videoCodec\".")
        }

        // Check the audio stream (if available).
        if (_audioCodec !== null && _audioCodec !== "aac") {
            throw IllegalArgumentException("Instagram only accepts videos encoded with the AAC audio codec. Your file \"$mediaFilename\" has \"$_audioCodec\".")
        }

        // Check the container format.
        if (_container.indexOf("mp4") < 0) {
            throw IllegalArgumentException("Instagram only accepts videos packed into MP4 containers. Your file \"$mediaFilename\" has \"$_container\".")
        }

        // Validate video resolution. Instagram allows between 480px-720px width.
        // NOTE: They"ll resize 720px wide videos on the server, to 640px instead.
        // NOTE: Their server CAN receive between 320px-1080px width without
        // rejecting the video, but the official app would NEVER upload such
        // resolutions. It"s controlled by the "ig_android_universe_video_production"
        // experiment variable, which currently enforces width of min:480, max:720.
        // If users want to upload bigger videos, they MUST resize locally first!
        val width = getWidth()
        if (width < MIN_WIDTH || width > MAX_WIDTH) {
            throw IllegalArgumentException("Instagram only accepts videos that are between $MIN_WIDTH and $MAX_WIDTH pixels wide. Your file \"mediaFilename\" is $width pixels wide.")
        }

        // Validate video length.
        // NOTE: Instagram has no disk size limit, but this length validation
        // also ensures we can only upload small files exactly as intended.
        val duration = getDuration()
        val minDuration = constraints.getMinDuration()
        val maxDuration = constraints.getMaxDuration()
        if (duration < minDuration || duration > maxDuration) {
            throw IllegalArgumentException("Instagram only accepts ${constraints.getTitle()} videos that are between ${"%.3f".format(minDuration)} and ${"%.3f".format(maxDuration)} seconds long. Your video \"$mediaFilename\" is ${"%.3f".format(duration)} seconds long.")
        }
    }

    /**
     * @param rotation
     *
     * @return int
     */
    private fun _normalizeRotation(rotation: Int): Int{
        // The angle must be in 0..359 degrees range.
        var result = rotation % 360
        // Negative angle can be normalized by adding it to 360:
        // 360 + (-90) = 270.
        if (result < 0) {
            result += 360
        }
        // The final angle must be one of 0, 90, 180 or 270 degrees.
        // So we are rounding it to the closest one.
        result = (result.toDouble() / 90).roundToInt() * 90

        return result
    }
}
