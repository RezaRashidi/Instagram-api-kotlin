

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Counts.
 *
 * @method mixed getCampaignNotification()
 * @method mixed getCommentLikes()
 * @method mixed getComments()
 * @method mixed getLikes()
 * @method mixed getPhotosOfYou()
 * @method mixed getRelationships()
 * @method mixed getRequests()
 * @method mixed getUsertags()
 * @method bool isCampaignNotification()
 * @method bool isCommentLikes()
 * @method bool isComments()
 * @method bool isLikes()
 * @method bool isPhotosOfYou()
 * @method bool isRelationships()
 * @method bool isRequests()
 * @method bool isUsertags()
 * @method this setCampaignNotification(mixed $value)
 * @method this setCommentLikes(mixed $value)
 * @method this setComments(mixed $value)
 * @method this setLikes(mixed $value)
 * @method this setPhotosOfYou(mixed $value)
 * @method this setRelationships(mixed $value)
 * @method this setRequests(mixed $value)
 * @method this setUsertags(mixed $value)
 * @method this unsetCampaignNotification()
 * @method this unsetCommentLikes()
 * @method this unsetComments()
 * @method this unsetLikes()
 * @method this unsetPhotosOfYou()
 * @method this unsetRelationships()
 * @method this unsetRequests()
 * @method this unsetUsertags()
 */
data class Counts (
    val relationships         : String,
    val requests              : String,
    val photos_of_you         : String,
    val usertags              : String,
    val comments              : String,
    val likes                 : String,
    val comment_likes         : String,
    val campaign_notification : String
){
//    val JSON_PROPERTY_MAP = [
//        "relationships"         => "",
//        "requests"              => "",
//        "photos_of_you"         => "",
//        "usertags"              => "",
//        "comments"              => "",
//        "likes"                 => "",
//        "comment_likes"         => "",
//        "campaign_notification" => "",
//    ]
}
