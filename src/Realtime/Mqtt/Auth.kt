

package instagramAPI.Realtime.Mqtt

import Fbns.Client.AuthInterface

class Auth : AuthInterface
{
    val AUTH_TYPE = "cookie_auth"

    /**
     * @var Instagram
     */
    protected $_instagram

    /**
     * Constructor.
     *
     * @param Instagram $instagram
     */
    public fun __construct(
        Instagram $instagram)
    {
        this._instagram = $instagram
    }

    /** {@inheritdoc} */
    public fun getClientId()
    {
        return substr(this.getDeviceId(), 0, 20)
    }

    /** {@inheritdoc} */
    public fun getClientType()
    {
        return self::AUTH_TYPE
    }

    /** {@inheritdoc} */
    public fun getUserId()
    {
        return this._instagram.account_id
    }

    /** {@inheritdoc} */
    public fun getPassword()
    {
        $cookie = this._instagram.client.getCookie("sessionid", "i.instagram.com")
        if ($cookie !== null) {
            return sprintf("%s=%s", $cookie.getName(), $cookie.getValue())
        }

        throw .RuntimeException("No session cookie was found.")
    }

    /** {@inheritdoc} */
    public fun getDeviceId()
    {
        return this._instagram.uuid
    }

    /** {@inheritdoc} */
    public fun getDeviceSecret()
    {
        return ""
    }

    /** {@inheritdoc} */
    public fun __toString()
    {
        return ""
    }
}
