

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * ShadowInstagramUser.
 *
 * @method BusinessManager getBusinessManager()
 * @method mixed getError()
 * @method int getFollowersCount()
 * @method string getId()
 * @method string getInstagramUserId()
 * @method Image getProfilePicture()
 * @method string getUsername()
 * @method bool isBusinessManager()
 * @method bool isError()
 * @method bool isFollowersCount()
 * @method bool isId()
 * @method bool isInstagramUserId()
 * @method bool isProfilePicture()
 * @method bool isUsername()
 * @method this setBusinessManager(BusinessManager $value)
 * @method this setError(mixed $value)
 * @method this setFollowersCount(int $value)
 * @method this setId(string $value)
 * @method this setInstagramUserId(string $value)
 * @method this setProfilePicture(Image $value)
 * @method this setUsername(string $value)
 * @method this unsetBusinessManager()
 * @method this unsetError()
 * @method this unsetFollowersCount()
 * @method this unsetId()
 * @method this unsetInstagramUserId()
 * @method this unsetProfilePicture()
 * @method this unsetUsername()
 */
data class ShadowInstagramUser (
    val id                : String,
    val instagram_user_id : String,
    val followers_count   : Int,
    val username          : String,
    val profile_picture   : Image,
    val business_manager  : BusinessManager,
    val error             : String
){
//    val JSON_PROPERTY_MAP = [
//        "id"                => "string",
//        "instagram_user_id" => "string",
//        "followers_count"   => "int",
//        "username"          => "string",
//        "profile_picture"   => "Image",
//        "business_manager"  => "BusinessManager",
//        "error"             => "",
//    ]
}
