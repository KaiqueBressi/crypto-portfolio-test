import kotlinx.coroutines.runBlocking
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.math.BigDecimal
import kotlin.test.assertEquals

class PortfolioPerformanceCalculatorSpec: Spek({
    describe("when requesting portfolio performance") {
        it("returns the calculated performance") {
            runBlocking {
                val btc = Asset("BTC", BigDecimal("0.12345"), BigDecimal("37870.5058"))
                val eth = Asset("ETH", BigDecimal("4.89532"), BigDecimal("2004.9774"))
                val assetApi = mock<AssetAPI>()
                whenever(assetApi.getAssets()).thenReturn(
                    listOf(AssetData("bitcoin", "BTC"), AssetData("ethereum", "ETH"))
                )
                whenever(assetApi.getAssetHistoryPrice("bitcoin", "d1", 1617753600000, 1617753601000)).thenReturn(
                    BigDecimal("56999.9728252053067291")
                )
                whenever(assetApi.getAssetHistoryPrice("ethereum", "d1", 1617753600000, 1617753601000)).thenReturn(
                    BigDecimal("2032.1394325557042107")
                )

                val portfolio = PortfolioPerformanceCalculator(assetApi).calculate(hashMapOf("BTC" to btc, "ETH" to eth))

                assertEquals(portfolio.total, 16984.62.toBigDecimal())
                assertEquals(portfolio.bestAsset, "BTC")
                assertEquals(portfolio.bestPerformance, 1.51.toBigDecimal())
                assertEquals(portfolio.worstAsset, "ETH")
                assertEquals(portfolio.worstPerformance, 1.01.toBigDecimal())
            }
        }

        it("ignores assets that it couldn't find") {
            runBlocking {
                val btc = Asset("BTC", BigDecimal("0.12345"), BigDecimal("37870.5058"))
                val eth = Asset("ETH", BigDecimal("4.89532"), BigDecimal("2004.9774"))
                val assetApi = mock<AssetAPI>()
                whenever(assetApi.getAssets()).thenReturn(
                    listOf(AssetData("bitcoin", "BTC"))
                )
                whenever(assetApi.getAssetHistoryPrice("bitcoin", "d1", 1617753600000, 1617753601000)).thenReturn(
                    BigDecimal("56999.9728252053067291")
                )

                val portfolio = PortfolioPerformanceCalculator(assetApi).calculate(hashMapOf("BTC" to btc, "ETH" to eth))

                assertEquals(portfolio.total, 7036.65.toBigDecimal())
                assertEquals(portfolio.bestAsset, "BTC")
                assertEquals(portfolio.bestPerformance, 1.51.toBigDecimal())
                assertEquals(portfolio.worstAsset, "BTC")
                assertEquals(portfolio.worstPerformance, 1.51.toBigDecimal())
            }
        }
    }
})