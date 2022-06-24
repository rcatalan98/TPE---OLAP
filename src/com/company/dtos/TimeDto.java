package com.company.dtos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TimeDto extends QueryDto {
    private static int idCount = 1;
    private final int timeKey;
    private final LocalDate date;
    private final String daynbmonth;
    private final String daynbyear;
    private final String daynbSeason;
    private final String monthnumber;
    private String monthname;
    private final String year;
    private String season;

    public TimeDto(LocalDate date) {
        this.timeKey = idCount++;
        this.date = date;
        this.daynbmonth = String.valueOf(date.getDayOfMonth());
        this.daynbyear = String.valueOf(date.getDayOfYear());
        this.monthnumber = String.valueOf(date.getMonthValue());
        this.monthname = date.getMonth().name();
        this.year = String.valueOf(date.getYear());
        if(date.getMonthValue() >= 8) {
            this.season = date.getYear() + "-" + (date.getYear() + 1);
            this.daynbSeason = String.valueOf(ChronoUnit.DAYS
                    .between(LocalDate.of(date.getYear(), 8, 1), date));
        }
        else {
            this.season = (date.getYear()-1) + "-" + date.getYear();
            this.daynbSeason = String.valueOf(ChronoUnit.DAYS
                    .between(LocalDate.of(date.getYear()-1, 8, 1), date));
        }
        setColumns();
        setValues();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeDto timeDto = (TimeDto) o;
        return date.equals(timeDto.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    public int getId() {
        return timeKey;
    }

    public String getDaynbmonth() { return daynbmonth; }

    public String getDaynbyear() { return daynbyear; }

    public String getDaynbSeason() { return daynbSeason; }

    public String getMonthnumber() { return monthnumber; }

    public String getMonthname() { return monthname; }

    public String getYear() { return year; }

    public String getSeason() { return season; }

    @Override
    public List<String> generateColumns() {
        return Arrays.asList("TImeKey", "DateId", "DayNbMonth", "DayNbYear", "DayNbSeason",
                "MonthNumber", "MonthName", "Year", "Season");
    }

    @Override
    public List<String> generateValues() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return Arrays.asList(String.valueOf(timeKey), "'"+date.format(formatter)+"'", daynbmonth, daynbyear,
                daynbSeason, monthnumber, monthname, year, season);
    }

    @Override
    public void sanitizeStrings() {
        this.monthname = "'" + monthname + "'";
        this.season = "'" + season + "'";
        setValues();
    }
}
