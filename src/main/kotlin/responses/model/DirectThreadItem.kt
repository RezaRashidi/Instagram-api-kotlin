

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * DirectThreadItem.
 *
 * @method ActionLog getActionLog()
 * @method AnimatedMedia getAnimatedMedia()
 * @method string getClientContext()
 * @method MediaShare getDirectMediaShare()
 * @method DirectExpiringSummary getExpiringMediaActionSummary()
 * @method FelixShare getFelixShare()
 * @method mixed getHideInThread()
 * @method string getItemId()
 * @method mixed getItemType()
 * @method mixed getLike()
 * @method DirectLink getLink()
 * @method LiveVideoShare getLiveVideoShare()
 * @method LiveViewerInvite getLiveViewerInvite()
 * @method Location getLocation()
 * @method DirectThreadItemMedia getMedia()
 * @method Item getMediaShare()
 * @method Placeholder getPlaceholder()
 * @method Item[] getPreviewMedias()
 * @method ProductShare getProductShare()
 * @method User getProfile()
 * @method Item getRavenMedia()
 * @method DirectReactions getReactions()
 * @method ReelShare getReelShare()
 * @method string[] getSeenUserIds()
 * @method StoryShare getStoryShare()
 * @method string getText()
 * @method mixed getTimestamp()
 * @method string getUserId()
 * @method VideoCallEvent getVideoCallEvent()
 * @method bool isActionLog()
 * @method bool isAnimatedMedia()
 * @method bool isClientContext()
 * @method bool isDirectMediaShare()
 * @method bool isExpiringMediaActionSummary()
 * @method bool isFelixShare()
 * @method bool isHideInThread()
 * @method bool isItemId()
 * @method bool isItemType()
 * @method bool isLike()
 * @method bool isLink()
 * @method bool isLiveVideoShare()
 * @method bool isLiveViewerInvite()
 * @method bool isLocation()
 * @method bool isMedia()
 * @method bool isMediaShare()
 * @method bool isPlaceholder()
 * @method bool isPreviewMedias()
 * @method bool isProductShare()
 * @method bool isProfile()
 * @method bool isRavenMedia()
 * @method bool isReactions()
 * @method bool isReelShare()
 * @method bool isSeenUserIds()
 * @method bool isStoryShare()
 * @method bool isText()
 * @method bool isTimestamp()
 * @method bool isUserId()
 * @method bool isVideoCallEvent()
 * @method this setActionLog(ActionLog $value)
 * @method this setAnimatedMedia(AnimatedMedia $value)
 * @method this setClientContext(string $value)
 * @method this setDirectMediaShare(MediaShare $value)
 * @method this setExpiringMediaActionSummary(DirectExpiringSummary $value)
 * @method this setFelixShare(FelixShare $value)
 * @method this setHideInThread(mixed $value)
 * @method this setItemId(string $value)
 * @method this setItemType(mixed $value)
 * @method this setLike(mixed $value)
 * @method this setLink(DirectLink $value)
 * @method this setLiveVideoShare(LiveVideoShare $value)
 * @method this setLiveViewerInvite(LiveViewerInvite $value)
 * @method this setLocation(Location $value)
 * @method this setMedia(DirectThreadItemMedia $value)
 * @method this setMediaShare(Item $value)
 * @method this setPlaceholder(Placeholder $value)
 * @method this setPreviewMedias(Item[] $value)
 * @method this setProductShare(ProductShare $value)
 * @method this setProfile(User $value)
 * @method this setRavenMedia(Item $value)
 * @method this setReactions(DirectReactions $value)
 * @method this setReelShare(ReelShare $value)
 * @method this setSeenUserIds(string[] $value)
 * @method this setStoryShare(StoryShare $value)
 * @method this setText(string $value)
 * @method this setTimestamp(mixed $value)
 * @method this setUserId(string $value)
 * @method this setVideoCallEvent(VideoCallEvent $value)
 * @method this unsetActionLog()
 * @method this unsetAnimatedMedia()
 * @method this unsetClientContext()
 * @method this unsetDirectMediaShare()
 * @method this unsetExpiringMediaActionSummary()
 * @method this unsetFelixShare()
 * @method this unsetHideInThread()
 * @method this unsetItemId()
 * @method this unsetItemType()
 * @method this unsetLike()
 * @method this unsetLink()
 * @method this unsetLiveVideoShare()
 * @method this unsetLiveViewerInvite()
 * @method this unsetLocation()
 * @method this unsetMedia()
 * @method this unsetMediaShare()
 * @method this unsetPlaceholder()
 * @method this unsetPreviewMedias()
 * @method this unsetProductShare()
 * @method this unsetProfile()
 * @method this unsetRavenMedia()
 * @method this unsetReactions()
 * @method this unsetReelShare()
 * @method this unsetSeenUserIds()
 * @method this unsetStoryShare()
 * @method this unsetText()
 * @method this unsetTimestamp()
 * @method this unsetUserId()
 * @method this unsetVideoCallEvent()
 */
data class DirectThreadItem (
    val item_id                       : String,
    val item_type                     : String,
    val text                          : String,
    val media_share                   : Item,
    val preview_medias                : MutableList<Item>,
    val media                         : DirectThreadItemMedia,
    val user_id                       : String,
    val timestamp                     : String,
    val client_context                : String,
    val hide_in_thread                : String,
    val action_log                    : ActionLog,
    val link                          : DirectLink,
    val reactions                     : DirectReactions,
    val raven_media                   : Item,
    val seen_user_ids                 : MutableList<String>,
    val expiring_media_action_summary : DirectExpiringSummary,
    val reel_share                    : ReelShare,
    val placeholder                   : Placeholder,
    val location                      : Location,
    val like                          : String,
    val live_video_share              : LiveVideoShare,
    val live_viewer_invite            : LiveViewerInvite,
    val profile                       : User,
    val story_share                   : StoryShare,
    val direct_media_share            : MediaShare,
    val video_call_event              : VideoCallEvent,
    val product_share                 : ProductShare,
    val animated_media                : AnimatedMedia,
    val felix_share                   : FelixShare
){
    val PLACEHOLDER = "placeholder"
    val TEXT = "text"
    val HASHTAG = "hashtag"
    val LOCATION = "location"
    val PROFILE = "profile"
    val MEDIA = "media"
    val MEDIA_SHARE = "media_share"
    val EXPIRING_MEDIA = "raven_media"
    val LIKE = "like"
    val ACTION_LOG = "action_log"
    val REACTION = "reaction"
    val REEL_SHARE = "reel_share"
    val STORY_SHARE = "story_share"
    val LINK = "link"
    val LIVE_VIDEO_SHARE = "live_video_share"
    val LIVE_VIEWER_INVITE = "live_viewer_invite"
    val PRODUCT_SHARE = "product_share"
    val VIDEO_CALL_EVENT = "video_call_event"

//    val JSON_PROPERTY_MAP = [
//        "item_id"                       => "String",
//        "item_type"                     => "",
//        "text"                          => "String",
//        "media_share"                   => "Item",
//        "preview_medias"                => "Item[]",
//        "media"                         => "DirectThreadItemMedia",
//        "user_id"                       => "string",
//        "timestamp"                     => "",
//        "client_context"                => "string",
//        "hide_in_thread"                => "",
//        "action_log"                    => "ActionLog",
//        "link"                          => "DirectLink",
//        "reactions"                     => "DirectReactions",
//        "raven_media"                   => "Item",
//        "seen_user_ids"                 => "string[]",
//        "expiring_media_action_summary" => "DirectExpiringSummary",
//        "reel_share"                    => "ReelShare",
//        "placeholder"                   => "Placeholder",
//        "location"                      => "Location",
//        "like"                          => "",
//        "live_video_share"              => "LiveVideoShare",
//        "live_viewer_invite"            => "LiveViewerInvite",
//        "profile"                       => "User",
//        "story_share"                   => "StoryShare",
//        "direct_media_share"            => "MediaShare",
//        "video_call_event"              => "VideoCallEvent",
//        "product_share"                 => "ProductShare",
//        "animated_media"                => "AnimatedMedia",
//        "felix_share"                   => "FelixShare",
//    ]
}
