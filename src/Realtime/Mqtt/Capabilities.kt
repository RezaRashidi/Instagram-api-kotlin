<?php

package InstagramAPI.Realtime.Mqtt;

class Capabilities
{
    val ACKNOWLEDGED_DELIVERY = 0;
    val PROCESSING_LASTACTIVE_PRESENCEINFO = 1;
    val EXACT_KEEPALIVE = 2;
    val REQUIRES_JSON_UNICODE_ESCAPES = 3;
    val DELTA_SENT_MESSAGE_ENABLED = 4;
    val USE_ENUM_TOPIC = 5;
    val SUPPRESS_GETDIFF_IN_CONNECT = 6;
    val USE_THRIFT_FOR_INBOX = 7;
    val USE_SEND_PINGRESP = 8;
    val REQUIRE_REPLAY_PROTECTION = 9;
    val DATA_SAVING_MODE = 10;
    val TYPING_OFF_WHEN_SENDING_MESSAGE = 11;

    val DEFAULT_SET = 0
        | 1 << self::ACKNOWLEDGED_DELIVERY
        | 1 << self::PROCESSING_LASTACTIVE_PRESENCEINFO
        | 1 << self::EXACT_KEEPALIVE
        | 0 << self::REQUIRES_JSON_UNICODE_ESCAPES
        | 1 << self::DELTA_SENT_MESSAGE_ENABLED
        | 1 << self::USE_ENUM_TOPIC
        | 0 << self::SUPPRESS_GETDIFF_IN_CONNECT
        | 1 << self::USE_THRIFT_FOR_INBOX
        | 1 << self::USE_SEND_PINGRESP
        | 0 << self::REQUIRE_REPLAY_PROTECTION
        | 0 << self::DATA_SAVING_MODE
        | 0 << self::TYPING_OFF_WHEN_SENDING_MESSAGE;
}
