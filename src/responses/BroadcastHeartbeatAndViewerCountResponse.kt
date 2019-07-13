

package instagramAPI.responses

import instagramAPI.Response

/**
 * BroadcastHeartbeatAndViewerCountResponse.
 *
 * @method string getBroadcastStatus()
 * @method string[] getCobroadcasterIds()
 * @method int getIsPolicyViolation()
 * @method int getIsTopLiveEligible()
 * @method mixed getMessage()
 * @method int getOffsetToVideoStart()
 * @method string getPolicyViolationReason()
 * @method string getStatus()
 * @method int getTotalUniqueViewerCount()
 * @method int getViewerCount()
 * @method model._Message[] get_Messages()
 * @method bool isBroadcastStatus()
 * @method bool isCobroadcasterIds()
 * @method bool isIsPolicyViolation()
 * @method bool isIsTopLiveEligible()
 * @method bool isMessage()
 * @method bool isOffsetToVideoStart()
 * @method bool isPolicyViolationReason()
 * @method bool isStatus()
 * @method bool isTotalUniqueViewerCount()
 * @method bool isViewerCount()
 * @method bool is_Messages()
 * @method this setBroadcastStatus(string $value)
 * @method this setCobroadcasterIds(string[] $value)
 * @method this setIsPolicyViolation(int $value)
 * @method this setIsTopLiveEligible(int $value)
 * @method this setMessage(mixed $value)
 * @method this setOffsetToVideoStart(int $value)
 * @method this setPolicyViolationReason(string $value)
 * @method this setStatus(string $value)
 * @method this setTotalUniqueViewerCount(int $value)
 * @method this setViewerCount(int $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetBroadcastStatus()
 * @method this unsetCobroadcasterIds()
 * @method this unsetIsPolicyViolation()
 * @method this unsetIsTopLiveEligible()
 * @method this unsetMessage()
 * @method this unsetOffsetToVideoStart()
 * @method this unsetPolicyViolationReason()
 * @method this unsetStatus()
 * @method this unsetTotalUniqueViewerCount()
 * @method this unsetViewerCount()
 * @method this unset_Messages()
 */
class BroadcastHeartbeatAndViewerCountResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        "broadcast_status"          to "string",
        "viewer_count"              to "int",
        "offset_to_video_start"     to "int",
        "total_unique_viewer_count" to "int",
        "is_top_live_eligible"      to "int",
        "cobroadcaster_ids"         to "string[]",
        "is_policy_violation"       to "int",
        "policy_violation_reason"   to "string"
    )
}
