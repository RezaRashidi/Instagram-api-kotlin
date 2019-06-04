

package InstagramAPI.Realtime.Mqtt

class Config
{
    /* MQTT server options */
    val DEFAULT_HOST = 'edge-mqtt.facebook.com'
    val DEFAULT_PORT = 443

    /* MQTT protocol options */
    val MQTT_KEEPALIVE = 900
    val MQTT_VERSION = 3

    /* MQTT client options */
    val NETWORK_TYPE_WIFI = 1
    val CLIENT_TYPE = 'cookie_auth'
    val PUBLISH_FORMAT = 'jz'
    val CONNECTION_TIMEOUT = 5
}
