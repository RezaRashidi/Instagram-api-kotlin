package InstagramAPI

object Signatures {
	/**
	 * Generate a keyed hash value using the HMAC method.
	 *
	 * @param string data
	 *
	 * @return string
	 */
	fun generateSignature(data:String) {
		return hash_hmac("sha256", data, Constants::IG_SIG_KEY)
	}

	/**
	 * @deprecated import signData() instead.
	 *
	 * @param string data
	 *
	 * @return string
	 */
	fun generateSignatureForPost(data:String) {
		return "ig_sig_key_version=".Constants::SIG_KEY_VERSION."&signed_body=".self::generateSignature(
			data).".".urlencode(data)
	}

	/**
	 * Generate signed array.
	 *
	 * @param array    data
	 * @param string[] exclude
	 *
	 * @return array
	 */
	fun signData(array data, array exclude = []) {
		result = []
		// Exclude some params from signed body.
		foreach(exclude as key) {
			if (isset(data[key])) {
				result[key] = data[key]
				unset(data[key])
			}
		}
		// Typecast all scalar values to string.
		foreach(data as & value) {
			if (is_scalar(value)) {
				value = (string) value
			}
		}
		unset(value) // Clear reference.
		// Reorder and convert data to JSON string.
		data = json_encode((object) Utils ::reorderByHashCode(data))
		// Sign data.
		result["ig_sig_key_version"] = Constants::SIG_KEY_VERSION
		result["signed_body"] = self::generateSignature(data).".".data
		// Return value must be reordered.
		return Utils::reorderByHashCode(result)
	}

	fun generateDeviceId() {
		// This has 10 million possible hash subdivisions per clock second.
		megaRandomHash = md5(number_format(microtime(true), 7, "", ""))

		return "android-".substr(megaRandomHash, 16)
	}

	/**
	 * Checks whether supplied UUID is valid or not.
	 *
	 * @param (string) uuid UUID to check.
	 *
	 * @return bool
	 */
	fun isValidUUID(uuid:String): Boolean {
		if (uuid !is String) {
			return false
		}

		return "#^[a-f\d]{8}-(?:[a-f\d]{4}-){3}[a-f\d]{12}#D".toRegex().matches(uuid)
	}

	fun generateUUID(keepDashes:Boolean = true):String {
		uuid =
			sprintf("%04x%04x-%04x-%04x-%04x-%04x%04x%04x", mt_rand(0, 0xffff), mt_rand(0, 0xffff), mt_rand(0, 0xffff),
			        mt_rand(0, 0x0fff) | 0x4000,
		mt_rand(0, 0x3fff) | 0x8000,
		mt_rand(0, 0xffff),
		mt_rand(0, 0xffff),
		mt_rand(0, 0xffff)
		)

		return keepDashes ? uuid : str_replace("-", "", uuid)
	}
}
