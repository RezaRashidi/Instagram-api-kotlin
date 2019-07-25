

package instagramAPI.realtimes

class Message
{
    /**
     * @var string
     */
    private lateinit var _module: String

    /**
     * @var mixed
     */
    private lateinit var _data: Any

    /**
     * Message constructor.
     *
     * @param string $module
     * @param mixed  $payload
     */
    fun constructor(module: String, payload: Any){
        _module = module
        _data = payload
    }

    /**
     * @return string
     */
    fun getModule(): String {
        return _module
    }

    /**
     * @return mixed
     */
    fun getData(): Any {
        return _data
    }
}
