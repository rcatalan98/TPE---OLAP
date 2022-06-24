import pandas as pd

appearances_tabla = pd.read_csv('/appearances.csv')
appearances_modif = appearances_tabla[['player_id', 'game_id', 'goals', 'assists', 'minutes_played', 'yellow_cards', 'red_cards']]
appearances_modif.to_csv('appearances_modif.csv', sep=';', index=False)

players = pd.read_csv ('players.csv')
players_modif = players[['player_id', 'current_club_id', 'pretty_name', 'date_of_birth', 'position', 'sub_position', 'foot', 'height_in_cm', 'market_value_in_gbp']]
players_modif['foot'] = players_modif['foot'].fillna('Right')
players_modif = players_modif[players_modif['date_of_birth'].notna()]
players_modif = players_modif.fillna(0)
players_modif.to_csv('players_modif.csv', sep=';', index=False)

clubs = pd.read_csv('clubs.csv')
clubs_modif = clubs[['club_id', 'pretty_name', 'domestic_competition_id', 'national_team_players']]
clubs_modif.to_csv('clubs_modif.csv', sep=';', index=False)

games = pd.read_csv('games.csv')
games_modif = games[['game_id', 'competition_code', 'season', 'round', 'date', 'home_club_id',	'away_club_id',	'home_club_goals',	'away_club_goals']]
games_modif.to_csv('games_modif.csv', sep=';', index=False)

players_valuation = pd.read_csv(player_valuations.csv')
players_valuation.to_csv('player_valuations_modif.csv', sep=';', index=False)
