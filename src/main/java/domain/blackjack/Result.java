package domain.blackjack;

import domain.player.BettingMoney;

import java.math.BigDecimal;

public enum Result {
    BLACKJACK("승(블랙잭)", BigDecimal.valueOf(2.5)),
    WIN("승", BigDecimal.valueOf(2.0)),
    DRAW("무", BigDecimal.valueOf(1.0)),
    LOSE("패", BigDecimal.valueOf(0.0)),
    ;

    private final String value;
    private final BigDecimal ratio;

    Result(String value, BigDecimal ratio) {
        this.value = value;
        this.ratio = ratio;
    }

    public Result convertToOpposite() {
        if (this == WIN || this == BLACKJACK) {
            return LOSE;
        }

        if (this == LOSE) {
            return WIN;
        }

        return DRAW;
    }

    public BettingMoney payOut(BettingMoney bettingMoney) {
        return bettingMoney.applyRatio(this.ratio);
    }

    public String getValue() {
        return value;
    }

    public BigDecimal getRatio() {
        return ratio;
    }
}
