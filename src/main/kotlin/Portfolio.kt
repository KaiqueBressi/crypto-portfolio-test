import java.math.BigDecimal
import java.math.RoundingMode

data class Portfolio(
    val totalValue: BigDecimal,
    val bestAsset: String,
    val bestPerformanceValue: BigDecimal,
    val worstAsset: String,
    val worstPerformanceValue: BigDecimal
) {
    val total: BigDecimal get() = this.totalValue.setScale(2, RoundingMode.HALF_UP)
    val bestPerformance: BigDecimal get() = this.bestPerformanceValue.setScale(2, RoundingMode.HALF_UP)
    val worstPerformance: BigDecimal get() = this.worstPerformanceValue.setScale(2, RoundingMode.HALF_UP)
}