package InstagramAPI

import java.math.BigInteger
import java.net.URLEncoder
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

object Signatures {
    /**
     * Generate a keyed hash value using the HMAC method.
     *
     * @param (string) data
     *
     * @return string
     */
    fun generateSignature(data: String): String {
        return hmacSHA256(Constants.IG_SIG_KEY, data)
    }

    /**
     * @deprecated import signData() instead.
     *
     * @param (string) data
     *
     * @return string
     */
    fun generateSignatureForPost(data: String): String {
        return "ig_sig_key_version=" + Constants.SIG_KEY_VERSION + "&signed_body=" + generateSignature(
            data
        ) + "." + URLEncoder.encode(data, "UTF-8")
    }

    /**
     * Generate signed array.
     *
     * @param array    data
     * @param (string[]) exclude
     *
     * @return array
     */
    fun signData(data: List<String>, exclude:List<String> = mutableListOf()) {
        val result = mutableMapOf<String, String>()
        // Exclude some params from signed body.
        for (key in exclude) {
            if (!(data[key].isBlank())) {
                result[key] = data[key]
//                unset(data[key])
            }
        }
        // Typecast all scalar values to string.
        foreach(data as & value) {
            if (is_scalar(value)) {
                var value = value as String
            }
        }
//        unset(value) // Clear reference.
        // Reorder and convert data to JSON string.
        data = json_encode((object) Utils.reorderByHashCode (data))
        // Sign data.
        result["ig_sig_key_version"] = Constants.SIG_KEY_VERSION
        result["signed_body"] = generateSignature(data) + "." + data
        // Return value must be reordered.
        return Utils.reorderByHashCode(result)
    }

    fun generateDeviceId(): String {
        // This has 10 million possible hash subdivisions per clock second.
        val megaRandomHash = number_format(System.currentTimeMillis() / 1000, 7, "", "").md5()
        return "android-" + megaRandomHash.substring(16)
    }

    /**
     * Checks whether supplied UUID is valid or not.
     *
     * @param (string) uuid UUID to check.
     *
     * @return bool
     */
    fun isValidUUID(uuid: String): Boolean {
        if (uuid !is String) {
            return false
        }
        //		#^[a-f\d]{8}-(?:[a-f\d]{4}-){3}[a-f\d]{12}$#D
        return "#^[a-f\\d]{8}-(?:[a-f\\d]{4}-){3}[a-f\\d]{12}#D".toRegex().matches(uuid)
    }

    fun generateUUID(keepDashes: Boolean = true): String {
        val s1 = String.format("%04X", (0..0xffff).random())
        val s2 = String.format("%04X", (0..0xffff).random())
        val s3 = String.format("%04X", (0..0xffff).random())
        val s4 = String.format("%04X", (0..0x0fff).random() or 0x4000)
        val s5 = String.format("%04X", (0..0x3fff).random() or 0x8000)
        val s6 = String.format("%04X", (0..0xffff).random())
        val s7 = String.format("%04X", (0..0xffff).random())
        val s8 = String.format("%04X", (0..0xffff).random())
        val uuid = "$s1$s2-$s3-$s4-$s5-$s6$s7$s8".toLowerCase()

        return if (keepDashes) uuid else uuid.replace("-", "")
    }
}

/*
* adding code to this file
*
* hash_hmac(SHA256)  to hmacSHA256()
*
* */

// build fun to hash string with key in HmacSHA256 method
fun hmacSHA256(key: String, message: String): String {
    val hasher = Mac.getInstance("HmacSHA256")
    hasher.init(SecretKeySpec(key.toByteArray(), "HmacSHA256"))

    val hash = hasher.doFinal(message.toByteArray())

    // to lowercase hexits
    return DatatypeConverter.printHexBinary(hash).toLowerCase()
}

// build fun to hash string in md5 method
fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}