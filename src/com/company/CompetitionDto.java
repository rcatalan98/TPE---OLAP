package com.company;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CompetitionDto extends QueryDto {
    private String id;
    private String name;
    private String type;
    private final CountryDto country;

    public CountryDto getCountry() { return country; }

    public String getId() { return id; }

    public String getName() { return name; }

    public String getType() { return type; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompetitionDto that = (CompetitionDto) o;
        return getId().equals(that.getId()) && getName().equals(that.getName()) && getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getType());
    }

    public CompetitionDto(String id, String name, String type, CountryDto country) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.country = country;
        setColumns();
        setValues();
    }

    @Override
    public List<String> generateColumns() {
        return Arrays.asList("CompetitionId", "Name", "Type");
    }

    @Override
    public List<String> generateValues() {
        return Arrays.asList(id, name, type);
    }

    @Override
    public void sanitizeStrings() {
        this.name = "'" + name + "'";
        this.type = "'" + type + "'";
        this.id = "'" + id + "'";
        setValues();
    }
}
