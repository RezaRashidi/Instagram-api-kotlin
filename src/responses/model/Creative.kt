

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * Creative.
 *
 * @method Text getContent()
 * @method mixed getDismissAction()
 * @method Text getFooter()
 * @method Image getImage()
 * @method Action getPrimaryAction()
 * @method Action getSecondaryAction()
 * @method Text getSocialContext()
 * @method Text getTitle()
 * @method bool isContent()
 * @method bool isDismissAction()
 * @method bool isFooter()
 * @method bool isImage()
 * @method bool isPrimaryAction()
 * @method bool isSecondaryAction()
 * @method bool isSocialContext()
 * @method bool isTitle()
 * @method this setContent(Text $value)
 * @method this setDismissAction(mixed $value)
 * @method this setFooter(Text $value)
 * @method this setImage(Image $value)
 * @method this setPrimaryAction(Action $value)
 * @method this setSecondaryAction(Action $value)
 * @method this setSocialContext(Text $value)
 * @method this setTitle(Text $value)
 * @method this unsetContent()
 * @method this unsetDismissAction()
 * @method this unsetFooter()
 * @method this unsetImage()
 * @method this unsetPrimaryAction()
 * @method this unsetSecondaryAction()
 * @method this unsetSocialContext()
 * @method this unsetTitle()
 */
data class Creative (
    val title            : Text,
    val content          : Text,
    val footer           : Text,
    val social_context   : Text,
    val primary_action   : Action,
    val secondary_action : Action,
    val dismiss_action   : String,
    val image            : Image
){ // there is two content in php but kotlin let have one with same name
//    val JSON_PROPERTY_MAP = [
//        "title"            => "Text",
//        "content"          => "Text",
//        "footer"           => "Text",
//        "social_context"   => "Text",
//        "content"          => "Text",
//        "primary_action"   => "Action",
//        "secondary_action" => "Action",
//        "dismiss_action"   => "",
//        "image"            => "Image",
//    ]
}
