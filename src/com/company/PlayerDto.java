package com.company;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlayerDto extends QueryDto {

    private final String id;
    private final SubPositionDto position;
    private final CountryDto country;
    private String name;
    private final String height;
    private String foot;
    private final String marketValue;
    private String dateOfBirth;
    private final ClubDto club;

    public PlayerDto(String id, SubPositionDto position, CountryDto country, String name,
                     String height, String foot, String marketValue, String dateOfBirth,
                     ClubDto club) {
        this.id = id;
        this.position = position;
        this.country = country;
        this.name = name;
        this.height = height;
        this.foot = foot;
        this.marketValue = marketValue;
        this.dateOfBirth = dateOfBirth;
        this.club = club;
        setColumns();
        setValues();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDto playerDto = (PlayerDto) o;
        return getId().equals(playerDto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public String getId() { return id; }

    public SubPositionDto getPosition() { return position; }

    public CountryDto getCountry() { return country; }

    public String getName() { return name; }

    public String getHeight() { return height; }

    public String getFoot() { return foot; }

    public String getMarketValue() { return marketValue; }

    public String getDateOfBirth() { return dateOfBirth; }

    public ClubDto getClub() { return club; }

    @Override
    public List<String> generateColumns() {
        return Arrays.asList("PlayerId", "PositionId", "CountryId", "Name", "DateOfBirth",
                "Height", "Foot", "MarketValue");
    }

    @Override
    public List<String> generateValues() {
        return Arrays.asList(id, String.valueOf(position.getId()),
                String.valueOf(country.getId()), name, dateOfBirth, height,
                foot, marketValue);
    }

    @Override
    public void sanitizeStrings() {
        this.name = "'" + name + "'";
        this.foot = "'" + foot + "'";
        this.dateOfBirth = "'" + dateOfBirth + "'";
        setValues();
    }
}
