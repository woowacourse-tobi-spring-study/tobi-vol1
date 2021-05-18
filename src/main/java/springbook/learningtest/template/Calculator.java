package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filepath) throws IOException {
        return fileReadTemplate(filepath, (line, value) -> value + Integer.parseInt(line), 0);
    }

    public Integer calcMultiply(String filepath) throws IOException {
        return fileReadTemplate(filepath, (line, value) -> value * Integer.parseInt(line), 1);
    }

    public String concatenateStrings(String filepath) throws IOException {
        return fileReadTemplate(filepath, (line, value) -> value + line, "");
    }

    public <T> T fileReadTemplate(String filepath, LineCallback<T> callback, T initVal) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            T res = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;
        } catch (IOException e) {
            throw e;
        }
    }
}
