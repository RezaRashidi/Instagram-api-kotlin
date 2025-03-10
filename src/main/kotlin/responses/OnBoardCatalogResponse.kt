

package instagramAPI.responses

import instagramAPI.Response

/**
 * OnBoardCatalogResponse.
 *
 * @method string getCurrentCatalogId()
 * @method bool getIsBusinessTargetedForShopping()
 * @method mixed getMessage()
 * @method string getShoppingOnboardingState()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isCurrentCatalogId()
 * @method bool isIsBusinessTargetedForShopping()
 * @method bool isMessage()
 * @method bool isShoppingOnboardingState()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setCurrentCatalogId(string $value)
 * @method this setIsBusinessTargetedForShopping(bool $value)
 * @method this setMessage(mixed $value)
 * @method this setShoppingOnboardingState(string $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetCurrentCatalogId()
 * @method this unsetIsBusinessTargetedForShopping()
 * @method this unsetMessage()
 * @method this unsetShoppingOnboardingState()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
data class OnBoardCatalogResponse (
    val shopping_onboarding_state         : String,
    val current_catalog_id                : String,
    val is_business_targeted_for_shopping : Boolean
){
//    val JSON_PROPERTY_MAP = [
//        "shopping_onboarding_state"         => "string",
//        "current_catalog_id"                => "string",
//        "is_business_targeted_for_shopping" => "bool",
//    ]
}
