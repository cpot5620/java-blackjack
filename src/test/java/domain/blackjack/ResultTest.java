package domain.blackjack;

import domain.player.BettingMoney;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ResultTest {
    @DisplayName("상대방 입장의 승무패 결과로 반환 가능하다.") // WIN -> LOSE, LOSE -> WIN
    @Test
    void convertResultSuccessTest() {
        assertThat(Result.WIN.convertToOpposite())
                .isEqualTo(Result.LOSE);

        assertThat(Result.LOSE.convertToOpposite())
                .isEqualTo(Result.WIN);
    }

    @ParameterizedTest(name = "게임 결과에 따른 지불 금액을 결정한다.")
    @EnumSource(value = Result.class)
    void updateBalanceSuccessTest(Result result) {
        BigDecimal money = BigDecimal.valueOf(10000);
        BettingMoney bettingMoney = BettingMoney.from(money);

        assertThat(result.payOut(bettingMoney).getMoney())
                .isEqualTo((money.multiply(result.getRatio())).intValue());
    }

    @DisplayName("블랙잭으로 승리할 경우, 보너스 배당을 적용한다.")
    @Test
    void updateBalanceWithBlackjackSuccessTest() {
        BigDecimal money = BigDecimal.valueOf(10000);
        BettingMoney bettingMoney = BettingMoney.from(money);

        assertThat(Result.BLACKJACK.payOut(bettingMoney).getMoney())
                .isEqualTo((money.multiply(Result.BLACKJACK.getRatio())).intValue());
    }
}
