package com.company;

import java.time.LocalDate;
import java.util.Objects;

public class MarketValueDto {
    private final String playerId;
    private final String marketValue;
    private final LocalDate date;

    public String getPlayerId() { return playerId; }

    public String getMarketValue() { return marketValue; }

    public LocalDate getDate() { return date; }

    public MarketValueDto(String playerId, String marketValue, LocalDate date) {
        this.playerId = playerId;
        this.marketValue = marketValue;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketValueDto that = (MarketValueDto) o;
        return getPlayerId().equals(that.getPlayerId()) &&
                getMarketValue().equals(that.getMarketValue()) &&
                getDate().equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayerId(), getMarketValue(), getDate());
    }
}
