package instagramAPI

import kotlin.math.pow

/*
* adding summery of change in this file
*
* change foreach from php to kotlin,
* change  IllegalArgumentException to IllegalArgumentException,
* change !is_string(x) to x !is string : type casting,
* convert static function to kotlin code with companion object,
* change strlen()     to .length,
* change str_repeat() to .repeat,
* change str_split()  to .chunked,
* change strpos()     to indexOf(),
* change str_pad()    to padStart(),
* change strrev()     to reversed(),
* change ltrim()      to trimStart(),
* simulate bcdiv()  fun from php : divide two number and round,
* simulate bcpow()  fun from php : pow two number and round,
* simulate bindec() fun from php : convert binary to decimal,
* simulate decbin() fun from php : convert decimal to binary,
*/
/**
 * Class for converting media IDs to/from Instagram"s shortcode system.
 *
 * The shortcode is the https://instagram.com/p/SHORTCODE/ part of the URL.
 * There are many reasons why you would want to be able to convert back and
 * forth between shortcodes and internal ID numbers. This library helps you!
 *
 * @author SteveJobzniak (https://github.com/SteveJobzniak)
 */
class InstagramID{
    /**
     * Base64 URL Safe Character Map.
     *
     * This is the Base64 "URL Safe" alphabet, which is what Instagram uses.
     *
     * @var string
     *
     * @see (https://tools.ietf.org/html/rfc4648)
     */
    val BASE64URL_CHARMAP = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"

    /**
     * Internal map of the results of all base10 digits (0-9) modulo 2.
     *
     * Used by the decimal-to-binary converter, to avoid costly bcmod() calls.
     * Arranged by decimal offset, so the answer for decimal 9 is in index 9.
     *
     * @var string
     */
    val BASE10_MOD2: List<String> = listOf("0", "1", "0", "1", "0", "1", "0", "1", "0", "1")


    companion object {
        /**
         * Runtime cached bit-value lookup table.
         *
         * @var array|null
         */
        private lateinit var bitValueTable: MutableList<Int>


        /**
         * Converts an Instagram ID to their shortcode system.
         *
         * @param (string)|int $id The ID to convert. Must be provided as a string if
         *                       it"s larger than the size of an integer, which MOST
         *                       Instagram IDs are!
         *
         * @throws \ IllegalArgumentException If bad parameters are provided.
         *
         * @return string The shortcode.
         */
        fun toCode(id: String): String {
            // First we must convert the ID number to a binary string.
            // NOTE: Conversion speed depends on number size. With the most common
            // number size used for Instagram"s IDs, my old laptop can do ~18k/s.

            var base2 = base10to2(id, false)
            // No left-padding. Throws if bad.

            if (base2 === "") {
                return "" // Nothing to convert.
            }

            // Left-pad with leading zeroes to make length a multiple of 6 bits.
            val padAmount: Int = (6 - (base2.length % 6))
            if (padAmount != 6 || base2.length === 0){
                base2 = "0".repeat(padAmount) + base2
            }

            // Now chunk it in segments of 6 bits at a time. Every 6 "digits" in a
            // binary number is just 1 "digit" in a base64 number, because base64
            // can represent the values 0-63, and 63 is "111111" (6 bits) in base2.
            // Example: 9999 base10 = 10 011100 001111 base2 = (2, 28, 15) base64.

            val chunks = base2.chunked(6)

            // Process and encode all chunks as base64 using Instagram"s alphabet.
            var encoded = ""
            chunks.forEach {
                // Interpret the chunk bitstring as an unsigned integer (0-63).
                val base64 = bindec(it.toLong())

                // Look up that base64 character in Instagram"s alphabet.
                encoded += InstagramID().BASE64URL_CHARMAP[base64]//Todo
            }

            return encoded
        }

        /**
         * Converts an Instagram shortcode to a numeric ID.
         *
         * @param (string) $code The shortcode.
         *
         * @throws \ IllegalArgumentException If bad parameters are provided.
         *
         * @return string The numeric ID.
         */
        fun fromCode(code: String): String {
            if ("/[^A-Za-z0-9\\-_]/".toRegex().matches(code)) { //Todo regex
                throw IllegalArgumentException("Input must be a valid Instagram shortcode.")
            }

            // Convert the base64 shortcode to a base2 binary string.
            var base2 = ""

            for(i in 0 until code.length){//Todo   review step of loop
                // Find the base64 value of the current character.
                val base64 = InstagramID().BASE64URL_CHARMAP.indexOf(code[i])

                // Convert it to 6 binary bits (left-padded if needed).
                base2 += decbin(base64).toString().padStart(6, '0')
            }
            // Now just convert the base2 binary string to a base10 decimal string.
            return base2to10(base2)
        }


        /**
         * Converts a decimal number of any size into a binary string.
         *
         * @param (string)|int $base10  The number to convert, in base 10. Must be 0+,
         *                            a positive integer. And you must pass the
         *                            number as a string if you want to convert a
         *                            number that"s larger than what fits in your
         *                            CPU"s integer size.
         * @param (bool)       $padLeft Whether to pad with leading zeroes.
         *
         * @throws \ IllegalArgumentException If the input isn"t a valid integer.
         *
         * @return string The binary bits as a string.
         */
        fun base10to2(base10F: String, padLeft: Boolean = true): String {
            var base10 = base10F
            if (base10 === "" || "/[^0-9]".toRegex().matches(base10)) { //Todo regex
                throw IllegalArgumentException("Input must be a positive integer.")
            }

            // Convert the arbitrary-length base10 input to a base2 binary string.
            // We process as strings to support unlimited input number sizes!
            var base2 = ""
            do {
                // Get the last digit.
                val lastDigit = base10[base10.length - 1].toInt()

                // If the last digit is uneven, put a one (1) in the base2 string,
                // otherwise use zero (0) instead. Array is 10x faster than bcmod.
                base2 += InstagramID().BASE10_MOD2[lastDigit]

                // Now divide the whole base10 string by two, discarding decimals.
                // NOTE: Division is unavoidable when converting decimal to binary,
                // but at least it"s implemented in pure C thanks to the BC library.
                // An implementation of arbitrary-length division by 2 in just PHP
                // was ~4x slower. Anyway, my old laptop can do ~1.6 million bcdiv()
                // per second so this is no problem.
                base10 = bcdiv(base10, "2")
            } while (base10 !== "0")

            // We built the base2 string backwards, so now we must reverse it.
            base2 = base2.reversed()

            // Add or remove proper left-padding with zeroes as needed.
            if (padLeft) {
                val padAmount = 8 - (base2.length % 8)
                if (padAmount != 8 || base2.length === 0) {
                    base2 = "0".repeat(padAmount) + base2
                }
            } else {
                base2 = base2.trimStart('0')
            }

            return base2
        }


        /**
         * Builds a binary bit-value lookup table.
         *
         * @param (int) $maxBitCount Maximum number of bits to calculate values for.
         *
         * @return array The lookup table, where offset 0 has the value of bit 1,
         *               offset 1 has the value of bit 2, and so on.
         */
        fun buildBinaryLookupTable(maxBitCount: Int): MutableList<Int>{
            val table = mutableListOf<Int>()
            for (bitPosition in 0 until maxBitCount){ //Todo   review step of loop
                val bitValue = bcpow(2, bitPosition)
                table.add(bitValue)
            }
            return table
        }


        /**
         * Converts a binary number of any size into a decimal string.
         *
         * @param (string) $base2 The binary bits as a string where each character is
         *                      either "1" or "0".
         *
         * @throws \ IllegalArgumentException If the input isn"t a binary string.
         *
         * @return string The decimal number as a string.
         */
        fun base2to10(base2: String): String {
            if ("/[^01]/".toRegex().matches(base2)) {// Todo regex
                throw IllegalArgumentException("Input must be a binary string.")
            }

            // Pre-build a ~80kb RAM table with all values for bits 1-512. Any
            // higher bits than that will be generated and cached live instead.
            if (bitValueTable === null) {
                bitValueTable = buildBinaryLookupTable(512)
            }

            // Reverse the bit-sequence so that the least significant bit is first,
            // which is necessary when converting binary via its bit offset powers.
            val base2rev = base2.reversed()

            // Process each bit individually and reconstruct the base10 number.
            var base10 = "0"
            val bits = base2rev.chunked(1)
            for (bitPosition in 0..bits.count()){ //Todo  for loop review
                if (bits[bitPosition] == "1") {
                    // Look up the bit value in the table or generate if missing.
                    var bitValue: Int
                    if (bitValueTable[bitPosition].toString().toBoolean()) { // Todo possible to not work -- maybe return always true
                        bitValue = bitValueTable[bitPosition]
                    } else {
                        bitValue = bcpow(2, bitPosition)
                        bitValueTable[bitPosition] = bitValue
                    }

                    // Now just add the bit"s value to the current total.
                    base10 = bcadd(base10, bitValue.toString())
                }
            }
            return base10
        }
    }
}

// Convert binary to decimal function
fun bindec(nu: Long): Int {
    var num: Long = nu
    var decimalNumber = 0
    var i = 0
    var remainder: Long

    while (num.toInt() != 0) {
        remainder = num % 10
        num /= 10
        decimalNumber += (remainder * Math.pow(2.0, i.toDouble())).toInt()
        ++i
    }
    return decimalNumber
}

// Convert decimal to binary function
fun decbin(n: Int): Long {
    var nu = n
    var binaryNumber: Long = 0
    var remainder: Int
    var i = 1

    while (nu != 0) {
        remainder = nu % 2
        nu /= 2
        binaryNumber += (remainder * i).toLong()
        i *= 10
    }
    return binaryNumber
}

// simulate bcdiv() from php without specific decimal
fun bcdiv(dividend: String, divisor: String): String {
    val num = dividend.toDouble().div(divisor.toDouble())
    return "%.0f".format(num)

}

// simulate bcpow() from php without specific decimal
fun bcpow(base: Int, exponent: Int): Int{
    val powNum= base.toDouble().pow(exponent.toDouble())
    return "%.0f".format(powNum).toInt()
}

// simulate bcpow() from php without specific decimal
fun bcadd(left_operand: String, right_operand: String): String {
    val addNum = left_operand.toDouble() + right_operand.toDouble()
    return "%.0f".format(addNum)
}



