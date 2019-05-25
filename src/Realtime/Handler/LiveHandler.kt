<?php

package InstagramAPI.Realtime.Handler;

import InstagramAPI.Client as HttpClient;
import InstagramAPI.Realtime.HandlerInterface;
import InstagramAPI.Realtime.Message;
import InstagramAPI.Realtime.Payload.Event.PatchEvent;
import InstagramAPI.Realtime.Payload.Event.PatchEventOp;
import InstagramAPI.Realtime.Payload.LiveBroadcast;
import InstagramAPI.Realtime.Payload.RealtimeEvent;

class LiveHandler : AbstractHandler : HandlerInterface
{
    val MODULE = 'live';

    /** {@inheritdoc} */
    public fun handleMessage(
        Message $message)
    {
        $data = $message.getData();

        if (isset($data['event'])) {
            this._processEvent($data);
        } else {
            throw new HandlerException('Invalid message (event type is missing).');
        }
    }

    /**
     * Process incoming event.
     *
     * @param array $message
     *
     * @throws HandlerException
     */
    protected fun _processEvent(
        array $message)
    {
        if ($message['event'] === RealtimeEvent::PATCH) {
            $event = new PatchEvent($message);
            foreach ($event.getData() as $op) {
                this._handlePatchOp($op);
            }
        } else {
            throw new HandlerException(sprintf('Unknown event type "%s".', $message['event']));
        }
    }

    /**
     * Handler for live broadcast creation/removal.
     *
     * @param PatchEventOp $op
     *
     * @throws HandlerException
     */
    protected fun _handlePatchOp(
        PatchEventOp $op)
    {
        switch ($op.getOp()) {
            case PatchEventOp::ADD:
                $event = 'live-started';
                break;
            case PatchEventOp::REMOVE:
                $event = 'live-stopped';
                break;
            default:
                throw new HandlerException(sprintf('Unsupported live broadcast op: "%s".', $op.getOp()));
        }
        if (!this._hasListeners($event)) {
            return;
        }

        $json = HttpClient::api_body_decode($op.getValue());
        if (!is_array($json)) {
            throw new HandlerException(sprintf('Failed to decode live broadcast JSON: %s.', json_last_error_msg()));
        }

        this._target.emit($event, [new LiveBroadcast($json)]);
    }
}
