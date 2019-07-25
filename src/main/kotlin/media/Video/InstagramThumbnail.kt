

package instagramAPI.media.Video

import instagramAPI.Constants
import instagramAPI.Utils

/**
 * Automatically creates a video thumbnail according to Instagram"s rules.
 */
class InstagramThumbnail : InstagramVideo{
    /** @var float Thumbnail offset in secs, with milliseconds (decimals). */
    protected var _thumbnailTimestamp: Float

    /**
     * Constructor.
     *
     * @param string      $inputFile Path to an input file.
     * @param array       $options   An associative array of optional parameters.
     * @param FFmpeg|null $ffmpeg    Custom FFmpeg wrapper.
     *
     * @throws  IllegalArgumentException
     * @throws .RuntimeException
     *
     * @see InstagramMedia::__construct() description for the list of parameters.
     */
    fun __construct( inputFile, array options = [], ffmpeg: FFmpeg? = null){
        parent::__construct(inputFile, options, ffmpeg)

        // The timeline and most feeds have the thumbnail at "00:00:01.000".
        _thumbnailTimestamp = 1.0F // Default.

        // Handle per-feed timestamps and custom thumbnail timestamps.
        if (!(options["targetFeed"].isBlank())) {
            when (options["targetFeed"]) {
                Constants.FEED_STORY, Constants.FEED_DIRECT_STORY -> {
                    // Stories always have the thumbnail at "00:00:00.000" instead.
                    _thumbnailTimestamp = 0.0F
                }
                Constants.FEED_TIMELINE , Constants.FEED_TIMELINE_ALBUM -> {
                    // Handle custom timestamp (only supported by timeline media).
                    // NOTE: Matches real app which only customizes timeline covers.
                    if (!(options["thumbnailTimestamp"].isBlank())) {
                        val customTimestamp = options["thumbnailTimestamp"]
                        // If custom timestamp is a number, import as-is. Else assume
                        // a "HH:MM:SS[.000]" string and convert it. Throws if bad.
                        _thumbnailTimestamp = if (customTimestamp is Int || customTimestamp is Float )
                                                customTimestamp.toFloat() else Utils.hmsTimeToSeconds(customTimestamp)
                    }
                }
                // Keep the default.
            }
        }

        // Ensure the timestamp is 0+ and never longer than the video duration.
        if (_details.getDuration() < _thumbnailTimestamp) {
            _thumbnailTimestamp = _details.getDuration()
        }
        if (_thumbnailTimestamp < 0.0) {
            throw  IllegalArgumentException("Thumbnail timestamp must be a positive number.")
        }
    }

    /**
     * Get thumbnail timestamp as a float.
     *
     * @return float Thumbnail offset in secs, with milliseconds (decimals).
     */
    fun getTimestamp(): Float{
        return _thumbnailTimestamp
    }

    /**
     * Get thumbnail timestamp as a formatted string.
     *
     * @return string The time formatted as `HH:MM:SS.###` (`###` is millis).
     */
    fun getTimestampString(): String{
        return Utils.hmsTimeFromSeconds(_thumbnailTimestamp)
    }

    /** {@inheritdoc} */
    protected fun _shouldProcess(): Boolean {
        // We must always process the video to get its thumbnail.
        return true
    }

    /** {@inheritdoc} */
    protected fun _ffmpegMustRunAgain( attempt: Int , ffmpegOutput: List<String>): Boolean {
        // If this was the first run, we must look for the "first frame is no
        // keyframe" error. It is a rare error which can happen when the user
        // wants to extract a frame from a timestamp that is before the first
        // keyframe of the video file. Most files can extract frames even at
        // `00:00:00.000`, but certain files may have garbage at the start of
        // the file, and thus extracting a garbage / empty / broken frame and
        // showing this error. The solution is to omit the `-ss` timestamp for
        // such files to automatically make ffmpeg extract the 1st VALID frame.
        // NOTE: We"ll only need to retry if timestamp is over 0.0. If it was
        // zero, then we already executed without `-ss` and shouldn"t retry.
        if (attempt === 1 && _thumbnailTimestamp > 0.0) {
            for (line in ffmpegOutput) {
                // Example: `[flv @ 0x7fc9cc002e00] warning: first frame is no keyframe`.
                if (line.indexOf(": first frame is no keyframe") > 0) {
                    return true
                }
            }
        }

        // If this was the 2nd run or there was no error, accept result as-is.
        return false
    }

    /** {@inheritdoc} */
    protected fun _getInputFlags(attempt: Int): List<String> {
        // The seektime *must* be specified here, before the input file.
        // Otherwise ffmpeg will do a slow conversion of the whole file
        // (but discarding converted frames) until it gets to target time.
        // See: https://trac.ffmpeg.org/wiki/Seeking
        // IMPORTANT: WE ONLY APPLY THE SEEK-COMMAND ON THE *FIRST* ATTEMPT. SEE
        // COMMENTS IN `_ffmpegMustRunAgain()` FOR MORE INFORMATION ABOUT WHY.
        // AND WE"LL OMIT SEEKING COMPLETELY IF IT"S "0.0" ("EARLIEST POSSIBLE"), TO
        // GUARANTEE SUCCESS AT GRABBING THE "EARLIEST FRAME" W/O NEEDING RETRIES.
        return if( attempt > 1 || _thumbnailTimestamp === 0.0F ) {
            listOf()
        } else { listOf("-ss ${getTimestampString()}")
        }
    }

    /** {@inheritdoc} */
    protected fun _getOutputFlags( attempt): List<String> {
        return listOf("-f mjpeg", "-vframes 1")
    }
}
