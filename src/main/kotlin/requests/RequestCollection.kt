

package instagramAPI.requests

import instagramAPI.Instagram
import instagramAPI.Utils
/**
 * Base class for grouping multiple related request funs.
 */
open class RequestCollection() {
    /** @var Instagram The parent class instance we belong to. */
    lateinit  var ig:Instagram

    /**
     * Constructor.
     *
     * @param Instagram parent The parent class instance we belong to.
     */
   constructor(parent:Instagram) : this() {
        this.ig = parent
    }

    /**
     * Paginate the request by given exclusion list.
     *
     * @param Request     request     The request to paginate.
     * @param array       excludeList Array of numerical entity IDs (ie "4021088339")
     *                                 to exclude from the response, allowing you to skip entities
     *                                 from a previous call to get more results.
     * @param string|null rankToken   The rank token from the previous page"s response.
     * @param int         limit       Limit the number of results per page.
     *
     * @throws  IllegalArgumentException
     *
     * @return Request
     */
    protected fun paginateWithExclusion(
        Request request,
        array excludeList = [],
        rankToken = null,
        limit = 30)
    {
        if (!count(excludeList)) {
            return request.addParam("count", (string) limit)
        }

        if (rankToken === null) {
            throw  IllegalArgumentException("You must supply the rank token for the pagination.")
        }
        Utils::throwIfInvalidRankToken(rankToken)

        return request
            .addParam("count", (string) limit)
            .addParam("exclude_list", "[".implode(", ", excludeList)."]")
            .addParam("rank_token", rankToken)
    }

    /**
     * Paginate the request by given multi-exclusion list.
     *
     * @param Request     request     The request to paginate.
     * @param array       excludeList Array of grouped numerical entity IDs (ie "users" => ["4021088339"])
     *                                 to exclude from the response, allowing you to skip entities
     *                                 from a previous call to get more results.
     * @param string|null rankToken   The rank token from the previous page"s response.
     * @param int         limit       Limit the number of results per page.
     *
     * @throws  IllegalArgumentException
     *
     * @return Request
     */
    protected fun paginateWithMultiExclusion(
        Request request,
        array excludeList = [],
        rankToken = null,
        limit = 30)
    {
        if (!count(excludeList)) {
            return request.addParam("count", (string) limit)
        }

        if (rankToken === null) {
            throw  IllegalArgumentException("You must supply the rank token for the pagination.")
        }
        Utils::throwIfInvalidRankToken(rankToken)

        exclude = []
        totalCount = 0
        foreach (excludeList as group => ids) {
            totalCount += count(ids)
            exclude[] = "."{group}.":[".implode(", ", ids)."]"
        }

        return request
            .addParam("count", (string) limit)
            .addParam("exclude_list", "{".implode(",", exclude)."}")
            .addParam("rank_token", rankToken)
    }
}
