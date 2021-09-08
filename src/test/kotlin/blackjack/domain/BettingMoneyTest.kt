package blackjack.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BettingMoneyTest {

    @Test
    fun `베팅금액을 초기화한다`() {
        val bettingMoney = BettingMoney(1000)
        val initializedBettingMoney = bettingMoney.initialize()

        assertThat(initializedBettingMoney).isEqualTo(BettingMoney(0))
    }

    @Test
    fun `베팅금액을 요청한 배수만큼 곱한다`() {
        val bettingMoney = BettingMoney(2000)
        val multiplyBettingMoney = bettingMoney.multiply(1.5)

        assertThat(multiplyBettingMoney).isEqualTo(BettingMoney(3500))
    }
}