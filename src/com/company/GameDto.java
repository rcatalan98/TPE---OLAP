package com.company;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GameDto extends QueryDto {
    private final String id;
    private final CompetitionDto competition;
    private final ClubDto homeTeam;
    private final ClubDto awayTeam;
    private final TimeDto gameDate;
    private String round;
    private final String homeGoals;
    private final String awayGoals;

    public GameDto(String id, CompetitionDto competition, ClubDto homeTeam, ClubDto awayTeam,
                   TimeDto gameDate, String round, String homeGoals, String awayGoals) {
        this.id = id;
        this.competition = competition;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.gameDate = gameDate;
        this.round = round;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        setColumns();
        setValues();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDto gameDto = (GameDto) o;
        return getId().equals(gameDto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public String getId() { return id; }

    public CompetitionDto getCompetition() { return competition; }

    public ClubDto getHomeTeam() { return homeTeam; }

    public ClubDto getAwayTeam() { return awayTeam; }

    public TimeDto getGameDate() { return gameDate; }

    public String getRound() { return round; }

    public String getHomeGoals() { return homeGoals; }

    public String getAwayGoals() {
        return awayGoals;
    }

    @Override
    public List<String> generateColumns() {
        return Arrays.asList("GameId", "CompetitionId", "HomeTeamId", "AwayTeamId",
                "GameDateId", "Round", "HomeGoals", "AwayGoals");
    }

    @Override
    public List<String> generateValues() {
        return Arrays.asList(id, competition.getId(),
                String.valueOf(homeTeam.getId()), String.valueOf(awayTeam.getId()),
                String.valueOf(gameDate.getId()), round, homeGoals, awayGoals);
    }

    @Override
    public void sanitizeStrings() {
        this.round = "'" + round + "'";
        setValues();
    }
}
