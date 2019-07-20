

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Item.
 *
 * @method string getActorFbid()
 * @method string getAdAction()
 * @method int getAdHeaderStyle()
 * @method string getAdId()
 * @method int getAdLinkType()
 * @method AdMetadata[] getAdMetadata()
 * @method string getAlgorithm()
 * @method AndroidLinks[] getAndroidLinks()
 * @method Attribution getAttribution()
 * @method mixed getBoostUnavailableReason()
 * @method mixed getBoostedStatus()
 * @method bool getCanReply()
 * @method bool getCanReshare()
 * @method bool getCanSeeInsightsAsBrand()
 * @method bool getCanViewMorePreviewComments()
 * @method bool getCanViewerReshare()
 * @method bool getCanViewerSave()
 * @method Caption getCaption()
 * @method bool getCaptionIsEdited()
 * @method float getCaptionPosition()
 * @method CarouselMedia[] getCarouselMedia()
 * @method int getCarouselMediaCount()
 * @method mixed getCarouselMediaType()
 * @method Channel getChannel()
 * @method string getClientCacheKey()
 * @method string getCode()
 * @method bool getCollapseComments()
 * @method int getCommentCount()
 * @method bool getCommentLikesEnabled()
 * @method bool getCommentThreadingEnabled()
 * @method mixed getCommentingDisabledForViewer()
 * @method Comment[] getComments()
 * @method mixed getCommentsDisabled()
 * @method string getConnectionId()
 * @method CoverMedia getCoverMedia()
 * @method mixed getCreativeConfig()
 * @method string getDeviceTimestamp()
 * @method bool getDirectReplyToAuthorEnabled()
 * @method string getDominantColor()
 * @method int getDrAdType()
 * @method string getDynamicItemId()
 * @method string getExpiringAt()
 * @method Explore getExplore()
 * @method string getExploreContext()
 * @method bool getExploreHideComments()
 * @method string getExploreSourceToken()
 * @method string getFbPageUrl()
 * @method Usertag getFbUserTags()
 * @method int getFilterType()
 * @method Hashtag getFollowHashtagInfo()
 * @method bool getForceOverlay()
 * @method Gating getGating()
 * @method bool getHasAudio()
 * @method bool getHasLiked()
 * @method bool getHasMoreComments()
 * @method bool getHasSharedToFb()
 * @method bool getHasViewerSaved()
 * @method bool getHideNuxText()
 * @method string[] getHighlightReelIds()
 * @method mixed getITunesItem()
 * @method mixed getIcon()
 * @method string getId()
 * @method Image_Versions2 getImageVersions2()
 * @method mixed getImportedTakenAt()
 * @method string getImpressionToken()
 * @method Injected getInjected()
 * @method string getInlineComposerDisplayCondition()
 * @method int getInlineComposerImpTriggerTime()
 * @method string getInventorySource()
 * @method IOSLinks[] getIosLinks()
 * @method mixed getIsAd4ad()
 * @method int getIsDashEligible()
 * @method bool getIsEof()
 * @method bool getIsNewSuggestion()
 * @method bool getIsReelMedia()
 * @method mixed getIsSeen()
 * @method mixed getIsSidecarChild()
 * @method mixed getLargeUrls()
 * @method float getLat()
 * @method string getLeadGenFormId()
 * @method int getLikeCount()
 * @method User[] getLikers()
 * @method string getLink()
 * @method string getLinkHintText()
 * @method string getLinkText()
 * @method float getLng()
 * @method Location getLocation()
 * @method bool getMainFeedCarouselHasUnseenCoverMedia()
 * @method string getMainFeedCarouselStartingMediaId()
 * @method int getMaxNumVisiblePreviewComments()
 * @method media getMedia()
 * @method string getMediaId()
 * @method string[] getMediaIds()
 * @method mixed getMediaInfos()
 * @method int getMediaType()
 * @method mixed getMultiAuthorReelNames()
 * @method bool getNearlyCompleteCopyrightMatch()
 * @method string getNextMaxId()
 * @method int getNumberOfQualities()
 * @method string getOrganicPostId()
 * @method string getOrganicTrackingToken()
 * @method int getOriginalHeight()
 * @method int getOriginalWidth()
 * @method string getOverlaySubtitle()
 * @method string getOverlayText()
 * @method string getOverlayTitle()
 * @method bool getPhotoOfYou()
 * @method string getPk()
 * @method Placeholder getPlaceholder()
 * @method mixed getPlaybackDurationSecs()
 * @method string getPreview()
 * @method Comment[] getPreviewComments()
 * @method ProductTags getProductTags()
 * @method string getProductType()
 * @method ReelMention[] getReelMentions()
 * @method ReelShare getReelShare()
 * @method string[] getSavedCollectionIds()
 * @method mixed getScreenshotterUserIds()
 * @method bool getShowOneTapFbShareTooltip()
 * @method string getSocialContext()
 * @method User[] getSponsorTags()
 * @method Stories getStories()
 * @method StoryCountdowns[] getStoryCountdowns()
 * @method StoryCta[] getStoryCta()
 * @method mixed getStoryEvents()
 * @method mixed getStoryFeedMedia()
 * @method mixed getStoryFriendLists()
 * @method StoryHashtag[] getStoryHashtags()
 * @method bool getStoryIsSavedToArchive()
 * @method StoryLocation[] getStoryLocations()
 * @method mixed getStoryMusicStickers()
 * @method mixed getStoryPollVoterInfos()
 * @method mixed getStoryPolls()
 * @method mixed getStoryProductItems()
 * @method StoryQuestionResponderInfos[] getStoryQuestionResponderInfos()
 * @method StoryQuestions[] getStoryQuestions()
 * @method mixed getStorySliders()
 * @method mixed getStorySoundOn()
 * @method SuggestedUsers getSuggestedUsers()
 * @method bool getSupportsReelReactions()
 * @method string getTakenAt()
 * @method mixed getThumbnailUrls()
 * @method Thumbnail getThumbnails()
 * @method int getTimezoneOffset()
 * @method string getTitle()
 * @method string[] getTopFollowers()
 * @method int getTopFollowersCount()
 * @method string[] getTopLikers()
 * @method int getTotalScreenshotCount()
 * @method int getTotalViewerCount()
 * @method mixed getUrlExpireAtSecs()
 * @method User getUser()
 * @method Usertag getUsertags()
 * @method float getValue()
 * @method string getVideoCodec()
 * @method string getVideoDashManifest()
 * @method float getVideoDuration()
 * @method VideoVersions[] getVideoVersions()
 * @method int getViewCount()
 * @method int getViewerCount()
 * @method mixed getViewerCursor()
 * @method User[] getViewers()
 * @method mixed getVisibility()
 * @method bool isActorFbid()
 * @method bool isAdAction()
 * @method bool isAdHeaderStyle()
 * @method bool isAdId()
 * @method bool isAdLinkType()
 * @method bool isAdMetadata()
 * @method bool isAlgorithm()
 * @method bool isAndroidLinks()
 * @method bool isAttribution()
 * @method bool isBoostUnavailableReason()
 * @method bool isBoostedStatus()
 * @method bool isCanReply()
 * @method bool isCanReshare()
 * @method bool isCanSeeInsightsAsBrand()
 * @method bool isCanViewMorePreviewComments()
 * @method bool isCanViewerReshare()
 * @method bool isCanViewerSave()
 * @method bool isCaption()
 * @method bool isCaptionIsEdited()
 * @method bool isCaptionPosition()
 * @method bool isCarouselMedia()
 * @method bool isCarouselMediaCount()
 * @method bool isCarouselMediaType()
 * @method bool isChannel()
 * @method bool isClientCacheKey()
 * @method bool isCode()
 * @method bool isCollapseComments()
 * @method bool isCommentCount()
 * @method bool isCommentLikesEnabled()
 * @method bool isCommentThreadingEnabled()
 * @method bool isCommentingDisabledForViewer()
 * @method bool isComments()
 * @method bool isCommentsDisabled()
 * @method bool isConnectionId()
 * @method bool isCoverMedia()
 * @method bool isCreativeConfig()
 * @method bool isDeviceTimestamp()
 * @method bool isDirectReplyToAuthorEnabled()
 * @method bool isDominantColor()
 * @method bool isDrAdType()
 * @method bool isDynamicItemId()
 * @method bool isExpiringAt()
 * @method bool isExplore()
 * @method bool isExploreContext()
 * @method bool isExploreHideComments()
 * @method bool isExploreSourceToken()
 * @method bool isFbPageUrl()
 * @method bool isFbUserTags()
 * @method bool isFilterType()
 * @method bool isFollowHashtagInfo()
 * @method bool isForceOverlay()
 * @method bool isGating()
 * @method bool isHasAudio()
 * @method bool isHasLiked()
 * @method bool isHasMoreComments()
 * @method bool isHasSharedToFb()
 * @method bool isHasViewerSaved()
 * @method bool isHideNuxText()
 * @method bool isHighlightReelIds()
 * @method bool isITunesItem()
 * @method bool isIcon()
 * @method bool isId()
 * @method bool isImageVersions2()
 * @method bool isImportedTakenAt()
 * @method bool isImpressionToken()
 * @method bool isInjected()
 * @method bool isInlineComposerDisplayCondition()
 * @method bool isInlineComposerImpTriggerTime()
 * @method bool isInventorySource()
 * @method bool isIosLinks()
 * @method bool isIsAd4ad()
 * @method bool isIsDashEligible()
 * @method bool isIsEof()
 * @method bool isIsNewSuggestion()
 * @method bool isIsReelMedia()
 * @method bool isIsSeen()
 * @method bool isIsSidecarChild()
 * @method bool isLargeUrls()
 * @method bool isLat()
 * @method bool isLeadGenFormId()
 * @method bool isLikeCount()
 * @method bool isLikers()
 * @method bool isLink()
 * @method bool isLinkHintText()
 * @method bool isLinkText()
 * @method bool isLng()
 * @method bool isLocation()
 * @method bool isMainFeedCarouselHasUnseenCoverMedia()
 * @method bool isMainFeedCarouselStartingMediaId()
 * @method bool isMaxNumVisiblePreviewComments()
 * @method bool isMedia()
 * @method bool isMediaId()
 * @method bool isMediaIds()
 * @method bool isMediaInfos()
 * @method bool isMediaType()
 * @method bool isMultiAuthorReelNames()
 * @method bool isNearlyCompleteCopyrightMatch()
 * @method bool isNextMaxId()
 * @method bool isNumberOfQualities()
 * @method bool isOrganicPostId()
 * @method bool isOrganicTrackingToken()
 * @method bool isOriginalHeight()
 * @method bool isOriginalWidth()
 * @method bool isOverlaySubtitle()
 * @method bool isOverlayText()
 * @method bool isOverlayTitle()
 * @method bool isPhotoOfYou()
 * @method bool isPk()
 * @method bool isPlaceholder()
 * @method bool isPlaybackDurationSecs()
 * @method bool isPreview()
 * @method bool isPreviewComments()
 * @method bool isProductTags()
 * @method bool isProductType()
 * @method bool isReelMentions()
 * @method bool isReelShare()
 * @method bool isSavedCollectionIds()
 * @method bool isScreenshotterUserIds()
 * @method bool isShowOneTapFbShareTooltip()
 * @method bool isSocialContext()
 * @method bool isSponsorTags()
 * @method bool isStories()
 * @method bool isStoryCountdowns()
 * @method bool isStoryCta()
 * @method bool isStoryEvents()
 * @method bool isStoryFeedMedia()
 * @method bool isStoryFriendLists()
 * @method bool isStoryHashtags()
 * @method bool isStoryIsSavedToArchive()
 * @method bool isStoryLocations()
 * @method bool isStoryMusicStickers()
 * @method bool isStoryPollVoterInfos()
 * @method bool isStoryPolls()
 * @method bool isStoryProductItems()
 * @method bool isStoryQuestionResponderInfos()
 * @method bool isStoryQuestions()
 * @method bool isStorySliders()
 * @method bool isStorySoundOn()
 * @method bool isSuggestedUsers()
 * @method bool isSupportsReelReactions()
 * @method bool isTakenAt()
 * @method bool isThumbnailUrls()
 * @method bool isThumbnails()
 * @method bool isTimezoneOffset()
 * @method bool isTitle()
 * @method bool isTopFollowers()
 * @method bool isTopFollowersCount()
 * @method bool isTopLikers()
 * @method bool isTotalScreenshotCount()
 * @method bool isTotalViewerCount()
 * @method bool isUrlExpireAtSecs()
 * @method bool isUser()
 * @method bool isUsertags()
 * @method bool isValue()
 * @method bool isVideoCodec()
 * @method bool isVideoDashManifest()
 * @method bool isVideoDuration()
 * @method bool isVideoVersions()
 * @method bool isViewCount()
 * @method bool isViewerCount()
 * @method bool isViewerCursor()
 * @method bool isViewers()
 * @method bool isVisibility()
 * @method this setActorFbid(string $value)
 * @method this setAdAction(string $value)
 * @method this setAdHeaderStyle(int $value)
 * @method this setAdId(string $value)
 * @method this setAdLinkType(int $value)
 * @method this setAdMetadata(AdMetadata[] $value)
 * @method this setAlgorithm(string $value)
 * @method this setAndroidLinks(AndroidLinks[] $value)
 * @method this setAttribution(Attribution $value)
 * @method this setBoostUnavailableReason(mixed $value)
 * @method this setBoostedStatus(mixed $value)
 * @method this setCanReply(bool $value)
 * @method this setCanReshare(bool $value)
 * @method this setCanSeeInsightsAsBrand(bool $value)
 * @method this setCanViewMorePreviewComments(bool $value)
 * @method this setCanViewerReshare(bool $value)
 * @method this setCanViewerSave(bool $value)
 * @method this setCaption(Caption $value)
 * @method this setCaptionIsEdited(bool $value)
 * @method this setCaptionPosition(float $value)
 * @method this setCarouselMedia(CarouselMedia[] $value)
 * @method this setCarouselMediaCount(int $value)
 * @method this setCarouselMediaType(mixed $value)
 * @method this setChannel(Channel $value)
 * @method this setClientCacheKey(string $value)
 * @method this setCode(string $value)
 * @method this setCollapseComments(bool $value)
 * @method this setCommentCount(int $value)
 * @method this setCommentLikesEnabled(bool $value)
 * @method this setCommentThreadingEnabled(bool $value)
 * @method this setCommentingDisabledForViewer(mixed $value)
 * @method this setComments(Comment[] $value)
 * @method this setCommentsDisabled(mixed $value)
 * @method this setConnectionId(string $value)
 * @method this setCoverMedia(CoverMedia $value)
 * @method this setCreativeConfig(mixed $value)
 * @method this setDeviceTimestamp(string $value)
 * @method this setDirectReplyToAuthorEnabled(bool $value)
 * @method this setDominantColor(string $value)
 * @method this setDrAdType(int $value)
 * @method this setDynamicItemId(string $value)
 * @method this setExpiringAt(string $value)
 * @method this setExplore(Explore $value)
 * @method this setExploreContext(string $value)
 * @method this setExploreHideComments(bool $value)
 * @method this setExploreSourceToken(string $value)
 * @method this setFbPageUrl(string $value)
 * @method this setFbUserTags(Usertag $value)
 * @method this setFilterType(int $value)
 * @method this setFollowHashtagInfo(Hashtag $value)
 * @method this setForceOverlay(bool $value)
 * @method this setGating(Gating $value)
 * @method this setHasAudio(bool $value)
 * @method this setHasLiked(bool $value)
 * @method this setHasMoreComments(bool $value)
 * @method this setHasSharedToFb(bool $value)
 * @method this setHasViewerSaved(bool $value)
 * @method this setHideNuxText(bool $value)
 * @method this setHighlightReelIds(string[] $value)
 * @method this setITunesItem(mixed $value)
 * @method this setIcon(mixed $value)
 * @method this setId(string $value)
 * @method this setImageVersions2(Image_Versions2 $value)
 * @method this setImportedTakenAt(mixed $value)
 * @method this setImpressionToken(string $value)
 * @method this setInjected(Injected $value)
 * @method this setInlineComposerDisplayCondition(string $value)
 * @method this setInlineComposerImpTriggerTime(int $value)
 * @method this setInventorySource(string $value)
 * @method this setIosLinks(IOSLinks[] $value)
 * @method this setIsAd4ad(mixed $value)
 * @method this setIsDashEligible(int $value)
 * @method this setIsEof(bool $value)
 * @method this setIsNewSuggestion(bool $value)
 * @method this setIsReelMedia(bool $value)
 * @method this setIsSeen(mixed $value)
 * @method this setIsSidecarChild(mixed $value)
 * @method this setLargeUrls(mixed $value)
 * @method this setLat(float $value)
 * @method this setLeadGenFormId(string $value)
 * @method this setLikeCount(int $value)
 * @method this setLikers(User[] $value)
 * @method this setLink(string $value)
 * @method this setLinkHintText(string $value)
 * @method this setLinkText(string $value)
 * @method this setLng(float $value)
 * @method this setLocation(Location $value)
 * @method this setMainFeedCarouselHasUnseenCoverMedia(bool $value)
 * @method this setMainFeedCarouselStartingMediaId(string $value)
 * @method this setMaxNumVisiblePreviewComments(int $value)
 * @method this setMedia(media $value)
 * @method this setMediaId(string $value)
 * @method this setMediaIds(string[] $value)
 * @method this setMediaInfos(mixed $value)
 * @method this setMediaType(int $value)
 * @method this setMultiAuthorReelNames(mixed $value)
 * @method this setNearlyCompleteCopyrightMatch(bool $value)
 * @method this setNextMaxId(string $value)
 * @method this setNumberOfQualities(int $value)
 * @method this setOrganicPostId(string $value)
 * @method this setOrganicTrackingToken(string $value)
 * @method this setOriginalHeight(int $value)
 * @method this setOriginalWidth(int $value)
 * @method this setOverlaySubtitle(string $value)
 * @method this setOverlayText(string $value)
 * @method this setOverlayTitle(string $value)
 * @method this setPhotoOfYou(bool $value)
 * @method this setPk(string $value)
 * @method this setPlaceholder(Placeholder $value)
 * @method this setPlaybackDurationSecs(mixed $value)
 * @method this setPreview(string $value)
 * @method this setPreviewComments(Comment[] $value)
 * @method this setProductTags(ProductTags $value)
 * @method this setProductType(string $value)
 * @method this setReelMentions(ReelMention[] $value)
 * @method this setReelShare(ReelShare $value)
 * @method this setSavedCollectionIds(string[] $value)
 * @method this setScreenshotterUserIds(mixed $value)
 * @method this setShowOneTapFbShareTooltip(bool $value)
 * @method this setSocialContext(string $value)
 * @method this setSponsorTags(User[] $value)
 * @method this setStories(Stories $value)
 * @method this setStoryCountdowns(StoryCountdowns[] $value)
 * @method this setStoryCta(StoryCta[] $value)
 * @method this setStoryEvents(mixed $value)
 * @method this setStoryFeedMedia(mixed $value)
 * @method this setStoryFriendLists(mixed $value)
 * @method this setStoryHashtags(StoryHashtag[] $value)
 * @method this setStoryIsSavedToArchive(bool $value)
 * @method this setStoryLocations(StoryLocation[] $value)
 * @method this setStoryMusicStickers(mixed $value)
 * @method this setStoryPollVoterInfos(mixed $value)
 * @method this setStoryPolls(mixed $value)
 * @method this setStoryProductItems(mixed $value)
 * @method this setStoryQuestionResponderInfos(StoryQuestionResponderInfos[] $value)
 * @method this setStoryQuestions(StoryQuestions[] $value)
 * @method this setStorySliders(mixed $value)
 * @method this setStorySoundOn(mixed $value)
 * @method this setSuggestedUsers(SuggestedUsers $value)
 * @method this setSupportsReelReactions(bool $value)
 * @method this setTakenAt(string $value)
 * @method this setThumbnailUrls(mixed $value)
 * @method this setThumbnails(Thumbnail $value)
 * @method this setTimezoneOffset(int $value)
 * @method this setTitle(string $value)
 * @method this setTopFollowers(string[] $value)
 * @method this setTopFollowersCount(int $value)
 * @method this setTopLikers(string[] $value)
 * @method this setTotalScreenshotCount(int $value)
 * @method this setTotalViewerCount(int $value)
 * @method this setUrlExpireAtSecs(mixed $value)
 * @method this setUser(User $value)
 * @method this setUsertags(Usertag $value)
 * @method this setValue(float $value)
 * @method this setVideoCodec(string $value)
 * @method this setVideoDashManifest(string $value)
 * @method this setVideoDuration(float $value)
 * @method this setVideoVersions(VideoVersions[] $value)
 * @method this setViewCount(int $value)
 * @method this setViewerCount(int $value)
 * @method this setViewerCursor(mixed $value)
 * @method this setViewers(User[] $value)
 * @method this setVisibility(mixed $value)
 * @method this unsetActorFbid()
 * @method this unsetAdAction()
 * @method this unsetAdHeaderStyle()
 * @method this unsetAdId()
 * @method this unsetAdLinkType()
 * @method this unsetAdMetadata()
 * @method this unsetAlgorithm()
 * @method this unsetAndroidLinks()
 * @method this unsetAttribution()
 * @method this unsetBoostUnavailableReason()
 * @method this unsetBoostedStatus()
 * @method this unsetCanReply()
 * @method this unsetCanReshare()
 * @method this unsetCanSeeInsightsAsBrand()
 * @method this unsetCanViewMorePreviewComments()
 * @method this unsetCanViewerReshare()
 * @method this unsetCanViewerSave()
 * @method this unsetCaption()
 * @method this unsetCaptionIsEdited()
 * @method this unsetCaptionPosition()
 * @method this unsetCarouselMedia()
 * @method this unsetCarouselMediaCount()
 * @method this unsetCarouselMediaType()
 * @method this unsetChannel()
 * @method this unsetClientCacheKey()
 * @method this unsetCode()
 * @method this unsetCollapseComments()
 * @method this unsetCommentCount()
 * @method this unsetCommentLikesEnabled()
 * @method this unsetCommentThreadingEnabled()
 * @method this unsetCommentingDisabledForViewer()
 * @method this unsetComments()
 * @method this unsetCommentsDisabled()
 * @method this unsetConnectionId()
 * @method this unsetCoverMedia()
 * @method this unsetCreativeConfig()
 * @method this unsetDeviceTimestamp()
 * @method this unsetDirectReplyToAuthorEnabled()
 * @method this unsetDominantColor()
 * @method this unsetDrAdType()
 * @method this unsetDynamicItemId()
 * @method this unsetExpiringAt()
 * @method this unsetExplore()
 * @method this unsetExploreContext()
 * @method this unsetExploreHideComments()
 * @method this unsetExploreSourceToken()
 * @method this unsetFbPageUrl()
 * @method this unsetFbUserTags()
 * @method this unsetFilterType()
 * @method this unsetFollowHashtagInfo()
 * @method this unsetForceOverlay()
 * @method this unsetGating()
 * @method this unsetHasAudio()
 * @method this unsetHasLiked()
 * @method this unsetHasMoreComments()
 * @method this unsetHasSharedToFb()
 * @method this unsetHasViewerSaved()
 * @method this unsetHideNuxText()
 * @method this unsetHighlightReelIds()
 * @method this unsetITunesItem()
 * @method this unsetIcon()
 * @method this unsetId()
 * @method this unsetImageVersions2()
 * @method this unsetImportedTakenAt()
 * @method this unsetImpressionToken()
 * @method this unsetInjected()
 * @method this unsetInlineComposerDisplayCondition()
 * @method this unsetInlineComposerImpTriggerTime()
 * @method this unsetInventorySource()
 * @method this unsetIosLinks()
 * @method this unsetIsAd4ad()
 * @method this unsetIsDashEligible()
 * @method this unsetIsEof()
 * @method this unsetIsNewSuggestion()
 * @method this unsetIsReelMedia()
 * @method this unsetIsSeen()
 * @method this unsetIsSidecarChild()
 * @method this unsetLargeUrls()
 * @method this unsetLat()
 * @method this unsetLeadGenFormId()
 * @method this unsetLikeCount()
 * @method this unsetLikers()
 * @method this unsetLink()
 * @method this unsetLinkHintText()
 * @method this unsetLinkText()
 * @method this unsetLng()
 * @method this unsetLocation()
 * @method this unsetMainFeedCarouselHasUnseenCoverMedia()
 * @method this unsetMainFeedCarouselStartingMediaId()
 * @method this unsetMaxNumVisiblePreviewComments()
 * @method this unsetMedia()
 * @method this unsetMediaId()
 * @method this unsetMediaIds()
 * @method this unsetMediaInfos()
 * @method this unsetMediaType()
 * @method this unsetMultiAuthorReelNames()
 * @method this unsetNearlyCompleteCopyrightMatch()
 * @method this unsetNextMaxId()
 * @method this unsetNumberOfQualities()
 * @method this unsetOrganicPostId()
 * @method this unsetOrganicTrackingToken()
 * @method this unsetOriginalHeight()
 * @method this unsetOriginalWidth()
 * @method this unsetOverlaySubtitle()
 * @method this unsetOverlayText()
 * @method this unsetOverlayTitle()
 * @method this unsetPhotoOfYou()
 * @method this unsetPk()
 * @method this unsetPlaceholder()
 * @method this unsetPlaybackDurationSecs()
 * @method this unsetPreview()
 * @method this unsetPreviewComments()
 * @method this unsetProductTags()
 * @method this unsetProductType()
 * @method this unsetReelMentions()
 * @method this unsetReelShare()
 * @method this unsetSavedCollectionIds()
 * @method this unsetScreenshotterUserIds()
 * @method this unsetShowOneTapFbShareTooltip()
 * @method this unsetSocialContext()
 * @method this unsetSponsorTags()
 * @method this unsetStories()
 * @method this unsetStoryCountdowns()
 * @method this unsetStoryCta()
 * @method this unsetStoryEvents()
 * @method this unsetStoryFeedMedia()
 * @method this unsetStoryFriendLists()
 * @method this unsetStoryHashtags()
 * @method this unsetStoryIsSavedToArchive()
 * @method this unsetStoryLocations()
 * @method this unsetStoryMusicStickers()
 * @method this unsetStoryPollVoterInfos()
 * @method this unsetStoryPolls()
 * @method this unsetStoryProductItems()
 * @method this unsetStoryQuestionResponderInfos()
 * @method this unsetStoryQuestions()
 * @method this unsetStorySliders()
 * @method this unsetStorySoundOn()
 * @method this unsetSuggestedUsers()
 * @method this unsetSupportsReelReactions()
 * @method this unsetTakenAt()
 * @method this unsetThumbnailUrls()
 * @method this unsetThumbnails()
 * @method this unsetTimezoneOffset()
 * @method this unsetTitle()
 * @method this unsetTopFollowers()
 * @method this unsetTopFollowersCount()
 * @method this unsetTopLikers()
 * @method this unsetTotalScreenshotCount()
 * @method this unsetTotalViewerCount()
 * @method this unsetUrlExpireAtSecs()
 * @method this unsetUser()
 * @method this unsetUsertags()
 * @method this unsetValue()
 * @method this unsetVideoCodec()
 * @method this unsetVideoDashManifest()
 * @method this unsetVideoDuration()
 * @method this unsetVideoVersions()
 * @method this unsetViewCount()
 * @method this unsetViewerCount()
 * @method this unsetViewerCursor()
 * @method this unsetViewers()
 * @method this unsetVisibility()
 */
data class Item (
    val taken_at                                 : String,
    val pk                                       : String,
    val id                                       : String,
    val device_timestamp                         : String,
    val media_type                               : Int,
    val dynamic_item_id                          : String,
    val code                                     : String,
    val client_cache_key                         : String,
    val filter_type                              : Int,
    val product_type                             : String,
    val nearly_complete_copyright_match          : Boolean,
    val image_versions2                          : Image_Versions2,
    val original_width                           : Int,
    val original_height                          : Int,
    val caption_position                         : Float,
    val is_reel_media                            : Boolean,
    val video_versions                           : MutableList<VideoVersions>,
    val has_audio                                : Boolean,
    val video_duration                           : Float,
    val user                                     : User,
    val can_see_insights_as_brand                : Boolean,
    val caption                                  : Caption,
    val title                                    : String,
    val caption_is_edited                        : Boolean,
    val photo_of_you                             : Boolean,
    val fb_user_tags                             : Usertag,
    val can_viewer_save                          : Boolean,
    val has_viewer_saved                         : Boolean,
    val organic_tracking_token                   : String,
    val follow_hashtag_info                      : Hashtag,
    val expiring_at                              : String,
    val is_dash_eligible                         : Int,
    val video_dash_manifest                      : String,
    val number_of_qualities                      : Int,
    val video_codec                              : String,
    val thumbnails                               : Thumbnail,
    val can_reshare                              : Boolean,
    val can_reply                                : Boolean,
    val can_viewer_reshare                       : Boolean,
    val visibility                               : String,
    val attribution                              : Attribution,
    val view_count                       : Int,
    val viewer_count                     : Int,
    val comment_count                    : Int,
    val can_view_more_preview_comments   : Boolean,
    val has_more_comments                : Boolean,
    val max_num_visible_preview_comments : Int,
    val preview_comments                 : MutableList<Comment>,
    val comments                                    : MutableList<Comment>,
    val comments_disabled                           : String,
    val reel_mentions                               : MutableList<ReelMention>,
    val story_cta                                   : MutableList<StoryCta>,
    val next_max_id                                 : String,
    val carousel_media                              : MutableList<CarouselMedia>,
    val carousel_media_type                         : String,
    val carousel_media_count                        : Int,
    val likers                                      : MutableList<User>,
    val like_count                                  : Int,
    val preview                                     : String,
    val has_liked                                   : Boolean,
    val explore_context                             : String,
    val explore_source_token                        : String,
    val explore_hide_comments                       : Boolean,
    val explore                                     : Explore,
    val impression_token                            : String,
    val usertags                                    : Usertag,
    val media                                       : String,
    val stories                                     : Stories,
    val top_likers                                  : MutableList<String>,
    val direct_reply_to_author_enabled              : Boolean,
    val suggested_users                             : SuggestedUsers,
    val is_new_suggestion                           : Boolean,
    val comment_likes_enabled                       : Boolean,
    val location                                    : Location,
    val lat                                         : Float,
    val lng                                         : Float,
    val channel                                     : Channel,
    val gating                                      : Gating,
    val injected                                    : Injected,
    val placeholder                                 : Placeholder,
    val algorithm                                   : String,
    val connection_id                               : String,
    val social_context                              : String,
    val icon                                        : String,
    val media_ids                                   : MutableList<String>,
    val media_id                                    : String,
    val thumbnail_urls                              : String,
    val large_urls                                  : String,
    val media_infos                                 : String,
    val value                                       : Float,
    val collapse_comments                           : Boolean,
    val link                                        : String,
    val link_text                                   : String,
    val link_hInt_text                              : String,
    val iTunesItem                                  : String,
    val ad_header_style                             : Int,
    val ad_metadata                                 : MutableList<AdMetadata>,
    val ad_action                                   : String,
    val ad_link_type                                : Int,
    val dr_ad_type                                  : Int,
    val android_links                               : MutableList<AndroidLinks>,
    val ios_links                                   : MutableList<IOSLinks>,
    val force_overlay                               : Boolean,
    val hide_nux_text                               : Boolean,
    val overlay_text                                : String,
    val overlay_title                               : String,
    val overlay_subtitle                            : String,
    val fb_page_url                                 : String,
    val playback_duration_secs                      : String,
    val url_expire_at_secs                          : String,
    val is_sidecar_child                            : String,
    val comment_threading_enabled                   : Boolean,
    val cover_media                                 : CoverMedia,
    val saved_collection_ids                        : MutableList<String>,
    val boosted_status                              : String,
    val boost_unavailable_reason                    : String,
    val viewers                                     : MutableList<User>,
    val viewer_cursor                               : String,
    val total_viewer_count                          : Int,
    val multi_author_reel_names                     : String,
    val screenshotter_user_ids                      : String,
    val reel_share                                  : ReelShare,
    val organic_post_id                             : String,
    val sponsor_tags                                : MutableList<User>,
    val story_poll_voter_infos                      : String,
    val imported_taken_at                           : String,
    val lead_gen_form_id                            : String,
    val ad_id                                       : String,
    val actor_fbid                                  : String,
    val is_ad4ad                                    : String,
    val commenting_disabled_for_viewer              : String,
    val is_seen                                     : String,
    val story_events                                : String,
    val story_hashtags                              : MutableList<StoryHashtag>,
    val story_polls                                 : String,
    val story_feed_media                            : String,
    val story_sound_on                              : String,
    val creative_config                             : String,
    val story_locations                             : MutableList<StoryLocation>,
    val story_sliders                               : String,
    val story_friend_lists                          : String,
    val story_product_items                         : String,
    val story_questions                             : MutableList<StoryQuestions>,
    val story_question_responder_infos              : MutableList<StoryQuestionResponderInfos>,
    val story_countdowns                            : MutableList<StoryCountdowns>,
    val story_music_stickers                        : String,
    val supports_reel_reactions                     : Boolean,
    val show_one_tap_fb_share_tooltip               : Boolean,
    val has_shared_to_fb                            : Boolean,
    val main_feed_carousel_starting_media_id        : String,
    val main_feed_carousel_has_unseen_cover_media   : Boolean,
    val inventory_source                            : String,
    val is_eof                                      : Boolean,
    val top_followers                               : MutableList<String>,
    val top_followers_count                         : Int,
    val story_is_saved_to_archive                   : Boolean,
    val timezone_offset                             : Int,
    val product_tags                                : ProductTags,
    val inline_composer_display_condition           : String,
    val inline_composer_imp_trigger_time            : Int,
    val highlight_reel_ids                          : MutableList<String>,
    val total_screenshot_count                      : Int,
    val dominant_color                              : String
){
    val PHOTO = 1
    val VIDEO = 2
    val CAROUSEL = 8

//    val JSON_PROPERTY_MAP = mapOf(
//        /*
//         * The Unix timestamp (UTC) of when the media was UPLOADED by the user.
//         * It is NOT when the media was "taken". It"s the upload time.
//         */
//        "taken_at"                                 to "string",
//        "pk"                                       to "string",
//        "id"                                       to "string",
//        "device_timestamp"                         to "string",
//        /*
//         * A number describing what type of media this is. Should be compared
//         * against the `Item::PHOTO`, `Item::VIDEO` and `Item::CAROUSEL` constants!
//         */
//        "media_type"                               to "int",
//        "dynamic_item_id"                          to "string",
//        "code"                                     to "string",
//        "client_cache_key"                         to "string",
//        "filter_type"                              to "int",
//        "product_type"                             to "string",
//        "nearly_complete_copyright_match"          to "bool",
//        "image_versions2"                          to "Image_Versions2",
//        "original_width"                           to "int",
//        "original_height"                          to "int",
//        "caption_position"                         to "float",
//        "is_reel_media"                            to "bool",
//        "video_versions"                           to "VideoVersions[]",
//        "has_audio"                                to "bool",
//        "video_duration"                           to "float",
//        "user"                                     to "User",
//        "can_see_insights_as_brand"                to "bool",
//        "caption"                                  to "Caption",
//        "title"                                    to "string",
//        "caption_is_edited"                        to "bool",
//        "photo_of_you"                             to "bool",
//        "fb_user_tags"                             to "Usertag",
//        "can_viewer_save"                          to "bool",
//        "has_viewer_saved"                         to "bool",
//        "organic_tracking_token"                   to "string",
//        "follow_hashtag_info"                      to "Hashtag",
//        "expiring_at"                              to "string",
//        "is_dash_eligible"                         to "int",
//        "video_dash_manifest"                      to "string",
//        "number_of_qualities"                      to "int",
//        "video_codec"                              to "string",
//        "thumbnails"                               to "Thumbnail",
//        "can_reshare"                              to "bool",
//        "can_reply"                                to "bool",
//        "can_viewer_reshare"                       to "bool",
//        "visibility"                               to "",
//        "attribution"                              to "Attribution",
//        /*
//         * This is actually a float in the reply, but is always `.0`, so we cast
//         * it to an int instead to make the number easier to manage.
//         */
//        "view_count"                       to "int",
//        "viewer_count"                     to "int",
//        "comment_count"                    to "int",
//        "can_view_more_preview_comments"   to "bool",
//        "has_more_comments"                to "bool",
//        "max_num_visible_preview_comments" to "int",
//        /*
//         * Preview of comments via feed replies.
//         *
//         * If "has_more_comments" is FALSE, then this has ALL of the comments.
//         * Otherwise, you"ll need to get all comments by querying the media.
//         */
//        "preview_comments"                 to "Comment[]",
//        /*
//         * Comments for the item.
//         *
//         * TODO: As of mid-2017, this field seems to no longer be used for
//         * timeline feed items? They now import "preview_comments" instead. But we
//         * won"t delete it, since some other feed MAY import this property for ITS
//         * Item object.
//         */
//        "comments"                                    to "Comment[]",
//        "comments_disabled"                           to "",
//        "reel_mentions"                               to "ReelMention[]",
//        "story_cta"                                   to "StoryCta[]",
//        "next_max_id"                                 to "string",
//        "carousel_media"                              to "CarouselMedia[]",
//        "carousel_media_type"                         to "",
//        "carousel_media_count"                        to "int",
//        "likers"                                      to "User[]",
//        "like_count"                                  to "int",
//        "preview"                                     to "string",
//        "has_liked"                                   to "bool",
//        "explore_context"                             to "string",
//        "explore_source_token"                        to "string",
//        "explore_hide_comments"                       to "bool",
//        "explore"                                     to "Explore",
//        "impression_token"                            to "string",
//        "usertags"                                    to "Usertag",
//        "media"                                       to "",
//        "stories"                                     to "Stories",
//        "top_likers"                                  to "string[]",
//        "direct_reply_to_author_enabled"              to "bool",
//        "suggested_users"                             to "SuggestedUsers",
//        "is_new_suggestion"                           to "bool",
//        "comment_likes_enabled"                       to "bool",
//        "location"                                    to "Location",
//        "lat"                                         to "float",
//        "lng"                                         to "float",
//        "channel"                                     to "Channel",
//        "gating"                                      to "Gating",
//        "injected"                                    to "Injected",
//        "placeholder"                                 to "Placeholder",
//        "algorithm"                                   to "string",
//        "connection_id"                               to "string",
//        "social_context"                              to "string",
//        "icon"                                        to "",
//        "media_ids"                                   to "string[]",
//        "media_id"                                    to "string",
//        "thumbnail_urls"                              to "",
//        "large_urls"                                  to "",
//        "media_infos"                                 to "",
//        "value"                                       to "float",
//        "collapse_comments"                           to "bool",
//        "link"                                        to "string",
//        "link_text"                                   to "string",
//        "link_hint_text"                              to "string",
//        "iTunesItem"                                  to "",
//        "ad_header_style"                             to "int",
//        "ad_metadata"                                 to "AdMetadata[]",
//        "ad_action"                                   to "string",
//        "ad_link_type"                                to "int",
//        "dr_ad_type"                                  to "int",
//        "android_links"                               to "AndroidLinks[]",
//        "ios_links"                                   to "IOSLinks[]",
//        "force_overlay"                               to "bool",
//        "hide_nux_text"                               to "bool",
//        "overlay_text"                                to "string",
//        "overlay_title"                               to "string",
//        "overlay_subtitle"                            to "string",
//        "fb_page_url"                                 to "string",
//        "playback_duration_secs"                      to "",
//        "url_expire_at_secs"                          to "",
//        "is_sidecar_child"                            to "",
//        "comment_threading_enabled"                   to "bool",
//        "cover_media"                                 to "CoverMedia",
//        "saved_collection_ids"                        to "string[]",
//        "boosted_status"                              to "",
//        "boost_unavailable_reason"                    to "",
//        "viewers"                                     to "User[]",
//        "viewer_cursor"                               to "",
//        "total_viewer_count"                          to "int",
//        "multi_author_reel_names"                     to "",
//        "screenshotter_user_ids"                      to "",
//        "reel_share"                                  to "ReelShare",
//        "organic_post_id"                             to "string",
//        "sponsor_tags"                                to "User[]",
//        "story_poll_voter_infos"                      to "",
//        "imported_taken_at"                           to "",
//        "lead_gen_form_id"                            to "string",
//        "ad_id"                                       to "string",
//        "actor_fbid"                                  to "string",
//        "is_ad4ad"                                    to "",
//        "commenting_disabled_for_viewer"              to "",
//        "is_seen"                                     to "",
//        "story_events"                                to "",
//        "story_hashtags"                              to "StoryHashtag[]",
//        "story_polls"                                 to "",
//        "story_feed_media"                            to "",
//        "story_sound_on"                              to "",
//        "creative_config"                             to "",
//        "story_locations"                             to "StoryLocation[]",
//        "story_sliders"                               to "",
//        "story_friend_lists"                          to "",
//        "story_product_items"                         to "",
//        "story_questions"                             to "StoryQuestions[]",
//        "story_question_responder_infos"              to "StoryQuestionResponderInfos[]",
//        "story_countdowns"                            to "StoryCountdowns[]",
//        "story_music_stickers"                        to "",
//        "supports_reel_reactions"                     to "bool",
//        "show_one_tap_fb_share_tooltip"               to "bool",
//        "has_shared_to_fb"                            to "bool",
//        "main_feed_carousel_starting_media_id"        to "string",
//        "main_feed_carousel_has_unseen_cover_media"   to "bool",
//        "inventory_source"                            to "string",
//        "is_eof"                                      to "bool",
//        "top_followers"                               to "string[]",
//        "top_followers_count"                         to "int",
//        "story_is_saved_to_archive"                   to "bool",
//        "timezone_offset"                             to "int",
//        "product_tags"                                to "ProductTags",
//        "inline_composer_display_condition"           to "string",
//        "inline_composer_imp_trigger_time"            to "int",
//        "highlight_reel_ids"                          to "string[]",
//        "total_screenshot_count"                      to "int",
//        /*
//         * HTML color string such as "#812A2A".
//         */
//        "dominant_color"                              to "string"
//    )

    /**
     * Get the web URL for this media item.
     *
     * @return string
     */
    fun getItemUrl(): String{
        return "https://www.instagram.com/p/${this._getProperty("code")}/"
    }

    /**
     * Checks whether this media item is an advertisement.
     *
     * @return bool
     */
    fun isAd(): Boolean{
        return this._getProperty("dr_ad_type") !== null
    }
}
