package blackjack.domain

import kotlin.math.abs

abstract class Participant(val name: String, cards: PlayerCards, var bettingMoney: Int, var profit: Int) {

    constructor(name: String, cards: PlayerCards) : this(name, PlayerCards(cards.toSet()), 0, 0)

    var cards: PlayerCards = cards
        private set

    var state: States = States.HIT
        get() {
            if (field == States.STAY || field == States.BLACK_JACK) {
                return field
            }

            return findStateByScore(cards.score)
        }
        private set

    var result: States = States.LOSE

    val isPlaying: Boolean
        get() = state == States.HIT

    init {
        if (cards.score == Game.BLACK_JACK_SCORE) {
            state = States.BLACK_JACK
        }
    }

    abstract fun findStateByScore(score: Int): States

    abstract fun isDealer(): Boolean

    fun addProfitByBlackjack() {
        profit += (bettingMoney * BLACKJACK_RECEIVE_MONEY_TIMES).toInt()
    }

    fun calculateProfitByBlackjack(): Int {
        return (bettingMoney * BLACKJACK_RECEIVE_MONEY_TIMES).toInt()
    }

    fun draw(card: Card) {
        throwExceptionIfIsNotPlayingState()

        cards = cards.addCard(card)
    }

    fun throwExceptionIfIsNotPlayingState() {
        if (isPlaying.not()) {
            throw IllegalStateException("게임이 진행 불가능한 상태입니다. : $state")
        }
    }

    fun stop() {
        state = States.STAY
    }

    fun win() {
        result = States.WIN
    }

    fun calculateToFindWinner(): Int {
        return abs(Game.BLACK_JACK_SCORE - cards.score)
    }

    fun isSameScore(player: Participant): Boolean {
        return cards.isSameScore(player.cards)
    }

    fun looseBettingMoney() {
        bettingMoney = INITIALIZE_MONEY
    }

    fun addProfitByBlackjackWithDealer() {
        profit += bettingMoney
    }

    companion object {
        const val BLACKJACK_RECEIVE_MONEY_TIMES = 1.5
        const val INITIALIZE_MONEY = 0
    }
}
