

package instagramAPI.pushs

import Fbns.Client.Json
import instagramAPI.pushs.Payload.BadgeCount

class Notification
{
    /**
     * @var string
     */
    protected lateinit var _json: String

    /**
     * @var string
     */
    protected lateinit var _title: String
    /**
     * @var string
     */
    protected lateinit var _message: String
    /**
     * @var string
     */
    protected lateinit var _tickerText: String
    /**
     * @var string
     */
    protected lateinit var _igAction: String
    /**
     * @var string
     */
    protected lateinit var _igActionOverride: String
    /**
     * @var string
     */
    protected lateinit var _optionalImage: String
    /**
     * @var string
     */
    protected lateinit var _optionalAvatarUrl: String
    /**
     * @var string
     */
    protected lateinit var _collapseKey: String
    /**
     * @var string
     */
    protected lateinit var _sound: String
    /**
     * @var string
     */
    protected lateinit var _pushId: String
    /**
     * @var string
     */
    protected lateinit var _pushCategory: String
    /**
     * @var string
     */
    protected lateinit var _intendedRecipientUserId: String
    /**
     * @var string
     */
    protected lateinit var _sourceUserId: String
    /**
     * @var BadgeCount
     */
    protected lateinit var _badgeCount: BadgeCount
    /**
     * @var string
     */
    protected lateinit var _inAppActors: String

    /**
     * @var string
     */
    protected lateinit var _actionPath: String
    /**
     * @var array
     */
    protected lateinit var _actionParams: Array

    /**
     * @param string $json
     */
    protected fun _parseJson( json: String){
        val data = Json.decode(json)

        _json = json

        if (!(data.t).isBlank()) {
            _title = data.t.toString()
        }
        if (!(data.m).isBlank()) {
            _message = data.m.toString()
        }
        if (!(data.tt).isBlank()) {
            _tickerText = data.tt.toString()
        }
        _actionPath = ""
        _actionParams = []
        if (!(data.ig).isBlank()) {
            _igAction = data.ig.toString()
            val parts = parse_url(_igAction)
            if (!(parts["path"]).isBlank()) {
                _actionPath = parts["path"]
            }
            if (!(parts["query"]).isBlank()) {
                parse_str(parts["query"], _actionParams)
            }
        }
        if ( !(data.collapse_key).isBlank() ) {
            _collapseKey = data.collapse_key.toString()
        }
        if ( !(data.i).isBlank() ) {
            _optionalImage = data.i.toString()
        }
        if ( !(data.a).isBlank() ) {
            _optionalAvatarUrl = data.a.toString()
        }
        if ( !(data.sound).isBlank() ) {
            _sound = data.sound.toString()
        }
        if ( !(data.pi).isBlank() ) {
            _pushId = data.pi.toString()
        }
        if ( !(data.c).isBlank() ) {
            _pushCategory = data.c.toString()
        }
        if ( !(data.u).isBlank() ) {
            _intendedRecipientUserId = data.u.toString()
        }
        if ( !(data.s).isBlank() ) {
            _sourceUserId = data.s.toString()
        }
        if ( !(data.igo).isBlank() ) {
            _igActionOverride = data.igo.toString()
        }
        if ( !(data.bc).isBlank() ) {
            _badgeCount = BadgeCount(data.bc.toString())
        }
        if ( !(data.ia).isBlank() ) {
            _inAppActors = data.ia.toString()
        }
    }

    /**
     * Notification constructor.
     *
     * @param string $json
     */
    fun constructor(json: String) {
        _parseJson(json)
    }

    /**
     * @return string
     */
    fun __toString(): String {
        return _json
    }

    /**
     * @return string
     */
    fun getTitle(): String {
        return _title
    }

    /**
     * @return string
     */
    fun getMessage(): String {
        return _message
    }

    /**
     * @return string
     */
    fun getTickerText(): String {
        return _tickerText
    }

    /**
     * @return string
     */
    fun getIgAction(): String {
        return _igAction
    }

    /**
     * @return string
     */
    fun getIgActionOverride(): String {
        return _igActionOverride
    }

    /**
     * @return string
     */
    fun getOptionalImage(): String {
        return _optionalImage
    }

    /**
     * @return string
     */
    fun getOptionalAvatarUrl(): String {
        return _optionalAvatarUrl
    }

    /**
     * @return string
     */
    fun getCollapseKey(): String {
        return _collapseKey
    }

    /**
     * @return string
     */
    fun getSound(): String {
        return _sound
    }

    /**
     * @return string
     */
    fun getPushId(): String {
        return _pushId
    }

    /**
     * @return string
     */
    fun getPushCategory(): String {
        return _pushCategory
    }

    /**
     * @return string
     */
    fun getIntendedRecipientUserId(): String {
        return _intendedRecipientUserId
    }

    /**
     * @return string
     */
    fun getSourceUserId(): String {
        return _sourceUserId
    }

    /**
     * @return BadgeCount
     */
    fun getBadgeCount(): BadgeCount {
        return _badgeCount
    }

    /**
     * @return string
     */
    fun getInAppActors(): String {
        return _inAppActors
    }

    /**
     * @return string
     */
    fun getActionPath(): String {
        return _actionPath
    }

    /**
     * @return array
     */
    fun getActionParams(){
        return _actionParams
    }

    /**
     * @param string $key
     *
     * @return mixed
     */
    fun getActionParam(key: String): Any? {
        return if( !(_actionParams[key]).isBlank() ) _actionParams[key] else null
    }
}
