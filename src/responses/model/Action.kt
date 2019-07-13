

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Action.
 *
 * @method bool getDismissPromotion()
 * @method int getLimit()
 * @method Text getTitle()
 * @method string getUrl()
 * @method bool isDismissPromotion()
 * @method bool isLimit()
 * @method bool isTitle()
 * @method bool isUrl()
 * @method this setDismissPromotion(bool $value)
 * @method this setLimit(int $value)
 * @method this setTitle(Text $value)
 * @method this setUrl(string $value)
 * @method this unsetDismissPromotion()
 * @method this unsetLimit()
 * @method this unsetTitle()
 * @method this unsetUrl()
 */
class Action : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "title"             => "Text",
        "url"               => "string",
        "limit"             => "int",
        "dismiss_promotion" => "bool",
    ]
}
