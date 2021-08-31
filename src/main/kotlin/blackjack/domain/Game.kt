package blackjack.domain

import blackjack.view.InputView
import blackjack.view.ResultView

class Game(val participants: Participants, val dealer: Dealer, private val cards: GameCards = GameCards()) {

    init {
        addProfitWhenHaveBlackjack()
    }

    private fun addProfitWhenHaveBlackjack() {
        val blackjacks = participants.filter { it.state === States.BLACK_JACK }

        if (blackjacks.isEmpty()) {
            return
        }

        if (blackjacks.isNotEmpty() && dealer.state !== States.BLACK_JACK) {
            addProfitsByBlackjack(blackjacks)
            val sumOfBlackjacksMoney = calculateProfitsByBlackjack(blackjacks)

            dealer.cutProfitByBlackjacks(sumOfBlackjacksMoney)
            return
        }

        blackjacks.forEach { it.addProfitByBlackjackWithDealer() }
    }

    private fun calculateProfitsByBlackjack(blackjacks: List<Participant>) =
            blackjacks.map { it.calculateProfitByBlackjack() }.sum()

    private fun addProfitsByBlackjack(blackjacks: List<Participant>) {
        blackjacks.map { it.addProfitByBlackjack() }
    }

    fun draw(participant: Participant) {
        participant.throwExceptionIfIsNotPlayingState()

        val card = cards.poll()
        participant.draw(card)
    }

    fun assignWinner() {
        if (dealer.isWinScore()) {
            return
        }

        val winner = participants.findWinnerScore()
        participants.makeWinners(winner)
    }

    fun playGameWithParticipants() {
        playGames()

        drawIfSmallerThanMinimum()
    }

    private fun playGames() {
        participants.forEach {
            playGame(it)
        }
    }

    private fun playGame(participant: Participant) {
        while (participant.isPlaying) {
            val answer = InputView.askIfPlayerWantToMoreCard(participant.name)

            drawOrStopByAnswer(participant, answer)

            ResultView.printPlayerCards(participant.name, participant.cards)

            looseMoneyWhenBust(participant)
        }
    }

    private fun looseMoneyWhenBust(participant: Participant) {
        if (participant.state === States.BUST) {
            participant.looseBettingMoney()
        }
    }

    private fun drawOrStopByAnswer(participant: Participant, answer: Boolean) {
        if (answer) {
            draw(participant)
            return
        }

        participant.stop()
    }

    private fun drawIfSmallerThanMinimum() {
        while (dealer.isSmallerThanMinimumScore()) {
            println("딜러는 16이하라 한장의 카드를 더 받았습니다.")
            draw(dealer)
        }
    }

    companion object {
        const val BLACK_JACK_SCORE = 21
        const val BLACK_JACK_CARD_COUNT = 2
        const val START_INDEX = 1
    }
}
