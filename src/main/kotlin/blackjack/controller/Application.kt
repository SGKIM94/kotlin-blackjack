package blackjack.controller

import blackjack.domain.*
import blackjack.view.InputView
import blackjack.view.ResultView

fun main() {
    val names = Names(InputView.playerNames())

    val gameCards = GameCards()

    val bettingMoneys = names.map{ InputView.bettingMoney(it) }

    val players = Player.generatePlayers(names, gameCards, bettingMoneys)
    val dealer = Dealer.generateDealer(gameCards)

    val game = Game(players, dealer, gameCards)

    ResultView.printInitNotice(names, Game.BLACK_JACK_CARD_COUNT)

    ResultView.printAllPlayerCards(game)

    game.playGameWithParticipants()

    ResultView.printAllResult(game)

    ResultView.printGameResultTitle()

    game.assignWinner()

    ResultView.printGameResults(dealer, game)
}
