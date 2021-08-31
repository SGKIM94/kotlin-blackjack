package blackjack.domain

class Player : Participant {

    constructor(name: String, cards: Set<Card>) : super(name, PlayerCards(cards))
    constructor(name: String, vararg cards: Card) : this(name, PlayerCards(cards.toSet()))
    constructor(name: String, bettingMoney: Int, vararg cards: Card) : this(name, PlayerCards(cards.toSet()), bettingMoney)
    constructor(name: String, cards: Set<Card>, bettingMoney: Int) : super(name, PlayerCards(cards.toSet()), bettingMoney, 0)

    override fun findStateByScore(score: Int): States {
        return when {
            score < Game.BLACK_JACK_SCORE -> States.HIT
            score > Game.BLACK_JACK_SCORE -> States.BUST
            else -> States.STAY
        }
    }

    override fun isDealer(): Boolean {
        return false
    }

    companion object {
        fun generatePlayers(names: Names, cards: GameCards, bettingMoneys: List<Int>): Participants {
            val sizeOfPlayers = bettingMoneys.size
            return Participants(
                (0 until sizeOfPlayers).map {
                    Player(names[it], cards.pollCardsToFirstDraw(), bettingMoneys[it])
                }.toSet()
            )
        }
    }
}