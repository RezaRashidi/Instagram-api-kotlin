

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * FriendshipStatus.
 *
 * @method bool getBlocking()
 * @method bool getFollowedBy()
 * @method bool getFollowing()
 * @method bool getIncomingRequest()
 * @method bool getIsBestie()
 * @method bool getIsBlockingReel()
 * @method bool getIsMutingReel()
 * @method bool getIsPrivate()
 * @method bool getMuting()
 * @method bool getOutgoingRequest()
 * @method bool isBlocking()
 * @method bool isFollowedBy()
 * @method bool isFollowing()
 * @method bool isIncomingRequest()
 * @method bool isIsBestie()
 * @method bool isIsBlockingReel()
 * @method bool isIsMutingReel()
 * @method bool isIsPrivate()
 * @method bool isMuting()
 * @method bool isOutgoingRequest()
 * @method this setBlocking(bool $value)
 * @method this setFollowedBy(bool $value)
 * @method this setFollowing(bool $value)
 * @method this setIncomingRequest(bool $value)
 * @method this setIsBestie(bool $value)
 * @method this setIsBlockingReel(bool $value)
 * @method this setIsMutingReel(bool $value)
 * @method this setIsPrivate(bool $value)
 * @method this setMuting(bool $value)
 * @method this setOutgoingRequest(bool $value)
 * @method this unsetBlocking()
 * @method this unsetFollowedBy()
 * @method this unsetFollowing()
 * @method this unsetIncomingRequest()
 * @method this unsetIsBestie()
 * @method this unsetIsBlockingReel()
 * @method this unsetIsMutingReel()
 * @method this unsetIsPrivate()
 * @method this unsetMuting()
 * @method this unsetOutgoingRequest()
 */
data class FriendshipStatus (
    val following        : Boolean,
    val followed_by      : Boolean,
    val incoming_request : Boolean,
    val outgoing_request : Boolean,
    val is_private       : Boolean,
    val is_blocking_reel : Boolean,
    val is_muting_reel   : Boolean,
    val blocking         : Boolean,
    val muting           : Boolean,
    val is_bestie        : Boolean
){
//    val JSON_PROPERTY_MAP = [
//        "following"        => "bool",
//        "followed_by"      => "bool",
//        "incoming_request" => "bool",
//        "outgoing_request" => "bool",
//        "is_private"       => "bool",
//        "is_blocking_reel" => "bool",
//        "is_muting_reel"   => "bool",
//        "blocking"         => "bool",
//        "muting"           => "bool",
//        "is_bestie"        => "bool",
//    ]
}
