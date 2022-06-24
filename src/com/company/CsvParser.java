package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CsvParser {
    public static List<List<String>> parseCsvFile(String file, String sep, boolean hasHeader) {
        List<List<String>> lines = new LinkedList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            String[] lineValues;

            if(!hasHeader) {
                lineValues = line.split(sep);
                List<String> lineList = new ArrayList<>();
                Collections.addAll(lineList, lineValues);
                lines.add(lineList);
            }
            line = br.readLine();
            while (line != null) {
                lineValues = line.split(sep);
                List<String> lineList = new ArrayList<>();
                Collections.addAll(lineList, lineValues);
                lines.add(lineList);
                line = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static List<String> filterLine(String[] lineValues, List<Integer> indexesToKeep) {
        List<String> line = new ArrayList<>();
        indexesToKeep.forEach(index -> line.add(lineValues[index]));
        return line;
    }
}
