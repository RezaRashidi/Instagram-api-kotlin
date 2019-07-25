

package instagramAPI.responses

import instagramAPI.Response
import instagramAPI.responses.model.ActionBadge
import instagramAPI.responses.model.DirectThreadItem
import instagramAPI.responses.model.PermanentItem
import instagramAPI.responses.model.User
import instagramAPI.responses.model.unpredictableKeys.DirectThreadLastSeenAtUnpredictableContainer

/**
 * DirectVisualThreadResponse.
 *
 * @method model.ActionBadge getActionBadge()
 * @method bool getCanonical()
 * @method int getExpiringMediaReceiveCount()
 * @method int getExpiringMediaSendCount()
 * @method bool getHasNewer()
 * @method bool getHasOlder()
 * @method model.User getInviter()
 * @method bool getIsGroup()
 * @method bool getIsPin()
 * @method bool getIsSpam()
 * @method model.DirectThreadItem[] getItems()
 * @method string getLastActivityAt()
 * @method mixed getLastActivityAtSecs()
 * @method model.PermanentItem getLastPermanentItem()
 * @method model.unpredictableKeys.DirectThreadLastSeenAtUnpredictableContainer getLastSeenAt()
 * @method model.User[] getLeftUsers()
 * @method mixed getMessage()
 * @method bool getMuted()
 * @method bool getNamed()
 * @method string getNewestCursor()
 * @method string getOldestCursor()
 * @method bool getPending()
 * @method string getPendingScore()
 * @method int getReshareReceiveCount()
 * @method int getReshareSendCount()
 * @method string getStatus()
 * @method string getThreadId()
 * @method string getThreadTitle()
 * @method string getThreadType()
 * @method string getThreadV2Id()
 * @method mixed getUnseenCount()
 * @method model.User[] getUsers()
 * @method bool getValuedRequest()
 * @method bool getVcMuted()
 * @method string getViewerId()
 * @method model._Message[] get_Messages()
 * @method bool isActionBadge()
 * @method bool isCanonical()
 * @method bool isExpiringMediaReceiveCount()
 * @method bool isExpiringMediaSendCount()
 * @method bool isHasNewer()
 * @method bool isHasOlder()
 * @method bool isInviter()
 * @method bool isIsGroup()
 * @method bool isIsPin()
 * @method bool isIsSpam()
 * @method bool isItems()
 * @method bool isLastActivityAt()
 * @method bool isLastActivityAtSecs()
 * @method bool isLastPermanentItem()
 * @method bool isLastSeenAt()
 * @method bool isLeftUsers()
 * @method bool isMessage()
 * @method bool isMuted()
 * @method bool isNamed()
 * @method bool isNewestCursor()
 * @method bool isOldestCursor()
 * @method bool isPending()
 * @method bool isPendingScore()
 * @method bool isReshareReceiveCount()
 * @method bool isReshareSendCount()
 * @method bool isStatus()
 * @method bool isThreadId()
 * @method bool isThreadTitle()
 * @method bool isThreadType()
 * @method bool isThreadV2Id()
 * @method bool isUnseenCount()
 * @method bool isUsers()
 * @method bool isValuedRequest()
 * @method bool isVcMuted()
 * @method bool isViewerId()
 * @method bool is_Messages()
 * @method this setActionBadge(model.ActionBadge $value)
 * @method this setCanonical(bool $value)
 * @method this setExpiringMediaReceiveCount(int $value)
 * @method this setExpiringMediaSendCount(int $value)
 * @method this setHasNewer(bool $value)
 * @method this setHasOlder(bool $value)
 * @method this setInviter(model.User $value)
 * @method this setIsGroup(bool $value)
 * @method this setIsPin(bool $value)
 * @method this setIsSpam(bool $value)
 * @method this setItems(model.DirectThreadItem[] $value)
 * @method this setLastActivityAt(string $value)
 * @method this setLastActivityAtSecs(mixed $value)
 * @method this setLastPermanentItem(model.PermanentItem $value)
 * @method this setLastSeenAt(model.unpredictableKeys.DirectThreadLastSeenAtUnpredictableContainer $value)
 * @method this setLeftUsers(model.User[] $value)
 * @method this setMessage(mixed $value)
 * @method this setMuted(bool $value)
 * @method this setNamed(bool $value)
 * @method this setNewestCursor(string $value)
 * @method this setOldestCursor(string $value)
 * @method this setPending(bool $value)
 * @method this setPendingScore(string $value)
 * @method this setReshareReceiveCount(int $value)
 * @method this setReshareSendCount(int $value)
 * @method this setStatus(string $value)
 * @method this setThreadId(string $value)
 * @method this setThreadTitle(string $value)
 * @method this setThreadType(string $value)
 * @method this setThreadV2Id(string $value)
 * @method this setUnseenCount(mixed $value)
 * @method this setUsers(model.User[] $value)
 * @method this setValuedRequest(bool $value)
 * @method this setVcMuted(bool $value)
 * @method this setViewerId(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetActionBadge()
 * @method this unsetCanonical()
 * @method this unsetExpiringMediaReceiveCount()
 * @method this unsetExpiringMediaSendCount()
 * @method this unsetHasNewer()
 * @method this unsetHasOlder()
 * @method this unsetInviter()
 * @method this unsetIsGroup()
 * @method this unsetIsPin()
 * @method this unsetIsSpam()
 * @method this unsetItems()
 * @method this unsetLastActivityAt()
 * @method this unsetLastActivityAtSecs()
 * @method this unsetLastPermanentItem()
 * @method this unsetLastSeenAt()
 * @method this unsetLeftUsers()
 * @method this unsetMessage()
 * @method this unsetMuted()
 * @method this unsetNamed()
 * @method this unsetNewestCursor()
 * @method this unsetOldestCursor()
 * @method this unsetPending()
 * @method this unsetPendingScore()
 * @method this unsetReshareReceiveCount()
 * @method this unsetReshareSendCount()
 * @method this unsetStatus()
 * @method this unsetThreadId()
 * @method this unsetThreadTitle()
 * @method this unsetThreadType()
 * @method this unsetThreadV2Id()
 * @method this unsetUnseenCount()
 * @method this unsetUsers()
 * @method this unsetValuedRequest()
 * @method this unsetVcMuted()
 * @method this unsetViewerId()
 * @method this unset_Messages()
 */
data class DirectVisualThreadResponse (
    val thread_id                     : String,
    val thread_v2_id                  : String,
    val users                         : MutableList<User>,
    val left_users                    : MutableList<User>,
    val items                         : MutableList<DirectThreadItem>,
    val last_activity_at              : String,
    val muted                         : Boolean,
    val is_pin                        : Boolean,
    val named                         : Boolean,
    val canonical                     : Boolean,
    val pending                       : Boolean,
    val valued_request                : Boolean,
    val thread_type                   : String,
    val viewer_id                     : String,
    val thread_title                  : String,
    val pending_score                 : String,
    val vc_muted                      : Boolean,
    val is_group                      : Boolean,
    val reshare_send_count            : Int,
    val reshare_receive_count         : Int,
    val expiring_media_send_count     : Int,
    val expiring_media_receive_count  : Int,
    val inviter                       : User,
    val has_older                     : Boolean,
    val has_newer                     : Boolean,
    val last_seen_at                  : DirectThreadLastSeenAtUnpredictableContainer,
    val newest_cursor                 : String,
    val oldest_cursor                 : String,
    val is_spam                       : Boolean,
    val last_permanent_item           : PermanentItem,
    val unseen_count                  : String,
    val action_badge                  : ActionBadge,
    val last_activity_at_secs         : String
){
//    val JSON_PROPERTY_MAP = [
//        Model.DirectThread::class, // Import property map.
//    ]
}
