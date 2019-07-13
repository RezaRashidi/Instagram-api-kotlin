

package instagramAPI.requests

import instagramAPI.Response

/**
 * funs for managing your push notifications.
 */
class Push(instagram:Instagram) : RequestCollection(instagram)
{
    /**
     * Register to the MQTT or GCM push server.
     *
     * @param string pushChannel The channel you want to register, it can be mqtt or gcm.
     * @param string token       The token used to register to the push channel.
     *
     * @throws  IllegalArgumentException
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.PushRegisterResponse
     */
    fun register(
        pushChannel,
        token)
    {
        // Make sure we only allow these for push channels.
        if (pushChannel != "mqtt" && pushChannel != "gcm") {
            throw  IllegalArgumentException(sprintf("Bad push channel "%s".", pushChannel))
        }

        request = this.ig.request("push/register/")
            .setSignedPost(false)
            .addPost("device_type", pushChannel === "mqtt" ? "android_mqtt" : "android_gcm")
            .addPost("is_main_push_channel", pushChannel === "mqtt")
            .addPost("phone_id", this.ig.phone_id)
            .addPost("device_token", token)
            .addPost("_csrftoken", this.ig.client.getToken())
            .addPost("guid", this.ig.uuid)
            .addPost("_uuid", this.ig.uuid)
            .addPost("users", this.ig.account_id)

        return request.getResponse(Response.PushRegisterResponse())
    }

    /**
     * Get push preferences.
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.PushPreferencesResponse
     */
    fun getPreferences()
    {
        return this.ig.request("push/all_preferences/")
            .getResponse(Response.PushPreferencesResponse())
    }

    /**
     * Set push preferences.
     *
     * @param array preferences Described in "extradocs/Push_setPreferences.txt".
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.PushPreferencesResponse
     */
    fun setPreferences(
        array preferences)
    {
        request = this.ig.request("push/preferences/")
        foreach (preferences as key => value) {
            request.addPost(key, value)
        }

        return request.getResponse(Response.PushPreferencesResponse())
    }
}
