package domain.player;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BettingMoneyTest {
    @ParameterizedTest(name = "배팅 금액은 최소 100원, 최대 1억이다.")
    @ValueSource(ints = {99, 100000001})
    void createBettingMoneyFailTest(int money) {
        assertThatThrownBy(() -> BettingMoney.from(BigDecimal.valueOf(money)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest(name = "배팅 금액은 최소 100원, 최대 1억이다.")
    @ValueSource(ints = {100, 100000000})
    void createBettingMoneySuccessTest(int money) {
        assertDoesNotThrow(() -> BettingMoney.from(BigDecimal.valueOf(money)));
    }

    @ParameterizedTest(name = "배팅 금액은 100원 단위로 가능하다.")
    @ValueSource(ints = {101, 110010})
    void createBettingMoneyFailByUnitTest(int money) {
        assertThatThrownBy(() -> BettingMoney.from(BigDecimal.valueOf(money)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest(name = "배팅 금액은 100원 단위로 가능하다.")
    @ValueSource(ints = {10000, 110100})
    void createBettingMoneySuccessByUnitTest(int money) {
        assertDoesNotThrow(() -> BettingMoney.from(BigDecimal.valueOf(money)));
    }

    @ParameterizedTest(name = "배율에 맞게 배팅금액을 돌려받을 수 있다.")
    @MethodSource("moneyAndRatioProvider")
    void receiveBettingMoneySuccessTest(BigDecimal money, BigDecimal ratio) {
        BettingMoney bettingMoney = BettingMoney.from(money);

        assertThat(bettingMoney.applyRatio(ratio).getMoney())
                .isEqualTo((money.multiply(ratio)).intValue());
    }

    static Stream<Arguments> moneyAndRatioProvider() {
        return Stream.of(
                Arguments.arguments(BigDecimal.valueOf(100), BigDecimal.valueOf(0.0)),
                Arguments.arguments(BigDecimal.valueOf(2000), BigDecimal.valueOf(1.5)),
                Arguments.arguments(BigDecimal.valueOf(1000000), BigDecimal.valueOf(2.5))
        );
    }
}
