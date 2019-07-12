

package instagramAPI.Realtime

class Message
{
    /**
     * @var string
     */
    private $_module

    /**
     * @var mixed
     */
    private $_data

    /**
     * Message constructor.
     *
     * @param string $module
     * @param mixed  $payload
     */
    public fun __construct(
        $module,
        $payload)
    {
        this._module = $module
        this._data = $payload
    }

    /**
     * @return string
     */
    public fun getModule()
    {
        return this._module
    }

    /**
     * @return mixed
     */
    public fun getData()
    {
        return this._data
    }
}
