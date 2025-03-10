

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Story.
 *
 * @method Args getArgs()
 * @method Counts getCounts()
 * @method string getPk()
 * @method int getStoryType()
 * @method int getType()
 * @method bool isArgs()
 * @method bool isCounts()
 * @method bool isPk()
 * @method bool isStoryType()
 * @method bool isType()
 * @method this setArgs(Args $value)
 * @method this setCounts(Counts $value)
 * @method this setPk(string $value)
 * @method this setStoryType(int $value)
 * @method this setType(int $value)
 * @method this unsetArgs()
 * @method this unsetCounts()
 * @method this unsetPk()
 * @method this unsetStoryType()
 * @method this unsetType()
 */
data class Story (
    val pk         : String,
    val counts     : Counts,
    val args       : Args,
    val type       : Int,
    val story_type : Int
){
//    val JSON_PROPERTY_MAP = [
//        "pk"         => "string",
//        "counts"     => "Counts",
//        "args"       => "Args",
//        "type"       => "int",
//        "story_type" => "int",
//    ]
}
