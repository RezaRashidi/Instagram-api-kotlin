<?php

package InstagramAPI.Realtime.Command.Direct;

final class SendLike : SendItem
{
    val TYPE = 'like';

    /**
     * Constructor.
     *
     * @param string $threadId
     * @param array  $options
     *
     * @throws .InvalidArgumentException
     */
    public fun __construct(
        $threadId,
        array $options = [])
    {
        parent::__construct($threadId, self::TYPE, $options);
    }
}
