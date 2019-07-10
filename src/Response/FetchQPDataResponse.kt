

package InstagramAPI.Response

import InstagramAPI.Response

/**
 * FetchQPDataResponse.
 *
 * @method int getClientCacheTtlInSec()
 * @method mixed getErrorMsg()
 * @method Model.QPExtraInfo[] getExtraInfo()
 * @method mixed getMessage()
 * @method Model.QPData[] getQpData()
 * @method string getRequestStatus()
 * @method string getStatus()
 * @method Model._Message[] get_Messages()
 * @method bool isClientCacheTtlInSec()
 * @method bool isErrorMsg()
 * @method bool isExtraInfo()
 * @method bool isMessage()
 * @method bool isQpData()
 * @method bool isRequestStatus()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setClientCacheTtlInSec(int $value)
 * @method this setErrorMsg(mixed $value)
 * @method this setExtraInfo(Model.QPExtraInfo[] $value)
 * @method this setMessage(mixed $value)
 * @method this setQpData(Model.QPData[] $value)
 * @method this setRequestStatus(string $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(Model._Message[] $value)
 * @method this unsetClientCacheTtlInSec()
 * @method this unsetErrorMsg()
 * @method this unsetExtraInfo()
 * @method this unsetMessage()
 * @method this unsetQpData()
 * @method this unsetRequestStatus()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class FetchQPDataResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "request_status"          to "string",
        "extra_info"              to "Model.QPExtraInfo[]",
        "qp_data"                 to "Model.QPData[]",
        "client_cache_ttl_in_sec" to "int",
        "error_msg"               to ""
    )
}
