

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * SuggestionCard.
 *
 * @method mixed getUpsellCiCard()
 * @method mixed getUpsellFbcCard()
 * @method UserCard getUserCard()
 * @method bool isUpsellCiCard()
 * @method bool isUpsellFbcCard()
 * @method bool isUserCard()
 * @method this setUpsellCiCard(mixed $value)
 * @method this setUpsellFbcCard(mixed $value)
 * @method this setUserCard(UserCard $value)
 * @method this unsetUpsellCiCard()
 * @method this unsetUpsellFbcCard()
 * @method this unsetUserCard()
 */
data class SuggestionCard (
    val user_card            : UserCard,
    val upsell_ci_card       : String,
    val upsell_fbc_card      : String
){
//    val JSON_PROPERTY_MAP = [
//        "user_card"            => "UserCard",
//        "upsell_ci_card"       => "",
//        "upsell_fbc_card"      => "",
//    ]
}
