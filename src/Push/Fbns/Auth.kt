

package InstagramAPI.Push.Fbns

import Fbns.Client.Auth.DeviceAuth
import Fbns.Client.AuthInterface
import InstagramAPI.Instagram

class Auth : AuthInterface{
    /**
     * @var Instagram
     */
    protected var _instagram: Instagram

    /**
     * @var DeviceAuth
     */
    protected var _deviceAuth: DeviceAuth

    /**
     * Auth constructor.
     *
     * @param Instagram $instagram
     */
    constructor(instagram: Instagram){
        _instagram = instagram
        _deviceAuth = _instagram.settings.getFbnsAuth()
    }

    /** {@inheritdoc} */
    fun getClientId(){
        return _deviceAuth.getClientId()
    }

    /** {@inheritdoc} */
    fun getClientType(){
        return _deviceAuth.getClientType()
    }

    /** {@inheritdoc} */
    fun getUserId()    {
        return _deviceAuth.getUserId()
    }

    /** {@inheritdoc} */
    fun getPassword()    {
        return _deviceAuth.getPassword()
    }

    /** {@inheritdoc} */
    fun getDeviceId()    {
        return _deviceAuth.getDeviceId()
    }

    /** {@inheritdoc} */
    fun getDeviceSecret()    {
        return _deviceAuth.getDeviceSecret()
    }

    /** {@inheritdoc} */
    fun __toString(){
        return _deviceAuth.__toString()
    }

    /**
     * Update auth data.
     *
     * @param string $auth
     *
     * @throws  IllegalArgumentException
     */
    fun update(auth: String){
        /* @var DeviceAuth $auth */
        _deviceAuth.read(auth)
        _instagram.settings.setFbnsAuth(_deviceAuth)
    }
}
