

package instagramAPI.responses.Model

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
class TabsInfo : AutoPropertyMapper
{
    val JSON_PROPERTY_MAP = [
        "tabs"      => "Tab[]",
        "selected"  => "string",
    ]
}
