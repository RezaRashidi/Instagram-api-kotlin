

package InstagramAPI.Exception

import InstagramAPI.Response

/**
 * The core exception that ALL other library exceptions derive from.
 *
 * If you catch this exception, you KNOW it came from our Instagram-API library.
 */
open class InstagramException //// TODO: implement RuntimeException on Kotlin
{
    /**
     * The full response that triggered the exception, if available.
     *
     * @var Response|null
     */
    private var _response:Response? = null

    /**
     * Check whether the exception has a full server response.
     *
     * @return bool TRUE if a full response is available, otherwise FALSE.
     */
     fun hasResponse():Boolean
    {
        return _response !== null
    }

    /**
     * Get the full server response.
     *
     * @return Response|null The full response if one exists, otherwise NULL.
     *
     * @see InstagramException::hasResponse()
     */
     fun getResponse():Response?
    {
        return this._response
    }

    /**
     * Internal. Sets the value of the full server response.
     *
     * @param Response|null $response The response value.
     */
     fun setResponse(
         response:Response? = null)
    {
        this._response = response
    }
}
