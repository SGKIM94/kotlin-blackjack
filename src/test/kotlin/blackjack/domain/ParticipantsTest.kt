package blackjack.domain

import blackjack.fixture.PlayFixture.TEST_NAME
import blackjack.fixture.PlayFixture.TEST_SECOND_NAME
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ParticipantsTest {

    @Test
    fun `점수륿_비교하여_우승자를_찾는다`() {
        val firstPlayer = Player(TEST_NAME, Card(CardSuite.HEART, CardNumber.ACE), Card(CardSuite.SPADE, CardNumber.JACK))
        val secondPlayer = Player(TEST_SECOND_NAME, Card(CardSuite.DIAMOND, CardNumber.TEN), Card(CardSuite.SPADE, CardNumber.FOUR))

        val participants = Participants(setOf(firstPlayer, secondPlayer))

        val winner = participants.findWinnerScore()

        assertThat(winner).isEqualTo(firstPlayer)
    }

    @Test
    fun `우승_점수를_가지는경우_우승자로_만든다`() {
        val firstPlayer = Player(TEST_NAME, Card(CardSuite.HEART, CardNumber.ACE), Card(CardSuite.SPADE, CardNumber.JACK))
        val secondPlayer = Player(TEST_SECOND_NAME, Card(CardSuite.DIAMOND, CardNumber.TEN), Card(CardSuite.SPADE, CardNumber.FOUR))

        val participants = Participants(setOf(firstPlayer, secondPlayer))

        participants.makeWinners(secondPlayer)

        assertThat(secondPlayer.result).isEqualTo(States.WIN)
    }

    @Test
    fun `플레이어들이 첫 카드가 블랙잭인 경우 베팅금액의 1점5배를 수익에 더한다`() {
        val firstPlayer = Player(TEST_NAME, 2000, Card(CardSuite.HEART, CardNumber.ACE), Card(CardSuite.SPADE, CardNumber.JACK))
        val secondPlayer = Player(TEST_SECOND_NAME, 1000, Card(CardSuite.DIAMOND, CardNumber.ACE), Card(CardSuite.SPADE, CardNumber.TEN))

        val participants = Participants(setOf(firstPlayer, secondPlayer))

        participants.addProfitsByBlackjack()

        assertThat(firstPlayer.profit).isEqualTo(3000)
        assertThat(secondPlayer.profit).isEqualTo(1500)
    }

    @Test
    fun `플레이어들이 첫 카드가 블랙잭인 경우 베팅금액의 1점5배를 딜러에게 받기 위해 계산한다`() {
        val firstPlayer = Player(TEST_NAME, 2000, Card(CardSuite.HEART, CardNumber.ACE), Card(CardSuite.SPADE, CardNumber.JACK))
        val secondPlayer = Player(TEST_SECOND_NAME, 1000, Card(CardSuite.DIAMOND, CardNumber.ACE), Card(CardSuite.SPADE, CardNumber.TEN))

        val participants = Participants(setOf(firstPlayer, secondPlayer))

        val allProfit = participants.calculateProfitsByBlackjack()

        assertThat(allProfit).isEqualTo(4500)
    }

    @Test
    fun `플레이어들과 딜러의 첫 카드가 블랙잭인 경우 배팅한 금액을 수익에 더한다`() {
        val firstPlayer = Player(TEST_NAME, 2000, Card(CardSuite.HEART, CardNumber.ACE), Card(CardSuite.SPADE, CardNumber.JACK))
        val secondPlayer = Player(TEST_SECOND_NAME, 1000, Card(CardSuite.DIAMOND, CardNumber.ACE), Card(CardSuite.SPADE, CardNumber.TEN))

        val participants = Participants(setOf(firstPlayer, secondPlayer))

        participants.addProfitsByBlackjackWithDealer()

        assertThat(firstPlayer.profit).isEqualTo(2000)
        assertThat(secondPlayer.profit).isEqualTo(1000)
    }

    @Test
    fun `패배한 플레이어들은 베팅한 금액만큼 수익에서 차감시킨다`() {
        val firstPlayer = Player(TEST_NAME, 2000, Card(CardSuite.HEART, CardNumber.ACE), Card(CardSuite.SPADE, CardNumber.JACK))
        val secondPlayer = Player(TEST_SECOND_NAME, 1000, Card(CardSuite.DIAMOND, CardNumber.TWO), Card(CardSuite.SPADE, CardNumber.THREE))
        val thirdPlayer = Player("테스터", 4000, Card(CardSuite.DIAMOND, CardNumber.FOUR), Card(CardSuite.SPADE, CardNumber.FIVE))

        val participants = Participants(setOf(firstPlayer, secondPlayer, thirdPlayer))

        participants.cutMoneyWithLosers(firstPlayer)

        assertThat(secondPlayer.profit).isEqualTo(-1000)
        assertThat(thirdPlayer.profit).isEqualTo(-4000)
    }
}
