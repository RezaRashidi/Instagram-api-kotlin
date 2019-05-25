

/*
 * This file is part of net-mqtt.
 *
 * Copyright (c) 2015 Sebastian Mößler code@binsoul.de
 *
 * This source file is subject to the MIT license.
 */

package InstagramAPI.Realtime.Mqtt

import BinSoul.Net.Mqtt.Exception.EndOfStreamException
import BinSoul.Net.Mqtt.Exception.MalformedPacketException
import BinSoul.Net.Mqtt.Exception.UnknownPacketTypeException
import BinSoul.Net.Mqtt.Packet
import BinSoul.Net.Mqtt.PacketStream
import BinSoul.Net.Mqtt.StreamParser as BaseStreamParser

/**
 * Provides methods to parse a stream of bytes into packets.
 */
class StreamParser : BaseStreamParser
{
    /** @var PacketStream */
    private $_buffer
    /** @var PacketFactory */
    private $_factory
    /** @var callable */
    private $_errorCallback

    /**
     * Constructs an instance of this class.
     */
    public fun __construct()
    {
        parent::__construct()
        this._buffer = PacketStream()
        this._factory = PacketFactory()
    }

    /**
     * Registers an error callback.
     *
     * @param callable $callback
     */
    public fun onError(
        $callback)
    {
        this._errorCallback = $callback
    }

    /**
     * Appends the given data to the internal buffer and parses it.
     *
     * @param string $data
     *
     * @return Packet[]
     */
    public fun push(
        $data)
    {
        this._buffer.write($data)

        $result = []
        while (this._buffer.getRemainingBytes() > 0) {
            $type = this._buffer.readByte() >> 4

            try {
                $packet = this._factory.build($type)
            } catch (UnknownPacketTypeException $e) {
                this._handleError($e)
                continue
            }

            this._buffer.seek(-1)
            $position = this._buffer.getPosition()

            try {
                $packet.read(this._buffer)
                $result[] = $packet
                this._buffer.cut()
            } catch (EndOfStreamException $e) {
                this._buffer.setPosition($position)
                break
            } catch (MalformedPacketException $e) {
                this._handleError($e)
            }
        }

        return $result
    }

    /**
     * Executes the registered error callback.
     *
     * @param .Throwable $exception
     */
    private fun _handleError(
        $exception)
    {
        if (this._errorCallback !== null) {
            $callback = this._errorCallback
            $callback($exception)
        }
    }
}
