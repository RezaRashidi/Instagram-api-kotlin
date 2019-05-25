

package InstagramAPI.Request

import InstagramAPI.Response

/**
 * funs related to managing and exploring user tags in media.
 */
class Usertag : RequestCollection
{
    /**
     * Tag a user in a media item.
     *
     * @param string  $mediaId     The media ID in Instagram's internal format (ie "3482384834_43294").
     * @param string  $userId      Numerical UserPK ID.
     * @param float[] $position    Position relative to image where the tag should sit. Example: [0.4890625,0.6140625]
     * @param string  $captionText Caption to import for the media.
     *
     * @throws .InvalidArgumentException
     * @throws .InstagramAPI.Exception.InstagramException
     *
     * @return .InstagramAPI.Response.EditMediaResponse
     */
    public fun tagMedia(
        $mediaId,
        $userId,
        array $position,
        $captionText = '')
    {
        $usertags = [
            'removed' => [],
            'in'      => [
                ['position' => $position, 'user_id' => $userId],
            ],
        ]

        return this.ig.media.edit($mediaId, $captionText, ['usertags' => $usertags])
    }

    /**
     * Untag a user from a media item.
     *
     * @param string $mediaId     The media ID in Instagram's internal format (ie "3482384834_43294").
     * @param string $userId      Numerical UserPK ID.
     * @param string $captionText Caption to import for the media.
     *
     * @throws .InvalidArgumentException
     * @throws .InstagramAPI.Exception.InstagramException
     *
     * @return .InstagramAPI.Response.EditMediaResponse
     */
    public fun untagMedia(
        $mediaId,
        $userId,
        $captionText = '')
    {
        $usertags = [
            'removed' => [
                $userId,
            ],
            'in'      => [],
        ]

        return this.ig.media.edit($mediaId, $captionText, ['usertags' => $usertags])
    }

    /**
     * Remove yourself from a tagged media item.
     *
     * @param string $mediaId The media ID in Instagram's internal format (ie "3482384834_43294").
     *
     * @throws .InstagramAPI.Exception.InstagramException
     *
     * @return .InstagramAPI.Response.MediaInfoResponse
     */
    public fun removeSelfTag(
        $mediaId)
    {
        return this.ig.request("usertags/{$mediaId}/remove/")
            .addPost('_uuid', this.ig.uuid)
            .addPost('_uid', this.ig.account_id)
            .addPost('_csrftoken', this.ig.client.getToken())
            .getResponse(Response.MediaInfoResponse())
    }

    /**
     * Get user taggings for a user.
     *
     * @param string      $userId Numerical UserPK ID.
     * @param string|null $maxId  Next "maximum ID", used for pagination.
     *
     * @throws .InstagramAPI.Exception.InstagramException
     *
     * @return .InstagramAPI.Response.UsertagsResponse
     */
    public fun getUserFeed(
        $userId,
        $maxId = null)
    {
        $request = this.ig.request("usertags/{$userId}/feed/")

        if ($maxId !== null) {
            $request.addParam('max_id', $maxId)
        }

        return $request.getResponse(Response.UsertagsResponse())
    }

    /**
     * Get user taggings for your own account.
     *
     * @param string|null $maxId Next "maximum ID", used for pagination.
     *
     * @throws .InstagramAPI.Exception.InstagramException
     *
     * @return .InstagramAPI.Response.UsertagsResponse
     */
    public fun getSelfUserFeed(
        $maxId = null)
    {
        return this.getUserFeed(this.ig.account_id, $maxId)
    }

    /**
     * Choose how photos you are tagged in will be added to your profile.
     *
     * @param bool $enabled TRUE to manually accept photos, or FALSE to accept automatically.
     *
     * @throws .InstagramAPI.Exception.InstagramException
     *
     * @return .InstagramAPI.Response.ReviewPreferenceResponse
     */
    public fun setReviewPreference(
        $enabled)
    {
        return this.ig.request('usertags/review_preference/')
            .addPost('_uuid', this.ig.uuid)
            .addPost('_uid', this.ig.account_id)
            .addPost('_csrftoken', this.ig.client.getToken())
            .addPost('enabled', (int) $enabled)
            .getResponse(Response.ReviewPreferenceResponse())
    }
}
