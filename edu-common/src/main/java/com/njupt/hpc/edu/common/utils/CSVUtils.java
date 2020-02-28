package com.njupt.hpc.edu.common.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2020-02-27 10:33
 **/
public class CSVUtils {

    public static List<String> readCSV(File file) throws IOException {
        List<String> result = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            result.add(line);
        }
        if (null != bufferedReader) {
            bufferedReader.close();
            bufferedReader = null;
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            List<String> result = CSVUtils
                    .readCSV(new File("/home/mola/IdeaProjects/edu/data-cache/z2CigmFq-test.csv"));
            result.forEach(s -> System.out.println(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
