

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * In.
 *
 * @method mixed getDurationInVideoInSec()
 * @method float[] getPosition()
 * @method Product getProduct()
 * @method mixed getStartTimeInVideoInSec()
 * @method mixed getTimeInVideo()
 * @method User getUser()
 * @method bool isDurationInVideoInSec()
 * @method bool isPosition()
 * @method bool isProduct()
 * @method bool isStartTimeInVideoInSec()
 * @method bool isTimeInVideo()
 * @method bool isUser()
 * @method this setDurationInVideoInSec(mixed $value)
 * @method this setPosition(float[] $value)
 * @method this setProduct(Product $value)
 * @method this setStartTimeInVideoInSec(mixed $value)
 * @method this setTimeInVideo(mixed $value)
 * @method this setUser(User $value)
 * @method this unsetDurationInVideoInSec()
 * @method this unsetPosition()
 * @method this unsetProduct()
 * @method this unsetStartTimeInVideoInSec()
 * @method this unsetTimeInVideo()
 * @method this unsetUser()
 */
data class In (
    val position                   : MutableList<Float>,
    val user                       : User,
    val time_in_video              : String,
    val start_time_in_video_in_sec : String,
    val duration_in_video_in_sec   : String,
    val product                    : Product
){
//    val JSON_PROPERTY_MAP = [
//        "position"                   => "float[]",
//        "user"                       => "User",
//        "time_in_video"              => "",
//        "start_time_in_video_in_sec" => "",
//        "duration_in_video_in_sec"   => "",
//        "product"                    => "Product",
//    ]
}
