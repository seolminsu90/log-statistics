package dk;

import java.io.IOException;

import dk.operation.LogProcess;

//메인 클래스
public class App {
    public static void main(String[] args) {
        System.out.println("Start Operation");
        try {
            LogProcess process = new LogProcess("input.log");
            process.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
