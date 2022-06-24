package com.company;

import com.company.dtos.*;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.company.dtos.Constants.*;

public class Main {

    static List<PlayerDto> players = new LinkedList<>();
    static List<ClubDto> clubs = new LinkedList<>();
    static List<CountryDto> countries = new LinkedList<>();
    static List<GameDto> games = new LinkedList<>();
    static List<CompetitionDto> competitions = new LinkedList<>();
    static List<PlayerGamePerformanceDto> appearances = new LinkedList<>();
    static List<PositionDto> positions = new LinkedList<>();
    static List<SubPositionDto> subPositions = new LinkedList<>();
    static List<TimeDto> timeKeys = new LinkedList<>();
    static List<MarketValueDto> marketValues = new LinkedList<>();

    public static void main(String[] args) {
	// write your code here
        List<String> pathNames;
        //lista de archivos por linea
        List<List<List<String>>> filesLines = new LinkedList<>();
        String baseDir = "./assets/";
        File f = new File(baseDir);
        pathNames = Arrays.asList(Objects.requireNonNull(f.list()));
        System.out.println("Reading files");
        for (String pathname : pathNames) {
            filesLines.add(CsvParser.parseCsvFile(baseDir + pathname, ";", true));
        }
        System.out.println("Starting to create entities");
        preProcess(filesLines, pathNames);
        System.out.println("Starting to create queries");
        generateQueries();
    }

    public static void generateQueries() {
        System.out.println("Writing position queries");
        QueryGenerator.generateInsertQuery("Position", positions, "positions");
        System.out.println("Writing subposition queries");
        QueryGenerator.generateInsertQuery("SubPosition", subPositions, "subPositions");
        System.out.println("Writing country queries");
        QueryGenerator.generateInsertQuery("Country", countries, "countries");
        System.out.println("Writing competition queries");
        QueryGenerator.generateInsertQuery("Competition", competitions, "competitions");
        System.out.println("Writing club queries");
        QueryGenerator.generateInsertQuery("Club", clubs, "clubs");
        System.out.println("Writing player queries");
        QueryGenerator.generateInsertQuery("Player", players, "players");
        System.out.println("Writing game queries");
        QueryGenerator.generateInsertQuery("Game", games, "games");
        System.out.println("Writing time queries");
        QueryGenerator.generateInsertQuery("\"Time\"", timeKeys, "timekeys");
        System.out.println("Writing appearances queries");
        List<PlayerGamePerformanceDto> first = new ArrayList<>(appearances.subList(0, (appearances.size() + 1)/3));
        QueryGenerator.generateInsertQuery("PlayerGamePerformance", first, "appearances1");

        List<PlayerGamePerformanceDto> second = new ArrayList<>(appearances.subList((appearances.size() + 1)/3, (appearances.size() + 1)*2/3));
        QueryGenerator.generateInsertQuery("PlayerGamePerformance", second, "appearances2");

        List<PlayerGamePerformanceDto> third = new ArrayList<>(appearances.subList((appearances.size() + 1)*2/3, appearances.size()));
        QueryGenerator.generateInsertQuery("PlayerGamePerformance", third, "appearances3");

    }

    public static void preProcess(List<List<List<String>>> filesLines, List<String> fileNames) {
        int playersFileIndex = fileNames.indexOf("players_modif.csv");
        List<List<String>> playerLines = filesLines.get(playersFileIndex);
        System.out.println("Generating positions and subpositions");
        generatePlayerPositions(playerLines);

        int competitionFileIndex = fileNames.indexOf("competitions_modif.csv");
        List<List<String>> competitionLines = filesLines.get(competitionFileIndex);
        System.out.println("Generating countries and competitions");
        generateCountriesAndCompetitions(competitionLines);

        int clubFileIndex = fileNames.indexOf("clubs_modif.csv");
        List<List<String>> clubLines = filesLines.get(clubFileIndex);
        System.out.println("Generating clubs");
        generateClubs(clubLines);

        int marketValueFileIndex = fileNames.indexOf("player_valuations_modif.csv");
        List<List<String>> marketValueLines = filesLines.get(marketValueFileIndex);
        System.out.println("Generating market values");
        generateMarketValues(marketValueLines, playerLines.size());
        System.out.println("Generating players");
        generatePlayers(playerLines);

        int gameFileIndex = fileNames.indexOf("games_modif.csv");
        List<List<String>> gameLines = filesLines.get(gameFileIndex);
        System.out.println("Generating games and times");
        generateGamesAndTimes(gameLines);

        int appearancesIndex = fileNames.indexOf("appearances_modif.csv");
        List<List<String>> appearancesLines = filesLines.get(appearancesIndex);
        System.out.println("Generating appearances");
        generateAppearances(appearancesLines);
    }

    public static void generateAppearances(List<List<String>> appearancesLines) {
        appearancesLines.forEach(appearancesLine -> {
            Optional<PlayerDto> p = players.stream()
                    .filter(player -> player.getId().equals(appearancesLine.get(AppearanceFields.PLAYER_ID.value)))
                    .findFirst();
            if(p.isPresent()) {
                PlayerGamePerformanceDto appearance = new PlayerGamePerformanceDto(
                        appearancesLine.get(AppearanceFields.APPEARANCE_GAME_ID.value), p.get(), p.get().getClub(),
                        appearancesLine.get(AppearanceFields.APPEARANCE_GOALS.value),
                        appearancesLine.get(AppearanceFields.APPEARANCE_ASSISTS.value),
                        appearancesLine.get(AppearanceFields.APPEARANCE_YELLOW_CARDS.value),
                        appearancesLine.get(AppearanceFields.APPEARANCE_RED_CARDS.value),
                        appearancesLine.get(AppearanceFields.APPEARANCE_MINUTES.value));
                appearances.add(appearance);
            }
        });
    }

    public static void generateGamesAndTimes(List<List<String>> gameLines) {
        gameLines.forEach(gameLine -> {
            CompetitionDto c = competitions.stream()
                    .filter(competition -> competition.getId()
                            .equals(gameLine.get(GameFields.GAME_COMPETITION.value)))
                    .findFirst().get();

            Optional<ClubDto> homeOpt = clubs.stream()
                    .filter(club -> club.getId()
                            .equals(gameLine.get(GameFields.GAME_HOME_CLUB_ID.value)))
                    .findFirst();
            ClubDto home = homeOpt.orElse(new ClubDto(gameLine.get(GameFields.GAME_HOME_CLUB_ID.value),
                    c.getCountry(), "Unidentified", "0"));

            Optional<ClubDto> awayOpt = clubs.stream()
                    .filter(club -> club.getId()
                            .equals(gameLine.get(GameFields.GAME_AWAY_CLUB_ID.value)))
                    .findFirst();
            ClubDto away = awayOpt.orElse(new ClubDto(gameLine.get(GameFields.GAME_AWAY_CLUB_ID.value),
                    c.getCountry(), "Unidentified", "0"));

            String[] dateParts = gameLine.get(GameFields.GAME_DATE.value).split("-");
            LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]),
                    Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
            TimeDto timeKey = new TimeDto(date);
            games.add(new GameDto(gameLine.get(GameFields.GAME_ID.value), c, home, away, timeKey,
                    gameLine.get(GameFields.GAME_ROUND.value),
                    gameLine.get(GameFields.GAME_HOME_GOALS.value),
                    gameLine.get(GameFields.GAME_AWAY_GOALS.value)));
            timeKeys.add(timeKey);
        });
    }

    public static void generateMarketValues(List<List<String>> marketValueLines, int players) {
        List<MarketValueDto> values = marketValueLines.stream()
                .map(marketValueLine -> {
                    String[] dateParts = marketValueLine.get(ValuationFields.VALUATION_DATE.value).split("-");
                    return new MarketValueDto(marketValueLine.get(ValuationFields.PLAYER_ID.value),
                            marketValueLine.get(ValuationFields.VALUATION_AMOUNT.value),
                            LocalDate.of(Integer.parseInt(dateParts[0]),
                                    Integer.parseInt(dateParts[1]),
                                    Integer.parseInt(dateParts[2])));
                }).sorted((MarketValueDto v1, MarketValueDto v2) -> v2.getDate().compareTo(v1.getDate()))
                .collect(Collectors.toList());

        Map<String, MarketValueDto> mostRecentValuations = new HashMap<>();
        for(MarketValueDto valuation : values) {
            if(!mostRecentValuations.containsKey(valuation.getPlayerId())) {
                mostRecentValuations.put(valuation.getPlayerId(), valuation);
            }
            if(mostRecentValuations.size() == players)
                break;
        }
        marketValues.addAll(mostRecentValuations.values());
    }

    public static void generatePlayers(List<List<String>> playerLines) {
        playerLines.forEach(playerLine -> {
            SubPositionDto sPosition = subPositions.stream()
                    .filter(subPosition -> subPosition.getName()
                            .equals(playerLine.get(PlayerFields.PLAYER_SUBPOSITION.value)))
                    .findFirst().get();

            ClubDto c = clubs.stream()
                    .filter(club -> club.getId()
                            .equals(playerLine.get(PlayerFields.PLAYER_CLUB.value)))
                    .findFirst().get();

            Optional<MarketValueDto> valuation = marketValues.stream()
                    .filter(marketValue -> marketValue.getPlayerId()
                            .equals(playerLine.get(PlayerFields.PLAYER_ID.value)))
                    .findFirst();
            MarketValueDto v = valuation.orElse(new MarketValueDto(playerLine
                    .get(PlayerFields.PLAYER_ID.value),
                    playerLine.get(PlayerFields.PLAYER_VALUATION.value).replaceAll(".0", ""),
                    LocalDate.MIN));

            PlayerDto player = new PlayerDto(playerLine.get(PlayerFields.PLAYER_ID.value),
                    sPosition, c.getCountry(), playerLine.get(PlayerFields.PLAYER_NAME.value),
                    playerLine.get(PlayerFields.PLAYER_HEIGHT.value),
                    playerLine.get(PlayerFields.PLAYER_FOOT.value),
                    (v.getMarketValue().equals("") ? "0" : v.getMarketValue()),
                    playerLine.get(PlayerFields.PLAYER_BIRTH.value), c);
            players.add(player);
        });
    }

    public static void generateClubs(List<List<String>> clubLines) {
        Set<ClubDto> clubSet = new HashSet<>();
        clubLines.forEach(clubLine -> {
            CountryDto c = competitions.stream()
                    .filter(competition -> competition.getId()
                            .equals(clubLine.get(ClubFields.CLUB_COMPETITION_ID.value)))
                    .findFirst().get().getCountry();
            clubSet.add(new ClubDto(clubLine.get(ClubFields.CLUB_ID.value), c,
                    clubLine.get(ClubFields.CLUB_NAME.value),
                    clubLine.get(ClubFields.NATIONAL_TEAM_PLAYERS.value)));
        });
        clubs.addAll(clubSet);
    }

    public static void generatePlayerPositions(List<List<String>> playerLines) {
        Set<SubPositionDto> subPositionsSet = new HashSet<>();
        Map<String, PositionDto> positionsMap = new HashMap<>();

        playerLines.forEach(playerLine -> {
            PositionDto p = new PositionDto(playerLine.get(PlayerFields.PLAYER_POSITION.value));
            if(!positionsMap.containsKey(p.getName()))
                positionsMap.put(p.getName(), p);
            SubPositionDto sp = new SubPositionDto(playerLine.get(PlayerFields.PLAYER_SUBPOSITION.value),
                    positionsMap.get(playerLine.get(PlayerFields.PLAYER_POSITION.value)));
            subPositionsSet.add(sp);
        });
        positions.addAll(positionsMap.values());
        subPositions.addAll(subPositionsSet);
    }

    public static void generateCountriesAndCompetitions(List<List<String>> competitionLines) {
        Set<CountryDto> countriesSet = new HashSet<>();
        Set<CompetitionDto> competitionsSet = new HashSet<>();

        competitionLines.forEach(competitionLine -> {
            CountryDto c = new CountryDto(competitionLine.get(CompetitionFields.COMPETITION_COUNTRY.value));
            competitionsSet.add(new CompetitionDto(competitionLine.get(CompetitionFields.COMPETITION_ID.value),
                    competitionLine.get(CompetitionFields.COMPETITION_NAME.value),
                    competitionLine.get(CompetitionFields.COMPETITION_TYPE.value), c));
            if(!c.getName().equals("") && !c.getName().equals(" "))
                countriesSet.add(c);
        });
        competitions.addAll(competitionsSet);
        countries.addAll(countriesSet);
    }
}
