

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Text.
 *
 * @method string getText()
 * @method bool isText()
 * @method this setText(string $value)
 * @method this unsetText()
 */
class Text : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "text" => "string",
    ]
}
