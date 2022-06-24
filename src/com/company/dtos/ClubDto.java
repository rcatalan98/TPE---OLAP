package com.company.dtos;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ClubDto extends QueryDto {
    private final String id;
    private final CountryDto country;
    private String name;
    private final String nationalTeamPlayers;

    public String getId() { return id; }

    public CountryDto getCountry() { return country; }

    public String getName() { return name; }

    public String getNationalTeamPlayers() { return nationalTeamPlayers; }

    public ClubDto(String id, CountryDto country, String name, String nationalTeamPlayers) {
        this.id = id;
        this.country = country;
        this.name = name;
        this.nationalTeamPlayers = nationalTeamPlayers;
        setColumns();
        setValues();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClubDto clubDto = (ClubDto) o;
        return getCountry().equals(clubDto.getCountry()) &&
                getName().equals(clubDto.getName()) &&
                getNationalTeamPlayers().equals(clubDto.getNationalTeamPlayers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountry(), getName(), getNationalTeamPlayers());
    }

    @Override
    public List<String> generateColumns() {
        return Arrays.asList("ClubId", "CountryId", "Name", "NationalTeamPlayers");
    }

    @Override
    public List<String> generateValues() {
        return Arrays.asList(id, String.valueOf(country.getId()),
                name, nationalTeamPlayers);
    }

    @Override
    public void sanitizeStrings() {
        this.name = "'" + name + "'";
        setValues();
    }
}
