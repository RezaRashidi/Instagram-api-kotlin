

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Effect.
 *
 * @method string getAssetUrl()
 * @method string getEffectFileId()
 * @method string getEffectId()
 * @method string getId()
 * @method mixed getInstructions()
 * @method string getThumbnailUrl()
 * @method mixed getTitle()
 * @method bool isAssetUrl()
 * @method bool isEffectFileId()
 * @method bool isEffectId()
 * @method bool isId()
 * @method bool isInstructions()
 * @method bool isThumbnailUrl()
 * @method bool isTitle()
 * @method this setAssetUrl(string $value)
 * @method this setEffectFileId(string $value)
 * @method this setEffectId(string $value)
 * @method this setId(string $value)
 * @method this setInstructions(mixed $value)
 * @method this setThumbnailUrl(string $value)
 * @method this setTitle(mixed $value)
 * @method this unsetAssetUrl()
 * @method this unsetEffectFileId()
 * @method this unsetEffectId()
 * @method this unsetId()
 * @method this unsetInstructions()
 * @method this unsetThumbnailUrl()
 * @method this unsetTitle()
 */
data class Effect (
    val title          : String,
    val id             : String,
    val effect_id      : String,
    val effect_file_id : String,
    val asset_url      : String,
    val thumbnail_url  : String,
    val instructions   : String
){
//    val JSON_PROPERTY_MAP = [
//        "title"          => "",
//        "id"             => "string",
//        "effect_id"      => "string",
//        "effect_file_id" => "string",
//        "asset_url"      => "string",
//        "thumbnail_url"  => "string",
//        "instructions"   => "",
//    ]
}
