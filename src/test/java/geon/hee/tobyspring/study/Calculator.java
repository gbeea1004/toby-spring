package geon.hee.tobyspring.study;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String filepath) throws IOException {
        return fileReadTemplate(filepath, br -> {
            Integer sum = 0;
            String line = null;
            while ((line = br.readLine()) != null) {
                sum += Integer.parseInt(line);
            }
            return sum;
        });
    }

    public Integer calcMultiply(String filepath) throws IOException {
        return fileReadTemplate(filepath, br -> {
            Integer multiply = 1;
            String line = null;
            while ((line = br.readLine()) != null) {
                multiply *= Integer.parseInt(line);
            }
            return multiply;
        });
    }

    private Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filepath));
            return callback.doSomethingWithReader(br);
        } catch (IOException e) {
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
