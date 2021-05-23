package template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public int calcSum(String path) throws IOException {
        BufferedReaderCallback sumCallback = br -> {
            int sum = 0;
            String line = null;
            while ((line = br.readLine()) != null) {
                sum += Integer.valueOf(line);
            }
            return sum;
        };
        return fileReadTemplate(path, sumCallback);
    }

    public int calcMultiply(String path) throws IOException {
        BufferedReaderCallback multiCallback = br -> {
            int multi = 1;
            String line = null;
            while ((line = br.readLine()) != null) {
                multi *= Integer.valueOf(line);
            }
            return multi;
        };
        return fileReadTemplate(path, multiCallback);
    }

    private Integer fileReadTemplate(String path, BufferedReaderCallback callback) throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(path));
            return callback.doSomethingWithReader(br);
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
