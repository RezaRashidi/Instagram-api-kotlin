

package instagramAPI.media

abstract class MediaDetails{
    /** @var int */
    private var _filesize: Int

    /** @var string */
    private lateinit var _filename: String

    /** @var int */
    private var _width: Int

    /** @var int */
    private var _height: Int

    /**
     * @return int
     */
    fun getWidth(): Int{
        return if(hasSwappedAxes()) _height else _width
    }

    /**
     * @return int
     */
    fun getHeight(): Int{
        return if (hasSwappedAxes()) _width else _height
    }

    /**
     * @return float
     */
    fun getAspectRatio(): Float{
        // NOTE: MUST `float`-cast to FORCE float even when dividing EQUAL ints.
        return (getWidth() / getHeight()).toFloat()
    }

    /**
     * @return string
     */
    fun getFilename(): String{
        return _filename
    }

    /**
     * @return int
     */
    fun getFilesize(): Int{
        return _filesize
    }

    /**
     * @return string
     */
    fun getBasename(): String{
        // Fix full path disclosure.
        return basename(_filename)
    }

    /**
     * Get the minimum allowed media width for this media type.
     *
     * @return int
     */
    abstract fun getMinAllowedWidth(): Int

    /**
     * Get the maximum allowed media width for this media type.
     *
     * @return int
     */
    abstract fun getMaxAllowedWidth(): Int

    /**
     * Check whether the media has swapped axes.
     *
     * @return bool
     */
    abstract fun hasSwappedAxes(): Boolean

    /**
     * Check whether the media is horizontally flipped.
     *
     * ```
     * *****      *****
     * *              *
     * ***    =>    ***
     * *              *
     * *              *
     * ```
     *
     * @return bool
     */
    abstract fun isHorizontallyFlipped(): Boolean

    /**
     * Check whether the media is vertically flipped.
     *
     * ```
     * *****      *
     * *          *
     * ***    =>  ***
     * *          *
     * *          *****
     * ```
     *
     * @return bool
     */
    abstract fun isVerticallyFlipped(): Boolean

    /**
     * Constructor.
     *
     * @param string $filename
     * @param int    $filesize
     * @param int    $width
     * @param int    $height
     */
    fun __construct( filename: String, filesize: Int, width: Int, height: Int){
        _filename = filename
        _filesize = filesize
        _width    = width
        _height   = height
    }

    /**
     * Verifies that a piece of media follows Instagram"s rules.
     *
     * @param ConstraintsInterface $constraints
     *
     * @throws  IllegalArgumentException If Instagram won"t allow this file.
     */
    fun validate( constraints: ConstraintsInterface){
        val mediaFilename = getBasename()

        // Check rotation.
        if (hasSwappedAxes() || isVerticallyFlipped() || isHorizontallyFlipped()) {
            throw IllegalArgumentException("Instagram only accepts non-rotated media. Your file \"$mediaFilename\" is either rotated or flipped or both.")
        }

        // Check Aspect Ratio.
        // NOTE: This Instagram rule is the same for both videos and photos.
        val aspectRatio = getAspectRatio()
        val minAspectRatio = constraints.getMinAspectRatio()
        val maxAspectRatio = constraints.getMaxAspectRatio()
        if (aspectRatio < minAspectRatio || aspectRatio > maxAspectRatio) {
            throw IllegalArgumentException("Instagram only accepts ${constraints.getTitle()} media with aspect ratios between ${"%.3f".format(minAspectRatio)} and ${"%.3f".format(maxAspectRatio)}. Your file \"$mediaFilename\" has a ${"%.4f".format(aspectRatio)} aspect ratio.")
        }
    }
}
