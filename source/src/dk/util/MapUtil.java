package dk.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MapUtil {
    // Map을 내림차순 정렬하여 리턴해준다. (Integer 형)
    public static Map<String, Integer> reverseSortedMapByIntegerValue(Map<String, Integer> integerValueMap) {
        if (integerValueMap.isEmpty()) {
            return Collections.emptyMap();
        }
        return integerValueMap.entrySet()
                .stream()
                    .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (x, y) -> x, LinkedHashMap::new));
    }

    // Map을 내림차순 정렬하여 리턴해준다. (Integer 형 * Limit 기능 추가)
    public static Map<String, Integer> reverseSortedMapByIntegerValue(Map<String, Integer> integerValueMap, int limit) {
        if (integerValueMap.isEmpty()) {
            return Collections.emptyMap();
        }
        return integerValueMap.entrySet()
                .stream()
                    .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(limit)
                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (x, y) -> x, LinkedHashMap::new));
    }

    // Map을 내림차순 정렬하여 리턴해준다. (Float 형)
    public static Map<String, Float> reverseSortedMapByFloatValue(Map<String, Float> floatValueMap) {
        if (floatValueMap.isEmpty()) {
            return Collections.emptyMap();
        }
        return floatValueMap.entrySet()
                .stream()
                    .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (x, y) -> x, LinkedHashMap::new));
    }

    // Map에 Execute한 횟수와 Value 기반으로 사용률을 매겨 리턴해준다.
    public static Map<String, Float> keyUsageFromMap(Map<String, Integer> map, int totalExecuteCnt) {
        if (map.isEmpty() && totalExecuteCnt > 0) {
            return Collections.emptyMap();
        }

        return map.entrySet()
                .stream()
                    .collect(Collectors.toMap(Entry::getKey, entry -> ((float) entry.getValue() / totalExecuteCnt * 100)));
    }
}
