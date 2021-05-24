package springbook.learningtest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public int calcSum(String path) throws IOException {
        return fileReadTemplate(path, new LineCallback<Integer>() {
            @Override
            public Integer doSomething(String line, Integer value) {
                return value + Integer.parseInt(line);
            }
        }, 0);
    }

    public int calcMultiple(String path) throws IOException {
        return fileReadTemplate(path, new LineCallback<Integer>() {
            @Override
            public Integer doSomething(String line, Integer value) {
                return value * Integer.parseInt(line);
            }
        }, 1);
    }

    public String concat(String path) throws IOException {
        return fileReadTemplate(path, new LineCallback<String>() {
            @Override
            public String doSomething(String line, String value) {
                return value + line;
            }
        }, "");
    }

    public <T> T fileReadTemplate(String filePath, LineCallback<T> lineCallback, T initValue) throws IOException {
        BufferedReader bufferedReader = null;
        String line = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            T sum = initValue;
            while ((line = bufferedReader.readLine()) != null) {
                sum = lineCallback.doSomething(line, sum);
            }
            return sum;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
