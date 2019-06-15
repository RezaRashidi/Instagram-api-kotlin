

package InstagramAPI.Push.Fbns

import Fbns.Client.Auth.DeviceAuth
import Fbns.Client.AuthInterface
import InstagramAPI.Instagram

class Auth : AuthInterface
{
    /**
     * @var Instagram
     */
    protected $_instagram

    /**
     * @var DeviceAuth
     */
    protected $_deviceAuth

    /**
     * Auth constructor.
     *
     * @param Instagram $instagram
     */
    public fun __construct(
        Instagram $instagram)
    {
        this._instagram = $instagram
        this._deviceAuth = this._instagram.settings.getFbnsAuth()
    }

    /** {@inheritdoc} */
    public fun getClientId()
    {
        return this._deviceAuth.getClientId()
    }

    /** {@inheritdoc} */
    public fun getClientType()
    {
        return this._deviceAuth.getClientType()
    }

    /** {@inheritdoc} */
    public fun getUserId()
    {
        return this._deviceAuth.getUserId()
    }

    /** {@inheritdoc} */
    public fun getPassword()
    {
        return this._deviceAuth.getPassword()
    }

    /** {@inheritdoc} */
    public fun getDeviceId()
    {
        return this._deviceAuth.getDeviceId()
    }

    /** {@inheritdoc} */
    public fun getDeviceSecret()
    {
        return this._deviceAuth.getDeviceSecret()
    }

    /** {@inheritdoc} */
    public fun __toString()
    {
        return this._deviceAuth.__toString()
    }

    /**
     * Update auth data.
     *
     * @param string $auth
     *
     * @throws  IllegalArgumentException
     */
    public fun update(
        $auth)
    {
        /* @var DeviceAuth $auth */
        this._deviceAuth.read($auth)
        this._instagram.settings.setFbnsAuth(this._deviceAuth)
    }
}
