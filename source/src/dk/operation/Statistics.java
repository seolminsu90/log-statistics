package dk.operation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//집계 파트 나눈 클래스
public class Statistics {

    private Map<String, Integer> map;
    private int executeCnt = 0;
    private boolean calculateExecuteCnt;

    public Statistics() {
        this.map = new HashMap<>();
        this.calculateExecuteCnt = false;
    }

    public Statistics(boolean calculateExecuteCnt) {
        this.map = new HashMap<>();
        this.calculateExecuteCnt = calculateExecuteCnt;
    }

    // Total 수행 수 반환
    public int getExecuteCnt() {
        return this.executeCnt;
    }

    // Map 반환
    public Map<String, Integer> getMap() {
        if (map.isEmpty()) {
            return Collections.emptyMap();
        }
        return this.map;
    }

    // Map에 중복 키일 경우 value를 증가시켜준다.
    public void putMapOrUpdateValue(String key) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + 1);
        } else {
            map.put(key, 1);
        }

        if (this.calculateExecuteCnt) {
            this.executeCnt++;
        }
    }
}
