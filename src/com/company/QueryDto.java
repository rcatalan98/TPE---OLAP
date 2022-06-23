package com.company;

import java.util.*;

public abstract class QueryDto {
    private List<String> columns;
    private List<String> values;

    public abstract List<String> generateColumns();

    public abstract List<String> generateValues();

    public abstract void sanitizeStrings();

    public QueryDto() {
        this.columns = Collections.emptyList();
        this.values = Collections.emptyList();
    }

    public void setColumns() {
        columns = this.generateColumns();
    }

    public void setValues() {
        values = this.generateValues();
    }

    public Map<String, String> toQueryParams() {
        sanitizeStrings();
        Map<String, String> fieldColumns = new HashMap<>();
        for(int i = 0;i < values.size();i++) {
            fieldColumns.put(this.columns.get(i), values.get(i));
        }
        return fieldColumns;
    }


}
