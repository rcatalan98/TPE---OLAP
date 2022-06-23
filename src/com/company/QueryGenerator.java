package com.company;

//INSERT INTO table_name (column1, column2, column3, ...)
//VALUES (value1, value2, value3, ...);

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class QueryGenerator {

    public static void generateInsertQuery(String tableName, List<? extends QueryDto> dtos, String file) {
        List<String> queries = new LinkedList<>();
        dtos.forEach(dto -> {
            queries.add(generateInsertQuery(tableName, dto.toQueryParams()));
        });
        generateDump(queries, "olapDW" + file);
    }

    public static String generateInsertQuery(String tableName, Map<String, String> fieldValues) {
        StringBuilder sb = new StringBuilder("INSERT INTO");
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        sb.append(" ").append(tableName).append(" ");

        for (Map.Entry<String, String> fieldValue : fieldValues.entrySet()) {
            columns.append(fieldValue.getKey()).append(",");
            values.append(fieldValue.getValue()).append(",");
        }
        columns.deleteCharAt(columns.length()-1);
        values.deleteCharAt(values.length()-1);
        columns.append(")");
        values.append(");");
        sb.append(columns).append(" VALUES ").append(values);
        return sb.toString();
    }

    private static void generateDump(List<String> queries, String fileName) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".sql", true))) {
            for (String query: queries) {
                writer.write(query);
                writer.newLine();
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
