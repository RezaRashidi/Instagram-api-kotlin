package instagramAPI.requests

import instagramAPI.Constants
import instagramAPI.Instagram
import instagramAPI.Response
import com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date

/**
 * Business-account related funs.
 *
 * These only work if you have a Business account.
 */
class Business(instagram: Instagram) : RequestCollection(instagram) {
	/**
	 * Get insights.
	 *
	 * @param day
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.InsightsResponse
	 */
	fun getInsights(day:String? = null) {
		if (empty(day)) {
			day = date("d")
		}

		return this.ig.request("insights/account_organic_insights/").addParam("show_promotions_in_landing_page", "true")
			.addParam("first", day).getResponse(Response.InsightsResponse())
	}

	/**
	 * Get media insights.
	 *
	 * @param string mediaId The media ID in Instagram"s internal format (ie "3482384834_43294").
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.MediaInsightsResponse
	 */
	fun getMediaInsights(mediaId:String) {
		return this.ig.request("insights/media_organic_insights/{mediaId}/")
			.addParam("ig_sig_key_version", Constants::SIG_KEY_VERSION).getResponse(Response.MediaInsightsResponse())
	}

	/**
	 * Get account statistics.
	 *
	 * @throws .instagramAPI.exception.InstagramException
	 *
	 * @return .instagramAPI.responses.GraphqlResponse
	 */
	fun getStatistics() {
		return this.ig.request("ads/graphql/").setSignedPost(false).setIsMultiResponse(true)
			.addParam("locale", Constants.USER_AGENT_LOCALE).addParam("vc_policy", "insights_policy")
			.addParam("surface", "account").addPost("access_token", "undefined")
			.addPost("fb_api_caller_class", "RelayModern")
			.addPost("variables", json_encode(["IgInsightsGridMediaImage_SIZE" => 240,
			         "timezone"                      => "Atlantic/Canary",
		"activityTab"                   => true,
		"audienceTab"                   => true,
		"contentTab"                    => true,
		"query_params"                  => json_encode([
		"access_token"  => "",
		"id"            => this.ig.account_id,
		]),
		]))
		.addPost("doc_id", "1926322010754880").getResponse(Response.GraphqlResponse())
	}
}
