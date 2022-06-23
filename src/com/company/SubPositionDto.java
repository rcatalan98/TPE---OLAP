package com.company;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SubPositionDto extends QueryDto {
    private static int idCount = 1;
    private final int id;
    private String name;
    private final PositionDto position;

    public int getId() { return id; }

    public String getName() { return name; }

    public PositionDto getPosition() { return position; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubPositionDto that = (SubPositionDto) o;
        return getName().equals(that.getName()) && getPosition().equals(that.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPosition());
    }

    public SubPositionDto(String name, PositionDto position) {
        this.id = idCount++;
        this.name = name;
        this.position = position;
        setColumns();
        setValues();
    }

    @Override
    public List<String> generateColumns() {
        return Arrays.asList("SubPositionId", "PositionId", "SubPositionName");
    }

    @Override
    public List<String> generateValues() {
        return Arrays.asList(String.valueOf(id), String.valueOf(position.getId()), name);
    }

    @Override
    public void sanitizeStrings() {
        this.name = "'" + name + "'";
        setValues();
    }
}
