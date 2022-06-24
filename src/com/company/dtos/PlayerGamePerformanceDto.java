package com.company;

import com.company.dtos.ClubDto;
import com.company.dtos.QueryDto;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlayerGamePerformanceDto extends QueryDto {
    private final String game;
    private final PlayerDto player;
    private final ClubDto club;
    private final String goals;
    private final String assists;
    private final String yellowCards;
    private final String redCards;
    private final String minutesPlayed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerGamePerformanceDto that = (PlayerGamePerformanceDto) o;
        return getGame().equals(that.getGame()) && getPlayer().equals(that.getPlayer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGame(), getPlayer());
    }

    public String getGame() { return game; }

    public PlayerDto getPlayer() { return player; }

    public ClubDto getClub() { return club; }

    public String getGoals() { return goals; }

    public String getAssists() { return assists; }

    public String getYellowCards() { return yellowCards; }

    public String getRedCards() { return redCards; }

    public String getMinutesPlayed() { return minutesPlayed; }

    public PlayerGamePerformanceDto(String game, PlayerDto player, ClubDto club, String goals,
                                    String assists, String yellowCards, String redCards,
                                    String minutesPlayed) {
        this.game = game;
        this.player = player;
        this.club = club;
        this.goals = goals;
        this.assists = assists;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.minutesPlayed = minutesPlayed;
        setColumns();
        setValues();
    }



    @Override
    public List<String> generateColumns() {
        return Arrays.asList("GameId", "PlayerId", "ClubId", "Goals", "Assists",
                "YellowCards", "RedCards", "MinutesPlayed");
    }

    @Override
    public List<String> generateValues() {
        return Arrays.asList(game, player.getId(), club.getId(), goals,
                assists, yellowCards, redCards, minutesPlayed);
    }

    @Override
    public void sanitizeStrings() {
    }
}
