package dk.operation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

//쓰기 전용 클래스
public class ResultWriter {
    private BufferedWriter bufferdWriter;
    private File file;

    public ResultWriter(String resultFilepath) throws IOException {
        this.file = new File(resultFilepath);
        this.bufferdWriter = new BufferedWriter(new FileWriter(file));
    }

    public void complete() throws IOException {
        System.out.println("Operation Complete.");
        this.bufferdWriter.close();
    }

    private void writeAndNewLine(String text) {
        try {
            if (file.isFile() && file.canWrite()) {
                this.bufferdWriter.write(text);
                this.bufferdWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResultWriter writeTopApiKey(String topApiKey) throws IOException {
        if (topApiKey == null) {
            this.writeAndNewLine("최다호출 API KEY (Error)");
            this.bufferdWriter.newLine();
            return this;
        }
        this.writeAndNewLine("최다호출 API KEY");
        this.writeAndNewLine(topApiKey);
        this.bufferdWriter.newLine();
        return this;
    }

    public ResultWriter writeTop3ApiServiceId(Map<String, Integer> top3ApiServiceMap) throws IOException {
        if (top3ApiServiceMap.isEmpty()) {
            this.writeAndNewLine("상위 3개의 API Service ID와 각각의 요청 수 (Error)");
            this.bufferdWriter.newLine();
            return this;
        }
        this.writeAndNewLine("상위 3개의 API Service ID와 각각의 요청 수");
        top3ApiServiceMap.forEach((k, v) -> this.writeAndNewLine(k + " : " + v));
        this.bufferdWriter.newLine();
        return this;
    }

    public ResultWriter writeBrowserUsage(Map<String, Float> browserUasgeMap) throws IOException {
        if (browserUasgeMap.isEmpty()) {
            this.writeAndNewLine("웹브라우저별 사용 비율 (Error)");
            this.bufferdWriter.newLine();
            return this;
        }
        this.writeAndNewLine("웹브라우저별 사용 비율");
        browserUasgeMap.forEach((k, v) -> this.writeAndNewLine(k + " : " + v + "%"));
        this.bufferdWriter.newLine();
        return this;
    }
}
