

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.Item
import instagramAPI.responses.model.User

/**
 * ReelMediaViewerResponse.
 *
 * @method mixed getMessage()
 * @method string getNextMaxId()
 * @method mixed getScreenshotterUserIds()
 * @method string getStatus()
 * @method int getTotalScreenshotCount()
 * @method int getTotalViewerCount()
 * @method model.Item getUpdatedMedia()
 * @method int getUserCount()
 * @method model.User[] getUsers()
 * @method model._Message[] get_Messages()
 * @method bool isMessage()
 * @method bool isNextMaxId()
 * @method bool isScreenshotterUserIds()
 * @method bool isStatus()
 * @method bool isTotalScreenshotCount()
 * @method bool isTotalViewerCount()
 * @method bool isUpdatedMedia()
 * @method bool isUserCount()
 * @method bool isUsers()
 * @method bool is_Messages()
 * @method this setMessage(mixed $value)
 * @method this setNextMaxId(string $value)
 * @method this setScreenshotterUserIds(mixed $value)
 * @method this setStatus(string $value)
 * @method this setTotalScreenshotCount(int $value)
 * @method this setTotalViewerCount(int $value)
 * @method this setUpdatedMedia(model.Item $value)
 * @method this setUserCount(int $value)
 * @method this setUsers(model.User[] $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetMessage()
 * @method this unsetNextMaxId()
 * @method this unsetScreenshotterUserIds()
 * @method this unsetStatus()
 * @method this unsetTotalScreenshotCount()
 * @method this unsetTotalViewerCount()
 * @method this unsetUpdatedMedia()
 * @method this unsetUserCount()
 * @method this unsetUsers()
 * @method this unset_Messages()
 */
data class ReelMediaViewerResponse (
    val users                     : MutableList<User>,
    val next_max_id               : String,
    val user_count                : Int,
    val total_viewer_count        : Int,
    val screenshotter_user_ids    : String,
    val total_screenshot_count    : Int,
    val updated_media             : Item
){
//    val JSON_PROPERTY_MAP = [
//        "users"                     => "model.User[]",
//        "next_max_id"               => "string",
//        "user_count"                => "int",
//        "total_viewer_count"        => "int",
//        "screenshotter_user_ids"    => "",
//        "total_screenshot_count"    => "int",
//        "updated_media"             => "model.Item",
//    ]
}
