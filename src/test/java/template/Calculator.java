package template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public int calcSum(String path) throws IOException {
        LineCallback sumCallback = (line, value) -> value + Integer.parseInt(line);
        return lineReadTemplate(path, sumCallback, 0);
    }

    public int calcMultiply(String path) throws IOException {
        LineCallback multiCallback = (line, value) -> value * Integer.parseInt(line);
        return lineReadTemplate(path, multiCallback, 1);
    }

    private Integer lineReadTemplate(String path, LineCallback callback, int initVal) throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(path));
            Integer res = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;
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
