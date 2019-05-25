<?php

package InstagramAPI.Realtime.Payload;

import InstagramAPI.AutoPropertyMapper;

/**
 * IrisSubscribeAck.
 *
 * @method string getErrorMessage()
 * @method int getErrorType()
 * @method int getSeqId()
 * @method bool getSucceeded()
 * @method bool isErrorMessage()
 * @method bool isErrorType()
 * @method bool isSeqId()
 * @method bool isSucceeded()
 * @method this setErrorMessage(string $value)
 * @method this setErrorType(int $value)
 * @method this setSeqId(int $value)
 * @method this setSucceeded(bool $value)
 * @method this unsetErrorMessage()
 * @method this unsetErrorType()
 * @method this unsetSeqId()
 * @method this unsetSucceeded()
 */
class IrisSubscribeAck : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        'seq_id'        => 'int',
        'succeeded'     => 'bool',
        'error_type'    => 'int',
        'error_message' => 'string',
    ];
}
