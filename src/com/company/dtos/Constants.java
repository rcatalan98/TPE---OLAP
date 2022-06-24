package com.company.dtos;

public class Constants {

    public enum PlayerFields {
        PLAYER_ID(0), PLAYER_CLUB(1), PLAYER_NAME(2), PLAYER_BIRTH(3),
        PLAYER_POSITION(4), PLAYER_SUBPOSITION(5), PLAYER_FOOT(6), PLAYER_HEIGHT(7),
        PLAYER_VALUATION(8), PLAYER_CITIZENSHIP(9);

        public final int value;

        PlayerFields(int i) {
            this.value = i;
        }
    }

    public enum AppearanceFields {
        PLAYER_ID(0), APPEARANCE_GAME_ID(1), APPEARANCE_GOALS(2),
        APPEARANCE_ASSISTS(3), APPEARANCE_MINUTES(4), APPEARANCE_YELLOW_CARDS(5),
        APPEARANCE_RED_CARDS(6);

        public final int value;

        AppearanceFields(int i) {
            this.value = i;
        }
    }

    public enum ClubFields {
        CLUB_ID(0), CLUB_NAME(1), CLUB_COMPETITION_ID(2), NATIONAL_TEAM_PLAYERS(3);

        public final int value;

        ClubFields(int i) {
            this.value = i;
        }
    }

    public enum CompetitionFields {
        COMPETITION_ID(0), COMPETITION_NAME(1), COMPETITION_TYPE(2),
        COMPETITION_COUNTRY(3);

        public final int value;

        CompetitionFields(int i) {
            this.value = i;
        }
    }

    public enum GameFields {
        GAME_ID(0), GAME_COMPETITION(1), GAME_SEASON(2), GAME_ROUND(3), GAME_DATE(4),
        GAME_HOME_CLUB_ID(5), GAME_AWAY_CLUB_ID(6), GAME_HOME_GOALS(7), GAME_AWAY_GOALS(8);

        public final int value;

        GameFields(int i) {
            this.value = i;
        }
    }

    public enum ValuationFields {
        PLAYER_ID(0), VALUATION_DATE(1), VALUATION_AMOUNT(2);

        public final int value;

        ValuationFields(int i) {
            this.value = i;
        }
    }
}
