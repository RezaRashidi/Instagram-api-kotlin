package InstagramAPI

import kotlin.math.pow

/*
* adding summery of change in this file
*
* change foreach from php to kotlin,
* change InvalidArgumentException to IllegalArgumentException,
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
 * Class for converting media IDs to/from Instagram's shortcode system.
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
     * @see https://tools.ietf.org/html/rfc4648
     */
    val BASE64URL_CHARMAP: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"

    /**
     * Internal map of the results of all base10 digits (0-9) modulo 2.
     *
     * Used by the decimal-to-binary converter, to avoid costly bcmod() calls.
     * Arranged by decimal offset, so the answer for decimal 9 is in index 9.
     *
     * @var string
     */
    val BASE10_MOD2: Array<String> = arrayOf("0", "1", "0", "1", "0", "1", "0", "1", "0", "1")

    /**
     * Runtime cached bit-value lookup table.
     *
     * @var array|null
     */

    companion object {
        val bitValueTable = null
    }

    /**
     * Converts an Instagram ID to their shortcode system.
     *
     * @param string|int $id The ID to convert. Must be provided as a string if
     *                       it's larger than the size of an integer, which MOST
     *                       Instagram IDs are!
     *
     * @throws \InvalidArgumentException If bad parameters are provided.
     *
     * @return string The shortcode.
     */

    object ToCode{
        fun toCode(id: String): String {
            // First we must convert the ID number to a binary string.
            // NOTE: Conversion speed depends on number size. With the most common
            // number size used for Instagram's IDs, my old laptop can do ~18k/s.

             var base2: String = Base10to2.base10to2(id, false)
            // No left-padding. Throws if bad.

            if (base2 === "") {
                return "" // Nothing to convert.
            }

            // Left-pad with leading zeroes to make length a multiple of 6 bits.
            var padAmount: Int = (6 - (base2.length % 6))
            if (padAmount != 6 || base2.length === 0) {
                base2 = "0".repeat(padAmount)
            }

            // Now chunk it in segments of 6 bits at a time. Every 6 "digits" in a
            // binary number is just 1 "digit" in a base64 number, because base64
            // can represent the values 0-63, and 63 is "111111" (6 bits) in base2.
            // Example: 9999 base10 = 10 011100 001111 base2 = (2, 28, 15) base64.

            var chunks = base2.chunked(6)

            // Process and encode all chunks as base64 using Instagram's alphabet.
            var encoded = ""
            (chunk in chunks).foreach {
                // Interpret the chunk bitstring as an unsigned integer (0-63).
                var base64 = bindec(chunk)

                // Look up that base64 character in Instagram's alphabet.
                encoded += this.BASE64URL_CHARMAP[base64]
            }

            return encoded
        }
    }

    /**
     * Converts an Instagram shortcode to a numeric ID.
     *
     * @param string $code The shortcode.
     *
     * @throws \InvalidArgumentException If bad parameters are provided.
     *
     * @return string The numeric ID.
     */

    object FromCode{
        fun fromCode(code: String) {
            if (code !is String || preg_match("/[^A-Za-z0-9\-_]/", code)) { //Todo regex
                throw IllegalArgumentException("Input must be a valid Instagram shortcode.")
            }

            // Convert the base64 shortcode to a base2 binary string.
            var base2 = ""

            var i: Int = 0
            for (i in 0 until code.length step ++i){
                // Find the base64 value of the current character.
                var base64 = this.BASE64URL_CHARMAP.indexOf(code[i])+1

                // Convert it to 6 binary bits (left-padded if needed).
                base2 += decbin(base64).toString().padStart(6, '0')
            }

            // Now just convert the base2 binary string to a base10 decimal string.
            var base10 = Base2to10.base2to10(base2)

            return base10
        }
    }


    /**
     * Converts a decimal number of any size into a binary string.
     *
     * @param string|int $base10  The number to convert, in base 10. Must be 0+,
     *                            a positive integer. And you must pass the
     *                            number as a string if you want to convert a
     *                            number that's larger than what fits in your
     *                            CPU's integer size.
     * @param bool       $padLeft Whether to pad with leading zeroes.
     *
     * @throws \InvalidArgumentException If the input isn't a valid integer.
     *
     * @return string The binary bits as a string.
     */

    object Base10to2{
        fun base10to2(base10, padLeft: Boolean = true){
            var base10 = base10 as String
                    if (base10 === "" || preg_match("/[^0-9]/", base10)) { //Todo regex
                        throw IllegalArgumentException("Input must be a positive integer.")
                    }

            // Convert the arbitrary-length base10 input to a base2 binary string.
            // We process as strings to support unlimited input number sizes!
            var base2 = ""
            do {
                // Get the last digit.
                var lastDigit = base10[base10.length - 1]

                // If the last digit is uneven, put a one (1) in the base2 string,
                // otherwise use zero (0) instead. Array is 10x faster than bcmod.
                base2 = base2 + this.BASE10_MOD2[lastDigit]

                // Now divide the whole base10 string by two, discarding decimals.
                // NOTE: Division is unavoidable when converting decimal to binary,
                // but at least it's implemented in pure C thanks to the BC library.
                // An implementation of arbitrary-length division by 2 in just PHP
                // was ~4x slower. Anyway, my old laptop can do ~1.6 million bcdiv()
                // per second so this is no problem.
                base10 = bcdiv(base10, "2")
            } while (base10 !== "0")

            // We built the base2 string backwards, so now we must reverse it.
            base2 = base2.reversed()

            // Add or remove proper left-padding with zeroes as needed.
            if (padLeft) {
                var padAmount = (8 - (base2.length % 8))
                if (padAmount != 8 || base2.length === 0) {
                    base2 = "0".repeat(padAmount) + base2
                }
            } else {
                base2 = base2.trimStart('0')
            }

            return base2
        }
    }

    /**
     * Builds a binary bit-value lookup table.
     *
     * @param int $maxBitCount Maximum number of bits to calculate values for.
     *
     * @return array The lookup table, where offset 0 has the value of bit 1,
     *               offset 1 has the value of bit 2, and so on.
     */

    object BuildBinaryLookupTable{
        fun buildBinaryLookupTable(maxBitCount: Int){
                var table = ArrayList<String>()
                var bitPosition = 0
                for (bitPosition in 0 until maxBitCount step ++bitPosition){
                    var bitValue = bcpow("2", bitPosition as string)
                    table.add(bitValue)
                }
                return table
        }
    }


    /**
     * Converts a binary number of any size into a decimal string.
     *
     * @param string $base2 The binary bits as a string where each character is
     *                      either "1" or "0".
     *
     * @throws \InvalidArgumentException If the input isn't a binary string.
     *
     * @return string The decimal number as a string.
     */

    object Base2to10{
        fun base2to10(base2: String){
            if (base2 !is String || preg_match("/[^01]/", base2)) {// Todo pregMatch
                throw IllegalArgumentException("Input must be a binary string.")
            }

            // Pre-build a ~80kb RAM table with all values for bits 1-512. Any
            // higher bits than that will be generated and cached live instead.
            if (bitValueTable === null) {
                bitValueTable = BuildBinaryLookupTable.buildBinaryLookupTable(512)
            }

            // Reverse the bit-sequence so that the least significant bit is first,
            // which is necessary when converting binary via its bit offset powers.
            var base2rev = base2.reversed()

            // Process each bit individually and reconstruct the base10 number.
            var base10 = "0"
            var bits = base2rev.chunked(1)
            var len = 0..bits.count()
            for (bitPosition in len step ++bitPosition){
                if (bits[bitPosition] == "1") {
                    // Look up the bit value in the table or generate if missing.
                    if (bitValueTable[bitPosition]) {
                        bitValue = bitValueTable[bitPosition]
                    } else {
                        bitValue = bcpow("2", bitPosition as String)
                        bitValueTable[bitPosition] = bitValue
                    }

                    // Now just add the bit's value to the current total.
                    base10 = bcadd(base10, bitValue.toSting())
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
fun bcpow(base: String, exponent: String): String{
    val powNum= base.toDouble().pow(exponent.toDouble())
    return "%.0f".format(powNum)
}

// simulate bcpow() from php without specific decimal
fun bcadd(left_operand: String, right_operand: String): String{
    val addNum = left_operand.toDouble() + right_operand.toDouble()
    return "%.0f".format(addNum)
}



