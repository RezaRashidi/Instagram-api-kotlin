package InstagramAPI

import InstagramAPI.Media.Video.FFmpeg
import InstagramAPI.Response.Model.Item
import InstagramAPI.Response.Model.Location
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter


object Utils{
    /**
     * Override for the default temp path used by various class funs.
     *
     * If this value is non-null, we'll import it. Otherwise we'll import the default
     * system tmp folder.
     *
     * TIP: If your default system temp folder isn't writable, it's NECESSARY
     * for you to set this value to another, writable path, like this:
     *
     * .InstagramAPI.Utils::$defaultTmpPath = '/home/example/foo/'
     */
    var defaultTmpPath = null

    /**
     * Used for multipart boundary generation.
     *
     * @var string
     */
    const val BOUNDARY_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    /**
     * Length of generated multipart boundary.
     *
     * @var int
     */
    const val BOUNDARY_LENGTH = 30

    /**
     * Name of the detected ffmpeg executable.
     *
     * @var string|null
     *
     * @deprecated
     * @see (FFmpeg)::$defaultBinary
     */
    var ffmpegBin = null

    /**
     * Name of the detected ffprobe executable, or FALSE if none found.
     *
     * @var string|bool|null
     */
    var ffprobeBin = null

    /**
     * Last uploadId generated with microtime().
     *
     * @var string|null
     */
    var _lastUploadId: String ?= null

    /**
     * @param (bool) $useNano Whether to return result in usec instead of msec.
     *
     * @return string
     */
    fun generateUploadId(useNano: Boolean = false): String {
        var result: String?
        if (!useNano) {
            while (true) {  // Todo : Time issue -> microtime(true) = System.currentTimeMillis() / 1000
                result = number_format(Math.round(System.currentTimeMillis().toDouble()), 0, '', '')
                if (_lastUploadId !== null && result === _lastUploadId) {
                    // NOTE: Fast machines can process files too quick (< 0.001
                    // sec), which leads to identical upload IDs, which leads to
                    // "500 Oops, an error occurred" errors. So we sleep 0.001
                    // sec to guarantee different upload IDs per each call.
                    usleep(1000)
                } else { // OK!
                    _lastUploadId = result
                    break
                }
            }
        } else {
            // Emulate System.nanoTime().
            result = number_format(System.currentTimeMillis() / 1000 - strtotime("Last Monday"), 6, '', '')
            // Append nanoseconds.
            result += (1..999).random().toString().padStart(3, '0')
        }

        return result.toString()
    }

    /**
     * Calculates Java hashCode() for a given string.
     *
     * WARNING: This method is not Unicode-aware, so import it only on ANSI strings.
     *
     * @param (string) $string
     *
     * @return int
     *
     * @see (https://en.wikipedia.org/wiki/Java_hashCode()#The_java.lang.String_hash_fun)
     */
    fun hashCode(string: String): Int{
        var result: Long = 0
        //for ($i = 0, $len = strlen($string) $i < $len ++$i)
        for (i in 0 until string.length){   //Todo check loop step
            result = (-result + (result.shl(5)) + string[i].toInt()) and 0xFFFFFFFF
        }
        if (PHP_INT_SIZE > 4) {
            if (result > 0x7FFFFFFF) {
                result -= 0x100000000
            } else if (result < -0x80000000) {
                result += 0x100000000
            }
        }

        return result.toInt()
    }

    /**
     * Reorders array by hashCode() of its keys.
     *
     * @param (array) $data
     *
     * @return array
     */
    fun reorderByHashCode(data: Map<String, String>){
        val hashCodes = mutableMapOf<String,String>()
        for ((key,value) in data) {
            hashCodes[key] = hashCode(key)
        }

        uksort(data, fun (a, b) import (hashCodes) {
            var a = hashCodes[a]
            var b = hashCodes[b]
            if (a < b) {
                return -1
            } else if (a > b) {
                return 1
            } else {
                return 0
            }
        })

        return data
    }

    /**
     * Generates random multipart boundary string.
     *
     * @return string
     */
    fun generateMultipartBoundary(): String{
        var result = ""
        val max = BOUNDARY_CHARS.length - 1
        for (i  in 0..BOUNDARY_LENGTH) {    // check loop step
            result += BOUNDARY_CHARS[(0..max).random()]
        }

        return result
    }

    /**
     * Generates user breadcrumb for import when posting a comment.
     *
     * @param (int) $size
     *
     * @return string
     */
    fun generateUserBreadcrumb(size: Int): String{
        val key = "iN4$" + "aGr0m"
        val date = System.currentTimeMillis().toInt()

        // typing time
        val term = (2..3).random() * 1000 + size * (15..20).random() * 100

        // android EditText change event occur count
        var text_change_event_count = Math.round((size / (2..3).random()).toDouble()).toInt()
        if (text_change_event_count == 0) {
            text_change_event_count = 1
        }

        // generate typing data
        val data = "$size $term $text_change_event_count $date"

        // base64_encode(hash_hmac('sha256', $data, $key, true)) => base64hashHmacSha256(key, data)
        // base64_encode($data) => Base64.getEncoder().encodeToString(data.toByteArray())
        val codedData: String = base64hashHmacSha256(key, data)
        return codedData + ".n" + Base64.getEncoder().encodeToString(data.toByteArray()) + ".n"
    }

    /**
     * Converts a hours/minutes/seconds timestamp to seconds.
     *
     * @param (string) $timeStr Either `HH:MM:SS[.###]` (24h-clock) or
     *                        `MM:SS[.###]` or `SS[.###]`. The `[.###]` is for
     *                        optional millisecond precision if wanted, such as
     *                        `00:01:01.149`.
     *
     * @throws IllegalArgumentException If any part of the input is invalid.
     *
     * @return float The number of seconds, with decimals (milliseconds).
     */
    fun hmsTimeToSeconds(timeStr: String): Float{
        if (timeStr !is String) {
            throw IllegalArgumentException("Invalid non-string timestamp.")
        }

        var sec = 0.0
        for ((offsetKey, v) in (timeStr.split(":")).reversed() ) {
            if (offsetKey > 2) {
                throw IllegalArgumentException("Invalid input \"$timeStr\" with too many components (max 3 is allowed \"HH:MM:SS\").")
            }

            // Parse component (supports "01" or "01.123" (milli-precision)).
            if (v === "" || ! ("/^\\d+(?:\\.\\d+)?\$/".toRegex().matches(v)) ) {
                throw IllegalArgumentException("Invalid non-digit or empty component \"$v\" in time string \"$timeStr\".")
            }
            if (offsetKey !== 0 && v.indexOf(".") !== false) {
                throw IllegalArgumentException("Unexpected period in time component \"$v\" in time string \"$timeStr\". Only the seconds-component supports milliseconds.")
            }

            // Convert the value to float and cap minutes/seconds to 60 (but
            // allow any number of hours).
            var v = v as Float
            var maxValue = if(offsetKey < 2) 60 else -1
            if (maxValue >= 0 && v > maxValue) {
                throw IllegalArgumentException("Invalid time component \"${v.toInt()}\" (its allowed range is 0-$maxValue) in time string \"$timeStr\".")
            }

            // Multiply the current component of the "01:02:03" string with the
            // power of its offset. Hour-offset will be 2, Minutes 1 and Secs 0
            // and "pow(60, 0)" will return 1 which is why seconds work too.
            sec += Math.pow(60.toDouble(), offsetKey) * v
        }

        return sec.toFloat()
    }

    /**
     * Converts seconds to a hours/minutes/seconds timestamp.
     *
     * @param (int)|float $sec The number of seconds. Can have fractions (millis).
     *
     * @throws IllegalArgumentException If any part of the input is invalid.
     *
     * @return string The time formatted as `HH:MM:SS.###` (`###` is millis).
     */
    fun hmsTimeFromSeconds(sece: Float): String{
        var sec = sece
        if (sec !is Int && sec !is Float) {
            throw IllegalArgumentException("Seconds must be a number.")
        }

        var wasNegative = false
        if (sec < 0) {
            wasNegative = true
            sec = Math.abs(sec)
        }

        // "%02d:%02d:%06.3f"  // "%06f" is becaimport it counts the whole string.
        val x1 = "%02.0f".format(Math.floor(sec.toDouble() / 3600))
        val x2 = "%02.0f".format(Math.floor((sec.toDouble()/60).rem(60)))
        val x3 = "%06.3f".format(sec.toDouble().rem(60))

        var result = "$x1 : $x2 : $x3"


        if (wasNegative) {
            result = "-$result"
        }

        return result
    }

    /**
     * Builds an Instagram media location JSON object in the correct format.
     *
     * This fun is used whenever we need to send a location to Instagram"s
     * API. All endpoints (so far) expect location data in this exact format.
     *
     * @param (Location) $location A model object representing the location.
     *
     * @throws IllegalArgumentException If the location is invalid.
     *
     * @return string The final JSON string ready to submit as an API parameter.
     */
    fun buildMediaLocationJSON(location: Location): String{
        if (location !is Location) {
            throw IllegalArgumentException("The location must be an instance of \\InstagramAPI\\Response\\Model\\Location.")
        }

        // Forbid locations that came from Location::searchFacebook() and
        // Location::searchFacebookByPoint()! They have slightly different
        // properties, and they don"t always contain all data we need. The
        // real application NEVER uses the "Facebook" endpoints for attaching
        // locations to media, and NEITHER SHOULD WE.
        if (location.getFacebookPlacesId() !== null) { // Todo : use method of object that doesn't exist
            throw IllegalArgumentException("You are not allowed to import Location model objects from the Facebook-based location search funs. They are not valid media locations!")
        }

        // Core location keys that always exist.
        var obj = mutableMapOf<String,Objects>(
            "name"            to location.getName(),
            "lat"             to location.getLat(),
            "lng"             to location.getLng(),
            "address"         to location.getAddress(),
            "external_source" to location.getExternalIdSource()
        )

        // Attach the location ID via a dynamically generated key.
        // NOTE: This automatically generates a key such as "facebook_places_id".
        val key = location.getExternalIdSource() + "_id"
        obj[key] = location.getExternalId()

        // Ensure that all keys are listed in the correct hash order.
        obj = reorderByHashCode(obj)

        return json_encode(obj)
    }

    /**
     * Check for ffprobe dependency.
     *
     * TIP: If your binary isn"t findable via the PATH environment locations,
     * you can manually set the correct path to it. Before calling any funs
     * that need FFprobe, you must simply assign a manual value (ONCE) to tell
     * us where to find your FFprobe, like this:
     *
     * .InstagramAPI.Utils::$ffprobeBin = "/home/exampleuser/ffmpeg/bin/ffprobe"
     *
     * @return string|bool Name of the library if present, otherwise FALSE.
     */
    fun checkFFPROBE(){
        // We only resolve this once per session and then cache the result.
        if (ffprobeBin === null) {
            @exec("ffprobe -version 2>&1", output, statusCode)
            if (statusCode === 0) {
                ffprobeBin = "ffprobe"
            } else {
                ffprobeBin = false // Nothing found!
            }
        }

        return ffprobeBin
    }

    /**
     * Verifies a user tag.
     *
     * Ensures that the input strictly contains the exact keys necessary for
     * user tag, and with proper values for them. We cannot validate that the
     * user-id actually exists, but that"s the job of the library user!
     *
     * @param (mixed) $userTag An array containing the user ID and the tag position.
     *                       Example: ["position"=>[0.5,0.5],"user_id"=>"123"].
     *
     * @throws IllegalArgumentException If the tag is invalid.
     */
    fun throwIfInvalidUserTag(userTag){
        // NOTE: We can import "array" type hint, but it doesn't give us enough freedom.
        if (userTag !is Array) {
            throw IllegalArgumentException("User tag must be an array.")
        }

        // Check for required keys.
        var requiredKeys = setOf("position", "user_id")
        var missingKeys = array_diff(requiredKeys, userTag.keys)
        if (!(missingKeys.isEmpty())) {
            throw IllegalArgumentException("Missing keys \"${missingKeys.joinToString(separator = "\", \"")}\" for user tag array.")
        }

        // Verify this product tag entry, ensuring that the entry is format
        // ["position"=>[0.0,1.0],"user_id"=>"123"] and nothing else.
        for ((key, value) in userTag) {
            when (key) {
                "user_id" ->{
                    if (value !is Int && !ctype_digit(value)) {
                        throw IllegalArgumentException("User ID must be an integer.")
                    }
                    if (value < 0) {
                        throw IllegalArgumentException("User ID must be a positive integer.")
                    }
                }
                "position" ->{
                    try {
                        throwIfInvalidPosition(value)
                    } catch (IllegalArgumentException e) {
                        throw IllegalArgumentException(sprintf("Invalid user tag position: %s", e.getMessage()), e.getCode(), e)
                    }
                }
                else ->{
                    throw IllegalArgumentException("Invalid key \"$key\" in user tag array.")
                }
            }
        }
    }

    /**
     * Verifies an array of media usertags.
     *
     * Ensures that the input strictly contains the exact keys necessary for
     * usertags, and with proper values for them. We cannot validate that the
     * user-id"s actually exist, but that"s the job of the library user!
     *
     * @param (mixed) $usertags The array of usertags, optionally with the "in" or
     *                        "removed" top-level keys holding the usertags. Example:
     *                        ["in"=>[["position"=>[0.5,0.5],"user_id"=>"123"], ...]].
     *
     * @throws IllegalArgumentException If any tags are invalid.
     */
    fun throwIfInvalidUsertags(usertags){
        // NOTE: We can import "array" typehint, but it doesn't give us enough freedom.
        if (!is_array(usertags)) {
            throw IllegalArgumentException("Usertags must be an array.")
        }

        if (usertags.isEmpty()) {
            throw IllegalArgumentException("Empty usertags array.")
        }

        for ((k,v) in usertags ) {
            if (!is_array(v)) {
                throw IllegalArgumentException("Invalid usertags array. The value for key \"$k\" must be an array.")
            }
            // Skip the section if it"s empty.
            if (v.isEmpty()) {
                continue
            }
            // Handle ["in"=>[...], "removed"=>[...]] top-level keys since
            // this input contained top-level array keys containing the usertags.
            when (k) {
                "in" ->{
                    for ((idx, userTag) in v) {
                        try {
                            throwIfInvalidUserTag(userTag)
                        } catch (IllegalArgumentException $e) {
                            throw IllegalArgumentException(
                                sprintf("Invalid usertag at index "%d": %s", $idx, $e.getMessage()),
                                e.getCode(),
                                e
                            )
                        }
                    }
                }
                "removed" ->{
                    // Check the array of userids to remove.
                    for (userId in v) {
                        if (!ctype_digit(userId) && (userId !is Int || userId < 0)) {
                            throw IllegalArgumentException("Invalid user ID in usertags \"removed\" array.")
                        }
                    }
                }
                else -> throw IllegalArgumentException("Invalid key \"$k\" in user tags array.")
            }
        }
    }

    /**
     * Verifies an array of product tags.
     *
     * Ensures that the input strictly contains the exact keys necessary for
     * product tags, and with proper values for them. We cannot validate that the
     * product"s-id actually exists, but that"s the job of the library user!
     *
     * @param (mixed) $productTags The array of usertags, optionally with the "in" or
     *                           "removed" top-level keys holding the usertags. Example:
     *                           ["in"=>[["position"=>[0.5,0.5],"product_id"=>"123"], ...]].
     *
     * @throws IllegalArgumentException If any tags are invalid.
     */
    fun throwIfInvalidProductTags(productTags){
        // NOTE: We can import "array" typehint, but it doesn't give us enough freedom.
        if (!is_array(productTags)) {
            throw IllegalArgumentException("Products tags must be an array.")
        }

        if (productTags.isEmpty()) {
            throw IllegalArgumentException("Empty product tags array.")
        }

        for ((k, v) in productTags) {
            if (!is_array(v)) {
                throw IllegalArgumentException("Invalid product tags array. The value for key \"$k\" must be an array.")
            }

            // Skip the section if it"s empty.
            if (v.isEmpty()) {
                continue
            }

            // Handle ["in"=>[...], "removed"=>[...]] top-level keys since
            // this input contained top-level array keys containing the product tags.
            when (k) {
                "in" ->{
                    // Check the array of product tags to insert.
                    for ((idx, productTag) in v) {
                        try {
                            throwIfInvalidProductTag(productTag)
                        } catch (IllegalArgumentException $e) {
                            throw IllegalArgumentException(
                                sprintf("Invalid product tag at index "%d": %s", $idx, $e.getMessage()),
                                $e.getCode(),
                                $e
                            )
                        }
                    }
                }
                "removed" ->{
                    // Check the array of product_id to remove.
                    for (productId in v) {
                        if ( !(productId.isDigit()) && ( productId !is Int || productId < 0) ) {
                            throw IllegalArgumentException("Invalid product ID in product tags \"removed\" array.")
                        }
                    }
                }
                else ->
                    throw IllegalArgumentException("Invalid key \"$k\" in product tags array.")
            }
        }
    }

    /**
     * Verifies a product tag.
     *
     * Ensures that the input strictly contains the exact keys necessary for
     * product tag, and with proper values for them. We cannot validate that the
     * product-id actually exists, but that"s the job of the library user!
     *
     * @param (mixed) $productTag An array containing the product ID and the tag position.
     *                          Example: ["position"=>[0.5,0.5],"product_id"=>"123"].
     *
     * @throws IllegalArgumentException If any tags are invalid.
     */
    fun throwIfInvalidProductTag(productTag){
        // NOTE: We can import "array" typehint, but it doesn't give us enough freedom.
        if (!is_array(productTag)) {
            throw IllegalArgumentException("Product tag must be an array.")
        }

        // Check for required keys.
        var requiredKeys = ["position", "product_id"]
        var missingKeys = array_diff(requiredKeys, productTag.keys)
        if (!(missingKeys.isEmpty())) {
            throw IllegalArgumentException("Missing keys \"${missingKeys.joinToString(separator = "\", \"")}\" for product tag array.")
        }

        // Verify this product tag entry, ensuring that the entry is format
        // ["position"=>[0.0,1.0],"product_id"=>"123"] and nothing else.
        for ((key, value) in productTag) {
            when (key) {
                "product_id" -> {
                    if (value!is Int && !ctype_digit(value)) {
                        throw IllegalArgumentException("Product ID must be an integer.")
                    }
                    if (value < 0) {
                        throw IllegalArgumentException("Product ID must be a positive integer.")
                    }
                }
                "position" -> {
                    try {
                        throwIfInvalidPosition(value)
                    } catch (IllegalArgumentException $e) {
                        throw IllegalArgumentException(sprintf("Invalid product tag position: %s", $e.getMessage()), $e.getCode(), $e)
                    }
                }
                else -> throw IllegalArgumentException("Invalid key \"$key\" in product tag array.")
            }
        }
    }

    /**
     * Verifies a position.
     *
     * @param (mixed) $position An array containing a position coordinates.
     *
     * @throws IllegalArgumentException
     */
    fun throwIfInvalidPosition(position){
        if (position !is Array<String>) {
            throw IllegalArgumentException("Position must be an array.")
        }

        if (!isset(position[0])) {
            throw IllegalArgumentException("X coordinate is required.")
        }
        val x = position[0]
        if (x !is Int && x !is Float) {
            throw IllegalArgumentException("X coordinate must be a number.")
        }
        if (x < 0.0 || x > 1.0) {
            throw IllegalArgumentException("X coordinate must be a float between 0.0 and 1.0.")
        }

        if (!isset(position[1])) {
            throw IllegalArgumentException("Y coordinate is required.")
        }
        val y = position[1]
        if (y !is Int && y !is Float) {
            throw IllegalArgumentException("Y coordinate must be a number.")
        }
        if (y < 0.0 || y > 1.0) {
            throw IllegalArgumentException("Y coordinate must be a float between 0.0 and 1.0.")
        }
    }

    /**
     * Verifies that a single hashtag is valid.
     *
     * This fun enforces the following requirements: It must be a string,
     * at least 1 character long, and cannot contain the "#" character itself.
     *
     * @param (mixed) $hashtag The hashtag to check (should be string but we
     *                       accept anything for checking purposes).
     *
     * @throws IllegalArgumentException
     */
    fun throwIfInvalidHashtag(hashtag: String){
        if (hashtag !is String || hashtag.isNotEmpty()) {
            throw IllegalArgumentException("Hashtag must be a non-empty string.")
        }
        // Perform an UTF-8 aware search for the illegal "#" symbol (anywhere).
        // NOTE: We must import mb_strpos() to support international tags.
        if (hashtag.contains("#")) {
            throw IllegalArgumentException("Hashtag \"$hashtag\" is not allowed to contain the \"#\" character.")
        }
    }

    /**
     * Verifies a rank token.
     *
     * @param (string) $rankToken
     *
     * @throws IllegalArgumentException
     */
    fun throwIfInvalidRankToken(rankToken: String) {
        if (!Signatures.isValidUUID(rankToken)) {
            throw IllegalArgumentException("\"$rankToken\" is not a valid rank token.")
        }
    }

    /**
     * Verifies an array of story poll.
     *
     * @param (array[]) $storyPoll Array with story poll key-value pairs.
     *
     * @throws IllegalArgumentException If it"s missing keys or has invalid values.
     */
    fun throwIfInvalidStoryPoll(storyPoll: Map<String, String>){
        val requiredKeys = setOf("question", "viewer_vote", "viewer_can_vote", "tallies", "is_sticker")

        if (storyPoll.count() !== 1) {
            throw IllegalArgumentException("Only one story poll is permitted. You added ${storyPoll.count()} story polls.")
        }

        // Ensure that all keys exist.
        var missingKeys = array_keys(array_diff_key(["question" to 1, "viewer_vote" to 1, "viewer_can_vote" to 1, "tallies" to 1, "is_sticker" to 1], storyPoll[0]))
        if (missingKeys.count()) {
            throw IllegalArgumentException("Missing keys \"${missingKeys.joinToString(separator = ", ")}\" for story poll array.")
        }

        for ((k,v) in storyPoll[0]) {
            when (k) {
                "question" -> {
                    if (v !is String) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story poll array-key \"$k\".")
                    }
                }
                "viewer_vote" -> {
                    if (v !== 0) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story poll array-key \"$k\".")
                    }
                }
                "viewer_can_vote" , "is_sticker" -> {
                    if (v !is Boolean && v !== true) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story poll array-key \"$k\".")
                    }
                }
                "tallies" -> {
                    if (v !is Aarray) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story poll array-key \"$k\".")
                    }
                    _throwIfInvalidStoryPollTallies(v)
                }
            }
        }
        _throwIfInvalidStoryStickerPlacement(array_diff_key(storyPoll[0], array_flip(requiredKeys)), "polls")
    }

    /**
     * Verifies an array of story slider.
     *
     * @param (array[]) $storySlider Array with story slider key-value pairs.
     *
     * @throws IllegalArgumentException If it"s missing keys or has invalid values.
     */
    fun throwIfInvalidStorySlider(storySlider)
    {
        var requiredKeys = setOf("question", "viewer_vote", "viewer_can_vote", "slider_vote_average", "slider_vote_count", "emoji", "background_color", "text_color", "is_sticker")

        if (storySlider.count() !== 1) {
            throw IllegalArgumentException("Only one story slider is permitted. You added ${storySlider.count()} story sliders.")
        }

        // Ensure that all keys exist.
        var missingKeys = array_keys(array_diff_key(["question" to 1, "viewer_vote" to 1, "viewer_can_vote" to 1, "slider_vote_average" to 1, "slider_vote_count" to 1, "emoji" to 1, "background_color" to 1, "text_color" to 1, "is_sticker" to 1], storySlider[0]))
        if (missingKeys.count()) {
            throw IllegalArgumentException("Missing keys \"${missingKeys.joinToString(separator = ", ")}\" for story slider array.")
        }

        for ((k, v) in storySlider[0]) {
            when (k) {
                "question" -> {
                    if (v !is String) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story poll array-key \"$k\".")
                    }
                }
                "viewer_vote", "slider_vote_count", "slider_vote_average" -> {
                    if (v !== 0) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story poll array-key \"$k\".")
                    }
                }
                "background_color", "text_color" -> {
                    if (!( "/^[0-9a-fA-F]{6}$/".toRegex().matches( v.substring(1) ) )) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story poll array-key \"$k\".")
                    }
                }
                "emoji" -> {
                    //TODO REQUIRES EMOJI VALIDATION
                }
                "viewer_can_vote" -> {
                    if (v !is Boolean && v !== false) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story poll array-key \"$k\".")
                    }
                }
                "is_sticker" -> {
                    if (v !is Boolean && v !== false) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story poll array-key \"$k\".")
                    }
                }

            }
        }
        _throwIfInvalidStoryStickerPlacement(array_diff_key(storySlider[0], array_flip(requiredKeys)), "sliders")
    }

    /**
     * Verifies an array of story question.
     *
     * @param (array) $storyQuestion Array with story question key-value pairs.
     *
     * @throws IllegalArgumentException If it"s missing keys or has invalid values.
     */
    fun throwIfInvalidStoryQuestion(storyQuestion){
        var requiredKeys = setOf("z", "viewer_can_interact", "background_color", "profile_pic_url", "question_type", "question", "text_color", "is_sticker")

        if (storyQuestion.count() !== 1) {
            throw IllegalArgumentException("Only one story question is permitted. You added ${storyQuestion.count()} story questions.")
        }

        // Ensure that all keys exist.
        var missingKeys = array_keys(array_diff_key(["viewer_can_interact" to 1, "background_color" to 1, "profile_pic_url" to 1, "question_type" to 1, "question" to 1, "text_color" to 1, "is_sticker" to 1], storyQuestion[0]))
        if (missingKeys.count()) {
            throw IllegalArgumentException("Missing keys \"${missingKeys.joinToString(separator = ", ")}\" for story question array.")
        }

        for ((k, v) in storyQuestion[0]) {
            when (k) {
                "z" -> { // May be used for AR in the future, for now it"s always 0.
                    if (v !== 0) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story question array-key \"$k\".")
                    }
                }
                "viewer_can_interact" -> {
                    if (v !is Boolean || v !== false) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story question array-key \"$k\".")
                    }
                }
                "background_color", "text_color" -> {
                    if (!( "/^[0-9a-fA-F]{6}$/".toRegex().matches( v.substring(1) ) )) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story question array-key \"$k\".")
                    }
                }
                "question_type" -> {
                    // At this time only text questions are supported.
                    if (v !is String || v !== "text") {
                        throw IllegalArgumentException("Invalid value \"$v\" for story question array-key \"$k\".")
                    }
                }
                "question" -> {
                    if (v !is String) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story question array-key \"$k\".")
                    }
                }
                "profile_pic_url" -> {
                    if (!hasValidWebURLSyntax(v)) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story question array-key \"$k\".")
                    }
                }
                "is_sticker" -> {
                    if (v !is Boolean && v !== true) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story question array-key \"$k\".")
                    }
                }
            }
        }
        _throwIfInvalidStoryStickerPlacement(array_diff_key(storyQuestion[0], array_flip(requiredKeys)), "questions")
    }

    /**
     * Verifies an array of story countdown.
     *
     * @param (array) $storyCountdown Array with story countdown key-value pairs.
     *
     * @throws IllegalArgumentException If it"s missing keys or has invalid values.
     */
    fun throwIfInvalidStoryCountdown(storyCountdown){
        var requiredKeys = setOf("z", "text", "text_color", "start_background_color", "end_background_color", "digit_color", "digit_card_color", "end_ts", "following_enabled", "is_sticker")

        if (storyCountdown.count() !== 1) {
            throw IllegalArgumentException("Only one story countdown is permitted. You added ${storyCountdown.count()} story countdowns.")
        }

        // Ensure that all keys exist.
        var missingKeys = array_keys(array_diff_key(["z" to 1, "text" to 1, "text_color" to 1, "start_background_color" to 1, "end_background_color" to 1, "digit_color" to 1, "digit_card_color" to 1, "end_ts" to 1, "following_enabled" to 1, "is_sticker" to 1], storyCountdown[0]))
        if (missingKeys.count()) {
            throw IllegalArgumentException("Missing keys \"${missingKeys.joinToString(separator = ", ")}\" for story countdown array.")
        }

        for ((k, v)in storyCountdown[0]) {
            when (k) {
                "z" -> { // May be used for AR in the future, for now it"s always 0.
                    if (v !== 0) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story countdown array-key \"$k\".")
                    }
                }
                "text" -> {
                    if (v !is String) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story countdown array-key \"$k\".")
                    }
                }
                "text_color", "start_background_color", "end_background_color", "digit_color", "digit_card_color" -> {
                    if (!( "/^[0-9a-fA-F]{6}$/".toRegex().matches( v.substring(1) ) )) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story countdown array-key \"$k\".")
                    }
                }
                "end_ts" -> {
                    if (v !is Int) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story countdown array-key \"$k\".")
                    }
                }
                "following_enabled" -> {
                    if (v !is Boolean) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story countdown array-key \"$k\".")
                    }
                }
                "is_sticker" -> {
                    if (v !is Boolean && v !== true) {
                        throw IllegalArgumentException("Invalid value \"$v\" for story countdown array-key \"$k\".")
                    }
                }
            }
        }
        _throwIfInvalidStoryStickerPlacement(array_diff_key(storyCountdown[0], array_flip(requiredKeys)), "countdowns")
    }

    /**
     * Verifies if tallies are valid.
     *
     * @param (array[]) $tallies Array with story poll key-value pairs.
     *
     * @throws IllegalArgumentException If it"s missing keys or has invalid values.
     */
    protected fun _throwIfInvalidStoryPollTallies(tallies){
        var requiredKeys = setOf("text", "count", "font_size")
        if (tallies.count() !== 2) {
            throw IllegalArgumentException("Missing data for tallies.")
        }

        for (tallie in tallies) {
            var missingKeys = array_keys(array_diff_key(["text" to 1, "count" to 1, "font_size" to 1], tallie))

            if (missingKeys.count()) {
                throw IllegalArgumentException("Missing keys \"${missingKeys.joinToString(separator = ", ")}\" for location array.")
            }
            for ((k, v) in tallie) {
                if (!in_array(k, requiredKeys, true)) {
                    throw IllegalArgumentException("Invalid key \"$k\" for story poll tallies.")
                }
                when (k) {
                    "text" -> {
                        if (v !is String) {
                            throw IllegalArgumentException("Invalid value \"$v\" for tallies array-key \"$k\".")
                        }
                    }
                    "count" -> {
                        if (v !== 0) {
                            throw IllegalArgumentException("Invalid value \"$v\" for tallies array-key \"$k\".")
                        }
                    }
                    "font_size" -> {
                        if (v !is Float || (v < 17.5 || v > 35.0)) {
                            throw IllegalArgumentException("Invalid value \"$v\" for tallies array-key \"$k\".")
                        }
                    }
                }
            }
        }
    }

    /**
     * Verifies an array of story mentions.
     *
     * @param (array[]) $storyMentions The array of all story mentions.
     *
     * @throws IllegalArgumentException If it"s missing keys or has invalid values.
     */
    fun throwIfInvalidStoryMentions(storyMentions){
        var requiredKeys = ["user_id"]

        for (mention in storyMentions) {
            // Ensure that all keys exist.
            var missingKeys = array_keys(array_diff_key(["user_id" to 1], mention))
            if (missingKeys.count()) {
                throw IllegalArgumentException("Missing keys \"${missingKeys.joinToString(separator = ", ")}\" for mention array.")
            }

            for ((k, v) in mention ) {
                when (k) {
                    "user_id" -> {
                        if (!ctype_digit(v) && (v !is Int || v < 0)) {
                            throw IllegalArgumentException("Invalid value \"$v\" for story mention array-key \"$k\".")
                        }
                    }
                }
            }
            _throwIfInvalidStoryStickerPlacement(array_diff_key(mention, array_flip(requiredKeys)), "story mentions")
        }
    }

    /**
     * Verifies if a story location sticker is valid.
     *
     * @param (array[]) $locationSticker Array with location sticker key-value pairs.
     *
     * @throws IllegalArgumentException If it"s missing keys or has invalid values.
     */
    fun throwIfInvalidStoryLocationSticker(locationSticker){
        var requiredKeys = setOf("location_id", "is_sticker")
        var missingKeys = array_keys(array_diff_key(["location_id" to 1, "is_sticker" to 1], locationSticker))

        if (missingKeys.count()) {
            throw IllegalArgumentException("Missing keys \"${missingKeys.joinToString(separator = ", ")}\" for location array.")
        }

        for ((k, v) in locationSticker) {
            when (k) {
                "location_id" -> {
                    if (v !is String && v !is Numeric) {
                        throw IllegalArgumentException("Invalid value \"$v\" for location array-key \"$k\".")
                    }
                }
                "is_sticker" -> {
                    if (v !is Boolean) {
                        throw IllegalArgumentException("Invalid value \"$v\" for hashtag array-key \"$k\".")
                    }
                }
            }
        }
        _throwIfInvalidStoryStickerPlacement(array_diff_key(locationSticker, array_flip(requiredKeys)), "location")
    }

    /**
     * Verifies if a caption is valid for a hashtag and verifies an array of hashtags.
     *
     * @param (string) $captionText The caption for the story hashtag to verify.
     * @param (array[]) $hashtags    The array of all story hashtags.
     *
     * @throws IllegalArgumentException If caption doesn't contain any hashtag,
     *                                   or if any tags are invalid.
     */
    fun throwIfInvalidStoryHashtags(captionText: String, hashtags){
        var requiredKeys = setOf("tag_name", "use_custom_title", "is_sticker")

        // Extract all hashtags from the caption using a UTF-8 aware regex.
        if (!preg_match_all("/#(.w+[^.x00-.x7F]?+)/u", captionText, tagsInCaption)) {
            throw IllegalArgumentException("Invalid caption for hashtag.")
        }

        // Verify all provided hashtags.
        for (hashtag in hashtags ) {
            var missingKeys = array_keys(array_diff_key(["tag_name" to 1, "use_custom_title" to 1, "is_sticker" to 1], hashtag))
            if (missingKeys.count()) {
                throw IllegalArgumentException("Missing keys \"${missingKeys.joinToString(separator = ", ")}\" for hashtag array.")
            }

            for ((k, v) in hashtag) {
                when(k) {
                    "tag_name" -> {
                        // Ensure that the hashtag format is valid.
                        throwIfInvalidHashtag(v)
                        // Verify that this tag exists somewhere in the caption to check.
                        if (!in_array(v, tagsInCaption[1])) {
                            // NOTE: UTF-8 aware.
                            throw IllegalArgumentException("Tag name \"$v\" does not exist in the caption text.")
                        }
                    }
                    "use_custom_title" -> {
                        if (v !is Boolean) {
                            throw IllegalArgumentException("Invalid value \"$v\" for hashtag array-key \"$k\".")
                        }
                    }
                    "is_sticker" -> {
                        if (v !is Boolean) {
                            throw IllegalArgumentException("Invalid value \"$v\" for hashtag array-key \"$k\".")
                        }
                    }
                }
            }
            _throwIfInvalidStoryStickerPlacement(array_diff_key(hashtag, array_flip(requiredKeys)), "hashtag")
        }
    }

    /**
     * Verifies an attached media.
     *
     * @param (array[]) $attachedMedia Array containing the attached media data.
     *
     * @throws IllegalArgumentException If it"s missing keys or has invalid values.
     */
    fun throwIfInvalidAttachedMedia(attachedMedia){
        var attachedMedia = reset(attachedMedia)
        var requiredKeys = setOf("media_id", "is_sticker")

        // Ensure that all keys exist.
        var missingKeys = array_keys(array_diff_key(["media_id" to 1, "is_sticker" to 1], attachedMedia))
        if (missingKeys.count()) {
            throw IllegalArgumentException("Missing keys \"${missingKeys.joinToString(separator = ", ")}\" for attached media.")
        }

        if (attachedMedia["media_id"] !is String && attachedMedia["media_id"] !is Numeric) {
            throw IllegalArgumentException("Invalid value ${attachedMedia["media_id"]} for media_id.")
        }

        if (attachedMedia["is_sticker"] !is Boolean && attachedMedia["is_sticker"] !== true) {
            throw IllegalArgumentException("Invalid value ${attachedMedia["is_sticker"]} for attached media.")
        }

        _throwIfInvalidStoryStickerPlacement(array_diff_key(attachedMedia, array_flip(requiredKeys)), "attached media")
    }

    /**
     * Verifies a story sticker"s placement parameters.
     *
     * There are many kinds of story stickers, such as hashtags, locations,
     * mentions, etc. To place them on the media, the user must provide certain
     * parameters for things like position and size. This fun verifies all
     * of those parameters and ensures that the sticker placement is valid.
     *
     * @param (array)  $storySticker The array describing the story sticker placement.
     * @param (string) $type         What type of sticker this is.
     *
     * @throws IllegalArgumentException If storySticker is missing keys or has invalid values.
     */
    fun _throwIfInvalidStoryStickerPlacement(storySticker, type: String){
        var requiredKeys = setOf("x", "y", "width", "height", "rotation")

        // Ensure that all required hashtag array keys exist.
        var missingKeys = array_keys(array_diff_key(["x" to 1, "y" to 1, "width" to 1, "height" to 1, "rotation" to 0], storySticker))
        if (missingKeys.count()) {
            throw IllegalArgumentException("Missing keys \"${missingKeys.joinToString(separator = ", ")}\" for \"$type\".")
        }

        // Check the individual array values.
        for ((k, v) in storySticker) {
            if (!in_array(k, requiredKeys, true)) {
                throw IllegalArgumentException("Invalid key \"$k\" for \"$type\".")
            }
            when (k) {
                "x", "y", "width", "height", "rotation" -> {
                    if (v !is Float || v < 0.0 || v > 1.0) {
                        throw IllegalArgumentException("Invalid value \"$v\" for \"$type\" key \"$k\".")
                    }
                }
            }
        }
    }

    /**
     * Checks and validates a media item"s type.
     *
     * @param (string)|int $mediaType The type of the media item. One of: "PHOTO", "VIDEO"
     *                              "CAROUSEL", or the raw value of the Item"s "getMediaType()" fun.
     *
     * @throws IllegalArgumentException If the type is invalid.
     *
     * @return string The verified final type either "PHOTO", "VIDEO" or "CAROUSEL".
     */
    fun checkMediaType(mediaType): String{
        if (ctype_digit(mediaType) || mediaType is Int) {
            if (mediaType == Item::PHOTO) {
                mediaType = "PHOTO"
            } else if (mediaType == Item::VIDEO) {
                mediaType = "VIDEO"
            } else (mediaType == Item::CAROUSEL) {
                mediaType = "CAROUSEL"
            }
        }
        if (!in_array(mediaType, ["PHOTO", "VIDEO", "CAROUSEL"], true)) {
            throw IllegalArgumentException("\"$mediaType\" is not a valid media type.")
        }

        return mediaType
    }

    fun formatBytes(bytes, precision: Int = 2){
        val units = setOf("B", "kB", "mB", "gB", "tB")

        bytes = Math.max(bytes, 0)
        var pow = Math.floor( (if (bytes) log(bytes) else 0)/log(1024) )
        pow = Math.min(pow, units.count())

        bytes = bytes / Math.pow(1024.0, pow)

        return Math.round(bytes, precision)+""+units[pow]
    }

    fun colouredString(string: String,colour: String){
        var colours = mutableMapOf<String,String>()
        colours["black"] = "030"
        colours["dark_gray"] = "130"
        colours["blue"] = "034"
        colours["light_blue"] = "134"
        colours["green"] = "032"
        colours["light_green"] = "132"
        colours["cyan"] = "036"
        colours["light_cyan"] = "136"
        colours["red"] = "031"
        colours["light_red"] = "131"
        colours["purple"] = "035"
        colours["light_purple"] = "135"
        colours["brown"] = "033"
        colours["yellow"] = "133"
        colours["light_gray"] = "037"
        colours["white"] = "137"

        var colored_string = ""

        if (isset(colours[colour])) {
            colored_string += ".033["+ colours[colour] + "m"
        }

        colored_string += "$string.033[0m"

        return colored_string
    }

    fun getFilterCode(filter: String): Int{
        var filters = mutableListOf<String>()
        filters[0] = "Normal"
        filters[615] = "Lark"
        filters[614] = "Reyes"
        filters[613] = "Juno"
        filters[612] = "Aden"
        filters[608] = "Perpetua"
        filters[603] = "Ludwig"
        filters[605] = "Slumber"
        filters[616] = "Crema"
        filters[24] = "Amaro"
        filters[17] = "Mayfair"
        filters[23] = "Rise"
        filters[26] = "Hudson"
        filters[25] = "Valencia"
        filters[1] = "X-Pro II"
        filters[27] = "Sierra"
        filters[28] = "Willow"
        filters[2] = "Lo-Fi"
        filters[3] = "Earlybird"
        filters[22] = "Brannan"
        filters[10] = "Inkwell"
        filters[21] = "Hefe"
        filters[15] = "Nashville"
        filters[18] = "Sutro"
        filters[19] = "Toaster"
        filters[20] = "Walden"
        filters[14] = "1977"
        filters[16] = "Kelvin"
        filters[-2] = "OES"
        filters[-1] = "YUV"
        filters[109] = "Stinson"
        filters[106] = "Vesper"
        filters[112] = "Clarendon"
        filters[118] = "Maven"
        filters[114] = "Gingham"
        filters[107] = "Ginza"
        filters[113] = "Skyline"
        filters[105] = "Dogpatch"
        filters[115] = "Brooklyn"
        filters[111] = "Moon"
        filters[117] = "Helena"
        filters[116] = "Ashby"
        filters[108] = "Charmes"
        filters[640] = "BrightContrast"
        filters[642] = "CrazyColor"
        filters[643] = "SubtleColor"

        return array_search(filter, filters)
    }

    /**
     * Creates a folder if missing, or ensures that it is writable.
     *
     * @param (string) $folder The directory path.
     *
     * @return bool TRUE if folder exists and is writable, otherwise FALSE.
     */
    fun createFolder(folder: String): Boolean{
        // Test write-permissions for the folder and create/fix if necessary.
        if ((is_dir(folder) && is_writable(folder))
            || (!is_dir(folder) && mkdir(folder, 0755, true))
            || chmod(folder, 0755)) {
            return true
        } else {
            return false
        }
    }

    /**
     * Recursively deletes a file/directory tree.
     *
     * @param (string) $folder         The directory path.
     * @param (bool)   $keepRootFolder Whether to keep the top-level folder.
     *
     * @return bool TRUE on success, otherwise FALSE.
     */
    fun deleteTree(folder: String,keepRootFolder: Boolean = false): String{
        // Handle bad arguments.
        if (folder.isEmpty() || !file_exists(folder)) {
            return true // No such file/folder exists.
        } else if (is_file(folder) || is_link(folder)) {
            return @unlink(folder) // Delete file/link.
        }

        // Delete all children.
        var files = RecursiveIteratorIterator(
            RecursiveDirectoryIterator(folder, RecursiveDirectoryIterator::SKIP_DOTS),
            RecursiveIteratorIterator::CHILD_FIRST
        )

        for (fileinfo in files) {
            var action = if(fileinfo.isDir()) "rmdir" else "unlink"
            if (!@action(fileinfo.getRealPath())) {
                return false // Abort due to the failure.
            }
        }

        // Delete the root folder itself?
        return if(!keepRootFolder) {return @rmdir(folder)} else {return true}
    }

    /**
     * Atomic filewriter.
     *
     * Safely writes contents to a file using an atomic two-step process.
     * If the script is killed before the write is complete, only the temporary
     * trash file will be corrupted.
     *
     * The algorithm also ensures that 100% of the bytes were written to disk.
     *
     * @param (string) $filename     Filename to write the data to.
     * @param (string) $data         Data to write to file.
     * @param (string) $atomicSuffix Lets you optionally provide a different
     *                             suffix for the temporary file.
     *
     * @return int|bool Number of bytes written on success, otherwise `FALSE`.
     */
    fun atomicWrite(filename: String, data: String, atomicSuffix: String = "atomictmp"){
        // Perform an exclusive (locked) overwrite to a temporary file.
        val filenameTmp = print("$filename.$atomicSuffix")
        var writeResult = @file_put_contents(filenameTmp, data, LOCK_EX)

        // Only proceed if we wrote 100% of the data bytes to disk.
        if (writeResult !== false && writeResult === strlen(data)) {
            // Now move the file to its real destination (replaces if exists).
            var moveResult = @rename(filenameTmp, filename)
            if (moveResult === true) {
                // Successful write and move. Return number of bytes written.
                return writeResult
            }
        }

        // We"ve failed. Remove the temporary file if it exists.
        if (is_file(filenameTmp)) {
            @unlink(filenameTmp)
        }

        return false // Failed.
    }

    /**
     * Creates an empty temp file with a unique filename.
     *
     * @param (string) $outputDir  Folder to place the temp file in.
     * @param (string) $namePrefix (optional) What prefix to import for the temp file.
     *
     * @throws .RuntimeException If the file cannot be created.
     *
     * @return string
     */
    fun createTempFile(outputDir: String, namePrefix: String = "TEMP"): String{
        // Automatically generates a name like "INSTATEMP_" or "INSTAVID_" etc.
        val finalPrefix = "INSTA${namePrefix}_"

        // Try to create the file (detects errors).
        var tmpFile = @tempnam(outputDir, finalPrefix)
        if (tmpFile !is String) {
            throw RuntimeException("Unable to create temporary output file in \"$outputDir\" (with prefix \"$finalPrefix\").")
        }

        return tmpFile
    }

    /**
     * Closes a file pointer if it"s open.
     *
     * Always import this fun instead of fclose()!
     *
     * Unlike the normal fclose(), this fun is safe to call multiple times
     * since it only attempts to close the pointer if it"s actually still open.
     * The normal fclose() would give an annoying warning in that scenario.
     *
     * @param (resource) $handle A file pointer opened by fopen() or fsockopen().
     *
     * @return bool TRUE on success or FALSE on failure.
     */
    fun safe_fclose(handle): Boolean{
        if (is_resource(handle)) {
            return fclose(handle)
        }

        return true
    }

    /**
     * Checks if a URL has valid "web" syntax.
     *
     * This fun is Unicode-aware.
     *
     * Be aware that it only performs URL syntax validation! It doesn"t check
     * if the domain/URL is fully valid and actually reachable!
     *
     * It verifies that the URL begins with either the "http://" or "https://"
     * protocol, and that it must contain a host with at least one period in it,
     * and at least two characters after the period (in other words, a TLD). The
     * rest of the string can be any sequence of non-whitespace characters.
     *
     * For example, "http://localhost" will not be seen as a valid web URL, and
     * "http://www.google.com foobar" is not a valid web URL since there"s a
     * space in it. But "https://bing.com" and "https://a.com/foo" are valid.
     * However, "http://a.abdabdbadbadbsa" is also seen as a valid URL, since
     * the validation is pretty simple and doesn't verify the TLDs (there are
     * too many now to catch them all and ones appear constantly).
     *
     * @param (string) $url
     *
     * @return bool TRUE if valid web syntax, otherwise FALSE.
     */
    fun hasValidWebURLSyntax(url: String): Boolean{// /^https?:\/\/[^\s.\/]+\.[^\s.\/]{2}\S*$/iu
        return "/^https?:\\/\\/[^\\s.\\/]+\\.[^\\s.\\/]{2}\\S*\$/iu".toRegex().matches(url)
    }

    /**
     * Extract all URLs from a text string.
     *
     * This fun is Unicode-aware.
     *
     * @param (string) $text The string to scan for URLs.
     *
     * @return array An array of URLs and their individual components.
     */
    fun extractURLs(text: String){
        var urls = mutableSetOf<String>()
        if (preg_match_all(
            // NOTE: This disgusting regex comes from the Android SDK, slightly
            // modified by Instagram and then encoded by us into PHP format. We
            // are NOT allowed to tweak this regex! It MUST match the official
            // app so that our link-detection acts *exactly* like the real app!
            // NOTE: Here is the "to PHP regex" conversion algorithm we used:
            // https://github.com/mgp25/Instagram-API/issues/1445#issuecomment-318921867
            "/((?:(http|https|Http|Https|rtsp|Rtsp):././(?:(?:[a-zA-Z0-9$.-._...+.!.*.".(.).,..?.&.=]|(?:.%[a-fA-F0-9]{2})){1,64}(?:.:(?:[a-zA-Z0-9$.-._...+.!.*.".(.).,..?.&.=]|(?:.%[a-fA-F0-9]{2})){1,25})?.@)?)?((?:(?:[a-zA-Z0-9.x{00A0}-.x{D7FF}.x{F900}-.x{FDCF}.x{FDF0}-.x{FFEF}._][a-zA-Z0-9.x{00A0}-.x{D7FF}.x{F900}-.x{FDCF}.x{FDF0}-.x{FFEF}._.-]{0,64}..)+(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnprwyz]|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eosuw]|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agksyz]|v[aceginu]|w[fs]|(?:.x{03B4}.x{03BF}.x{03BA}.x{03B9}.x{03BC}.x{03AE}|.x{0438}.x{0441}.x{043F}.x{044B}.x{0442}.x{0430}.x{043D}.x{0438}.x{0435}|.x{0440}.x{0444}|.x{0441}.x{0440}.x{0431}|.x{05D8}.x{05E2}.x{05E1}.x{05D8}|.x{0622}.x{0632}.x{0645}.x{0627}.x{06CC}.x{0634}.x{06CC}|.x{0625}.x{062E}.x{062A}.x{0628}.x{0627}.x{0631}|.x{0627}.x{0644}.x{0627}.x{0631}.x{062F}.x{0646}|.x{0627}.x{0644}.x{062C}.x{0632}.x{0627}.x{0626}.x{0631}|.x{0627}.x{0644}.x{0633}.x{0639}.x{0648}.x{062F}.x{064A}.x{0629}|.x{0627}.x{0644}.x{0645}.x{063A}.x{0631}.x{0628}|.x{0627}.x{0645}.x{0627}.x{0631}.x{0627}.x{062A}|.x{0628}.x{06BE}.x{0627}.x{0631}.x{062A}|.x{062A}.x{0648}.x{0646}.x{0633}|.x{0633}.x{0648}.x{0631}.x{064A}.x{0629}|.x{0641}.x{0644}.x{0633}.x{0637}.x{064A}.x{0646}|.x{0642}.x{0637}.x{0631}|.x{0645}.x{0635}.x{0631}|.x{092A}.x{0930}.x{0940}.x{0915}.x{094D}.x{0937}.x{093E}|.x{092D}.x{093E}.x{0930}.x{0924}|.x{09AD}.x{09BE}.x{09B0}.x{09A4}|.x{0A2D}.x{0A3E}.x{0A30}.x{0A24}|.x{0AAD}.x{0ABE}.x{0AB0}.x{0AA4}|.x{0B87}.x{0BA8}.x{0BCD}.x{0BA4}.x{0BBF}.x{0BAF}.x{0BBE}|.x{0B87}.x{0BB2}.x{0B99}.x{0BCD}.x{0B95}.x{0BC8}|.x{0B9A}.x{0BBF}.x{0B99}.x{0BCD}.x{0B95}.x{0BAA}.x{0BCD}.x{0BAA}.x{0BC2}.x{0BB0}.x{0BCD}|.x{0BAA}.x{0BB0}.x{0BBF}.x{0B9F}.x{0BCD}.x{0B9A}.x{0BC8}|.x{0C2D}.x{0C3E}.x{0C30}.x{0C24}.x{0C4D}|.x{0DBD}.x{0D82}.x{0D9A}.x{0DCF}|.x{0E44}.x{0E17}.x{0E22}|.x{30C6}.x{30B9}.x{30C8}|.x{4E2D}.x{56FD}|.x{4E2D}.x{570B}|.x{53F0}.x{6E7E}|.x{53F0}.x{7063}|.x{65B0}.x{52A0}.x{5761}|.x{6D4B}.x{8BD5}|.x{6E2C}.x{8A66}|.x{9999}.x{6E2F}|.x{D14C}.x{C2A4}.x{D2B8}|.x{D55C}.x{AD6D}|xn.-.-0zwm56d|xn.-.-11b5bs3a9aj6g|xn.-.-3e0b707e|xn.-.-45brj9c|xn.-.-80akhbyknj4f|xn.-.-90a3ac|xn.-.-9t4b11yi5a|xn.-.-clchc0ea0b2g2a9gcd|xn.-.-deba0ad|xn.-.-fiqs8s|xn.-.-fiqz9s|xn.-.-fpcrj9c3d|xn.-.-fzc2c9e2c|xn.-.-g6w251d|xn.-.-gecrj9c|xn.-.-h2brj9c|xn.-.-hgbk6aj7f53bba|xn.-.-hlcj6aya9esc7a|xn.-.-j6w193g|xn.-.-jxalpdlp|xn.-.-kgbechtv|xn.-.-kprw13d|xn.-.-kpry57d|xn.-.-lgbbat1ad8j|xn.-.-mgbaam7a8h|xn.-.-mgbayh7gpa|xn.-.-mgbbh1a71e|xn.-.-mgbc0a9azcg|xn.-.-mgberp4a5d4ar|xn.-.-o3cw4h|xn.-.-ogbpf8fl|xn.-.-p1ai|xn.-.-pgbs0dh|xn.-.-s9brj9c|xn.-.-wgbh1c|xn.-.-wgbl6a|xn.-.-xkc2al3hye2a|xn.-.-xkc2dl3a5ee0h|xn.-.-yfro4i67o|xn.-.-ygbi2ammx|xn.-.-zckzah|xxx)|y[et]|z[amw]))|(?:(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])..(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)..(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)..(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))(?:.:.d{1,5})?)(./(?:(?:[a-zA-Z0-9.x{00A0}-.x{D7FF}.x{F900}-.x{FDCF}.x{FDF0}-.x{FFEF}../.?.:.@.&.=.#.~.-...+.!.*.".(.).,._])|(?:.%[a-fA-F0-9]{2}))*)?(?:.b|$)/iu",
            text,
            var matches,
            PREG_SET_ORDER
        ) !== false) {
            for (match in matches) {
                urls = (
                    "fullUrl"  => match[0], // "https://foo:bar@www.bing.com/?foo=#test"
                    "baseUrl"  => match[1], // "https://foo:bar@www.bing.com"
                    "protocol" => match[2], // "https" (empty if no protocol)
                    "domain"   => match[3], // "www.bing.com"
                    "path"     => isset($match[4]) ? $match[4] : "", // "/?foo=#test"
                )
            }
        }

        return urls
    }
}

/*
* adding code to this file
*
*
* base64_encode(x) to Base64.getEncoder().encodeToString(x.toByteArray())
* hash_hmac        to base64hashHmacSha256 (just one type)
* floor()          to Math.floor
* fmod()           to rem()
* implode()        to .joinToString()
* substr()         to .substring()
* <<               to .shl()  : Bitwise Operators
* array_keys()     to .keys
*
*
*
*
* */


// function to covert string to hash code (HmacSHA256 format) then convert it to base64 encode
fun base64hashHmacSha256(key: String, message: String): String{
    val hasher = Mac.getInstance("HmacSHA256")
    hasher.init(SecretKeySpec(key.toByteArray(), "HmacSHA256"))

    val hash = hasher.doFinal(message.toByteArray())

    // to lowercase hexits
    //return DatatypeConverter.printHexBinary(hash)
    // to base64
    return DatatypeConverter.printBase64Binary(hash)

//    error on return keyword
//    try {
//        val hasher = Mac.getInstance("HmacSHA256")
//        hasher.init(SecretKeySpec(key.toByteArray(), "HmacSHA256"))
//
//        val hash = hasher.doFinal(message.toByteArray())
//
//        // to lowercase hexits
//        DatatypeConverter.printHexBinary(hash)
//        // to base64
//        return DatatypeConverter.printBase64Binary(hash)
//    }
//    catch (e: NoSuchAlgorithmException) {}
//    catch (e: InvalidKeyException) {}
}