

package instagramAPI.requests

import instagramAPI.Constants
import instagramAPI.Instagram
import instagramAPI.Response

/**
 * funs related to Instagram"s "creative assets", such as stickers.
 */
class Creative(instagram: Instagram) : RequestCollection(instagram)
{
    /**
     * Get sticker assets.
     *
     * NOTE: This gives you a list of the stickers that the app can "paste" on
     * top of story media. If you want to import any of them, you will have to
     * apply them MANUALLY via some external image/video editor or library!
     *
     * @param string     stickerType Type of sticker (currently only "static_stickers").
     * @param array|null location    (optional) Array containing lat, lng and horizontalAccuracy.
     *
     * @throws  IllegalArgumentException
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.StickerAssetsResponse
     */
    fun getStickerAssets(
        stickerType:String = "static_stickers",
        array location = null)
    {
        if (stickerType != "static_stickers") {
            throw  IllegalArgumentException("You must provide a valid sticker type.")
        }
        if (location !== null && (!isset(location["lat"])
                                    || !isset(location["lng"])
                                    || !isset(location["horizontalAccuracy"]))) {
            throw  IllegalArgumentException("Your location array must contain keys for "lat", "lng" and "horizontalAccuracy".")
        }

        request = this.ig.request("creatives/assets/")
            .addPost("type", stickerType)

        if (location !== null) {
            request
                .addPost("lat", location["lat"])
                .addPost("lng", location["lng"])
                .addPost("horizontalAccuracy", location["horizontalAccuracy"])
        }

        return request.getResponse(Response.StickerAssetsResponse())
    }

    /**
     * Get face models that can be used to customize photos or videos.
     *
     * NOTE: The files are some strange binary format that only the Instagram
     * app understands. If anyone figures out the format, please contact us.
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.FaceModelsResponse
     */
    fun getFaceModels()
    {
        return this.ig.request("creatives/face_models/")
            .addPost("_uuid", this.ig.uuid)
            .addPost("_uid", this.ig.account_id)
            .addPost("_csrftoken", this.ig.client.getToken())
            .addPost("aml_facetracker_model_version", 12)
            .getResponse(Response.FaceModelsResponse())
    }

    /**
     * Get face overlay effects to customize photos or videos.
     *
     * These are effects such as "bunny ears" and similar overlays.
     *
     * NOTE: The files are some strange binary format that only the Instagram
     * app understands. If anyone figures out the format, please contact us.
     *
     * @param array|null location (optional) Array containing lat, lng and horizontalAccuracy.
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.FaceEffectsResponse
     */
    fun getFaceEffects(
        array location = null)
    {
        request = this.ig.request("creatives/face_effects/")
            .addPost("_uuid", this.ig.uuid)
            .addPost("_uid", this.ig.account_id)
            .addPost("_csrftoken", this.ig.client.getToken())
            .addPost("supported_capabilities_new", json_encode(Constants::SUPPORTED_CAPABILITIES))

        if (location !== null) {
            request
                .addPost("lat", location["lat"])
                .addPost("lng", location["lng"])
                .addPost("horizontalAccuracy", location["horizontalAccuracy"])
        }

        return request.getResponse(Response.FaceEffectsResponse())
    }
}
