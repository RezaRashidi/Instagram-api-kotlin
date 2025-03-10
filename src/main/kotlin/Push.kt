

package instagramAPI

//import Evenement.EventEmitterInterface
//import Evenement.EventEmitterTrait
import instagramAPI.pushs.Fbns
import instagramAPI.React.Connector
//import Psr.Log.LoggerInterface
//import Psr.Log.NullLogger
//import React.EventLoop.LoopInterface

/**
 * The following events are emitted.
 *
 *   Posts:
 *     - post - "USERNAME just shared a post."
 *       media?id=1111111111111111111_1111111111
 *     - first_post - "See NAME"s first Instagram post."
 *       user?username=USERNAME
 *     - resurrected_user_post - "USERNAME posted for the first time in a while. Be the first to add a comment."
 *       media?id=1111111111111111111_1111111111
 *     - recent_follow_post - "USERNAME just shared a post."
 *       media?id=1111111111111111111_1111111111
 *     - fb_first_post - "Your Facebook friend NAME just shared their first Instagram post"
 *       user?username=USERNAME
 *     - first_bestie_post - "USERNAME just shared a post with their close friends list."
 *       media?id=1111111111111111111_1111111111
 *     - follower_activity_with_location - "USERNAME tagged LOCATION in a post."
 *       media?id=1111111111111111111 <- Yep, no author ID here.
 *
 *   Stories:
 *     - first_reel_post - "See USERNAME"s first story on Instagram."
 *       user?username=USERNAME&launch_reel=1
 *     - resurrected_reel_post - "USERNAME added to their story for the first time in a while."
 *       user?username=USERNAME&launch_reel=1
 *     - first_bestie_post - "USERNAME just shared a post with their close friends list."
 *       user?username=USERNAME&launch_reel=1
 *     - story_poll_vote - "USERNAME voted YES to "POLL". Currently: 2 YES, 1 NO"
 *       user?username=USERNAME&launch_reel=1&media_id=1111111111111111111_1111111111&include_viewers=1
 *     - story_poll_close - "Your poll, "POLL" ends in an hour. Results so far: 2 YES, 1 NO"
 *       user?username=USERNAME&launch_reel=1&media_id=1111111111111111111_1111111111&include_viewers=1
 *     - story_producer_expire_media - "Your story has NUMBER views. Find out who"s seen it before it disappears."
 *       user?username=USERNAME&launch_reel=1
 *     - story_poll_result_share - "Your poll is almost over, and YES is winning. See and share the results."
 *       user?username=USERNAME&launch_reel=1&media_id=1111111111111111111_1111111111&include_viewers=1
 *     - story_daily_digest - "USERNAME1, USERNAME2 and USERNAME3 recently added to their stories."
 *       mainfeed?launch_reel_user_ids=1111111111,2222222222,3333333333,4444444444
 *
 *   Followers and contacts:
 *     - new_follower - "NAME (USERNAME) started following you."
 *       user?username=USERNAME
 *     - private_user_follow_request - "NAME (@USERNAME) has requested to follow you."
 *       user?username=USERNAME
 *     - follow_request_approved - "USERNAME accepted your follow request. Now you can see their photos and videos."
 *       user?username=USERNAME
 *     - contactjoined - "Your Facebook friend NAME is on Instagram as USERNAME."
 *       user?username=USERNAME
 *     - contact_joined_email - "NAME, one of your contacts, is on Instagram as @USERNAME. Would you like to follow them?"
 *       user?username=USERNAME
 *     - fb_friend_connected - "Your Facebook friend NAME is on Instagram as USERNAME."
 *       user?username=USERNAME
 *     - follower_follow - "USERNAME1 and USERNAME2 followed NAME on Instagram. See their posts."
 *       user?username=USERNAME
 *     - follower_activity_reminders - "USERNAME1, USERNAME2 and others shared NUMBER photos."
 *       mainfeed
 *
 *   Comments:
 *     - comment - "USERNAME commented: "TEXT""
 *       media?id=1111111111111111111_1111111111&forced_preview_comment_id=11111111111111111
 *       comments_v2?media_id=1111111111111111111_1111111111&target_comment_id=11111111111111111
 *     - mentioned_comment - "USERNAME mentioned you in a comment: TEXT..."
 *       media?id=1111111111111111111_1111111111
 *       comments_v2?media_id=1111111111111111111_1111111111
 *     - comment_on_tag - "USERNAME commented on a post you"re tagged in."
 *       media?id=1111111111111111111 <- Yep, no author ID here.
 *     - comment_subscribed - "USERNAME also commented on USERNAME"s post: "TEXT""
 *       comments_v2?media_id=1111111111111111111_1111111111&target_comment_id=11111111111111111
 *     - comment_subscribed_on_like - "USERNAME commented on a post you liked: TEXT"
 *       comments_v2?media_id=1111111111111111111_1111111111&target_comment_id=11111111111111111
 *     - reply_to_comment_with_threading - "USERNAME replied to your comment on your post: "TEXT""
 *       comments_v2?media_id=1111111111111111111_1111111111&target_comment_id=11111111111111111
 *
 *   Likes:
 *     - like - "USERNAME liked your post."
 *       media?id=1111111111111111111_1111111111
 *     - like_on_tag - "USERNAME liked a post you"re tagged in."
 *       media?id=1111111111111111111_1111111111
 *     - comment_like - "USERNAME liked your comment: "TEXT...""
 *       media?id=1111111111111111111_1111111111&forced_preview_comment_id=11111111111111111
 *
 *   Direct:
 *     - direct_v2_message - "USERNAME sent you a message."
 *       direct_v2?id=11111111111111111111111111111111111111&x=11111111111111111111111111111111111
 *     - direct_v2_message - "USERNAME wants to send you a message."
 *       direct_v2?id=11111111111111111111111111111111111111&t=p
 *     - direct_v2_message - "USERNAME sent you a photo."
 *       direct_v2?id=11111111111111111111111111111111111111&x=11111111111111111111111111111111111&t=ds
 *
 *   Live:
 *     - live_broadcast - "USERNAME started a live video. Watch it before it ends!"
 *       broadcast?id=11111111111111111&reel_id=1111111111&published_time=1234567890
 *     - live_with_broadcast - "USERNAME1 is going live now with USERNAME2."
 *       broadcast?id=11111111111111111&reel_id=1111111111&published_time=1234567890
 *     - live_broadcast_revoke
 *       broadcast?id=11111111111111111&reel_id=1111111111&published_time=1234567890
 *
 *   Business:
 *     - aymt - "Your promotion was approved." or "Your promotion has ended." or internationalized message.
 *       media?id=1111111111111111111 <- Yep, no author ID here.
 *     - ad_preview - "Your ad is ready to preview"
 *       media?id=1111111111111111111_1111111111
 *     - branded_content_tagged - "USERNAME tagged you as a business partner on a post."
 *       media?id=1111111111111111111_1111111111
 *     - branded_content_untagged - "USERNAME removed you as a business partner on a post."
 *       media?id=1111111111111111111_1111111111
 *     - business_profile - "Add a website so customers can learn about your business."
 *       editprofile?user_id=1111111111
 *
 *   Unsorted:
 *     - usertag - "USERNAME tagged you in a post"
 *       media?id=1111111111111111111_1111111111
 *     - video_view_count - "People viewed your video more than NUMBER times."
 *       media?id=1111111111111111111_1111111111
 *     - copyright_video - "Your video may have copyrighted content that belongs to someone else."
 *       news
 *     - report_updated - "Your support request from DATE was just updated."
 *       news
 *     - promote_account - "Check out today"s photo from TEXT."
 *       user?username=USERNAME
 *     - unseen_notification_reminders
 *       news
 *
 *   System:
 *     - silent_push - Some kind of service push that does nothing. Nothing important, I hope.
 *     - incoming - The event that catches all pushes. Useful for debugging and logging.
 *     - warning - An exception of severity "warning" occured.
 *     - error - An exception of severity "error" occurred. It"s not guaranteed that the pushs client will continue to work.
 */
class Push //: EventEmitterInterface
{
    import EventEmitterTrait

    /** @var Instagram */
    protected var _instagram: Instagram

    /** @var LoopInterface */
    protected var _loop: LoopInterface

    /** @var LoggerInterface */
    protected var _logger: LoggerInterface

    /** @var Fbns */
    protected var _fbns: Fbns

    /** @var Fbns.Auth */
    protected var _fbnsAuth: Fbns.Auth

    /**
     * pushs constructor.
     *
     * @param LoopInterface        $loop
     * @param Instagram            $instagram
     * @param LoggerInterface|null $logger
     *
     * @throws .RuntimeException
     */
    constructor( loop: LoopInterface, instagram: Instagram, logger: LoggerInterface? = null)
    {
        if (PHP_SAPI !== "cli") {
            throw RuntimeException("The pushs client can only run from the command line.")
        }

        _instagram = instagram
        _loop = loop
        _logger = logger
        if (_logger === null) {
            _logger = NullLogger()
        }

        _fbnsAuth = Fbns.Auth(_instagram)
        _fbns = _getFbns()
    }

    /**
     * Incoming notification callback.
     *
     * @param Notification $notification
     */
    protected fun _onPush(notification: Notification){
        val collapseKey = notification.getCollapseKey()
        _logger.info("Received a push with collapse key \"$collapseKey\"" , [notification as String])
        this.emit("incoming", [notification])
        if (collapseKey.isNotEmpty()) {
            this.emit(collapseKey, [notification])
        }
    }

    /**
     * Create a FBNS receiver.
     *
     * @return Fbns
     */
    protected fun _getFbns(): Fbns {
        val fbns = Fbns(
            this,
            Connector(_instagram, _loop),
            _fbnsAuth,
            _instagram.device,
            _loop,
            _logger
        )
        fbns.on("fbns_auth", fun (authJson) {
            try {
                _fbnsAuth.update(authJson)
            } catch (e: Exception) {
                _logger.error("Failed to update FBNS auth: ${e.message}", [authJson])
            }
        })
        fbns.on("fbns_token", fun (token) {
            // Refresh the "last token activity" timestamp.
            // The age of this timestamp helps us detect when the user
            // has stopped using the pushs features due to inactivity.
            try {
                _instagram.settings.set("last_fbns_token", System.currentTimeMillis()/1000 )
            } catch (e: Exception) {
                _logger.error("Failed to write FBNS token timestamp: ${e.message}")
            }
            // Read our old token. If an identical value exists, then we know
            // that we"ve already registered that token during this session.
            try {
                val oldToken = _instagram.settings.get("fbns_token")
                // Do nothing when the token is equal to the old one.
                if (token === oldToken) {
                    return
                }
            } catch (e: Exception) {
                _logger.error("Failed to read FBNS token: ${e.message}")
            }
            // Register the token.
            try {
                _instagram.push.register("mqtt", token)
            } catch (e: Exception) {
                this.emit("error", [e])
            }
            // Save the newly received token to the storage.
            // NOTE: We save it even if the registration failed, since we now
            // got it from the server and assume they"ve given us a good one.
            // However, it"ll always be re-validated during the general login()
            // flow, and will be cleared there if it fails to register there.
            try {
                _instagram.settings.set("fbns_token", token)
            } catch (e: Exception) {
                _logger.error("Failed to update FBNS token: ${e.message}", [token])
            }
        })
        fbns.on("push", fun (notification: Notification) {
            this._onPush(notification)
        })

        return fbns
    }

    /**
     * Start pushs receiver.
     */
    fun start(){
        _fbns.start()
    }

    /**
     * Stop pushs receiver.
     */
    fun stop(){
        _fbns.stop()
    }
}
