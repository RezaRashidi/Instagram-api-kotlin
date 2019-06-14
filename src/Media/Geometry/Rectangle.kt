

package InstagramAPI.Media.Geometry

class Rectangle
{
    /** @var int */
    protected $_x

    /** @var int */
    protected $_y

    /** @var int */
    protected $_width

    /** @var int */
    protected $_height

    /** @var float */
    protected $_aspectRatio

    /**
     * Constructor.
     *
     * @param int $x
     * @param int $y
     * @param int $width
     * @param int $height
     */
    public fun __construct(
        $x,
        $y,
        $width,
        $height)
    {
        this._x = (int) $x
        this._y = (int) $y
        this._width = (int) $width
        this._height = (int) $height
        // NOTE: MUST `float`-cast to FORCE float even when dividing EQUAL ints.
        this._aspectRatio = (float) (this._width / this._height)
    }

    /**
     * Get stored X1 offset for this rectangle.
     *
     * @return int
     */
    public fun getX()
    {
        return this._x
    }

    /**
     * Get stored Y1 offset for this rectangle.
     *
     * @return int
     */
    public fun getY()
    {
        return this._y
    }

    /**
     * Get stored X1 offset for this rectangle.
     *
     * This does the same thing as `getX()`. It is just a mental
     * convenience when working in X1/X2 space.
     *
     * @return int
     */
    public fun getX1()
    {
        return this._x
    }

    /**
     * Get stored Y1 offset for this rectangle.
     *
     * This does the same thing as `getY()`. It is just a mental
     * convenience when working in Y1/Y2 space.
     *
     * @return int
     */
    public fun getY1()
    {
        return this._y
    }

    /**
     * Get calculated X2 offset (X1+Width) for this rectangle.
     *
     * @return int
     */
    public fun getX2()
    {
        return this._x + this._width
    }

    /**
     * Get calculated Y2 offset (Y1+Height) for this rectangle.
     *
     * @return int
     */
    public fun getY2()
    {
        return this._y + this._height
    }

    /**
     * Get stored width for this rectangle.
     *
     * @return int
     */
    public fun getWidth()
    {
        return this._width
    }

    /**
     * Get stored height for this rectangle.
     *
     * @return int
     */
    public fun getHeight()
    {
        return this._height
    }

    /**
     * Get stored aspect ratio for this rectangle.
     *
     * @return float
     */
    public fun getAspectRatio()
    {
        return this._aspectRatio
    }

    /**
     * Create a object with swapped axes.
     *
     * @return self
     */
    public fun withSwappedAxes()
    {
        return self(this._y, this._x, this._height, this._width)
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
     * @throws . IllegalArgumentException
     *
     * @return self
     */
    public fun withRescaling(
        $newScale = 1.0,
        $roundingFunc = "round")
    {
        if (!is_float($newScale) && !is_int($newScale)) {
            throw . IllegalArgumentException("The scale must be a float or integer.")
        }
        if ($roundingFunc !== "round" && $roundingFunc !== "floor" && $roundingFunc !== "ceil") {
            throw . IllegalArgumentException(sprintf("Invalid rounding fun "%s".", $roundingFunc))
        }

        $newWidth = (int) $roundingFunc($newScale * this._width)
        $newHeight = (int) $roundingFunc($newScale * this._height)

        return self(this._x, this._y, $newWidth, $newHeight)
    }
}
