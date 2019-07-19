

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.QPData
import instagramAPI.responses.model.QPExtraInfo

/**
 * FetchQPDataResponse.
 *
 * @method int getClientCacheTtlInSec()
 * @method mixed getErrorMsg()
 * @method model.QPExtraInfo[] getExtraInfo()
 * @method mixed getMessage()
 * @method model.QPData[] getQpData()
 * @method string getRequestStatus()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
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
 * @method this setExtraInfo(model.QPExtraInfo[] $value)
 * @method this setMessage(mixed $value)
 * @method this setQpData(model.QPData[] $value)
 * @method this setRequestStatus(string $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetClientCacheTtlInSec()
 * @method this unsetErrorMsg()
 * @method this unsetExtraInfo()
 * @method this unsetMessage()
 * @method this unsetQpData()
 * @method this unsetRequestStatus()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class FetchQPDataResponse (
    val request_status          : String,
    val extra_info              : MutableList<QPExtraInfo>,
    val qp_data                 : MutableList<QPData>,
    val client_cache_ttl_in_sec : Int,
    val error_msg               : String
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "request_status"          to "string",
//        "extra_info"              to "model.QPExtraInfo[]",
//        "qp_data"                 to "model.QPData[]",
//        "client_cache_ttl_in_sec" to "int",
//        "error_msg"               to ""
//    )
}
