import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.math.BigDecimal
import kotlin.test.assertEquals

class AssetCsvReaderSpec: Spek({
    describe("when reading assets from csv") {
        it("returns the read assets") {
            val btc = Asset("BTC", BigDecimal("0.12345"), BigDecimal("37870.5058"))
            val eth = Asset("ETH", BigDecimal("4.89532"), BigDecimal("2004.9774"))

            val assetCsvReader = AssetCsvReader()
            val assets = assetCsvReader.readAssets("assets.csv")

            assertEquals(assets, hashMapOf("BTC" to btc, "ETH" to eth))
        }
    }
})