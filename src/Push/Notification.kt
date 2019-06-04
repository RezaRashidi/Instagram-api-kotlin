

package InstagramAPI.Push

import Fbns.Client.Json
import InstagramAPI.Push.Payload.BadgeCount

class Notification
{
    /**
     * @var string
     */
    protected $_json

    /**
     * @var string
     */
    protected $_title
    /**
     * @var string
     */
    protected $_message
    /**
     * @var string
     */
    protected $_tickerText
    /**
     * @var string
     */
    protected $_igAction
    /**
     * @var string
     */
    protected $_igActionOverride
    /**
     * @var string
     */
    protected $_optionalImage
    /**
     * @var string
     */
    protected $_optionalAvatarUrl
    /**
     * @var string
     */
    protected $_collapseKey
    /**
     * @var string
     */
    protected $_sound
    /**
     * @var string
     */
    protected $_pushId
    /**
     * @var string
     */
    protected $_pushCategory
    /**
     * @var string
     */
    protected $_intendedRecipientUserId
    /**
     * @var string
     */
    protected $_sourceUserId
    /**
     * @var BadgeCount
     */
    protected $_badgeCount
    /**
     * @var string
     */
    protected $_inAppActors

    /**
     * @var string
     */
    protected $_actionPath
    /**
     * @var array
     */
    protected $_actionParams

    /**
     * @param string $json
     */
    protected fun _parseJson(
        $json)
    {
        $data = Json::decode($json)

        this._json = $json

        if (isset($data.t)) {
            this._title = (string) $data.t
        }
        if (isset($data.m)) {
            this._message = (string) $data.m
        }
        if (isset($data.tt)) {
            this._tickerText = (string) $data.tt
        }
        this._actionPath = ""
        this._actionParams = []
        if (isset($data.ig)) {
            this._igAction = (string) $data.ig
            $parts = parse_url(this._igAction)
            if (isset($parts["path"])) {
                this._actionPath = $parts["path"]
            }
            if (isset($parts["query"])) {
                parse_str($parts["query"], this._actionParams)
            }
        }
        if (isset($data.collapse_key)) {
            this._collapseKey = (string) $data.collapse_key
        }
        if (isset($data.i)) {
            this._optionalImage = (string) $data.i
        }
        if (isset($data.a)) {
            this._optionalAvatarUrl = (string) $data.a
        }
        if (isset($data.sound)) {
            this._sound = (string) $data.sound
        }
        if (isset($data.pi)) {
            this._pushId = (string) $data.pi
        }
        if (isset($data.c)) {
            this._pushCategory = (string) $data.c
        }
        if (isset($data.u)) {
            this._intendedRecipientUserId = (string) $data.u
        }
        if (isset($data.s)) {
            this._sourceUserId = (string) $data.s
        }
        if (isset($data.igo)) {
            this._igActionOverride = (string) $data.igo
        }
        if (isset($data.bc)) {
            this._badgeCount = BadgeCount((string) $data.bc)
        }
        if (isset($data.ia)) {
            this._inAppActors = (string) $data.ia
        }
    }

    /**
     * Notification constructor.
     *
     * @param string $json
     */
    public fun __construct(
        $json)
    {
        this._parseJson($json)
    }

    /**
     * @return string
     */
    public fun __toString()
    {
        return this._json
    }

    /**
     * @return string
     */
    public fun getTitle()
    {
        return this._title
    }

    /**
     * @return string
     */
    public fun getMessage()
    {
        return this._message
    }

    /**
     * @return string
     */
    public fun getTickerText()
    {
        return this._tickerText
    }

    /**
     * @return string
     */
    public fun getIgAction()
    {
        return this._igAction
    }

    /**
     * @return string
     */
    public fun getIgActionOverride()
    {
        return this._igActionOverride
    }

    /**
     * @return string
     */
    public fun getOptionalImage()
    {
        return this._optionalImage
    }

    /**
     * @return string
     */
    public fun getOptionalAvatarUrl()
    {
        return this._optionalAvatarUrl
    }

    /**
     * @return string
     */
    public fun getCollapseKey()
    {
        return this._collapseKey
    }

    /**
     * @return string
     */
    public fun getSound()
    {
        return this._sound
    }

    /**
     * @return string
     */
    public fun getPushId()
    {
        return this._pushId
    }

    /**
     * @return string
     */
    public fun getPushCategory()
    {
        return this._pushCategory
    }

    /**
     * @return string
     */
    public fun getIntendedRecipientUserId()
    {
        return this._intendedRecipientUserId
    }

    /**
     * @return string
     */
    public fun getSourceUserId()
    {
        return this._sourceUserId
    }

    /**
     * @return BadgeCount
     */
    public fun getBadgeCount()
    {
        return this._badgeCount
    }

    /**
     * @return string
     */
    public fun getInAppActors()
    {
        return this._inAppActors
    }

    /**
     * @return string
     */
    public fun getActionPath()
    {
        return this._actionPath
    }

    /**
     * @return array
     */
    public fun getActionParams()
    {
        return this._actionParams
    }

    /**
     * @param string $key
     *
     * @return mixed
     */
    public fun getActionParam(
        $key)
    {
        return isset(this._actionParams[$key]) ? this._actionParams[$key] : null
    }
}
