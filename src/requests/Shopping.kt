package instagramAPI.requests

import instagramAPI.Constants
import instagramAPI.Instagram
import instagramAPI.Response

/**
 * funs related to Shopping and catalogs.
 */
class Shopping(instagram: Instagram) : RequestCollection(instagram)
{
    /**
     * Get on tag product information.
     *
     * @param string productId   The product ID.
     * @param string mediaId     The media ID in Instagram"s internal format (ie "1820978425064383299").
     * @param string merchantId  The merchant ID in Instagram"s internal format (ie "20100000").
     * @param int    deviceWidth Device width (optional).
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.OnTagProductResponse
     */
    fun getOnTagProductInfo(
        productId,
        mediaId,
        merchantId,
        deviceWidth = 720)
    {
        return this.ig.request("commerce/products/{productId}/details/")
            .addParam("source_media_id", mediaId)
            .addParam("merchant_id", merchantId)
            .addParam("device_width", deviceWidth)
            .addParam("hero_carousel_enabled", false)
            .getResponse(Response.OnTagProductResponse())
    }

    /**
     * Get catalogs.
     *
     * @param string locale The device user"s locale, such as "en_US.
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.GraphqlResponse
     */
    fun getCatalogs(
        locale = "en_US")
    {
        return this.ig.request("wwwgraphql/ig/query/")
            .addParam("locale", locale)
            .addUnsignedPost("access_token", "undefined")
            .addUnsignedPost("fb_api_caller_class", "RelayModern")
            .addUnsignedPost("variables", ["sources" => null])
            .addUnsignedPost("doc_id", "1742970149122229")
            .getResponse(Response.GraphqlResponse())
    }

    /**
     * Get catalog items.
     *
     * @param string catalogId The catalog"s ID.
     * @param string query     Finds products containing this string.
     * @param int    offset    Offset, used for pagination. Values must be multiples of 20.
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.GraphqlResponse
     */
    fun getCatalogItems(
        catalogId,
        query = "",
        offset = null)
    {
        if (offset !== null) {
            if (offset % 20 !== 0) {
                throw  IllegalArgumentException("Offset must be multiple of 20.")
            }
            offset = [
                "offset" => offset,
                "tier"   => "products.elasticsearch.thrift.atn",
            ]
        }

        queryParams = [
            query,
            catalogId,
            "96",
            "20",
            json_encode(offset),
        ]

        return this.ig.request("wwwgraphql/ig/query/")
            .addUnsignedPost("doc_id", "1747750168640998")
            .addUnsignedPost("locale", Constants::ACCEPT_LANGUAGE)
            .addUnsignedPost("vc_policy", "default")
            .addUnsignedPost("strip_nulls", "true")
            .addUnsignedPost("strip_defaults", "true")
            .addUnsignedPost("query_params", json_encode(queryParams, JSON_FORCE_OBJECT))
            .getResponse(Response.GraphqlResponse())
    }

    /**
     * Sets on board catalog.
     *
     * @param string catalogId The catalog"s ID.
     *
     * @throws .instagramAPI.exception.InstagramException
     *
     * @return .instagramAPI.responses.OnBoardCatalogResponse
     */
    fun setOnBoardCatalog(
        catalogId)
    {
        return this.ig.request("commerce/onboard/")
            .addPost("current_catalog_id", catalogId)
            .addPost("_uid", this.ig.account_id)
            .addPost("_uuid", this.ig.uuid)
            .addPost("_csrftoken", this.ig.client.getToken())
            .getResponse(Response.OnBoardCatalogResponse())
    }
}
