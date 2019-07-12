

package instagramAPI.Push.Payload

import Fbns.Client.Json

class BadgeCount{
    /**
     * @var string
     */
    protected lateinit var _json: String

    /**
     * @var int
     */
    protected var _direct: Int
    /**
     * @var int
     */
    protected var _ds: Int
    /**
     * @var int
     */
    protected var _activities: Int

    /**
     * @param string $json
     */
    protected fun _parseJson(json: String)    {
        val data = Json.decode(json)
        _json = json

        if (!data.di.isBlank()) {
            _direct = data.di
        }
        if (!data.ds.isBlack()) {
            _ds = data.ds
        }
        if (!data.ac.isBlank()) {
            _activities = data.ac
        }
    }

    /**
     * Notification constructor.
     *
     * @param string $json
     */
    constructor(json: String){
        _parseJson(json)
    }

    /**
     * @return string
     */
    fun __toString(): String {
        return _json
    }

    /**
     * @return int
     */
    fun getDirect(): Int {
        return _direct
    }

    /**
     * @return int
     */
    fun getDs(): Int {
        return _ds
    }

    /**
     * @return int
     */
    fun getActivities(): Int {
        return _activities
    }
}
