

package InstagramAPI.Request

import InstagramAPI.Instagram
import InstagramAPI.Request
import InstagramAPI.Utils

/**
 * Base class for grouping multiple related request funs.
 */
class RequestCollection
{
    /** @var Instagram The parent class instance we belong to. */
    public $ig

    /**
     * Constructor.
     *
     * @param Instagram $parent The parent class instance we belong to.
     */
    public fun __construct(
        $parent)
    {
        this.ig = $parent
    }

    /**
     * Paginate the request by given exclusion list.
     *
     * @param Request     $request     The request to paginate.
     * @param array       $excludeList Array of numerical entity IDs (ie "4021088339")
     *                                 to exclude from the response, allowing you to skip entities
     *                                 from a previous call to get more results.
     * @param string|null $rankToken   The rank token from the previous page's response.
     * @param int         $limit       Limit the number of results per page.
     *
     * @throws .InvalidArgumentException
     *
     * @return Request
     */
    protected fun _paginateWithExclusion(
        Request $request,
        array $excludeList = [],
        $rankToken = null,
        $limit = 30)
    {
        if (!count($excludeList)) {
            return $request.addParam('count', (string) $limit)
        }

        if ($rankToken === null) {
            throw .InvalidArgumentException('You must supply the rank token for the pagination.')
        }
        Utils::throwIfInvalidRankToken($rankToken)

        return $request
            .addParam('count', (string) $limit)
            .addParam('exclude_list', '['.implode(', ', $excludeList).']')
            .addParam('rank_token', $rankToken)
    }

    /**
     * Paginate the request by given multi-exclusion list.
     *
     * @param Request     $request     The request to paginate.
     * @param array       $excludeList Array of grouped numerical entity IDs (ie "users" => ["4021088339"])
     *                                 to exclude from the response, allowing you to skip entities
     *                                 from a previous call to get more results.
     * @param string|null $rankToken   The rank token from the previous page's response.
     * @param int         $limit       Limit the number of results per page.
     *
     * @throws .InvalidArgumentException
     *
     * @return Request
     */
    protected fun _paginateWithMultiExclusion(
        Request $request,
        array $excludeList = [],
        $rankToken = null,
        $limit = 30)
    {
        if (!count($excludeList)) {
            return $request.addParam('count', (string) $limit)
        }

        if ($rankToken === null) {
            throw .InvalidArgumentException('You must supply the rank token for the pagination.')
        }
        Utils::throwIfInvalidRankToken($rankToken)

        $exclude = []
        $totalCount = 0
        foreach ($excludeList as $group => $ids) {
            $totalCount += count($ids)
            $exclude[] = "."{$group}.":[".implode(', ', $ids).']'
        }

        return $request
            .addParam('count', (string) $limit)
            .addParam('exclude_list', '{'.implode(',', $exclude).'}')
            .addParam('rank_token', $rankToken)
    }
}
