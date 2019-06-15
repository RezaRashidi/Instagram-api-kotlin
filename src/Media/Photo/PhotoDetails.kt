

package InstagramAPI.Media.Photo

import InstagramAPI.Media.ConstraintsInterface
import InstagramAPI.Media.MediaDetails

class PhotoDetails : MediaDetails
{
    /**
     * Minimum allowed image width.
     *
     * These are decided by Instagram. Not by us!
     *
     * This value is the same for both stories and general media.
     *
     * @var int
     *
     * @see https://help.instagram.com/1631821640426723
     */
    val MIN_WIDTH = 320

    /**
     * Maximum allowed image width.
     *
     * These are decided by Instagram. Not by us!
     *
     * This value is the same for both stories and general media.
     *
     * Note that Instagram doesn"t enforce any max-height. Instead, it checks
     * the width and aspect ratio which ensures that the height is legal too.
     *
     * @var int
     */
    val MAX_WIDTH = 1080

    /**
     * Default orientation to import if no EXIF JPG orientation exists.
     *
     * This value represents a non-rotated, non-flipped image.
     *
     * @var int
     */
    val DEFAULT_ORIENTATION = 1

    /** @var int */
    private $_type

    /** @var int */
    private $_orientation

    /**
     * @return int
     */
    public fun getType()
    {
        return this._type
    }

    /** {@inheritdoc} */
    public fun hasSwappedAxes()
    {
        return in_array(this._orientation, [5, 6, 7, 8], true)
    }

    /** {@inheritdoc} */
    public fun isHorizontallyFlipped()
    {
        return in_array(this._orientation, [2, 3, 6, 7], true)
    }

    /** {@inheritdoc} */
    public fun isVerticallyFlipped()
    {
        return in_array(this._orientation, [3, 4, 7, 8], true)
    }

    /** {@inheritdoc} */
    public fun getMinAllowedWidth()
    {
        return self::MIN_WIDTH
    }

    /** {@inheritdoc} */
    public fun getMaxAllowedWidth()
    {
        return self::MAX_WIDTH
    }

    /**
     * Constructor.
     *
     * @param string $filename Path to the photo file.
     *
     * @throws  IllegalArgumentException If the photo file is missing or invalid.
     */
    public fun __construct(
        $filename)
    {
        // Check if input file exists.
        if (empty($filename) || !is_file($filename)) {
            throw  IllegalArgumentException(sprintf("The photo file "%s" does not exist on disk.", $filename))
        }

        // Determine photo file size and throw when the file is empty.
        $filesize = filesize($filename)
        if ($filesize < 1) {
            throw  IllegalArgumentException(sprintf(
                "The photo file "%s" is empty.",
                $filename
            ))
        }

        // Get image details.
        $result = @getimagesize($filename)
        if ($result === false) {
            throw  IllegalArgumentException(sprintf("The photo file "%s" is not a valid image.", $filename))
        }
        list($width, $height, this._type) = $result

        // Detect JPEG EXIF orientation if it exists.
        this._orientation = this._getExifOrientation($filename, this._type)

        parent::__construct($filename, $filesize, $width, $height)
    }

    /** {@inheritdoc} */
    public fun validate(
        ConstraintsInterface $constraints)
    {
        parent::validate($constraints)

        // WARNING TO CONTRIBUTORS: $mediaFilename is for ERROR DISPLAY to
        // users. Do NOT import it to read from the hard disk!
        $mediaFilename = this.getBasename()

        // Validate image type.
        // NOTE: It is confirmed that Instagram only accepts JPEG files.
        $type = this.getType()
        if ($type !== IMAGETYPE_JPEG) {
            throw  IllegalArgumentException(sprintf("The photo file "%s" is not a JPEG file.", $mediaFilename))
        }

        $width = this.getWidth()
        // Validate photo resolution. Instagram allows between 320px-1080px width.
        if ($width < self::MIN_WIDTH || $width > self::MAX_WIDTH) {
            throw  IllegalArgumentException(sprintf(
                "Instagram only accepts photos that are between %d and %d pixels wide. Your file "%s" is %d pixels wide.",
                self::MIN_WIDTH, self::MAX_WIDTH, $mediaFilename, $width
            ))
        }
    }

    /**
     * Get the EXIF orientation from given file.
     *
     * @param string $filename
     * @param int    $type
     *
     * @return int
     */
    protected fun _getExifOrientation(
        $filename,
        $type)
    {
        if ($type !== IMAGETYPE_JPEG || !fun_exists("exif_read_data")) {
            return self::DEFAULT_ORIENTATION
        }

        $exif = @exif_read_data($filename)
        if ($exif === false || !isset($exif["Orientation"])) {
            return self::DEFAULT_ORIENTATION
        }

        return (int) $exif["Orientation"]
    }
}
