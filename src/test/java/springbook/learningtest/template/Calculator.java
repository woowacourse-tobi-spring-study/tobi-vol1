package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filePath) throws IOException {
        return lineReadTemplate(filePath, (line, result) -> result + Integer.parseInt(line), 0);
    }

    public Integer calcMultiply(String filePath) throws IOException {
        return lineReadTemplate(filePath, (line, result) -> result * Integer.parseInt(line), 1);
    }

    public String concatenate(String filePath) throws IOException {
        return lineReadTemplate(filePath, (line, result) -> result + line, "");
    }

    public <T> T lineReadTemplate(String filePath, LineCallback<T> callback, T initVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            T result = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                result = callback.doSomethingWithLine(line, result);
            }
            return result;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
