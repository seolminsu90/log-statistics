#### 실행방법

- 동봉된 Jar 파일을 실행하면 됩니다.
- input.log는 jar파일과 같은 경로에 놓아야 합니다.
- output.log는 jar파일과 같은 경로에 생성됩니다.

```bash
java -jar process.jar
```

#### 해결 방법

- 파일을 라인 별로 읽어 옴
- 정규식을 활용하여 로그의 각 영역을 분할
- split, replace 등을 활용하여 url에서 원하는 값을 가져옴
- HashMap을 이용하여 중복된 데이터 별 호출수 카운팅
- Stream api를 활용하여 정렬 및 Limit 처리


#### 메인 클래스

- App.java 
