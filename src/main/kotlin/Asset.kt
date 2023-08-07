import java.math.BigDecimal

data class Asset(
    val symbol: String,
    val quantity: BigDecimal,
    val price: BigDecimal,
) {
    val total get() = this.quantity * this.price
}
