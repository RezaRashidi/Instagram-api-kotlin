

package instagramAPI.responses.model

import instagramAPI.AutoPropertyMapper

/**
 * TabsInfo.
 *
 * @method string getSelected()
 * @method Tab[] getTabs()
 * @method bool isSelected()
 * @method bool isTabs()
 * @method this setSelected(string $value)
 * @method this setTabs(Tab[] $value)
 * @method this unsetSelected()
 * @method this unsetTabs()
 */
data class TabsInfo (
    val tabs     : MutableList<Tab>,
    val selected : String
){
//    val JSON_PROPERTY_MAP = [
//        "tabs"      => "Tab[]",
//        "selected"  => "string",
//    ]
}
