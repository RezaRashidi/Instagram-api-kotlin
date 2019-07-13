

package instagramAPI.media.Photo

import instagramAPI.Media.Geometry.Dimensions
import instagramAPI.Media.Geometry.Rectangle
import instagramAPI.Media.InstagramMedia
import instagramAPI.Utils

/**
 * Automatically prepares a photo file according to Instagram"s rules.
 *
 * @property PhotoDetails $_details
 */
class InstagramPhoto : InstagramMedia()
{
    /**
     * Output JPEG quality.
     *
     * This value was chosen becaimport 100 is very wasteful. And don"t tweak this
     * number, becaimport the JPEG quality number is actually totally meaningless
     * (it is non-standardized) and Instagram can"t even read it from the file.
     * They have no idea what quality we"ve used, and it can be harmful to go
     * lower since different JPEG compressors (such as PHP"s implementation) use
     * different quality scales and are often awful at lower qualities! We know
     * that PHP"s JPEG quality at 95 is great, so there"s no reason to lower it.
     *
     * @var int
     */
    val JPEG_QUALITY = 95

    /**
     * Constructor.
     *
     * @param string $inputFile Path to an input file.
     * @param array  $options   An associative array of optional parameters.
     *
     * @throws  IllegalArgumentException
     *
     * @see InstagramMedia::__construct() description for the list of parameters.
     */
     constructor(inputFile: String, array options = []){
        super(inputFile, options)
        _details = PhotoDetails(_inputFile)
    }

    /** {@inheritdoc} */
    protected fun _isMod2CanvasRequired(): Boolean {
        return false
    }

    /** {@inheritdoc} */
    protected fun _createOutputFile( srcRect: Rectangle, dstRect: Rectangle, canvas: Dimensions): String? {
        var outputFile: String? = null

        try {
            // Attempt to process the input file.
            val resource = _loadImage()

            try {
                val output = _processResource(resource, srcRect, dstRect, canvas)
            } finally {
                @imagedestroy(resource)
            }

            // Write the result to disk.
            try {
                // Prepare output file.
                outputFile = Utils.createTempFile(_tmpPath, "IMG")

                if (!imagejpeg(output, outputFile, JPEG_QUALITY)) {
                    throw RuntimeException("Failed to create JPEG image file.")
                }
            } finally {
                @imagedestroy(output)
            }
        } catch (e: Exception) {
            if (outputFile !== null && is_file(outputFile)) {
                @unlink(outputFile)
            }

            throw e // Re-throw.
        }

        return outputFile
    }

    /**
     * Loads image into a resource.
     *
     * @throws .RuntimeException
     *
     * @return resource
     */
    protected fun _loadImage(){
        // Read the correct input file format.
        val resource = when (_details.getType()) {
            IMAGETYPE_JPEG -> imagecreatefromjpeg(_inputFile)
            IMAGETYPE_PNG  -> imagecreatefrompng(_inputFile)
            IMAGETYPE_GIF  -> imagecreatefromgif(_inputFile)
            else           -> throw RuntimeException("Unsupported image type.")
        }
        if (resource === false) {
            throw RuntimeException("Failed to load image.")
        }

        return resource
    }

    /**
     * @param resource   $source  The original image loaded as a resource.
     * @param Rectangle  $srcRect Rectangle to copy from the input.
     * @param Rectangle  $dstRect Destination place and scale of copied pixels.
     * @param Dimensions $canvas  The size of the destination canvas.
     *
     * @throws .exception
     * @throws .RuntimeException
     *
     * @return resource
     */
    protected fun _processResource( source, srcRectRE: Rectangle, dstRectRE: Rectangle, canvasRE: Dimensions) {
        // If our input image pixels are stored rotated, swap all coordinates.
        val srcRect
        val dstRect
        val canvas

        if (_details.hasSwappedAxes()) {
            srcRect = srcRectRE.withSwappedAxes()
            dstRect = dstRectRE.withSwappedAxes()
            canvas = canvasRE.withSwappedAxes()
        }

        // Create an output canvas with our desired size.
        var output = imagecreatetruecolor(canvas.getWidth(), canvas.getHeight())
        if (output === false) {
            throw RuntimeException("Failed to create output image.")
        }

        // Fill the output canvas with our background color.
        // NOTE: If cropping, this is just to have a nice background in
        // the resulting JPG if a transparent image was used as input.
        // If expanding, this will be the color of the border as well.
        val bgColor = imagecolorallocate(output, _bgColor[0], _bgColor[1], _bgColor[2])
        if (bgColor === false) {
            throw RuntimeException("Failed to allocate background color.")
        }
        if (imagefilledrectangle(output, 0, 0, canvas.getWidth() - 1, canvas.getHeight() - 1, bgColor) === false) {
            throw RuntimeException("Failed to fill image with background color.")
        }

        // Copy the resized (and resampled) image onto the canvas.
        if (imagecopyresampled(
                output, source,
                dstRect.getX(), dstRect.getY(),
                srcRect.getX(), srcRect.getY(),
                dstRect.getWidth(), dstRect.getHeight(),
                srcRect.getWidth(), srcRect.getHeight()
            ) === false) {
            throw RuntimeException("Failed to resample image.")
        }

        // Handle image rotation.
        output = _rotateResource(output, bgColor)

        return output
    }

    /**
     * Wrapper for PHP"s imagerotate fun.
     *
     * @param resource $original
     * @param int      $bgColor
     *
     * @throws .RuntimeException
     *
     * @return resource
     */
    protected fun _rotateResource( original, bgColor: Int){
        var angle = 0
        var flip = null
        // Find out angle and flip.
        if (_details.hasSwappedAxes()) {
            if (_details.isHorizontallyFlipped() && _details.isVerticallyFlipped()) {
                angle = -90
                flip = IMG_FLIP_HORIZONTAL
            } else if (_details.isHorizontallyFlipped()) {
                angle = -90
            } else if (_details.isVerticallyFlipped()) {
                angle = 90
            } else {
                angle = -90
                flip = IMG_FLIP_VERTICAL
            }
        } else {
            if (_details.isHorizontallyFlipped() && _details.isVerticallyFlipped()) {
                flip = IMG_FLIP_BOTH
            } else if (_details.isHorizontallyFlipped()) {
                flip = IMG_FLIP_HORIZONTAL
            } else if (_details.isVerticallyFlipped()) {
                flip = IMG_FLIP_VERTICAL
            } else {
                // Do nothing.
            }
        }

        // Flip the image resource if needed. Does not create a resource.
        if (flip !== null && imageflip(original, flip) === false) {
            throw RuntimeException("Failed to flip image.")
        }

        // Return original resource if no rotation is needed.
        if (angle === 0) {
            return original
        }

        // Attempt to create a new, rotated image resource.
        val result = imagerotate(original, angle, bgColor)
        if (result === false) {
            throw RuntimeException("Failed to rotate image.")
        }

        // Destroy the original resource since we"ll return the resource.
        @imagedestroy(original)

        return result
    }
}
