

package instagramAPI.responses

import instagramAPI.Response

/**
 * ReportExploreMediaResponse.
 *
 * @method mixed getExploreReportStatus()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isExploreReportStatus()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setExploreReportStatus(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetExploreReportStatus()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class ReportExploreMediaResponse (
    val explore_report_status : String
){
//    val JSON_PROPERTY_MAP = [
//        "explore_report_status" => "",
//    ]
}
