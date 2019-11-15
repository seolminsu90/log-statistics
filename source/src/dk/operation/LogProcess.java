package dk.operation;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dk.util.MapUtil;

//여러 행위 모은 프로세스 클래스
public class LogProcess {

    private final String COMMON_URL = "http://apis.daum.net/search/";
    private final Pattern p = Pattern.compile("\\[(.*?)\\]");
    private String filePath;
    private Statistics topApiKeyOp, top3ApiServiceOp, browserUsageOp;

    // 추출할 기본 내용들 초기화
    public LogProcess(String filePath) throws IOException {
        this.filePath = filePath;
        this.topApiKeyOp = new Statistics();
        this.top3ApiServiceOp = new Statistics();
        this.browserUsageOp = new Statistics(true);
    }

    // 실행
    public void execute() {
        try {
            LogReader reader = new LogReader(filePath);
            reader.getLogStream().forEach(this::parseAndPut);

            ResultWriter writer = new ResultWriter("output.log");

            writer.writeTopApiKey(getTopApiKey())
                  .writeTop3ApiServiceId(getTop3ApiService())
                  .writeBrowserUsage(getBrowserUsage())
                  .complete();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 한줄 한줄 읽은 파일 스트림으로 부터 데이터 추출 & 넣기
    private void parseAndPut(String logString) {
        Matcher m = p.matcher(logString);

        int matchIdx = 0;
        boolean isCounting = false;
        while (m.find()) {
            String matchLogString = m.group(1);
            if (matchIdx == 0 && matchLogString.equals("200")) {
                // HTTP Response Code가 200일 때만 횟수를 기록
                isCounting = true;
            } else if (isCounting && matchIdx == 1 && matchLogString.startsWith(COMMON_URL)) {
                // 공통 URL을 제거한 후, ServiceId 영역과 QueryString 영역을 분리
                String[] slice = matchLogString.replaceAll(COMMON_URL, "").split("\\?");
                if (slice.length == 2) {
                    String apiServiceId = slice[0];
                    String apiKey = getApiKeyValue(slice[1]);

                    topApiKeyOp.putMapOrUpdateValue(apiKey);
                    top3ApiServiceOp.putMapOrUpdateValue(apiServiceId);
                } else {
                    System.out.println("전혀 예상하지 못한 케이스");
                }
            } else if (isCounting && matchIdx == 2) {
                // 사용 브라우저를 저장
                browserUsageOp.putMapOrUpdateValue(matchLogString);
            }
            matchIdx++;
        }
    }

    // Query String으로 부터 apikey의 값을 가져온다
    private String getApiKeyValue(String queryString) {
        String[] keyValues = queryString.split("\\&");
        for (String keyValue : keyValues) {
            if (keyValue.startsWith("apikey=")) {
                return keyValue.split("apikey=")[1];
            }
        }
        return null;
    }

    // 가장 많이 호출된 Apikey 한개의 키값 리턴
    public String getTopApiKey() {
        Map<String, Integer> sortedMap = MapUtil.reverseSortedMapByIntegerValue(topApiKeyOp.getMap());

        if (sortedMap.isEmpty()) {
            return null;
        } else {
            return sortedMap.entrySet()
                    .iterator()
                    .next()
                    .getKey();
        }
    }

    // 상위 3개의 API ServiceID와 요청수를 담은 Map을 리턴
    public Map<String, Integer> getTop3ApiService() {
        Map<String, Integer> sortedMap = MapUtil.reverseSortedMapByIntegerValue(top3ApiServiceOp.getMap(), 3);
        if (sortedMap.isEmpty()) {
            return Collections.emptyMap();
        } else {
            return sortedMap;
        }
    }

    // 웹 브라우저별 사용 비율을 담은 Map 리턴
    public Map<String, Float> getBrowserUsage() {
        Map<String, Float> convertedMap = MapUtil.keyUsageFromMap(browserUsageOp.getMap(),
                browserUsageOp.getExecuteCnt());
        if (convertedMap.isEmpty()) {
            return Collections.emptyMap();
        } else {
            return MapUtil.reverseSortedMapByFloatValue(convertedMap);
        }
    }
}
