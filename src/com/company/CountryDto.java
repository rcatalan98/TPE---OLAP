package com.company;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CountryDto extends QueryDto {

    private static int idCount = 1;
    private final int id;
    private String name;

    public CountryDto(String name) {
        this.id = idCount++;
        this.name = name;
        setColumns();
        setValues();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryDto that = (CountryDto) o;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public List<String> generateColumns() {
        return Arrays.asList("CountryId", "CountryName");
    }

    @Override
    public List<String> generateValues() {
        return Arrays.asList(String.valueOf(id), name);
    }

    @Override
    public void sanitizeStrings() {
        this.name = "'" + name + "'";
        setValues();
    }
}
