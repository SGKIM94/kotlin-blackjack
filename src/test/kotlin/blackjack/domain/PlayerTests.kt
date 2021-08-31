package blackjack.domain

import blackjack.fixture.PlayFixture.TEST_NAME
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PlayerTests {

    @Test
    fun `플레이어는 카드 2장을 가지고 시작한다`() {
        val player = Player(TEST_NAME, Card(CardSuite.HEART, CardNumber.ACE), Card(CardSuite.DIAMOND, CardNumber.QUEEN))

        assertThat(player.state).isEqualTo(States.BLACK_JACK)
    }

    @Test
    fun `플레이어는 카드를 더 받을 수 있다`() {
        val player = Player(TEST_NAME, Card(CardSuite.HEART, CardNumber.QUEEN), Card(CardSuite.DIAMOND, CardNumber.QUEEN))

        player.draw(Card(CardSuite.SPADE, CardNumber.KING))

        assertThat(player.state).isEqualTo(States.BUST)
    }

    @Test
    fun `플레이어는 카드를 받지 않을 수 있다`() {
        val player = Player(TEST_NAME, Card(CardSuite.HEART, CardNumber.QUEEN), Card(CardSuite.DIAMOND, CardNumber.QUEEN))

        player.stop()

        assertThrows<IllegalStateException> {
            player.draw(Card(CardSuite.SPADE, CardNumber.KING))
        }
    }

    @ParameterizedTest
    @CsvSource(
        "HEART,QUEEN,SPADE,KING",
        "HEART,ACE,SPADE,ACE",
        "HEART,TWO,SPADE,TEN",
        "HEART,FOUR,SPADE,FIVE",
        "HEART,SEVEN,SPADE,ACE",
        "HEART,NINE,SPADE,NINE"
    )
    fun `카드의 숫자의 합이 21미만이면 Hit 이다`(
        firstCardSuite: CardSuite,
        firstCardNumber: CardNumber,
        secondCardSuite: CardSuite,
        secondCardNumber: CardNumber
    ) {
        val game = Player(TEST_NAME, Card(firstCardSuite, firstCardNumber), Card(secondCardSuite, secondCardNumber))
        val state = game.state

        assertThat(state).isEqualTo(States.HIT)
    }

    @Test
    fun `처음 받은 2개의 두 카드의 숫자 합이 21이면 Blackjack 이다`() {
        val game = Player(TEST_NAME, Card(CardSuite.HEART, CardNumber.ACE), Card(CardSuite.SPADE, CardNumber.JACK))
        val state = game.state

        assertThat(state).isEqualTo(States.BLACK_JACK)
    }

    @Test
    fun `카드의 숫자의 합이 21미만이면 카드를 받을 수 있다`() {
        val game = Player(TEST_NAME, Card(CardSuite.HEART, CardNumber.KING), Card(CardSuite.SPADE, CardNumber.QUEEN))
        val state = game.state

        assertThat(state).isEqualTo(States.HIT)

        game.draw(Card(CardSuite.SPADE, CardNumber.THREE))

        assertThat(game.state).isEqualTo(States.BUST)
    }

    @Test
    fun `처음 받은 2장의 카드의 합이 21인 경우에만 블랙잭이 된다`() {
        val player = Player(TEST_NAME, Card(CardSuite.DIAMOND, CardNumber.ACE), Card(CardSuite.DIAMOND, CardNumber.QUEEN))

        assertThat(player.state).isEqualTo(States.BLACK_JACK)
    }

    @Test
    fun `처음 받은 2장 이상의 카드의 합이 21인 경우에는 STAY 이다`() {
        val player = Player(TEST_NAME, Card(CardSuite.DIAMOND, CardNumber.KING), Card(CardSuite.DIAMOND, CardNumber.QUEEN))

        player.draw(Card(CardSuite.DIAMOND, CardNumber.ACE))

        assertThat(player.state).isEqualTo(States.STAY)
    }

    @Test
    fun `딜러가 21을 초과하면 그 시점까지 남아 있던 플레이어들은 가지고 있는 패에 상관 없이 승리한다`() {
        val game = Dealer(setOf(Card(CardSuite.HEART, CardNumber.QUEEN), Card(CardSuite.SPADE, CardNumber.JACK)))

        game.draw(Card(CardSuite.DIAMOND, CardNumber.QUEEN))

        assertThat(game.state).isEqualTo(States.WIN)
    }

    @ParameterizedTest
    @CsvSource("1000, 1500", "0, 0", "2000, 3000")
    fun `처음 받은 2개의 카드가 블랙잭인 경우 베팅 금액의 1점5배를 딜러에게 받는다`(bettingMoney: Int, expectedWinningMoney: Int) {
        val player = Player(TEST_NAME, bettingMoney, Card(CardSuite.HEART, CardNumber.ACE), Card(CardSuite.SPADE, CardNumber.JACK))
        val state = player.state

        assertThat(state).isEqualTo(States.BLACK_JACK)
        assertThat(player.bettingMoney).isEqualTo(expectedWinningMoney)
    }

    @Test
    fun `플레이어의_베팅금액을_0원으로_만든다`() {
        val player = Player(TEST_NAME, 1000, Card(CardSuite.HEART, CardNumber.ACE), Card(CardSuite.SPADE, CardNumber.JACK))

        player.looseBettingMoney()

        assertThat(player.bettingMoney).isEqualTo(0)
    }

    @Test
    fun `처음 카드가 블랙잭인 경우 베팅금액의 1점5배를 수익금에 더한다`() {
        val player = Player(TEST_NAME, 1000, Card(CardSuite.HEART, CardNumber.ACE), Card(CardSuite.SPADE, CardNumber.JACK))

        player.addProfitByBlackjack()

        assertThat(player.profit).isEqualTo(1500)
    }
}
