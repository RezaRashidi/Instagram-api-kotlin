

package instagramAPI.media.Photo

import instagramAPI.media.ConstraintsInterface
import instagramAPI.media.MediaDetails

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
    private var _type: Int

    /** @var int */
    private var _orientation: Int

    /**
     * @return int
     */
    fun getType(): Int {
        return _type
    }

    /** {@inheritdoc} */
    fun hasSwappedAxes(): Boolean {
        return arrayOf(5, 6, 7, 8).contains(_orientation)
    }

    /** {@inheritdoc} */
    fun isHorizontallyFlipped(): Boolean {
        return arrayOf(2, 3, 6, 7).contains(_orientation)
    }

    /** {@inheritdoc} */
    fun isVerticallyFlipped(): Boolean {
        return arrayOf(3, 4, 7, 8).contains(_orientation)
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
     * @param string $filename Path to the photo file.
     *
     * @throws  IllegalArgumentException If the photo file is missing or invalid.
     */
    fun constructor(filename: String){
        // Check if input file exists.
        if (filename.isEmpty() || !is_file(filename)) {
            throw IllegalArgumentException("The photo file \"$filename\" does not exist on disk.")
        }

        // Determine photo file size and throw when the file is empty.
        val filesize = filesize(filename)
        if (filesize < 1) {
            throw IllegalArgumentException("The photo file \"$filename\" is empty.")
        }

        // Get image details.
        val result = @getimagesize(filename)
        if (result === false) {
            throw IllegalArgumentException("The photo file \"$filename\" is not a valid image.")
        }
        list(width, height, _type) = result

        // Detect JPEG EXIF orientation if it exists.
        _orientation = _getExifOrientation(filename, _type)

        super constructor(filename, filesize, width, height)
    }

    /** {@inheritdoc} */
    fun validate(constraints: ConstraintsInterface){
        super validate(constraints)

        // WARNING TO CONTRIBUTORS: $mediaFilename is for ERROR DISPLAY to
        // users. Do NOT import it to read from the hard disk!
        val mediaFilename = getBasename()

        // Validate image type.
        // NOTE: It is confirmed that Instagram only accepts JPEG files.
        val type = getType()
        if (type !== IMAGETYPE_JPEG) {
            throw IllegalArgumentException("The photo file \"$mediaFilename\" is not a JPEG file.")
        }

        val width = getWidth()
        // Validate photo resolution. Instagram allows between 320px-1080px width.
        if (width < MIN_WIDTH || width > MAX_WIDTH) {
            throw  IllegalArgumentException("Instagram only accepts photos that are between $MIN_WIDTH and $MAX_WIDTH pixels wide. Your file \"$mediaFilename\" is $width pixels wide.")
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
    protected fun _getExifOrientation(filename: String, type: Int): Int {
        if (type !== IMAGETYPE_JPEG || !fun_exists("exif_read_data")) {
            return DEFAULT_ORIENTATION
        }

        val exif = @exif_read_data(filename)
        if (exif === false || !isset(exif["Orientation"])) {
            return DEFAULT_ORIENTATION
        }

        return exif["Orientation"].toInt()
    }
}
