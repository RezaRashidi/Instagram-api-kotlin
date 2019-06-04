

package InstagramAPI.Realtime.Mqtt

class Topics
{
    val PUBSUB = "/pubsub"
    val PUBSUB_ID = "88"

    val SEND_MESSAGE = "/ig_send_message"
    val SEND_MESSAGE_ID = "132"

    val SEND_MESSAGE_RESPONSE = "/ig_send_message_response"
    val SEND_MESSAGE_RESPONSE_ID = "133"

    val IRIS_SUB = "/ig_sub_iris"
    val IRIS_SUB_ID = "134"

    val IRIS_SUB_RESPONSE = "/ig_sub_iris_response"
    val IRIS_SUB_RESPONSE_ID = "135"

    val MESSAGE_SYNC = "/ig_message_sync"
    val MESSAGE_SYNC_ID = "146"

    val REALTIME_SUB = "/ig_realtime_sub"
    val REALTIME_SUB_ID = "149"

    val GRAPHQL = "/graphql"
    val GRAPHQL_ID = "9"

    val REGION_HINT = "/t_region_hint"
    val REGION_HINT_ID = "150"

    val ID_TO_TOPIC_MAP = [
        self::PUBSUB_ID                => self::PUBSUB,
        self::SEND_MESSAGE_ID          => self::SEND_MESSAGE,
        self::SEND_MESSAGE_RESPONSE_ID => self::SEND_MESSAGE_RESPONSE,
        self::IRIS_SUB_ID              => self::IRIS_SUB,
        self::IRIS_SUB_RESPONSE_ID     => self::IRIS_SUB_RESPONSE,
        self::MESSAGE_SYNC_ID          => self::MESSAGE_SYNC,
        self::REALTIME_SUB_ID          => self::REALTIME_SUB,
        self::GRAPHQL_ID               => self::GRAPHQL,
        self::REGION_HINT_ID           => self::REGION_HINT,
    ]

    val TOPIC_TO_ID_MAP = [
        self::PUBSUB                => self::PUBSUB_ID,
        self::SEND_MESSAGE          => self::SEND_MESSAGE_ID,
        self::SEND_MESSAGE_RESPONSE => self::SEND_MESSAGE_RESPONSE_ID,
        self::IRIS_SUB              => self::IRIS_SUB_ID,
        self::IRIS_SUB_RESPONSE     => self::IRIS_SUB_RESPONSE_ID,
        self::MESSAGE_SYNC          => self::MESSAGE_SYNC_ID,
        self::REALTIME_SUB          => self::REALTIME_SUB_ID,
        self::GRAPHQL               => self::GRAPHQL_ID,
        self::REGION_HINT           => self::REGION_HINT_ID,
    ]
}
