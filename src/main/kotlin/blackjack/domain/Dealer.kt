package blackjack.domain

class Dealer(cards: PlayerCards) : Participant(DEALER_NAME, cards) {

    constructor(cards: Set<Card>) : this(PlayerCards(cards))

    override fun findStateByScore(score: Int): States {
        return when {
            score < Game.BLACK_JACK_SCORE -> States.HIT
            score < Game.BLACK_JACK_SCORE -> States.HIT
            score >= Game.BLACK_JACK_SCORE -> States.WIN
            else -> States.STAY
        }
    }

    override fun isDealer(): Boolean {
        return true
    }

    fun isSmallerThanMinimumScore(): Boolean {
        return cards.score <= MINIMUM_DEALER_FIRST_SCORE
    }

    fun isWinScore(): Boolean {
        return cards.score > Game.BLACK_JACK_SCORE
    }

    fun cutProfitByBlackjacks(sumOfBlackjacksMoney: Int) {
        profit -= sumOfBlackjacksMoney
    }

    companion object {
        fun generateDealer(cards: GameCards) = Dealer(cards.pollCardsToFirstDraw())

        private const val MINIMUM_DEALER_FIRST_SCORE = 16
        const val DEALER_NAME = "딜러"
    }
}
