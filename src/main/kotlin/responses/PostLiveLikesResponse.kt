

package instagramAPI.responses

import instagramAPI.Response

/**
 * PostLiveLikesResponse.
 *
 * @method mixed getEndingOffset()
 * @method mixed getMessage()
 * @method mixed getNextFetchOffset()
 * @method mixed getStartingOffset()
 * @method string getStatus()
 * @method mixed getTimeSeries()
 * @method model._Message[] get_Messages()
 * @method bool isEndingOffset()
 * @method bool isMessage()
 * @method bool isNextFetchOffset()
 * @method bool isStartingOffset()
 * @method bool isStatus()
 * @method bool isTimeSeries()
 * @method bool is_Messages()
 * @method this setEndingOffset(mixed $value)
 * @method this setMessage(mixed $value)
 * @method this setNextFetchOffset(mixed $value)
 * @method this setStartingOffset(mixed $value)
 * @method this setStatus(string $value)
 * @method this setTimeSeries(mixed $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetEndingOffset()
 * @method this unsetMessage()
 * @method this unsetNextFetchOffset()
 * @method this unsetStartingOffset()
 * @method this unsetStatus()
 * @method this unsetTimeSeries()
 * @method this unset_Messages()
 */
data class PostLiveLikesResponse (
    val starting_offset   : String,
    val ending_offset     : String,
    val next_fetch_offset : String,
    val time_series       : String
){
//    val JSON_PROPERTY_MAP = [
//        "starting_offset"   => "",
//        "ending_offset"     => "",
//        "next_fetch_offset" => "",
//        "time_series"       => "",
//    ]
}
