package blackjack.domain

class BettingMoney(private val bettingMoney: Int) {

    fun initialize(): BettingMoney {
        return BettingMoney(Participant.INITIALIZE_MONEY)
    }

    fun multiply(time: Double): BettingMoney {
        return BettingMoney((bettingMoney * time).toInt())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BettingMoney

        if (bettingMoney != other.bettingMoney) return false

        return true
    }

    override fun hashCode(): Int {
        return bettingMoney
    }
}