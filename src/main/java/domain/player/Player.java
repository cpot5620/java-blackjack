package domain.player;

import domain.blackjack.Result;

import java.util.Objects;

public class Player extends Participant {
    private static final String UNAVAILABLE_NAME = "'%s'라는 이름은 사용할 수 없습니다.";

    private BettingMoney bettingMoney;

    private Player(Name name, BettingMoney bettingMoney) {
        super(name);
        this.bettingMoney = bettingMoney;
    }

    public static Player of(Name name, BettingMoney bettingMoney) {
        validateNameIsNotSameDealer(name);

        return new Player(name, bettingMoney);
    }

    private static void validateNameIsNotSameDealer(Name name) {
        if (DEALER_NAME.equals(name.getName())) {
            throw new IllegalArgumentException(String.format(UNAVAILABLE_NAME, DEALER_NAME));
        }
    }

    public BettingMoney calculateProfitBy(Result result) {
        BettingMoney currentMoney = result.payOut(bettingMoney);

        return bettingMoney.calculateProfit(currentMoney);
    }

    public void applyRevenue(BettingMoney profit) {
        this.bettingMoney = bettingMoney.updateRevenue(profit);
    }

    public int getMoney() {
        return bettingMoney.getMoney();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(getName(), player.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
