package context;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private final Map<String, Object> data = new HashMap<>();

    public void set(String key, Object value) {
        data.put(key, value);
    }

    public <T> T get(String key, Class<T> type) {
        return type.cast(data.get(key));
    }

    public boolean contains(String key) {
        return data.containsKey(key);
    }

    public void clear() {
        data.clear();
    }
}