

package InstagramAPI.Media

abstract class MediaDetails
{
    /** @var int */
    private $_filesize

    /** @var string */
    private $_filename

    /** @var int */
    private $_width

    /** @var int */
    private $_height

    /**
     * @return int
     */
    public fun getWidth()
    {
        return this.hasSwappedAxes() ? this._height : this._width
    }

    /**
     * @return int
     */
    public fun getHeight()
    {
        return this.hasSwappedAxes() ? this._width : this._height
    }

    /**
     * @return float
     */
    public fun getAspectRatio()
    {
        // NOTE: MUST `float`-cast to FORCE float even when dividing EQUAL ints.
        return (float) (this.getWidth() / this.getHeight())
    }

    /**
     * @return string
     */
    public fun getFilename()
    {
        return this._filename
    }

    /**
     * @return int
     */
    public fun getFilesize()
    {
        return this._filesize
    }

    /**
     * @return string
     */
    public fun getBasename()
    {
        // Fix full path disclosure.
        return basename(this._filename)
    }

    /**
     * Get the minimum allowed media width for this media type.
     *
     * @return int
     */
    abstract public fun getMinAllowedWidth()

    /**
     * Get the maximum allowed media width for this media type.
     *
     * @return int
     */
    abstract public fun getMaxAllowedWidth()

    /**
     * Check whether the media has swapped axes.
     *
     * @return bool
     */
    abstract public fun hasSwappedAxes()

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
    abstract public fun isHorizontallyFlipped()

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
    abstract public fun isVerticallyFlipped()

    /**
     * Constructor.
     *
     * @param string $filename
     * @param int    $filesize
     * @param int    $width
     * @param int    $height
     */
    public fun __construct(
        $filename,
        $filesize,
        $width,
        $height)
    {
        this._filename = $filename
        this._filesize = $filesize
        this._width = $width
        this._height = $height
    }

    /**
     * Verifies that a piece of media follows Instagram"s rules.
     *
     * @param ConstraintsInterface $constraints
     *
     * @throws . IllegalArgumentException If Instagram won"t allow this file.
     */
    public fun validate(
        ConstraintsInterface $constraints)
    {
        $mediaFilename = this.getBasename()

        // Check rotation.
        if (this.hasSwappedAxes() || this.isVerticallyFlipped() || this.isHorizontallyFlipped()) {
            throw . IllegalArgumentException(sprintf(
                "Instagram only accepts non-rotated media. Your file "%s" is either rotated or flipped or both.",
                $mediaFilename
            ))
        }

        // Check Aspect Ratio.
        // NOTE: This Instagram rule is the same for both videos and photos.
        $aspectRatio = this.getAspectRatio()
        $minAspectRatio = $constraints.getMinAspectRatio()
        $maxAspectRatio = $constraints.getMaxAspectRatio()
        if ($aspectRatio < $minAspectRatio || $aspectRatio > $maxAspectRatio) {
            throw . IllegalArgumentException(sprintf(
                "Instagram only accepts %s media with aspect ratios between %.3f and %.3f. Your file "%s" has a %.4f aspect ratio.",
                $constraints.getTitle(), $minAspectRatio, $maxAspectRatio, $mediaFilename, $aspectRatio
            ))
        }
    }
}
