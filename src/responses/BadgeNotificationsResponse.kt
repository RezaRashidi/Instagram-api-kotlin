

package instagramAPI.responses

import instagramAPI.Response

/**
 * BadgeNotificationsResponse.
 *
 * @method model.unpredictableKeys.CoreUnpredictableContainer getBadgePayload()
 * @method mixed getMessage()
 * @method string getStatus()
 * @method model._Message[] get_Messages()
 * @method bool isBadgePayload()
 * @method bool isMessage()
 * @method bool isStatus()
 * @method bool is_Messages()
 * @method this setBadgePayload(model.unpredictableKeys.CoreUnpredictableContainer $value)
 * @method this setMessage(mixed $value)
 * @method this setStatus(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetBadgePayload()
 * @method this unsetMessage()
 * @method this unsetStatus()
 * @method this unset_Messages()
 */
class BadgeNotificationsResponse : Response(){
    override val JSON_PROPERTY_MAP = mapOf(
        // Only exists if you have notifications contains data keyed by userId:
        // TODO: Currently defined as a Core container, which means that all of
        // the data sub-values will be arrays (no type-conversion of the values
        // will happen). We should define this as a specific type (like
        // UserUnpredictableContainer) when we know exactly what the data is.
        "badge_payload" to "model.unpredictableKeys.CoreUnpredictableContainer"
    )
}
