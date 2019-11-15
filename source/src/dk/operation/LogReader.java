package dk.operation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class LogReader {
    private Stream<String> logStream;

    public LogReader(String filePath) throws IOException {
        this.logStream = this.read(filePath);
    }

    private Stream<String> read(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath), StandardCharsets.UTF_8);
    }

    public Stream<String> getLogStream() {
        return this.logStream;
    }
}
