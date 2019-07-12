

package instagramAPI.Media.Video

import instagramAPI.Media.Geometry.Dimensions
import instagramAPI.Media.Geometry.Rectangle
import instagramAPI.Media.InstagramMedia
import instagramAPI.Utils
import Winbox.Args

/**
 * Automatically prepares a video file according to Instagram"s rules.
 *
 * @property VideoDetails $_details
 */
class InstagramVideo : InstagramMedia() {
    /** @var FFmpeg */
    protected lateinit var _ffmpeg: FFmpeg?

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
    fun __construct( inputFile: String, array options = [], ffmpeg: FFmpeg? = null){
        parent::__construct(inputFile, options)
        this._details = VideoDetails(this._inputFile)

        _ffmpeg = ffmpeg
        if (_ffmpeg === null) {
            _ffmpeg = FFmpeg.factory()
        }
    }

    /** {@inheritdoc} */
    protected fun _isMod2CanvasRequired(): Boolean {
        return true
    }

    /** {@inheritdoc} */
    protected fun _createOutputFile( srcRect: Rectangle, dstRect: Rectangle, canvas: Dimensions): String {
        var outputFile: String? = null

        try {
            // Prepare output file.
            outputFile = Utils.createTempFile(this._tmpPath, "VID")
            // Attempt to process the input file.
            // --------------------------------------------------------------
            // WARNING: This calls ffmpeg, which can run for a long time. The
            // user may be running in a CLI. In that case, if they press Ctrl-C
            // to abort, PHP won"t run ANY of our shutdown/destructor handlers!
            // Therefore they"ll still have the temp file if they abort ffmpeg
            // conversion with Ctrl-C, since our auto-cleanup won"t run. There"s
            // absolutely nothing good we can do about that (except a signal
            // handler to interrupt their Ctrl-C, which is a terrible idea).
            // Their OS should clear its temp folder periodically. Or if they
            // import a custom temp folder, it"s THEIR own job to clear it!
            // --------------------------------------------------------------
            _processVideo(srcRect, dstRect, canvas, outputFile)
        } catch (e: Exception) {
            if (outputFile !== null && is_file(outputFile)) {
                @unlink(outputFile)
            }

            throw e // Re-throw.
        }

        return outputFile
    }

    /**
     * @param Rectangle  $srcRect    Rectangle to copy from the input.
     * @param Rectangle  $dstRect    Destination place and scale of copied pixels.
     * @param Dimensions $canvas     The size of the destination canvas.
     * @param string     $outputFile
     *
     * @throws .RuntimeException
     */
    protected fun _processVideo( srcRect: Rectangle, dstRect: Rectangle, canvas: Dimensions, outputFile: String){
        // Swap to correct dimensions if the video pixels are stored rotated.
        if (this._details.hasSwappedAxes()) {
            srcRect = srcRect.withSwappedAxes()
            dstRect = dstRect.withSwappedAxes()
            canvas = canvas.withSwappedAxes()
        }

        // Prepare filters.
        val bgColor = sprintf("0x%02X%02X%02X", ...this._bgColor)
        var filters = listOf(
            "crop=w=${srcRect.getWidth().toInt()}:h=${srcRect.getHeight().toInt()}:x=${srcRect.getX().toInt()}:y=${srcRect.getY().toInt()}",
            "scale=w=${dstRect.getWidth().toInt()}:h=${dstRect.getHeight().toInt()}",
            "pad=w=${canvas.getWidth().toInt()}:h=${canvas.getHeight().toInt()}:x=${dstRect.getX().toInt()}:y=${dstRect.getY().toInt()}:color=$bgColor"
        )

        var attempt = 0
        do {
            ++attempt

            // Reset the messageline-array to avoid mixing runs.
            var ffmpegOutput = []

            // Get the flags to apply to the input file.
            val inputFlags = _getInputFlags(attempt)

            // Rotate the video (if needed to).
            val rotationFilters = _getRotationFilters()
            if (rotationFilters.count() > 0) {
                if (this._ffmpeg.hasNoAutorotate()) {
                    inputFlags[] = "-noautorotate"
                }
                filters = array_merge(filters, rotationFilters)
            }

            // Video format can"t copy since we always need to re-encode due to video filtering.
            ffmpegOutput = this._ffmpeg.run(
                "-y ${inputFlags.joinToString(" ")} -i ${Args.escape(this._inputFile)} -vf ${Args.escape(filters.joinToString(","))} ${_getOutputFlags(attempt).joinToString(" ")} ${Args.escape(outputFile)}"
            )
        } while (_ffmpegMustRunAgain(attempt, ffmpegOutput))
    }

    /**
     * Internal fun to determine whether ffmpeg needs to run again.
     *
     * @param int      $attempt      Which ffmpeg attempt just executed.
     * @param string[] $ffmpegOutput Array of error strings from the attempt.
     *
     * @throws .RuntimeException If this fun wants to give up and determines
     *                           that we cannot succeed and should throw completely.
     *
     * @return bool TRUE to run again, FALSE to accept the current output.
     */
    protected fun _ffmpegMustRunAgain( attempt: Int, array ffmpegOutput): Boolean{
        return false
    }

    /**
     * Get the input flags (placed before the input filename).
     *
     * @param int $attempt The current ffmpeg execution attempt.
     *
     * @return string[]
     */
    protected fun _getInputFlags( attempt: Int): MutableList<String>{
        return mutableListOf()
    }

    /**
     * Get the output flags (placed before the output filename).
     *
     * @param int $attempt The current ffmpeg execution attempt.
     *
     * @return string[]
     */
    protected fun _getOutputFlags( attempt: Int): MutableList<String> {
        val result = mutableListOf(
            "-metadata:s:v rotate=\"\"", // Strip rotation from metadata.
            "-f mp4" // Force output format to MP4.
        )

        // Force H.264 for the video.
        result.add("-c:v libx264 -preset fast -crf 24")

        // Force AAC for the audio.
        if (this._details.getAudioCodec() !== "aac") {
            if (this._ffmpeg.hasLibFdkAac()) {
                result.add("-c:a libfdk_aac -vbr 4")
            } else {
                // The encoder "aac" is experimental but experimental codecs are not enabled,
                // add "-strict -2" if you want to import it.
                result.add("-strict -2 -c:a aac -b:a 96k")
            }
        } else {
            result.add("-c:a copy")
        }

        // Cut too long videos.
        if (this._details.getDuration() > this._constraints.getMaxDuration()) {
            // FFmpeg cuts video sticking to a closest frame. As a result we might
            // end with a video that is longer than desired duration. To prevent this
            // we must import a duration that is somewhat smaller than its maximum allowed
            // value. 0.1 sec is 1 frame of 10 FPS video.
            val maxDuration = this._constraints.getMaxDuration() - 0.1
            result.add("-t ${"%.2f".format(maxDuration)}")
        }

        // TODO Loop too short videos.
        if (this._details.getDuration() < this._constraints.getMinDuration()) {
            var times = Math.ceil(this._constraints.getMinDuration() / this._details.getDuration())
        }

        return result
    }

    /**
     * Get an array of filters needed to restore video orientation.
     *
     * @return array
     */
    protected fun _getRotationFilters(): MutableList<String> {
        val result = mutableListOf<String>()
        if (this._details.hasSwappedAxes()) {
            if (this._details.isHorizontallyFlipped() && this._details.isVerticallyFlipped()) {
                result.add("transpose=clock")
                result.add("hflip")
            } else if (this._details.isHorizontallyFlipped()) {
                result.add("transpose=clock")
            } else if (this._details.isVerticallyFlipped()) {
                result.add("transpose=cclock")
            } else {
                result.add("transpose=cclock")
                result.add("vflip")
            }
        } else {
            if (this._details.isHorizontallyFlipped()) {
                result.add("hflip")
            }
            if (this._details.isVerticallyFlipped()) {
                result.add("vflip")
            }
        }

        return result
    }
}
