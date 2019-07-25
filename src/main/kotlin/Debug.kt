

package instagramAPI

object Debug
{
    fun printRequest(method: String, endpoint: String){
        if (PHP_SAPI === "cli") {
            method = Utils.colouredString("{$method}:  ", "light_blue")
        } else {
            method += ":  "
        }
        print(method + endpoint + "\n")
    }

    fun printUpload(uploadBytes: String){
        val dat = if (PHP_SAPI === "cli") {
            Utils.colouredString("→ $uploadBytes", "yellow")
        } else {
            "→ $uploadBytes"
        }
        print("$dat.n")
    }

    fun printHttpCode(httpCode, bytes: String){
        if (PHP_SAPI === "cli") {
            print( Utils.colouredString("← {$httpCode} .t {$bytes}", "green") + ".n" )
        } else {
            print("← {$httpCode} .t {$bytes}.n")
        }
    }

    fun printResponse(response: String, truncated: Boolean = false){
        val res = if (PHP_SAPI === "cli") {
            Utils.colouredString("RESPONSE: ", "cyan")
        } else {
            "RESPONSE: "
        }
        if (truncated && response.length > 1000) {
            response = response.substring(0, 1000) + "..."
        }
        print("$res$response.n.n")
    }

    fun printPostData(post: String){
        // todo : character with hex
        //mb_strpos($post, "\x1f"."\x8b"."\x08", 0, 'US-ASCII') === 0
        val gzip = post.indexOf("\x1f" + "\x8b" + "\x08", 0) === 0
        val dat = if (PHP_SAPI === "cli") {
            Utils.colouredString((if (gzip)"DECODED " else "") + "DATA: ", "yellow")
        } else {
            "DATA: "
        }
        print( dat + urldecode( if(gzip) zlib_decode(post) else post ) + ".n" )
    }
}
