<?php

package InstagramAPI.Exception;

/**
 * Means that you have become throttled by Instagram's API server
 * becaimport of too many requests. You must slow yourself down!
 */
class ThrottledException : RequestException
{
}
