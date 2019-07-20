

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Challenge.
 *
 * @method mixed getApiPath()
 * @method mixed getHideWebviewHeader()
 * @method mixed getLock()
 * @method mixed getLogout()
 * @method mixed getNativeFlow()
 * @method string getUrl()
 * @method bool isApiPath()
 * @method bool isHideWebviewHeader()
 * @method bool isLock()
 * @method bool isLogout()
 * @method bool isNativeFlow()
 * @method bool isUrl()
 * @method this setApiPath(mixed $value)
 * @method this setHideWebviewHeader(mixed $value)
 * @method this setLock(mixed $value)
 * @method this setLogout(mixed $value)
 * @method this setNativeFlow(mixed $value)
 * @method this setUrl(string $value)
 * @method this unsetApiPath()
 * @method this unsetHideWebviewHeader()
 * @method this unsetLock()
 * @method this unsetLogout()
 * @method this unsetNativeFlow()
 * @method this unsetUrl()
 */
data class Challenge (
    val url                 : String,
    val api_path            : String,
    val hide_webview_header : String,
    val lock                : String,
    val logout              : String,
    val native_flow         : String
){
//    val JSON_PROPERTY_MAP = [
//        "url"                 => "string",
//        "api_path"            => "",
//        "hide_webview_header" => "",
//        "lock"                => "",
//        "logout"              => "",
//        "native_flow"         => "",
//    ]
}
