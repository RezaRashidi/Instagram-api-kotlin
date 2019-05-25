<?php

package InstagramAPI.Push.Payload;

import Fbns.Client.Json;

class BadgeCount
{
    /**
     * @var string
     */
    protected $_json;

    /**
     * @var int
     */
    protected $_direct;
    /**
     * @var int
     */
    protected $_ds;
    /**
     * @var int
     */
    protected $_activities;

    /**
     * @param string $json
     */
    protected fun _parseJson(
        $json)
    {
        $data = Json::decode($json);
        this._json = $json;

        if (isset($data.di)) {
            this._direct = (int) $data.di;
        }
        if (isset($data.ds)) {
            this._ds = (int) $data.ds;
        }
        if (isset($data.ac)) {
            this._activities = (int) $data.ac;
        }
    }

    /**
     * Notification constructor.
     *
     * @param string $json
     */
    public fun __construct(
        $json)
    {
        this._parseJson($json);
    }

    /**
     * @return string
     */
    public fun __toString()
    {
        return this._json;
    }

    /**
     * @return int
     */
    public fun getDirect()
    {
        return this._direct;
    }

    /**
     * @return int
     */
    public fun getDs()
    {
        return this._ds;
    }

    /**
     * @return int
     */
    public fun getActivities()
    {
        return this._activities;
    }
}
