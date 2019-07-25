

package instagramAPI.media.Geometry

class Dimensions{
    /** @var int */
    protected var _width: Int

    /** @var int */
    protected var _height: Int

    /** @var float */
    protected var _aspectRatio: Float

    /**
     * Constructor.
     *
     * @param int $width
     * @param int $height
     */
    fun constructor( width: Int, height: Int){
        _width = width
        _height = height
        // NOTE: MUST `float`-cast to FORCE float even when dividing EQUAL ints.
        _aspectRatio = (_width / _height).toFloat()
    }

    /**
     * Get stored width for these dimensions.
     *
     * @return int
     */
    fun getWidth(): Int{
        return _width
    }

    /**
     * Get stored height for these dimensions.
     *
     * @return int
     */
    fun getHeight(): Int{
        return _height
    }

    /**
     * Get stored aspect ratio for these dimensions.
     *
     * @return float
     */
    fun getAspectRatio(): Float{
        return _aspectRatio
    }

    /**
     * Create a object with swapped axes.
     *
     * @return self
     */
    fun withSwappedAxes(){
        return self(_height, _width)
    }

    /**
     * Create a new, scale-adjusted object.
     *
     * @param float|int $newScale     The scale factor to apply.
     * @param string    $roundingFunc One of `round` (default), `floor` or `ceil`.
     *
     * @throws  IllegalArgumentException
     *
     * @return self
     */
    fun withRescaling( newScale: Float = 1.0f, roundingFunc: String = "round"){
        if (newScale !is Float) {
            throw IllegalArgumentException("The scale must be a float or integer.")
        }
        if (roundingFunc !== "round" && roundingFunc !== "floor" && roundingFunc !== "ceil") {
            throw IllegalArgumentException("Invalid rounding fun \"$roundingFunc\".")
        }

        val newWidth  = roundingFunc(newScale * _width).toInt()
        val newHeight = roundingFunc(newScale * _height).toInt()

        return self(newWidth, newHeight)
    }
}
