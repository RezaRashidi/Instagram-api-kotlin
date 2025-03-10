

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Location.
 *
 * @method string getAddress()
 * @method string getCity()
 * @method int getCountry()
 * @method string getCreatedAt()
 * @method mixed getEndTime()
 * @method int getEventCategory()
 * @method string getExternalId()
 * @method string getExternalIdSource()
 * @method string getExternalSource()
 * @method string getFacebookEventsId()
 * @method string getFacebookPlacesId()
 * @method float getLat()
 * @method float getLng()
 * @method Location getLocationDict()
 * @method string getName()
 * @method string getPk()
 * @method string getPlaceFbid()
 * @method string getPlaceName()
 * @method string getProfilePicUrl()
 * @method string getProfilePicUsername()
 * @method string getShortName()
 * @method mixed getStartTime()
 * @method mixed getTimeGranularity()
 * @method mixed getTimezone()
 * @method mixed getType()
 * @method bool isAddress()
 * @method bool isCity()
 * @method bool isCountry()
 * @method bool isCreatedAt()
 * @method bool isEndTime()
 * @method bool isEventCategory()
 * @method bool isExternalId()
 * @method bool isExternalIdSource()
 * @method bool isExternalSource()
 * @method bool isFacebookEventsId()
 * @method bool isFacebookPlacesId()
 * @method bool isLat()
 * @method bool isLng()
 * @method bool isLocationDict()
 * @method bool isName()
 * @method bool isPk()
 * @method bool isPlaceFbid()
 * @method bool isPlaceName()
 * @method bool isProfilePicUrl()
 * @method bool isProfilePicUsername()
 * @method bool isShortName()
 * @method bool isStartTime()
 * @method bool isTimeGranularity()
 * @method bool isTimezone()
 * @method bool isType()
 * @method this setAddress(string $value)
 * @method this setCity(string $value)
 * @method this setCountry(int $value)
 * @method this setCreatedAt(string $value)
 * @method this setEndTime(mixed $value)
 * @method this setEventCategory(int $value)
 * @method this setExternalId(string $value)
 * @method this setExternalIdSource(string $value)
 * @method this setExternalSource(string $value)
 * @method this setFacebookEventsId(string $value)
 * @method this setFacebookPlacesId(string $value)
 * @method this setLat(float $value)
 * @method this setLng(float $value)
 * @method this setLocationDict(Location $value)
 * @method this setName(string $value)
 * @method this setPk(string $value)
 * @method this setPlaceFbid(string $value)
 * @method this setPlaceName(string $value)
 * @method this setProfilePicUrl(string $value)
 * @method this setProfilePicUsername(string $value)
 * @method this setShortName(string $value)
 * @method this setStartTime(mixed $value)
 * @method this setTimeGranularity(mixed $value)
 * @method this setTimezone(mixed $value)
 * @method this setType(mixed $value)
 * @method this unsetAddress()
 * @method this unsetCity()
 * @method this unsetCountry()
 * @method this unsetCreatedAt()
 * @method this unsetEndTime()
 * @method this unsetEventCategory()
 * @method this unsetExternalId()
 * @method this unsetExternalIdSource()
 * @method this unsetExternalSource()
 * @method this unsetFacebookEventsId()
 * @method this unsetFacebookPlacesId()
 * @method this unsetLat()
 * @method this unsetLng()
 * @method this unsetLocationDict()
 * @method this unsetName()
 * @method this unsetPk()
 * @method this unsetPlaceFbid()
 * @method this unsetPlaceName()
 * @method this unsetProfilePicUrl()
 * @method this unsetProfilePicUsername()
 * @method this unsetShortName()
 * @method this unsetStartTime()
 * @method this unsetTimeGranularity()
 * @method this unsetTimezone()
 * @method this unsetType()
 */
data class Location (
    val name: String,
    val external_id_source: String,
    val external_source: String,
    val address: String,
    val lat: Float,
    val lng: Float,
    val external_id: String,
    val facebook_places_id: String,
    val city: String,
    val pk: String,
    val short_name: String,
    val facebook_events_id: String,
    val start_time: String,
    val end_time: String,
    val location_dict: Location,
    val type: String,
    val profile_pic_url: String,
    val profile_pic_username: String,
    val time_granularity: String,
    val timezone: String,
    val country: Int,
    val created_at: String,
    val event_category: Int,
    val place_fbid: String,
    val place_name: String
) {
//    val JSON_PROPERTY_MAP = mapOf(
//        "name"                 to "string",
//        "external_id_source"   to "string",
//        "external_source"      to "string",
//        "address"              to "string",
//        "lat"                  to "float",
//        "lng"                  to "float",
//        "external_id"          to "string",
//        "facebook_places_id"   to "string",
//        "city"                 to "string",
//        "pk"                   to "string",
//        "short_name"           to "string",
//        "facebook_events_id"   to "string",
//        "start_time"           to "",
//        "end_time"             to "",
//        "location_dict"        to "Location",
//        "type"                 to "",
//        "profile_pic_url"      to "string",
//        "profile_pic_username" to "string",
//        "time_granularity"     to "",
//        "timezone"             to "",
//        /*
//         * Country number such as int(398), but it has no relation to actual
//         * country codes, so the number is useless...
//         */
//        "country"              to "int",
//        /*
//         * Regular unix timestamp of when the location was created.
//         */
//        "created_at"           to "string",
//        /*
//         * Some kind of internal number to signify what type of event a special
//         * location (such as a festival) is. We"ve only seen this with int(0).
//         */
//        "event_category"       to "int",
//        /*
//         * 64-bit integer with the facebook places ID for the location.
//         */
//        "place_fbid"           to "string",
//        /*
//         * Human-readable name of the facebook place for the location.
//         */
//        "place_name"           to "string"
//    )
}
