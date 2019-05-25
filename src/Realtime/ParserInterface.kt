<?php

package InstagramAPI.Realtime;

interface ParserInterface
{
    /**
     * Parse incoming MQTT message.
     *
     * @param string $topic   MQTT topic.
     * @param string $payload MQTT payload.
     *
     * @return Message[]
     */
    public fun parseMessage(
        $topic,
        $payload
    );
}
