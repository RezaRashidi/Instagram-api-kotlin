

package InstagramAPI.Media.Video

import InstagramAPI.Utils
import Symfony.Component.Process.Process
import Winbox.Args

class FFmpeg{
    companion object{
        val BINARIES = listOf(
            "ffmpeg",
            "avconv"
        )

        val WINDOWS_BINARIES = listOf(
            "ffmpeg.exe",
            "avconv.exe"
        )

        /** @var string|null */
        lateinit var defaultBinary: String?

        /** @var int|null */
        var defaultTimeout: Int?

        /** @var FFmpeg[] */
        protected var _instances = []

        /**
         * Create a instance or import a cached one.
         *
         * @param string|null $ffmpegBinary Path to a ffmpeg binary, or NULL to autodetect.
         *
         * @return static
         */
        fun factory( ffmpegBinary: String? = null){
            if (ffmpegBinary === null) {
            return _autoDetectBinary()
        }

            if (!(_instances[ffmpegBinary].isBlank())) {
            return _instances[ffmpegBinary]
        }

            val instance = static(ffmpegBinary)
            _instances[ffmpegBinary] = instance

            return instance
        }

        /**
         * @throws .RuntimeException
         *
         * @return static
         */
        protected fun _autoDetectBinary(){
            val binaries = if (defined("PHP_WINDOWS_VERSION_MAJOR")) WINDOWS_BINARIES else BINARIES
            if (defaultBinary !== null) {
                array_unshift(binaries, defaultBinary)
            }
            /* Backwards compatibility. */
            if (Utils.ffmpegBin !== null) {
                array_unshift(binaries, Utils.ffmpegBin)
            }

            val instance = null
            for (binary in binaries) {
                if (!(_instances[binary].isBlank())) {
                    return _instances[binary]
                }

                try {
                    instance = static(binary)
                } catch (e: Exception) {
                    continue
                }
                defaultBinary = binary
                _instances[binary] = instance

                return instance
            }

            throw RuntimeException("You must have FFmpeg to process videos. Ensure that its binary-folder exists in your PATH environment variable, or manually set its full path via \"\\InstagramAPI\\Media\\Video\\FFmpeg::$defaultBinary = '/home/exampleuser/ffmpeg/bin/ffmpeg';\" at the start of your script.")
        }


    }


    /** @var string */
    protected lateinit var _ffmpegBinary: String

    /** @var bool */
    protected var _hasNoAutorotate: Boolean

    /** @var bool */
    protected var _hasLibFdkAac: Boolean

    /**
     * Constructor.
     *
     * @param string $ffmpegBinary
     *
     * @throws .RuntimeException When a ffmpeg binary is missing.
     */
    protected fun __construct(ffmpegBinary: String){
        _ffmpegBinary = ffmpegBinary

        try {
            version()
        } catch (e: Exception) {
            throw RuntimeException("It seems that the path to ffmpeg binary is invalid. Please check your path to ensure that it is correct.")
        }
    }



    /**
     * Run a command and wrap errors into an Exception (if any).
     *
     * @param string $command
     *
     * @throws .RuntimeException
     *
     * @return string[]
     */
    fun run(command: String){
        val process = runAsync(command)
        val exitCode

        try {
            exitCode = process.wait()
        } catch (e: Exception) {
            throw RuntimeException("Failed to run the ffmpeg binary: ${e.getMessage()}")
        }
        if (exitCode) {
            val errors = preg_replace("#[.r.n]+#", ""], ["", trim(process.getErrorOutput()))
            val errorMsg = "FFmpeg Errors: [\"$errors\"], Command: \"$command\"."

            throw RuntimeException(errorMsg, exitCode)
        }

        return preg_split("#[.r.n]+#", process.getOutput(), null, PREG_SPLIT_NO_EMPTY)
    }

    /**
     * Run a command asynchronously.
     *
     * @param string $command
     *
     * @return Process
     */
    fun runAsync(command: String): Process {
        val fullCommand = "${Args.escape(_ffmpegBinary)} -v error $command"

        val process = Process(fullCommand)
        if (defaultTimeout is Int && defaultTimeout > 60) {
            process.setTimeout(defaultTimeout)
        }
        process.start()

        return process
    }

    /**
     * Get the ffmpeg version.
     *
     * @throws .RuntimeException
     *
     * @return string
     */
    fun version(): String{
        return run("-version")[0]
    }

    /**
     * Get the path to the ffmpeg binary.
     *
     * @return string
     */
    fun getFFmpegBinary(): String{
        return _ffmpegBinary
    }

    /**
     * Check whether ffmpeg has -noautorotate flag.
     *
     * @return bool
     */
    fun hasNoAutorotate(): Boolean{
        if (_hasNoAutorotate === null) {
            try {
                run("-noautorotate -f lavfi -i color=color=red -t 1 -f null -")
                _hasNoAutorotate = true
            } catch (e: RuntimeException) {
                _hasNoAutorotate = false
            }
        }

        return _hasNoAutorotate
    }

    /**
     * Check whether ffmpeg has libfdk_aac audio encoder.
     *
     * @return bool
     */
    fun hasLibFdkAac(): Boolean{
        if (_hasLibFdkAac === null) {
            _hasLibFdkAac = _hasAudioEncoder("libfdk_aac")
        }

        return _hasLibFdkAac
    }

    /**
     * Check whether ffmpeg has specified audio encoder.
     *
     * @param string $encoder
     *
     * @return bool
     */
    protected fun _hasAudioEncoder(encoder: String): Boolean {
        try {
            run("-f lavfi -i anullsrc=channel_layout=stereo:sample_rate=44100 -c:a ${Args.escape(encoder)} -t 1 -f null -")
            return true
        } catch (e: RuntimeException) {
            return false
        }
    }


}
