

package instagramAPI.media.Geometry

class Rectangle
{
    /** @var int */
    protected var _x: Int

    /** @var int */
    protected var _y: Int

    /** @var int */
    protected var _width: Int

    /** @var int */
    protected var _height: Int

    /** @var float */
    protected var _aspectRatio: Float

    /**
     * Constructor.
     *
     * @param int $x
     * @param int $y
     * @param int $width
     * @param int $height
     */
    fun constructor( x: Int, y: Int, width: Int, height: Int){
        _x = x
        _y = y
        _width = width
        _height =height
        // NOTE: MUST `float`-cast to FORCE float even when dividing EQUAL ints.
        _aspectRatio = (_width / _height).toFloat()
    }

    /**
     * Get stored X1 offset for this rectangle.
     *
     * @return int
     */
    fun getX(): Int{
        return _x
    }

    /**
     * Get stored Y1 offset for this rectangle.
     *
     * @return int
     */
    fun getY(): Int{
        return _y
    }

    /**
     * Get stored X1 offset for this rectangle.
     *
     * This does the same thing as `getX()`. It is just a mental
     * convenience when working in X1/X2 space.
     *
     * @return int
     */
    fun getX1(): Int{
        return _x
    }

    /**
     * Get stored Y1 offset for this rectangle.
     *
     * This does the same thing as `getY()`. It is just a mental
     * convenience when working in Y1/Y2 space.
     *
     * @return int
     */
    fun getY1(): Int{
        return _y
    }

    /**
     * Get calculated X2 offset (X1+Width) for this rectangle.
     *
     * @return int
     */
    fun getX2(): Int{
        return _x + _width
    }

    /**
     * Get calculated Y2 offset (Y1+Height) for this rectangle.
     *
     * @return int
     */
    fun getY2(): Int{
        return _y + _height
    }

    /**
     * Get stored width for this rectangle.
     *
     * @return int
     */
    fun getWidth(): Int{
        return _width
    }

    /**
     * Get stored height for this rectangle.
     *
     * @return int
     */
    fun getHeight(): Int{
        return _height
    }

    /**
     * Get stored aspect ratio for this rectangle.
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
        return self(_y, _x, _height, _width)
    }

    /**
     * Create a new, scale-adjusted object.
     *
     * NOTE: The x1/y1 offsets are not affected. Only the width and height. But
     * those dimensions WILL affect the x2/y2 offsets, as you"d expect.
     *
     * @param float|int $newScale     The scale factor to apply.
     * @param string    $roundingFunc One of `round` (default), `floor` or `ceil`.
     *
     * @throws  IllegalArgumentException
     *
     * @return self
     */
    fun withRescaling( newScale: Float = 1.0f, roundingFunc: String = "round")
    {
        if (newScale !is Float) {
            throw IllegalArgumentException("The scale must be a float or integer.")
        }
        if (roundingFunc !== "round" && roundingFunc !== "floor" && roundingFunc !== "ceil") {
            throw IllegalArgumentException("Invalid rounding fun \"$roundingFunc\".")
        }

        val newWidth  = roundingFunc(newScale * _width)
        val newHeight = roundingFunc(newScale * _height)

        return self(_x, _y, newWidth, newHeight)
    }
}
