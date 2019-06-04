

/*
 * This file is part of net-mqtt.
 *
 * Copyright (c) 2015 Sebastian Mößler code@binsoul.de
 *
 * This source file is subject to the MIT license.
 */

package InstagramAPI.Realtime.Mqtt

import BinSoul.Net.Mqtt.Exception.UnknownPacketTypeException
import BinSoul.Net.Mqtt.Packet
import BinSoul.Net.Mqtt.Packet.ConnectRequestPacket
import BinSoul.Net.Mqtt.Packet.ConnectResponsePacket
import BinSoul.Net.Mqtt.Packet.DisconnectRequestPacket
import BinSoul.Net.Mqtt.Packet.PingRequestPacket
import BinSoul.Net.Mqtt.Packet.PingResponsePacket
import BinSoul.Net.Mqtt.Packet.PublishCompletePacket
import BinSoul.Net.Mqtt.Packet.PublishReceivedPacket
import BinSoul.Net.Mqtt.Packet.PublishReleasePacket
import BinSoul.Net.Mqtt.Packet.PublishRequestPacket
import BinSoul.Net.Mqtt.Packet.SubscribeRequestPacket
import BinSoul.Net.Mqtt.Packet.SubscribeResponsePacket
import BinSoul.Net.Mqtt.Packet.UnsubscribeRequestPacket
import BinSoul.Net.Mqtt.Packet.UnsubscribeResponsePacket
import Fbns.Client.Common.PublishAckPacket

/**
 * Builds instances of the {@see Packet} interface.
 */
class PacketFactory
{
    /**
     * Map of packet types to packet classes.
     *
     * @var string[]
     */
    private static $_mapping = [
        Packet::TYPE_CONNECT     => ConnectRequestPacket::class,
        Packet::TYPE_CONNACK     => ConnectResponsePacket::class,
        Packet::TYPE_PUBLISH     => PublishRequestPacket::class,
        Packet::TYPE_PUBACK      => PublishAckPacket::class,
        Packet::TYPE_PUBREC      => PublishReceivedPacket::class,
        Packet::TYPE_PUBREL      => PublishReleasePacket::class,
        Packet::TYPE_PUBCOMP     => PublishCompletePacket::class,
        Packet::TYPE_SUBSCRIBE   => SubscribeRequestPacket::class,
        Packet::TYPE_SUBACK      => SubscribeResponsePacket::class,
        Packet::TYPE_UNSUBSCRIBE => UnsubscribeRequestPacket::class,
        Packet::TYPE_UNSUBACK    => UnsubscribeResponsePacket::class,
        Packet::TYPE_PINGREQ     => PingRequestPacket::class,
        Packet::TYPE_PINGRESP    => PingResponsePacket::class,
        Packet::TYPE_DISCONNECT  => DisconnectRequestPacket::class,
    ]

    /**
     * Builds a packet object for the given type.
     *
     * @param int $type
     *
     * @throws UnknownPacketTypeException
     *
     * @return Packet
     */
    public fun build(
        $type)
    {
        if (!isset(self::$_mapping[$type])) {
            throw UnknownPacketTypeException(sprintf("Unknown packet type %d.", $type))
        }

        $class = self::$_mapping[$type]

        return $class()
    }
}
