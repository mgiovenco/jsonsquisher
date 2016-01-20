import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import java.util.Iterator;

/**
 * Created by mgiovenco on 1/20/16.
 */
public class JsonFlattener {

    /**
     * Code to flatten a json object.
     * Example:
     * "deliveryDetails": { "deliveryServiceId": 123 }
     * becomes
     * "deliveryDetails.deliveryServiceId": 123
     *
     * @param object
     * @param flattened
     * @param parentKey
     * @return
     */
    private static JSONObject flatten(JSONObject object, JSONObject flattened, String parentKey) {
        if (flattened == null) {
            flattened = new JSONObject();
        }
        Iterator<?> keys = object.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            try {
                if (object.get(key) instanceof JSONObject) {
                    if (parentKey != null) {
                        flatten(object.getJSONObject(key), flattened, parentKey + "." + key);
                    } else {
                        flatten(object.getJSONObject(key), flattened, key);
                    }
                } else {
                    if (parentKey != null) {
                        flattened.put(parentKey + "." + key, object.get(key));
                    } else {
                        flattened.put(key, object.get(key));
                    }
                }
            } catch (JSONException e) {
                System.out.println("The following exception occurred while flattening json object: " + e);
            }
        }
        return flattened;
    }
}

