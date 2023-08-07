import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.math.BigDecimal
import kotlin.test.assertContains
import kotlin.test.assertEquals

class AssetAPISpec: Spek({
    describe("when requesting all assets") {
        it("returns the list of assets") {
           runBlocking {
               val mockEngine = MockEngine {
                   respond(
                       content = ByteReadChannel("""{"data":[{"id":"Bitcoin","symbol":"BTC"}]}"""),
                       status = HttpStatusCode.OK,
                       headers = headersOf(HttpHeaders.ContentType, "application/json")
                   )
               }

               val assetApi = AssetAPI(mockEngine)
               val assets = assetApi.getAssets()

               assertContains(assets, AssetData("Bitcoin", "BTC"))
           }
        }
    }

    describe("when requesting asset history") {
        it("returns the asset price") {
            runBlocking {
                val mockEngine = MockEngine {
                    respond(
                        content = ByteReadChannel("""{"data":[{"priceUsd":"56000"}]}"""),
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }

                val assetApi = AssetAPI(mockEngine)
                val bitcoinPrice = assetApi.getAssetHistoryPrice("Bitcoin", "d1", 1617753600000, 7753601000)

                assertEquals(bitcoinPrice, BigDecimal(56000))
            }
        }
    }
})