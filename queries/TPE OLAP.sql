---2.A

with young_players_defenders_mktvalue as (
    select *
    from player p natural join position as pos
    where  extract(year from age(CURRENT_DATE,p.dateofbirth)) <=23 and p.marketvalue <= 7000000 and pos.positionname = 'Defender'
),
young_players as (
    select *
    from player p
    where  extract(year from age(CURRENT_DATE,p.dateofbirth)) <=23
),
champions_played_times as (
    SELECT perf.playerid, SUM(perf.minutesplayed) AS sum FROM playergameperformance AS perf NATURAL JOIN game AS g
    WHERE g.competitionid = 'CL' AND g.gamedateid IN (
        SELECT aux.timekey FROM time as aux
        WHERE season = '2019-2020' or season = '2020-2021' or season = '2021-2022'
    )
    GROUP BY perf.playerid
),
played_last_two_seasons as (
    SELECT DISTINCT g2.gameid FROM game AS g2
    WHERE gamedateid IN (
            SELECT aux.timekey FROM time as aux
            WHERE season = '2020-2021' or season = '2021-2022'
        )
)
SELECT DISTINCT young_players.name, young_players.playerid, champions_played_times.sum as minutes_played, young_players.marketvalue as market_value FROM young_players_defenders_mktvalue as young_players NATURAL JOIN champions_played_times
ORDER BY champions_played_times.sum DESC
LIMIT 1

---2.B
SELECT DISTINCT young_players.name, young_players.marketvalue, SUM(playergameperformance.goals) FROM young_players NATURAL JOIN playergameperformance
WHERE gameid IN (SELECT * FROM played_last_two_seasons)
GROUP BY young_players.playerid, young_players.name,young_players.marketvalue
HAVING SUM(playergameperformance.goals) >= 20
ORDER BY young_players.marketvalue


---6.B
SELECT c.name, CAST(SUM(p.redcards) AS FLOAT)/COUNT(g.gamedateid) as avg FROM competition as c NATURAL JOIN game as g NATURAL JOIN playergameperformance as p
WHERE g.gamedateid IN (
        SELECT aux.timekey FROM time as aux
        WHERE season = '2019-2020' or season = '2020-2021' or season = '2021-2022'
    )
GROUP BY c.competitionid
HAVING COUNT(g.gamedateid) > 3000
ORDER BY avg ASC
LIMIT 1




