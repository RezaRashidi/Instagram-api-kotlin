

package instagramAPI.media

import instagramAPI.Constants
import instagramAPI.media.Constraints.ConstraintsFactory
import instagramAPI.media.Geometry.Dimensions
import instagramAPI.media.Geometry.Rectangle
import java.io.File
import kotlin.math.*

/**
 * Automatically prepares a media file according to Instagram"s rules.
 *
 * Validates, transcodes, resizes and crops/expands a media file to match
 * Instagram"s requirements, if necessary. You can also import this with your own
 * parameters, to force your media into different aspects, ie square, or for
 * adding colored borders to media, and so on... Read the constructor options!
 *
 * Usage:
 *
 * - Create an instance of the appropriate subclass (such as `InstagramPhoto` or
 *   `InstagramVideo`) with your media file and requirements.
 * - Call `getFile()` to get the path to a media file matching the requirements.
 *   This will be the same as the input file if no processing was required.
 * - Optionally, call `deleteFile()` if you want to delete the temporary file
 *   ahead of time instead of automatically when PHP does its object garbage
 *   collection. This fun is safe and won"t delete the original input file.
 *
 * @author SteveJobzniak (https://github.com/SteveJobzniak)
 */
abstract class InstagramMedia
{
    /** @var int Crop Operation. */
    val CROP = 1

    /** @var int Expand Operation. */
    val EXPAND = 2

    /**
     * Override for the default temp path used by all class instances.
     *
     * If you don"t provide any tmpPath to the constructor, we"ll import this value
     * instead (if non-null). Otherwise we"ll import the default system tmp folder.
     *
     * TIP: If your default system temp folder isn"t writable, it"s NECESSARY
     * for you to set this value to another, writable path, like this:
     *
     * ```
     * .instagramAPI.InstagramMedia::$defaultTmpPath = "/home/example/foo/"
     * ```
     *
     * @var string|null
     */
    companion object{
        var defaultTmpPath: String? = null
    }

    /** @var bool Whether to output debugging info during calculation steps. */
    protected var _debug: Boolean

    /** @var string Input file path. */
    protected var _inputFile: String

    /** @var float|null Minimum allowed aspect ratio. */
    protected var _minAspectRatio: Float?

    /** @var float|null Maximum allowed aspect ratio. */
    protected var _maxAspectRatio: Float?

    /** @var float Whether to allow the aspect ratio (during processing) to
     * deviate slightly from the min/max targets. See constructor for info. */
    protected var _allowNewAspectDeviation: Float

    /** @var int Crop focus position (-50 .. 50) when cropping horizontally. */
    protected var _horCropFocus: Int

    /** @var int Crop focus position (-50 .. 50) when cropping vertically. */
    protected var _verCropFocus: Int

    /** @var array Background color [R, G, B] for the final media. */
    protected lateinit var _bgColor: MutableList<Int>

    /** @var int Operation to perform on the media. */
    protected var _operation: Int

    /** @var string Path to a tmp directory. */
    protected lateinit var _tmpPath: String

    /** @var ConstraintsInterface Target feed"s specific constraints. */
    protected lateinit var _constraints: ConstraintsInterfac

    /** @var string Output file path. */
    protected lateinit var _outputFile: String

    /** @var MediaDetails The media details for our input file. */
    protected lateinit var _details: MediaDetails

    /** @var float|null Optional forced aspect ratio target to apply in case of
     * input being outside allowed min/max range (OR if the input deviates too
     * much from this target, in case this target ratio was user-provided). */
    protected var _forceTargetAspectRatio: Float?

    /** @var bool Whether the user specified the "forced target ratio"
     * themselves, which means we should be VERY strict about applying it. */
    protected var _hasUserForceTargetAspectRatio: Boolean

    /**
     * Constructor.
     *
     * Available `$options` parameters:
     *
     * - "targetFeed" (int): One of the FEED_X constants. MUST be used if you"re
     *   targeting stories. Defaults to `Constants::FEED_TIMELINE`.
     *
     * - "horCropFocus" (int): Crop focus position (-50 .. 50) when cropping
     *   horizontally (reducing width). Uses intelligent guess if not set.
     *
     * - "verCropFocus" (int): Crop focus position (-50 .. 50) when cropping
     *   vertically (reducing height). Uses intelligent guess if not set.
     *
     * - "minAspectRatio" (float): Minimum allowed aspect ratio. Uses
     *   auto-selected class constants (with the correct, legal Instagram
     *   aspect ratio limits for your chosen target feed) if not set.
     *
     * - "maxAspectRatio" (float): Maximum allowed aspect ratio. Uses
     *   auto-selected class constants (with the correct, legal Instagram
     *   aspect ratio limits for your chosen target feed) if not set.
     *
     * - "forceAspectRatio" (float|int): Tell the media processor to enforce a
     *   specific aspect ratio target. This custom value MUST be within the
     *   "minAspectRatio" to "maxAspectRatio" range! NOTE: When your goal is to
     *   generate a custom media aspect ratio, you should normally ONLY specify
     *   THIS parameter and should NEVER tamper with the min/max aspect ratio
     *   limits! Becaimport when you"re specifying a custom forced ratio here, we
     *   WILL ALWAYS verify/process the media as-necessary to fit your desired
     *   ratio (EVEN if the input media was "already valid" within the overall,
     *   larger legal min/max ratio ranges). ALSO NOTE: We WON"T process input
     *   media when it"s already VERY close to the desired target ratio. The
     *   ONLY custom aspect ratio value that is 100% guaranteed to be FULLY
     *   ENFORCED is `1.0` (square). If you specify ANY other ratio, we will
     *   accept a VERY SMALL deviation when the media is already almost exactly
     *   at the desired ratio. This is done to prevent pointless "impossible
     *   processing", since achieving EXACT non-`1.0` ratios is almost always
     *   IMPOSSIBLE, so it"s not worth trying with media that"s already very
     *   close. The reason why non-`1.0` ratios are impossibly hard to hit in
     *   MOST cases, is becaimport pixels cannot be subdivided into smaller
     *   fractions than "1 whole pixel", so in most cases when we ask for
     *   something like `1.25385` ratio and we have an input file such as
     *   `640x512` (`1.25` ratio), it is PHYSICALLY IMPOSSIBLE to achieve a
     *   `1.25385` ratio with that input and we could AT BEST reach a tiny
     *   fraction away from that target such as `641x512` (`1.251953125`
     *   ratio), `639x512` (`1.248046875` ratio), `640x513` (`1.247563353`
     *   ratio) or `640x511` (`1.252446184` ratio). So whether you CAN hit your
     *   EXACT desired non-`1.0` ratio or NOT depends 100% on your input file"s
     *   resolution! That"s why we passthrough files that are already super
     *   close to the desired ratio. Either way, such a tiny difference in
     *   aspect ratio won"t be visible to the eye, so if you are using this
     *   "target ratio" feature for creating Instagram albums where all media
     *   has the same aspect ratio, the final result WILL still look perfect.
     *
     * - "useRecommendedRatio" (bool): Whether to import the recommended aspect
     *   ratio for your specific media type and target feed (such as using an
     *   appropriate `9:16` portrait widescreen for stories). Some targets use
     *   the recommended ratio by default, and others disable this by default.
     *   Therefore, you do NOT need to set this option manually unless you have
     *   a VERY special reason to do so! NOTE: This will ALWAYS be set to
     *   `FALSE` if you"re using the "forceAspectRatio" parameter!
     *
     * - "allowNewAspectDeviation" (bool): Whether to allow the new, final
     *   aspect ratio (during processing) to deviate slightly from the MIN/MAX
     *   limits. By setting this option to `TRUE`, you will tell our processing
     *   to allow "slightly too high/slightly too low" final aspect ratios
     *   (instead of throwing an exception) and to still permit the final
     *   "closest-possible canvas" we"ve calculated anyway. You should NEVER
     *   need to import this feature, but if you have manually configured extremely
     *   narrow min/max aspect ratio parameters (which your input media CANNOT
     *   be tweaked to fit perfectly within) then you MAY want to enable this.
     *
     * - "bgColor" (array) - Array with 3 color components `[R, G, B]`
     *   (0-255/0x00-0xFF) for the background. Uses white if not set.
     *
     * - "operation" (int) - Operation to perform on the media (CROP or EXPAND).
     *   Uses `self::CROP` if not set.
     *
     * - "tmpPath" (string) - Path to temp directory. Uses system temp location
     *   or the class-default (`self::$defaultTmpPath`) if not set.
     *
     * - "debug" (bool) - Whether to output debugging info during calculation
     *   steps.
     *
     * @param string $inputFile Path to an input file.
     * @param array  $options   An associative array of optional parameters.
     *                          See constructor description.
     *
     * @throws  IllegalArgumentException
     */
   constructor( inputFile: String, array options = []){
        // Assign variables for all options, to avoid bulky code repetition.
        var targetFeed: Int        = if(!(options["targetFeed"].isBlank())) options["targetFeed"] else Constants.FEED_TIMELINE
        var horCropFocus: Int?     = if( !(options["horCropFocus"].isBlank()) ) options["horCropFocus"] else null
        var verCropFocus: Int?     = if( !(options["verCropFocus"])) options["verCropFocus"] else null
        var minAspectRatio: Float? = if( !(options["minAspectRatio"].isBlank()) ) options["minAspectRatio"] else null
        var maxAspectRatio: Float? = if( !(options["maxAspectRatio"].isBlank()) ) options["maxAspectRatio"] else null
        var userForceTargetAspectRatio= if( !(options["forceAspectRatio"].isBlank()) ) options["forceAspectRatio"] else null
        var useRecommendedRatio: Boolean?      = if( !(options["useRecommendedRatio"].isBlank()) ) options["useRecommendedRatio"].toBoolean() else null
        var allowNewAspectDeviation: Boolean   = if( !(options["allowNewAspectDeviation"].isBlank())) options["allowNewAspectDeviation"].toBoolean() else false
        var bgColor: MutableList<Int>          = if( !(options["bgColor"].isBlank())) options["bgColor"] else null
        var operation: Int         = if( !(options["operation"].isBlank())) options["operation"] else CROP
        var tmpPath: String?       = if( !(options["tmpPath"].isBlank())) options["tmpPath"].toString() else null
        val debug: Boolean         = if( !(options["debug"].isBlank())) options["debug"] else false

        // Debugging.
        _debug = debug === true

        // Input file.
        if (!File(inputFile).isFile) {
            throw IllegalArgumentException("Input file \"$inputFile\" doesn't exist.")
        }
        _inputFile = inputFile

        // Horizontal crop focus.
        if (horCropFocus !== null && (horCropFocus !is Int || horCropFocus < -50 || horCropFocus > 50)) {
            throw IllegalArgumentException("Horizontal crop focus must be between -50 and 50.")
        }
        _horCropFocus = horCropFocus!!

        // Vertical crop focus.
        if (verCropFocus !== null && (verCropFocus !is Int || verCropFocus < -50 || verCropFocus > 50)) {
            throw IllegalArgumentException("Vertical crop focus must be between -50 and 50.")
        }
        _verCropFocus = verCropFocus!!

        // Minimum and maximum aspect ratio range.
        if (minAspectRatio !== null && minAspectRatio !is Float) {
            throw IllegalArgumentException("Minimum aspect ratio must be a floating point number.")
        }
        if (maxAspectRatio !== null && maxAspectRatio !is Float) {
            throw IllegalArgumentException("Maximum aspect ratio must be a floating point number.")
        }

        // Does the user want to override (force) the final "target aspect ratio" choice?
        // NOTE: This will be used to override `this._forceTargetAspectRatio`.
        _hasUserForceTargetAspectRatio = false
        if (userForceTargetAspectRatio !== null) {
            if (userForceTargetAspectRatio !is Float && userForceTargetAspectRatio !is Int) {
                throw IllegalArgumentException("Custom target aspect ratio must be a float or integer.")
            }
            userForceTargetAspectRatio = userForceTargetAspectRatio as Float
            _hasUserForceTargetAspectRatio = true
            useRecommendedRatio = false // We forcibly disable this too, to avoid risk of future bugs.
        }

        // Create constraints and determine whether to import "recommended target aspect ratio" (if one is available for feed).
        _constraints = ConstraintsFactory.createFor(targetFeed)
        if (!_hasUserForceTargetAspectRatio && useRecommendedRatio === null) {
            // No value is provided, so let"s guess it.
            if (minAspectRatio !== null || maxAspectRatio !== null) {
                // If we have at least one custom ratio, we must not import recommended ratio.
                useRecommendedRatio = false
            } else {
                // import the recommended value from constraints (either on or off, depending on which target feed).
                useRecommendedRatio = _constraints.useRecommendedRatioByDefault()
            }
        }

        // Determine the legal min/max aspect ratios for the target feed.
        if (!_hasUserForceTargetAspectRatio && useRecommendedRatio === true) {
            _forceTargetAspectRatio = _constraints.getRecommendedRatio()
            var deviation = _constraints.getRecommendedRatioDeviation()
            minAspectRatio = _forceTargetAspectRatio - deviation
            maxAspectRatio = _forceTargetAspectRatio + deviation
        } else {
            // If the user hasn"t specified a custom target aspect ratio, this
            // "force" value will remain NULL (and the target ratio will be
            // auto-calculated by the canvas generation algorithms instead).
            _forceTargetAspectRatio = userForceTargetAspectRatio
            var allowedMinRatio = _constraints.getMinAspectRatio()
            var allowedMaxRatio = _constraints.getMaxAspectRatio()

            // Select allowed aspect ratio range based on defaults and user input.
            if (minAspectRatio !== null && (allowedMinRatio > minAspectRatio || allowedMaxRatio < minAspectRatio)) {
                throw IllegalArgumentException("Minimum aspect ratio must be between ${"%.3f".format(allowedMinRatio)} and ${"%.3f".format(allowedMaxRatio)}.")
            }
            if (minAspectRatio === null) {
                minAspectRatio = allowedMinRatio
            }
            if (maxAspectRatio !== null && (allowedMinRatio > maxAspectRatio || allowedMaxRatio < maxAspectRatio)) {
                throw IllegalArgumentException("Maximum aspect ratio must be between ${"%.3f".format(allowedMinRatio)} and ${"%.3f".format(allowedMaxRatio)}.")
            }
            if (maxAspectRatio === null) {
                maxAspectRatio = allowedMaxRatio
            }
            if (minAspectRatio !== null && maxAspectRatio !== null && minAspectRatio > maxAspectRatio) {
                throw  IllegalArgumentException("Maximum aspect ratio must be greater than or equal to minimum.")
            }

            // Validate custom target aspect ratio legality if provided by user.
            if (_hasUserForceTargetAspectRatio) {
                if (minAspectRatio !== null && _forceTargetAspectRatio!! < minAspectRatio) {
                    throw IllegalArgumentException("Custom target aspect ratio (${"%.5f".format(_forceTargetAspectRatio)}) must be greater than or equal to the minimum aspect ratio (${"%.5f".format(minAspectRatio)}).")
                }
                if (maxAspectRatio !== null && _forceTargetAspectRatio!! > maxAspectRatio) {
                    throw IllegalArgumentException("Custom target aspect ratio (${"%.5f".format(_forceTargetAspectRatio)}) must be lesser than or equal to the maximum aspect ratio (${"%.5f".format(maxAspectRatio)}).")
                }
            }
        }
        _minAspectRatio = minAspectRatio
        _maxAspectRatio = maxAspectRatio

        // Allow the aspect ratio of the final, canvas to deviate slightly from the min/max range?
        _allowNewAspectDeviation = allowNewAspectDeviation

        // Background color.
        if ( bgColor !== null && ( bgColor !is Array || bgColor.count() !== 3 || bgColor[0].isBlank() || bgColor[1].isBlank() || bgColor[2].isBlank() ) ) {
            throw  IllegalArgumentException("The background color must be a 3-element array [R, G, B].")
        } else if (bgColor === null) {
            bgColor = mutableListOf(255, 255, 255) // White.
        }
        _bgColor = bgColor

        // media operation.
        if (operation !== CROP && operation !== EXPAND) {
            throw  IllegalArgumentException("The operation must be one of the class constants CROP or EXPAND.")
        }
        _operation = operation

        // Temporary directory path.
        if (tmpPath === null) {
            tmpPath = if(defaultTmpPath !== null) defaultTmpPath else sys_get_temp_dir()
        }
        if (!File(tmpPath).isDirectory || !File(tmpPath).canWrite()) {
            throw IllegalArgumentException("Directory $tmpPath does not exist or is not writable.")
        }
        _tmpPath = realpath(tmpPath)
    }

    /**
     * Destructor.
     */
    fun __destruct(){
        deleteFile()
    }

    /**
     * Removes the output file if it exists and differs from input file.
     *
     * This fun is safe and won"t delete the original input file.
     *
     * Is automatically called when the class instance is destroyed by PHP.
     * But you can manually call it ahead of time if you want to force cleanup.
     *
     * Note that getFile() will still work afterwards, but will have to process
     * the media again to a temp file if the input file required processing.
     *
     * @return bool
     */
    fun deleteFile(): Boolean{
        // Only delete if outputfile exists and isn"t the same as input file.
        if (_outputFile !== null && _outputFile !== _inputFile && File(_outputFile).isFile) {
            val result = @unlink(_outputFile)
            _outputFile = null // Reset so getFile() will work again.
            return result
        }

        return true
    }

    /**
     * Gets the path to a media file matching the requirements.
     *
     * The automatic processing is performed the first time that this fun
     * is called. Which means that no CPU time is wasted if you never call this
     * fun at all.
     *
     * Due to the processing, the first call to this fun may take a moment.
     *
     * If the input file already fits all of the specifications, we simply
     * return the input path instead, without any need to re-process it.
     *
     * @throws .exception
     * @throws .RuntimeException
     *
     * @return string The path to the media file.
     *
     * @see InstagramMedia::_shouldProcess() The criteria that determines processing.
     */
    fun getFile(): String{
        if (_outputFile === null) {
            _outputFile = if( _shouldProcess() ) _process() else _inputFile
        }

        return _outputFile
    }

    /**
     * Checks whether we should process the input file.
     *
     * @return bool
     */
    protected fun _shouldProcess(): Boolean{
        val inputAspectRatio = _details.getAspectRatio()

        // Process if aspect ratio < minimum allowed.
        if (_minAspectRatio !== null && inputAspectRatio < _minAspectRatio!!) {
            return true
        }

        // Process if aspect ratio > maximum allowed.
        if (_maxAspectRatio !== null && inputAspectRatio > _maxAspectRatio!!) {
            return true
        }

        // Process if USER provided the custom aspect ratio target and input deviates too much.
        if (_hasUserForceTargetAspectRatio) {
            if (_forceTargetAspectRatio == 1.0f) {
                // User wants a SQUARE canvas, which can ALWAYS be achieved (by
                // making both sides equal). Process input if not EXACTLY square.
                // WARNING: Comparison here and above MUST import `!=` (NOT strict
                // `!==`) to support both int(1) and float(1.0) values!
                if (inputAspectRatio != 1.0f) {
                    return true
                }
            } else {
                // User wants a non-square canvas, which is almost always
                // IMPOSSIBLE to achieve perfectly. Only process if input
                // deviates too much from the desired target.
                val acceptableDeviation = 0.003 // Allow a very narrow range around the user"s target.
                val acceptableMinAspectRatio = _forceTargetAspectRatio - acceptableDeviation
                val acceptableMaxAspectRatio = _forceTargetAspectRatio + acceptableDeviation
                if (inputAspectRatio < acceptableMinAspectRatio || inputAspectRatio > acceptableMaxAspectRatio) {
                    return true
                }
            }
        }

        // Process if the media can"t be uploaded to Instagram as is.
        // NOTE: Nobody is allowed to call `isMod2CanvasRequired()` here. That
        // isn"t its purpose. Whether a final Mod2 canvas is required for actual
        // resizing has NOTHING to do with whether the input file is ok.
        return try {
            _details.validate(_constraints)
            false
        } catch (e: Exception) {
            true
        }
    }

    /**
     * Whether this processor requires Mod2 width and height canvas dimensions.
     *
     * If this returns FALSE, the calculated `InstagramMedia` canvas passed to
     * this processor _may_ contain uneven width and/or height as the selected
     * output dimensions.
     *
     * Therefore, this fun must return TRUE if (and ONLY IF) perfectly even
     * dimensions are necessary for this particular processor"s output format.
     *
     * For example, JPEG images accept any dimensions and must therefore return
     * FALSE. But H264 videos require EVEN dimensions and must return TRUE.
     *
     * @return bool
     */
    abstract protected fun _isMod2CanvasRequired(): Boolean

    /**
     * Process the input file and create the file.
     *
     * @throws .RuntimeException
     *
     * @return string The path to the file.
     */
    protected fun _process(): String{
        // Get the dimensions of the original input file.
        val inputCanvas = Dimensions(_details.getWidth(), _details.getHeight())

        // Create an output canvas with the desired dimensions.
        // WARNING: This creates a LEGAL canvas which MUST be followed EXACTLY.
        val canvasInfo = _calculateNewCanvas( // Throws.
            _operation,
            inputCanvas.getWidth(),
            inputCanvas.getHeight(),
            _isMod2CanvasRequired(),
            _details.getMinAllowedWidth(),
            _details.getMaxAllowedWidth(),
            _minAspectRatio,
            _maxAspectRatio,
            _forceTargetAspectRatio,
            _allowNewAspectDeviation
        )
        val outputCanvas = canvasInfo["canvas"]

        // Determine the media operation"s resampling parameters and perform it.
        // NOTE: This section is EXCESSIVELY commented to explain each step. The
        // algorithm is pretty easy after you understand it. But without the
        // detailed comments, future contributors may not understand any of it!
        // "We"d rather have a WaLL oF TeXt for future reference, than bugs due
        // to future misunderstandings!" - SteveJobzniak -)
        val srcRect
        val dstRect
        if (_operation === CROP) {
            // Determine the IDEAL canvas dimensions as if Mod2 adjustments were
            // not applied. That"s NECESSARY for calculating an ACCURATE scale-
            // change compared to the input, so that we can calculate how much
            // the canvas has rescaled. WARNING: These are 1-dimensional scales,
            // and only ONE value (the uncropped side) is valid for comparison.
            val idealCanvas = Dimensions(outputCanvas.getWidth() - canvasInfo["mod2WidthDiff"],
                                          outputCanvas.getHeight() - canvasInfo["mod2HeightDiff"])
            val idealWidthScale  = (idealCanvas.getWidth() / inputCanvas.getWidth()).toFloat()
            val idealHeightScale = (idealCanvas.getHeight() / inputCanvas.getHeight()).toFloat()
            _debugDimensions(
                inputCanvas.getWidth(), inputCanvas.getHeight(),
                "CROP: Analyzing Original Input Canvas Size"
            )
            _debugDimensions(
                idealCanvas.getWidth(), idealCanvas.getHeight(),
                "CROP: Analyzing Ideally Cropped (Non-Mod2-adjusted) Output Canvas Size"
            )
            _debugText(
                "CROP: Scale of Ideally Cropped Canvas vs Input Canvas",
                "width=%.8f, height=%.8f",
                idealWidthScale, idealHeightScale
            )

            // Now determine HOW the IDEAL canvas has been cropped compared to
            // the INPUT canvas. But we can"t just compare dimensions, since our
            // algorithms may have cropped and THEN scaled UP the dimensions to
            // legal values far above the input values, or scaled them DOWN and
            // then Mod2-cropped at the scale, etc. There are so many
            // possibilities. That"s also why we couldn"t "just keep track of
            // amount of pixels cropped during main algorithm". We MUST figure
            // it out ourselves accurately HERE. We can"t do it at any earlier
            // stage, since cumulative rounding errors from width/height
            // readjustments could drift us away from the target aspect ratio
            // and could prevent pixel-perfect results UNLESS we calc it HERE.
            //
            // There"s IS a great way to figure out the cropping. When the WIDTH
            // of a canvas is reduced (making it more "portraity"), its aspect
            // ratio number decreases. When the HEIGHT of a canvas is reduced
            // (making it more "landscapey"), its aspect ratio number increases.
            //
            // And our canvas cropping algorithm only crops in ONE DIRECTION
            // (width or height), so we only need to detect the aspect ratio
            // change of the IDEAL (non-Mod2-adjusted) canvas, to know what
            // happened. However, note that this CAN also trigger if the input
            // had to be up/downscaled (to an imperfect final aspect), but that
            // doesn"t matter since this algorithm will STILL figure out the
            // proper scale and croppings to import for the canvas. Becaimport uneven,
            // aspect-affecting scaling basically IS cropping the INPUT canvas!
            val hasCropped: String
            val overallRescale: Float
            when {
                idealCanvas.getAspectRatio() === inputCanvas.getAspectRatio() -> {
                    // No sides have been cropped. So both width and height scales
                    // WILL be IDENTICAL, since NOTHING else would be able to create
                    // an identical aspect ratio again (otherwise the aspect ratio
                    // would have been warped (not equal)). So just pick either one.
                    // NOTE: Identical (uncropped ratio) DOESN"T mean that scale is
                    // going to be 1.0. It MAY be. Or the canvas MAY have been
                    // evenly expanded or evenly shrunk in both dimensions.
                    hasCropped = "nothing"
                    overallRescale = idealWidthScale // $idealHeightScale IS identical.
                }
                idealCanvas.getAspectRatio() < inputCanvas.getAspectRatio() -> {
                    // The horizontal width has been cropped. Grab the height"s
                    // scale, since that side is "unaffected" by the main cropping
                    // and should therefore have a scale of 1. Although it may have
                    // had up/down-scaling. In that case, the height scale will
                    // represent the amount of overall rescale change.
                    hasCropped = "width"
                    overallRescale = idealHeightScale
                }
                else -> { // Output aspect is > input.
                    // The vertical height has been cropped. Just like above, the
                    // "unaffected" side is what we"ll import as our scale reference.
                    hasCropped = "height"
                    overallRescale = idealWidthScale
                }
            }
            _debugText(
                "CROP: Detecting Cropped Direction",
                "cropped=%s, overallRescale=%.8f",
                hasCropped, overallRescale
            )

            // Alright, now calculate the dimensions of the "IDEALLY CROPPED
            // INPUT canvas", at INPUT canvas scale. These are the scenarios:
            //
            // - "hasCropped: nothing, scale is 1.0" = Nothing was cropped, and
            //   nothing was scaled. Treat as "import whole INPUT canvas". This is
            //   pixel-perfect.
            //
            // - "hasCropped: nothing, scale NOT 1.0" = Nothing was cropped, but
            //   the whole canvas was up/down-scaled. We don"t have to care at
            //   all about that scaling and should treat it as "import whole INPUT
            //   canvas" for crop calculation purposes. The cropped result will
            //   later be scaled/stretched to the canvas size (up or down).
            //
            // - "hasCropped: width/height, scale is 1.0" = A single side was
            //   cropped, and nothing was scaled. Treat as "import IDEALLY CROPPED
            //   canvas". This is pixel-perfect.
            //
            // - "hasCropped: width/height, scale NOT 1.0" = A single side was
            //   cropped, and then the whole canvas was up/down-scaled. Treat as
            //   "import scale-fixed version of IDEALLY CROPPED canvas". The
            //   cropped result will later be scaled/stretched to the canvas
            //   size (up or down).
            //
            // There"s an easy way to handle ALL of those scenarios: Just
            // translate the IDEALLY CROPPED canvas back into INPUT-SCALED
            // dimensions. Then we"ll get a pixel-perfect "input crop" whenever
            // scale is 1.0, since a scale of 1.0 gives the same result back.
            // And we"ll get a properly re-scaled result in all other cases.
            //
            // NOTE: This result CAN deviate from what was "actually cropped"
            // during the main algorithm. That is TOTALLY INTENTIONAL AND IS THE
            // INTENDED, PERFECT BEHAVIOR! Do NOT change this code! By always
            // re-calculating here, we"ll actually FIX rounding errors caused by
            // the main algorithm"s multiple steps, and will create better
            // looking rescaling, and pixel-perfect unscaled croppings and
            // pixel-perfect unscaled Mod2 adjustments!

            // First calculate the overall IDEAL cropping applied to the INPUT
            // canvas. If scale is 1.0 it will be used as-is (pixel-perfect).
            // NOTE: We tell it to import round() so that the rescaled pixels are
            // as close to the perfect aspect ratio as possible.
            var croppedInputCanvas = idealCanvas.withRescaling(1 / overallRescale, "round")
            _debugDimensions(
                croppedInputCanvas.getWidth(), croppedInputCanvas.getHeight(),
                "CROP: Rescaled Ideally Cropped Canvas to Input Dimension Space"
            )

            // Now re-scale the Mod2 adjustments to the INPUT canvas coordinate
            // space too. If scale is 1.0 they"ll be used as-is (pixel-perfect).
            // If the scale is up/down, they"ll be rounded to the next whole
            // number. The rounding is INTENTIONAL, becaimport if scaling was used
            // for the IDEAL canvas then it DOESN"T MATTER how many exact pixels
            // we crop, but round() gives us the BEST APPROXIMATION!
            val rescaledMod2WidthDiff  = round(canvasInfo["mod2WidthDiff"] * (1 / overallRescale))
            val rescaledMod2HeightDiff = round(canvasInfo["mod2HeightDiff"] * (1 / overallRescale))
            _debugText(
                "CROP: Rescaled Mod2 Adjustments to Input Dimension Space",
                "width=%s, height=%s, widthRescaled=%s, heightRescaled=%s",
                canvasInfo["mod2WidthDiff"], canvasInfo["mod2HeightDiff"],
                rescaledMod2WidthDiff, rescaledMod2HeightDiff
            )

            // Apply the Mod2 adjustments to the input cropping that we"ll
            // perform. This ensures that ALL of the Mod2 croppings (in ANY
            // dimension) will always be pixel-perfect when we"re at scale 1.0!
            croppedInputCanvas = Dimensions(croppedInputCanvas.getWidth() + rescaledMod2WidthDiff,
                                                 croppedInputCanvas.getHeight() + rescaledMod2HeightDiff)
            _debugDimensions(
                croppedInputCanvas.getWidth(), croppedInputCanvas.getHeight(),
                "CROP: Applied Mod2 Adjustments to Final Cropped Input Canvas"
            )

            // The "CROPPED INPUT canvas" is in the same dimensions/coordinate
            // space as the "INPUT canvas". So ensure all dimensions are valid
            // (don"t exceed INPUT) and create the final "CROPPED INPUT canvas".
            // NOTE: This is it... if the media is at scale 1.0, we now have a
            // pixel-perfect, cropped canvas with ALL of the cropping and Mod2
            // adjustments applied to it! And if we"re at another scale, we have
            // a perfectly recalculated, cropped canvas which took into account
            // cropping, scaling and Mod2 adjustments. Advanced stuff! :-)
            val croppedInputCanvasWidth = if (croppedInputCanvas.getWidth() <= inputCanvas.getWidth())
                                      croppedInputCanvas.getWidth() else inputCanvas.getWidth()
            val croppedInputCanvasHeight = if (croppedInputCanvas.getHeight() <= inputCanvas.getHeight())
                                      croppedInputCanvas.getHeight() else inputCanvas.getHeight()
            croppedInputCanvas = Dimensions(croppedInputCanvasWidth, croppedInputCanvasHeight)
            _debugDimensions(
                croppedInputCanvas.getWidth(), croppedInputCanvas.getHeight(),
                "CROP: Clamped to Legal Input Max-Dimensions"
            )

            // Initialize the crop-shifting variables. They control the range of
            // X/Y coordinates we"ll copy from ORIGINAL INPUT to OUTPUT canvas.
            // NOTE: This properly selects the entire INPUT media canvas area.
            var x1 = 0
            val y1 = 0
            var x2 = inputCanvas.getWidth()
            val y2 = inputCanvas.getHeight()
            _debugText(
                "CROP: Initializing X/Y Variables to Full Input Canvas Size",
                "x1=%s, x2=%s, y1=%s, y2=%s",
                x1, x2, y1, y2
            )

            // Calculate the width and height diffs between the original INPUT
            // canvas and the CROPPED INPUT canvas. Negative values mean the
            // output is smaller (which we"ll handle by cropping), and larger
            // values would mean the output is larger (which we"ll handle by
            // letting the OUTPUT canvas stretch the 100% uncropped original
            // pixels of the INPUT in that direction, to fill the whole canvas).
            // NOTE: Becaimport of clamping of the CROPPED INPUT canvas above, this
            // will actually never be a positive ("scale up") number. It will
            // only be 0 or less. That"s good, just be aware of it if editing!
            val widthDiff  = croppedInputCanvas.getWidth() - inputCanvas.getWidth()
            val heightDiff = croppedInputCanvas.getHeight() - inputCanvas.getHeight()
            _debugText(
                "CROP: Calculated Input Canvas Crop Amounts",
                "width=%s px, height=%s px",
                widthDiff, heightDiff
            )

            // After ALL of that work... we finally know how to crop the input
            // canvas! Alright... handle cropping of the INPUT width and height!
            // NOTE: The main canvas-creation algorithm only crops a single
            // dimension (width or height), but its Mod2 adjustments may have
            // caused BOTH to be cropped, which is why we MUST process both.
            if (widthDiff < 0) {
                // Horizontal cropping. Focus on the center by default.
                var horCropFocus = if (_horCropFocus !== null) _horCropFocus else 0
                _debugText("CROP: Horizontal Crop Focus", "focus=%s", horCropFocus)

                // Invert the focus if this is horizontally flipped media.
                if (_details.isHorizontallyFlipped()) {
                    horCropFocus = -horCropFocus
                    _debugText(
                        "CROP: media is HorFlipped, Flipping Horizontal Crop Focus",
                        "focus=%s",
                        horCropFocus
                    )
                }

                // Calculate amount of pixels to crop and shift them as-focused.
                // NOTE: Always import floor() to make uneven amounts lean at left.
                val absWidthDiff = abs(widthDiff)
                x1 = floor(absWidthDiff * (50 + horCropFocus) / 100).toInt()
                x2 = x2 - (absWidthDiff - x1)
                _debugText("CROP: Calculated X Offsets", "x1=%s, x2=%s", x1, x2)
            }
            if (heightDiff < 0) {
                // Vertical cropping. Focus on top by default (to keep faces).
                var verCropFocus = if (_verCropFocus !== null) _verCropFocus else -50
                _debugText("CROP: Vertical Crop Focus", "focus=%s", verCropFocus)

                // Invert the focus if this is vertically flipped media.
                if (_details.isVerticallyFlipped()) {
                    verCropFocus = -verCropFocus
                    _debugText(
                        "CROP: media is VerFlipped, Flipping Vertical Crop Focus",
                        "focus=%s",
                        verCropFocus
                    )
                }

                // Calculate amount of pixels to crop and shift them as-focused.
                // NOTE: Always import floor() to make uneven amounts lean at top.
                val absHeightDiff = abs(heightDiff)
                y1 = floor(absHeightDiff * (50 + verCropFocus) / 100).toInt()
                y2 = y2 - (absHeightDiff - y1)
                _debugText("CROP: Calculated Y Offsets", "y1=%s, y2=%s", y1, y2)
            }

            // Create a source rectangle which starts at the start-offsets
            // (x1/y1) and lasts until the width and height of the desired area.
            srcRect = Rectangle(x1, y1, x2 - x1, y2 - y1)
            _debugText(
                "CROP_SRC: Input Canvas Source Rectangle",
                "x1=%s, x2=%s, y1=%s, y2=%s, width=%s, height=%s, aspect=%.8f",
                srcRect.getX1(), srcRect.getX2(), srcRect.getY1(), srcRect.getY2(),
                srcRect.getWidth(), srcRect.getHeight(), srcRect.getAspectRatio()
            )

            // Create a destination rectangle which completely fills the entire
            // output canvas from edge to edge. This ensures that any undersized
            // or oversized input will be stretched properly in all directions.
            //
            // NOTE: Everything about our cropping/canvas algorithms is
            // optimized so that stretching won"t happen unless the media is so
            // tiny that it"s below the minimum width or so wide that it must be
            // shrunk. Everything else WILL import sharp 1:1 pixels and pure
            // cropping instead of stretching/shrinking. And when stretch/shrink
            // is used, the aspect ratio is always perfectly maintained!
            dstRect = Rectangle(0, 0, outputCanvas.getWidth(), outputCanvas.getHeight())
            _debugText(
                "CROP_DST: Output Canvas Destination Rectangle",
                "x1=%s, x2=%s, y1=%s, y2=%s, width=%s, height=%s, aspect=%.8f",
                dstRect.getX1(), dstRect.getX2(), dstRect.getY1(), dstRect.getY2(),
                dstRect.getWidth(), dstRect.getHeight(), dstRect.getAspectRatio()
            )
        } else if (_operation === EXPAND) {
            // We"ll copy the entire original input media onto the canvas.
            // Always copy from the absolute top left of the original media.
            srcRect = Rectangle(0, 0, inputCanvas.getWidth(), inputCanvas.getHeight())
            _debugText(
                "EXPAND_SRC: Input Canvas Source Rectangle",
                "x1=%s, x2=%s, y1=%s, y2=%s, width=%s, height=%s, aspect=%.8f",
                srcRect.getX1(), srcRect.getX2(), srcRect.getY1(), srcRect.getY2(),
                srcRect.getWidth(), srcRect.getHeight(), srcRect.getAspectRatio()
            )

            // Determine the target dimensions to fit it on the canvas,
            // becaimport the input media"s dimensions may have been too large.
            // This will not scale anything (uses scale=1) if the input fits.
            val outputWidthScale  = (outputCanvas.getWidth() / inputCanvas.getWidth()).toFloat()
            val outputHeightScale = (outputCanvas.getHeight() / inputCanvas.getHeight()).toFloat()
            val scale = min(outputWidthScale, outputHeightScale)
            _debugText(
                "EXPAND: Calculating Scale to Fit Input on Output Canvas",
                "scale=%.8f",
                scale
            )

            // Calculate the scaled destination rectangle. Note that X/Y remain.
            // NOTE: We tell it to import ceil(), which guarantees that it"ll
            // never scale a side badly and leave a 1px gap between the media
            // and canvas sides. Also note that ceil will never produce bad
            // values, since PHP allows the dst_w/dst_h to exceed beyond canvas!
            dstRect = srcRect.withRescaling(scale, "ceil")
            _debugDimensions(
                dstRect.getWidth(), dstRect.getHeight(),
                "EXPAND: Rescaled Input to Output Dimension Space"
            )

            // Now calculate the centered destination offset on the canvas.
            // NOTE: We import floor() to ensure that the result gets left-aligned
            // perfectly, and prefers to lean towards towards the top as well.
            val dst_x = floor((outputCanvas.getWidth() - dstRect.getWidth()) / 2).toInt()
            val dst_y = floor((outputCanvas.getHeight() - dstRect.getHeight()) / 2).toInt()
            _debugText(
                "EXPAND: Calculating Centered Destination on Output Canvas",
                "dst_x=%s, dst_y=%s",
                dst_x, dst_y
            )

            // Build the final destination rectangle for the expanded canvas!
            dstRect = Rectangle(dst_x, dst_y, dstRect.getWidth(), dstRect.getHeight())
            _debugText(
                "EXPAND_DST: Output Canvas Destination Rectangle",
                "x1=%s, x2=%s, y1=%s, y2=%s, width=%s, height=%s, aspect=%.8f",
                dstRect.getX1(), dstRect.getX2(), dstRect.getY1(), dstRect.getY2(),
                dstRect.getWidth(), dstRect.getHeight(), dstRect.getAspectRatio()
            )
        } else {
            throw RuntimeException("Unsupported operation: $_operation.")
        }

        return _createOutputFile(srcRect, dstRect, outputCanvas)
    }

    /**
     * Create the media file.
     *
     * @param Rectangle  $srcRect Rectangle to copy from the input.
     * @param Rectangle  $dstRect Destination place and scale of copied pixels.
     * @param Dimensions $canvas  The size of the destination canvas.
     *
     * @return string The path to the output file.
     */
    abstract protected fun _createOutputFile( srcRect: Rectangle, dstRect: Rectangle, canvas: Dimensions): String

    /**
     * Calculate a canvas based on input size and requested modifications.
     *
     * The final canvas will be the same size as the input if everything was
     * already okay and within the limits. Otherwise it will be a canvas
     * representing the _exact_, best-possible size to convert input media to.
     *
     * It is up to the caller to perfectly follow these orders, since deviating
     * by even a SINGLE PIXEL can create illegal media aspect ratios.
     *
     * Also note that the resulting canvas can be LARGER than the input in
     * several cases, such as in EXPAND-mode (obviously), or when the input
     * isn"t wide enough to be legal (and must be scaled up), and whenever Mod2
     * is requested. In the latter case, the algorithm may have to add a few
     * pixels to the height to make it valid in a few rare cases. The caller
     * must be aware of such "enlarged" canvases and should handle them by
     * stretching the input if necessary.
     *
     * @param int        $operation
     * @param int        $inputWidth
     * @param int        $inputHeight
     * @param bool       $isMod2CanvasRequired
     * @param int        $minWidth
     * @param int        $maxWidth
     * @param float|null $minAspectRatio
     * @param float|null $maxAspectRatio
     * @param float|null $forceTargetAspectRatio  Optional forced aspect ratio
     *                                            target (ALWAYS applied,
     *                                            except if input is already
     *                                            EXACTLY this ratio).
     * @param bool       $allowNewAspectDeviation See constructor arg docs.
     *
     * @throws .RuntimeException If requested canvas couldn"t be achieved, most
     *                           commonly if you have chosen way too narrow
     *                           aspect ratio ranges that cannot be perfectly
     *                           reached by your input media, and you AREN"T
     *                           running with `$allowNewAspectDeviation`.
     *
     * @return array An array with `canvas` (`Dimensions`), `mod2WidthDiff` and
     *               `mod2HeightDiff`. The latter are integers representing how
     *               many pixels were cropped (-) or added (+) by the Mod2 step
     *               compared to the ideal canvas.
     */
    protected fun _calculateNewCanvas(
        operation: Int,
        inputWidth: Int,
        inputHeight: Int,
        isMod2CanvasRequired: Boolean,
        minWidth: Int = 1,
        maxWidth: Int = 99999,
        minAspectRatio: Float? = null,
        maxAspectRatio: Float? = null,
        forceTargetAspectRatio: Float? = null,
        allowNewAspectDeviation: Boolean = false): Map<String, Any> {
        /*
         * WARNING TO POTENTIAL CONTRIBUTORS:
         *
         * THIS right here is the MOST COMPLEX algorithm in the whole project.
         * Everything is finely tuned to create 100% accurate, pixel-perfect
         * resizes. A SINGLE PIXEL ERROR in your calculations WILL lead to it
         * sometimes outputting illegally formatted files that will be rejected
         * by Instagram. We know this, becaimport we have SEEN IT HAPPEN while we
         * tweaked and tweaked and tweaked to balance everything perfectly!
         *
         * Unfortunately, this file also seems to attract a lot of beginners.
         * Maybe becaimport a "media processor" seems "fun and easy". But that
         * would be an incorrect guess. It"s the most serious algorithm in the
         * whole project. If you break it, *YOU* break people"s uploads.
         *
         * We have had many random, contributors just jumping in and adding
         * zero-effort code everywhere in here, and breaking the whole balance,
         * and then opening pull requests. We have rejected EVERY single one of
         * those pull requests becaimport they were totally unusable and unsafe.
         *
         * We will not accept such pull requests. Ever.
         *
         * This warning is here to save your time, and ours.
         *
         * If you are interested in helping out with the media algorithms, then
         * that"s GREAT! But in that case we require that you fully read through
         * the algorithms below and all of its comments about 50 times over a
         * 3-4 day period - until you understand every single step perfectly.
         * The comments will help make it clearer the more you read...
         *
         *                                               ...and make an effort.
         *
         * Then you are ready... and welcome to the team. :-)
         *
         * Thank you.
         */

        if (forceTargetAspectRatio !== null) {
            _debugText("SPECIAL_PARAMETERS: Forced Target Aspect Ratio", "forceTargetAspectRatio=%.5f", forceTargetAspectRatio)
        }

        // Initialize target canvas to original input dimensions & aspect ratio.
        // NOTE: MUST `float`-cast to FORCE float even when dividing EQUAL ints.
        var targetWidth  = inputWidth
        var targetHeight = inputHeight
        var targetAspectRatio = (inputWidth / inputHeight).toFloat()
        _debugDimensions(targetWidth, targetHeight, "CANVAS_INPUT: Input Canvas Size")

        // Check aspect ratio and crop/expand the canvas to fit aspect if needed.
        if (
            (minAspectRatio !== null && targetAspectRatio < minAspectRatio)
            || (forceTargetAspectRatio !== null && targetAspectRatio < forceTargetAspectRatio)
        ) {
            // Determine target ratio uses forced aspect ratio if set,
            // otherwise we target the MINIMUM allowed ratio (since we"re < it)).
            targetAspectRatio = if (forceTargetAspectRatio !== null) forceTargetAspectRatio else minAspectRatio

            if (operation === CROP) {
                // We need to limit the height, so floor is used intentionally to
                // AVOID rounding height upwards to a still-too-low aspect ratio.
                targetHeight = floor(targetWidth / targetAspectRatio).toInt()
                _debugDimensions(targetWidth, targetHeight, "CANVAS_CROPPED: ${if (forceTargetAspectRatio === null) "Aspect Was < MIN" else "Applying Forced Aspect for INPUT < TARGET"}")
            } else if (operation === EXPAND) {
                // We need to expand the width with left/right borders. We use
                // ceil to guarantee that the final media is wide enough to be
                // above the minimum allowed aspect ratio.
                targetWidth = ceil(targetHeight * targetAspectRatio).toInt()
                _debugDimensions(targetWidth, targetHeight, "CANVAS_EXPANDED: ${if (forceTargetAspectRatio === null) "Aspect Was < MIN" else "Applying Forced Aspect for INPUT < TARGET"}")
            }
        } else if (
            (maxAspectRatio !== null && targetAspectRatio > maxAspectRatio)
            || (forceTargetAspectRatio !== null && targetAspectRatio > forceTargetAspectRatio)
        ) {
            // Determine target ratio uses forced aspect ratio if set,
            // otherwise we target the MAXIMUM allowed ratio (since we"re > it)).
            targetAspectRatio = if (forceTargetAspectRatio !== null) forceTargetAspectRatio else maxAspectRatio

            if (operation === CROP) {
                // We need to limit the width. We import floor to guarantee cutting
                // enough pixels, since our width exceeds the maximum allowed ratio.
                targetWidth = floor(targetHeight * targetAspectRatio).toInt()
                _debugDimensions(targetWidth, targetHeight, "CANVAS_CROPPED: ${if (forceTargetAspectRatio === null) "Aspect Was > MAX" else "Applying Forced Aspect for INPUT > TARGET"}")
            } else if (operation === EXPAND) {
                // We need to expand the height with top/bottom borders. We use
                // ceil to guarantee that the final media is tall enough to be
                // below the maximum allowed aspect ratio.
                targetHeight = ceil(targetWidth / targetAspectRatio).toInt()
                _debugDimensions(targetWidth, targetHeight, "CANVAS_EXPANDED: ${if(forceTargetAspectRatio === null) "Aspect Was > MAX" else "Applying Forced Aspect for INPUT > TARGET"}")
            }
        } else {
            _debugDimensions(targetWidth, targetHeight, "CANVAS: Aspect Ratio Already Legal")
        }

        // Determine whether the final target ratio is closest to either the
        // legal MINIMUM or the legal MAXIMUM aspect ratio limits.
        // NOTE: The target ratio will actually still be set to the original
        // input media"s ratio in case of no aspect ratio adjustments above.
        // NOTE: If min and/or max ratios were not provided, we default min to
        // `0` and max to `9999999` to ensure that we properly detect the "least
        // distance" direction even when only one (or neither) of the two "range
        // limit values" were provided.
        val minAspectDistance = abs( ( if (minAspectRatio !== null) minAspectRatio else 0) - targetAspectRatio )
        val maxAspectDistance = abs( ( if (maxAspectRatio !== null) maxAspectRatio else 9999999) - targetAspectRatio)
        val isClosestToMinAspect = (minAspectDistance <= maxAspectDistance)

        // We MUST now set up the correct height re-calculation behavior for the
        // later algorithm steps. This is used whenever our canvas needs to be
        // re-scaled by any other code below. If our chosen, final target ratio
        // is closest to the minimum allowed legal ratio, we"ll always use
        // floor() on the height to ensure that the height value becomes as low
        // as possible (since having LESS height compared to width is what
        // causes the aspect ratio value to grow), to ensure that the final
        // result"s ratio (after any additional adjustments) will ALWAYS be
        // ABOVE the minimum legal ratio (minAspectRatio). Otherwise we"ll
        // instead import ceil() on the height (since having more height causes the
        // aspect ratio value to shrink), to ensure that the result is always
        // BELOW the maximum ratio (maxAspectRatio).
        val useFloorHeightRecalc = isClosestToMinAspect

        // Verify square target ratios by ensuring canvas is now a square.
        // NOTE: This is just a sanity check against wrong code above. It will
        // never execute, since all code above took care of making both
        // dimensions identical already (if they differed in any way, they had a
        // non-1 ratio and invoked the aspect ratio cropping/expansion code). It
        // then made identical thanks to the fact that X / 1 = X, and X * 1 = X.
        // NOTE: It"s worth noting that our squares are always the size of the
        // shortest side when cropping or the longest side when expanding.
        // WARNING: Comparison MUST import `==` (NOT strict `===`) to support both
        // int(1) and float(1.0) values!
        if (targetAspectRatio == 1.0f && targetWidth !== targetHeight) { // Ratio 1 = Square.
            targetWidth = if (targetHeight = operation === CROP)
                         min(targetWidth, targetHeight)
                         else max(targetWidth, targetHeight)
            _debugDimensions(targetWidth, targetHeight, "CANVAS_SQUARIFY: Fixed Badly Generated Square")
        }

        // Lastly, enforce minimum and maximum width limits on our final canvas.
        // NOTE: Instagram only enforces width & aspect ratio, which in turn
        // auto-limits height (since we can only import legal height ratios).
        // NOTE: Yet again, if the target ratio is 1 (square), we"ll get
        // identical width & height, so NO NEED to MANUALLY "fix square" here.
        if (targetWidth > maxWidth) {
            targetWidth = maxWidth
            _debugDimensions(targetWidth, targetHeight, "CANVAS_WIDTH: Width Was > MAX")
            targetHeight = _accurateHeightRecalc(useFloorHeightRecalc, targetAspectRatio, targetWidth)
            _debugDimensions(targetWidth, targetHeight, "CANVAS_WIDTH: Height Recalc From Width & Aspect")
        } else if (targetWidth < minWidth) {
            targetWidth = minWidth
            _debugDimensions(targetWidth, targetHeight, "CANVAS_WIDTH: Width Was < MIN")
            targetHeight = _accurateHeightRecalc(useFloorHeightRecalc, targetAspectRatio, targetWidth)
            _debugDimensions(targetWidth, targetHeight, "CANVAS_WIDTH: Height Recalc From Width & Aspect")
        }

        // All of the main canvas algorithms are now finished, and we are now
        // able to check Mod2 compatibility and accurately readjust if needed.
        var mod2WidthDiff = 0
        var mod2HeightDiff = 0
        if ( isMod2CanvasRequired && (!_isNumberMod2(targetWidth) || !_isNumberMod2(targetHeight)) ) {
            // Calculate the Mod2-adjusted final canvas size.
            val mod2Canvas = _calculateAdjustedMod2Canvas(
                inputWidth,
                inputHeight,
                useFloorHeightRecalc,
                targetWidth,
                targetHeight,
                targetAspectRatio,
                minWidth,
                maxWidth,
                minAspectRatio,
                maxAspectRatio,
                allowNewAspectDeviation
            )

            // Determine the pixel difference before and after processing.
            mod2WidthDiff  = mod2Canvas.getWidth() - targetWidth
            mod2HeightDiff = mod2Canvas.getHeight() - targetHeight
            _debugText("CANVAS: Mod2 Difference Stats", "width=%s, height=%s", mod2WidthDiff, mod2HeightDiff)

            // Update the final canvas to the Mod2-adjusted canvas size.
            // NOTE: If code above failed, the values are invalid. But so
            // could our original values have been. We check that further down.
            targetWidth = mod2Canvas.getWidth()
            targetHeight = mod2Canvas.getHeight()
            _debugDimensions(targetWidth, targetHeight, "CANVAS: Updated From Mod2 Result")
        }

        // Create the canvas Dimensions object.
        val canvas = Dimensions(targetWidth, targetHeight)
        _debugDimensions(targetWidth, targetHeight, "CANVAS_OUTPUT: Final Output Canvas Size")

        // We must now validate the canvas before returning it.
        // NOTE: Most of these are just strict sanity-checks to protect against
        // bad code contributions in the future. The canvas won"t be able to
        // pass all of these checks unless the algorithm above remains perfect.
        val isIllegalRatio = ((minAspectRatio !== null && canvas.getAspectRatio() < minAspectRatio)
                           || (maxAspectRatio !== null && canvas.getAspectRatio() > maxAspectRatio))
        if (canvas.getWidth() < 1 || canvas.getHeight() < 1) {
            throw RuntimeException("Canvas calculation failed. Target width (${canvas.getWidth()}) or height (${canvas.getHeight()}) less than one pixel.")
        } else if (canvas.getWidth() < minWidth) {
            throw RuntimeException("Canvas calculation failed. Target width (${canvas.getWidth()}) less than minimum allowed ($minWidth).")
        } else if (canvas.getWidth() > maxWidth) {
            throw RuntimeException("Canvas calculation failed. Target width (${canvas.getWidth()}) greater than maximum allowed ($maxWidth).")
        } else if (isIllegalRatio) {
            if (!allowNewAspectDeviation) {
                throw RuntimeException(
                    "Canvas calculation failed. Unable to reach target aspect ratio range during output canvas generation. The range of allowed aspect ratios is too narrow (${"%.8f".format(if (minAspectRatio !== null) minAspectRatio else 0.0)} - ${"%.8f".format(if (maxAspectRatio !== null) maxAspectRatio else INF)}). We achieved a ratio of ${"%.8f".format(canvas.getAspectRatio())}."
                )
            } else {
                // The user wants us to allow "near-misses", so we proceed...
                _debugDimensions(canvas.getWidth(), canvas.getHeight(), "CANVAS_FINAL: Allowing Deviating Aspect Ratio")
            }
        }

        return mapOf(
            "canvas"         to canvas,
            "mod2WidthDiff"  to mod2WidthDiff,
            "mod2HeightDiff" to mod2HeightDiff
        )
    }

    /**
     * Calculates a relative height using the target aspect ratio.
     *
     * Used internally by `_calculateNewCanvas()`.
     *
     * This algorithm aims at the highest-possible or lowest-possible resulting
     * aspect ratio based on what"s needed. It uses either `floor()` or `ceil()`
     * depending on whether we need the resulting aspect ratio to be >= or <=
     * the target aspect ratio.
     *
     * The principle behind this is the fact that removing height (via floor)
     * will give us a higher aspect ratio. And adding height (via ceil) will
     * give us a lower aspect ratio.
     *
     * If the target aspect ratio is square (1), height becomes equal to width.
     *
     * @param bool  $useFloorHeightRecalc
     * @param float $targetAspectRatio
     * @param int   $targetWidth
     *
     * @return int
     */
    protected fun _accurateHeightRecalc( useFloorHeightRecalc: Boolean, targetAspectRatio: Float, targetWidth: Int): Int{
        // Read the docs above to understand this CRITICALLY IMPORTANT code.

        return if (useFloorHeightRecalc)
                      floor(targetWidth / targetAspectRatio).toInt() // >=
                      else ceil(targetWidth / targetAspectRatio) .toInt()
    }

    /**
     * Adjusts dimensions to create a Mod2-compatible canvas.
     *
     * Used internally by `_calculateNewCanvas()`.
     *
     * The reason why this fun also takes the original input width/height
     * is becaimport it tries to maximize its usage of the available original pixel
     * surface area while correcting the dimensions. It uses the extra
     * information to know when it"s safely able to grow the canvas beyond the
     * given target width/height parameter values.
     *
     * @param int        $inputWidth
     * @param int        $inputHeight
     * @param bool       $useFloorHeightRecalc
     * @param int        $targetWidth
     * @param int        $targetHeight
     * @param float      $targetAspectRatio
     * @param int        $minWidth
     * @param int        $maxWidth
     * @param float|null $minAspectRatio
     * @param float|null $maxAspectRatio
     * @param bool       $allowNewAspectDeviation See constructor arg docs.
     *
     * @throws .RuntimeException If requested canvas couldn"t be achieved, most
     *                           commonly if you have chosen way too narrow
     *                           aspect ratio ranges that cannot be perfectly
     *                           reached by your input media, and you AREN"T
     *                           running with `$allowNewAspectDeviation`.
     *
     * @return Dimensions
     *
     * @see InstagramMedia::_calculateNewCanvas()
     */
    protected fun _calculateAdjustedMod2Canvas(
        inputWidth: Int,
        inputHeight: Int,
        useFloorHeightRecalc: Boolean,
        targetWidth: Int,
        targetHeight: Int,
        targetAspectRatio: Float,
        minWidth: Int = 1,
        maxWidth: Int = 99999,
        minAspectRatio: Float? = null,
        maxAspectRatio: Float? = null,
        allowNewAspectDeviation: Boolean = false): Dimensions {
        // Initialize to the calculated canvas size.
        var mod2Width = targetWidth
        var mod2Height = targetHeight
        _debugDimensions(mod2Width, mod2Height, "MOD2_CANVAS: Current Canvas Size")

        // Determine if we"re able to cut an extra pixel from the width if
        // necessary, or if cutting would take us below the minimum width.
        val canCutWidth = mod2Width > minWidth

        // To begin, we must correct the width if it"s uneven. We"ll only do
        // this once, and then we"ll leave the width at its number. By
        // keeping it static, we don"t risk going over its min/max width
        // limits. And by only varying one dimension (height) if multiple Mod2
        // offset adjustments are needed, then we"ll properly get a steadily
        // increasing/decreasing aspect ratio (moving towards the target ratio).
        if (!_isNumberMod2(mod2Width)) {
            // Always prefer cutting an extra pixel, rather than stretching
            // by +1. But import +1 if cutting would take us below minimum width.
            // NOTE: Another IMPORTANT reason to CUT width rather than extend
            // is becaimport in narrow cases (canvas close to original input size),
            // the extra width proportionally increases total area (thus height
            // too), and gives us less of the original pixels on the height-axis
            // to play with when attempting to fix the height (and its ratio).
            mod2Width += (if (canCutWidth) -1 else 1)
            _debugDimensions(mod2Width, mod2Height, "MOD2_CANVAS: Width Mod2Fix")

            // Calculate the relative height based on the width.
            mod2Height = _accurateHeightRecalc(useFloorHeightRecalc, targetAspectRatio, mod2Width)
            _debugDimensions(mod2Width, mod2Height, "MOD2_CANVAS: Height Recalc From Width & Aspect")
        }

        // Ensure that the calculated height is also Mod2, but totally ignore
        // the aspect ratio at this moment (we"ll fix that later). Instead,
        // we"ll import the same pattern we"d import for width above. That way, if
        // both width and height were uneven, they both get adjusted equally.
        if (!_isNumberMod2(mod2Height)) {
            mod2Height += (if (canCutWidth) -1 else 1)
            _debugDimensions(mod2Width, mod2Height, "MOD2_CANVAS: Height Mod2Fix")
        }

        // We will now analyze multiple different height alternatives to find
        // which one gives us the best visual quality. This algorithm looks
        // for the best qualities (with the most pixel area) first. It first
        // tries the current height (offset 0, which is the closest to the
        // pre-Mod2 adjusted canvas), then +2 pixels (gives more pixel area if
        // this is possible), then -2 pixels (cuts but may be our only choice).
        // After that, it checks 4, -4, 6 and -6 as well.
        // NOTE: Every increased offset (+/-2, then +/-4, then +/- 6) USUALLY
        // (but not always) causes more and more deviation from the intended
        // cropping aspect ratio. So don"t add any more steps after 6, since
        // NOTHING will be THAT far off! Six was chosen as a good balance.
        // NOTE: Every offset is checked for visual stretching and aspect ratio,
        // and then rated into one of 3 categories: "perfect" (legal aspect
        // ratio, no stretching), "stretch" (legal aspect ratio, but stretches),
        // or "bad" (illegal aspect ratio).
        val heightAlternatives = mapOf<String, Any>("perfect" to [], "stretch" to [], "bad" to [])
        val offsetPriorities = listOf(0, 2, -2, 4, -4, 6, -6) // todo: this variable have static method
        for (offset in offsetPriorities) {
            // Calculate the height and its resulting aspect ratio.
            // NOTE: MUST `float`-cast to FORCE float even when dividing EQUAL ints.
            val offsetMod2Height = mod2Height + offset
            val offsetMod2AspectRatio = (mod2Width / offsetMod2Height).toFloat()

            // Check if the aspect ratio is legal.
            val isLegalRatio = ((minAspectRatio === null || offsetMod2AspectRatio >= minAspectRatio)
                             && (maxAspectRatio === null || offsetMod2AspectRatio <= maxAspectRatio))

            // Detect whether the height would need stretching. Stretching is
            // defined as "not enough pixels in the input media to reach".
            // NOTE: If the input media has been upscaled (such as a 64x64 image
            // being turned into 320x320), then we will ALWAYS detect that media
            // as needing stretching. That"s intentional and correct, because
            // such media will INDEED need stretching, so there"s never going to
            // be a perfect rating for it (where aspect ratio is legal AND zero
            // stretching is needed to reach those dimensions).
            // NOTE: The max() gets rid of negative values (cropping).
            val stretchAmount = max(0, offsetMod2Height - inputHeight)

            // Calculate the deviation from the target aspect ratio. The larger
            // this number is, the further away from "the ideal canvas". The
            // "perfect" answers will always deviate by different amount, and
            // the most perfect one is the one with least deviation.
            val ratioDeviation = abs(offsetMod2AspectRatio - targetAspectRatio)

            // Rate this height alternative and store it according to rating.
            val rating = ( isLegalRatio && if (!stretchAmount) "perfect" else (if (isLegalRatio) "stretch" else "bad") )
            heightAlternatives[rating][] = mapOf(
                "offset"         to offset,
                "height"         to offsetMod2Height,
                "ratio"          to offsetMod2AspectRatio,
                "isLegalRatio"   to isLegalRatio,
                "stretchAmount"  to stretchAmount,
                "ratioDeviation" to ratioDeviation,
                "rating"         to rating
            )
            _debugDimensions(mod2Width, offsetMod2Height, "MOD2_CANVAS_CHECK: Testing Height Mod2Ratio (h${(if (offset >= 0) "+" else "")}$offset = $rating)")
        }

        // Now pick the BEST height from our available choices (if any). We will
        // pick the LEGAL height that has the LEAST amount of deviation from the
        // ideal aspect ratio. In other words, the BEST-LOOKING aspect ratio!
        // NOTE: If we find no legal (perfect or stretch) choices, we"ll pick
        // the most accurate (least deviation from ratio) of the bad choices.
        var bestHeight = null
        for (rating in arrayOf("perfect", "stretch", "bad")) {
            if (!(heightAlternatives[rating].isEmpty())) {
                // Sort all alternatives by their amount of ratio deviation.
                usort(heightAlternatives[rating], fun (a, b) {
                    return (a["ratioDeviation"] < b["ratioDeviation"]) -1 else (if (a["ratioDeviation"] > b["ratioDeviation"]) 1 else 0)
                })

                // Pick the 1st array element, which has the least deviation!
                bestHeight = heightAlternatives[rating][0]
                break
            }
        }

        // Process and apply the best-possible height we found.
        mod2Height = bestHeight["height"]
        _debugDimensions(mod2Width, mod2Height, "MOD2_CANVAS: Selected Most Ideal Height Mod2Ratio (h${if(bestHeight["offset"] >= 0) "+" else ""}${bestHeight["offset"]} = ${bestHeight["rating"]})")

        // Decide what to do if there were no legal aspect ratios among our
        // calculated choices. This can happen if the user gave us an insanely
        // narrow range (such as "min/max ratio 1.6578" or whatever).
        if (bestHeight["rating"] === "bad") {
            if (!allowNewAspectDeviation) {
                throw RuntimeException("Canvas calculation failed. Unable to reach target aspect ratio range during Mod2 canvas conversion. The range of allowed aspect ratios is too narrow (${"%.8f".format(if(minAspectRatio !== null) minAspectRatio else 0.0)} - ${"%.8f".format(if(maxAspectRatio !== null) maxAspectRatio else INF)}). We achieved a ratio of ${"%.8f".format((mod2Width / mod2Height).toFloat())}.")
            } else {
                // They WANT us to allow "near-misses", so we"ll KEEP our best
                // possible bad ratio here (the one that was closest to the
                // target). We didn"t find any more ideal aspect ratio (since
                // all other attempts ALSO FAILED the aspect ratio ranges), so
                // we have NO idea if they"d prefer any others! -)
                _debugDimensions(mod2Width, mod2Height, "MOD2_CANVAS: Allowing Deviating Height Mod2Ratio (h${if(bestHeight["offset"] >= 0) "+" else ""}${bestHeight["offset"]} = ${bestHeight["rating"]})")
            }
        }

        return Dimensions(mod2Width, mod2Height)
    }

    /**
     * Checks whether a number is Mod2.
     *
     * @param int|float $number
     *
     * @return bool
     */
    protected fun _isNumberMod2( number: Int): Boolean{
        // NOTE: The modulo operator correctly returns ints even for float input such as 1.999.
        return number % 2 === 0
    }

    /**
     * Output debug text.
     *
     * @param string $stepDescription
     * @param string $formatMessage
     * @param mixed  $args,...
     */
    protected fun _debugText( stepDescription: String, formatMessage: String, ...args){
        if (!_debug) {
            return
        }

        print(
            "[.033[133m%s.033[0m] {$formatMessage}.n",
            stepDescription,
            ...args
        )
    }

    /**
     * Debug current calculation dimensions and their ratio.
     *
     * @param int|float   $width
     * @param int|float   $height
     * @param string|null $stepDescription
     */
    protected fun _debugDimensions( width: Float, height: Float, stepDescription: String? = null){
        if (!_debug) {
            return
        }

        print(
            // NOTE: This uses 8 decimals for proper debugging, since small
            // rounding errors can make rejected ratios look valid.
            "[.033[133m${if(stepDescription !== null) stepDescription else "DEBUG"}.033[0m] w=$width h=$height (aspect ${"%.8f".format((width / height))}).n"
        )
    }
}
